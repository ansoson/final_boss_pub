package engine.shapes;

import debugger.collisions.Interval;
import debugger.collisions.PolygonShape;
import debugger.support.Display;
import engine.support.Vec2d;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ColPolygon implements Shape {

    Vec2d min;
    Vec2d max;

    protected Vec2d[] points;


    public ColPolygon(Vec2d... points) {
            this.points = points;
            double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE, maxX = 0, maxY = 0;
            for(Vec2d v : points) {
                minX = Double.min(minX, v.x);
                minY = Double.min(minY, v.y);
                maxX = Double.max(maxX, v.x);
                maxY = Double.max(maxY, v.y);
            }
            min = new Vec2d(minX, minY);
            max = new Vec2d(maxX, maxY);
        }

    public ColPolygon(ArrayList<Vec2d> points) {
        this.points = points.toArray(new Vec2d[0]);
        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE, maxX = 0, maxY = 0;
        for(Vec2d v : points) {
            minX = Double.min(minX, v.x);
            minY = Double.min(minY, v.y);
            maxX = Double.max(maxX, v.x);
            maxY = Double.max(maxY, v.y);
        }
        min = new Vec2d(minX, minY);
        max = new Vec2d(maxX, maxY);
    }


    public int getNumPoints() {
        return points.length;
    }

    public Vec2d getPoint(int i) {
        return points[i];
    }

    @Override
    public Vec2d collides(Shape o) {
        return o.collidesPolygon(this);
    }

    @Override
    public Vec2d collidesCircle(ColCircle c) {
        Vec2d f = c.collidesPolygon(this);
        return f == null ? null : f.reflect();
    }

    @Override
    public Vec2d collidesAAB(ColAAB aab) {
        Vec2d f = aab.collidesPolygon(this);
        return f == null ? null : f.reflect();
    }

    @Override
    public Vec2d collidesPoint(Vec2d point) {
        for(int i = 0; i < this.points.length; i++){
            Vec2d edge = this.points[i].minus(this.points[((i+1)%this.points.length)]);
            Vec2d p = this.points[i].minus(point);
            double d = edge.cross(p);
            if(d > 0){
                return null;
            }
        }
        return new Vec2d(0,0);
    }

    @Override
    public Vec2d collidesPolygon(ColPolygon poly) {
        Double minMagnitude = Double.MAX_VALUE;
        Vec2d mtv = null;
        ArrayList<Vec2d> edge = new ArrayList<>();
        for(int i = 0; i < this.points.length; i++){
            edge.add(this.points[((i+1)%this.points.length)].minus(this.points[i]));
        }
        for(int i = 0; i < poly.points.length; i++){
            edge.add(poly.points[((i+1)%poly.points.length)].minus(poly.points[i]));
        }

        for(Vec2d e : edge) {
            Vec2d axis = e.perpendicular().normalize();
            Double aMin = this.getPoint(0).dot(axis);
            Double aMax = this.getPoint(0).dot(axis);
            Double bMin = poly.getPoint(0).dot(axis);
            Double bMax = poly.getPoint(0).dot(axis);
            for(Vec2d aPoint : this.points){
                Double ap = aPoint.dot(axis);
                aMin = Math.min(aMin, ap);
                aMax = Math.max(aMax, ap);
            }
            for(Vec2d bpoint: poly.points){
                Double ap = bpoint.dot(axis);
                bMin = Math.min(bMin, ap);
                bMax = Math.max(bMax, ap);
            }
            Interval aInterval = new Interval(aMin, aMax);
            Interval bInterval = new Interval(bMin, bMax);
            Double mtv1d = intervalMTV(aInterval, bInterval);
            if (mtv1d == null) {
                return null;
            }
            if (Math.abs(mtv1d) < minMagnitude) {
                minMagnitude = Math.abs(mtv1d);
                mtv = axis.smult(mtv1d);
            }
        }
        return mtv;
    }
}
