package engine.shapes;

import debugger.collisions.AABShape;
import debugger.collisions.CircleShape;
import debugger.collisions.Interval;
import engine.support.Vec2d;

import java.util.ArrayList;

public class ColCircle implements Shape {

    Vec2d center;
    double radius;
    boolean stat;

    public ColCircle(Vec2d center, double radius, boolean stat) {
        this.center = center;
        this.radius = radius;
        this.stat = stat;
    }


    //construct directly from pos/size
    public ColCircle(Vec2d pos, Vec2d size){
        this.center = new Vec2d(pos.x+size.x/2, pos.y+size.y/2);
        this.radius = (size.x + size.y)/2;

    }

    public Vec2d collides(Shape o) {
            return o.collidesCircle(this);
     }

    public Vec2d collidesAAB(ColAAB s2) {
        Vec2d f = s2.collidesCircle(this);
        return f == null ? null : f.reflect();
    }

    public Vec2d collidesCircle(ColCircle s2) {
        double statMult = 0.5;
        if(stat){
            statMult = 1;
        }
        double rad = this.radius + s2.radius;
        Vec2d dir = s2.center.minus(this.center).normalize();
        double distance = (Math.pow(Math.pow((this.center.x - s2.center.x), 2) + Math.pow((this.center.y - s2.center.y), 2), 0.5));
        if(distance <= rad){
            return dir.smult(-statMult*(rad-distance));
        }
        return null;
    }

    public Vec2d collidesPoint(Vec2d s2) {
        if(Math.pow(Math.pow((this.center.x - s2.x), 2) + Math.pow((this.center.y - s2.y), 2),0.5) <= this.radius){
            return new Vec2d(0,0);
        }
        else {
            return null;
        }
    }

    @Override
    public Vec2d collidesPolygon(ColPolygon poly) {
        Double minMagnitude = Double.MAX_VALUE;
        Vec2d mtv = null;
        ArrayList<Vec2d> edge = new ArrayList<>();
        double circDist = Double.MAX_VALUE;
        Vec2d closest = null;
        for(int i = 0; i < poly.points.length; i++){
            Vec2d e = poly.points[i].minus(poly.points[((i+1)%poly.points.length)]);
            double distance = e.dist(this.center);
            if(distance < circDist) {
                closest = e.minus(this.center);
                circDist = distance;
            }
            edge.add(e);
        }
        edge.add(closest);
        System.out.println(closest);
        if(closest == null) {
            System.out.println("Something has gone wrong here.");
            return null;
        }
        for(Vec2d e : edge) {
            Vec2d axis = e.perpendicular().normalize();
            Double bMin = poly.getPoint(0).dot(axis);
            Double bMax = poly.getPoint(0).dot(axis);
            for(Vec2d bpoint: poly.points){
                Double ap = bpoint.dot(axis);
                bMin = Math.min(bMin, ap);
                bMax = Math.max(bMax, ap);
            }
            Interval aInterval = new Interval(this.center.projectOnto(axis).dot(axis)-this.radius, this.center.projectOnto(axis).dot(axis)+this.radius);
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
