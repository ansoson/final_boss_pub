package debugger.collisions;

import debugger.support.Vec2d;
import debugger.support.interfaces.Week6Reqs;
import debugger.support.shapes.PolygonShapeDefine;

import java.util.ArrayList;

import static com.sun.javafx.util.Utils.clamp;

/**
 * Fill this class in during Week 6. Make sure to also change the week variable in Display.java.
 */
public final class Week6 extends Week6Reqs {

	// AXIS-ALIGNED BOXES

	@Override
	public Vec2d collision(AABShape s1, AABShape s2) {
		if(s1.topLeft.x <= s2.topLeft.x + s2.size.x && s2.topLeft.x <= s1.topLeft.x + s1.size.x &&
				s1.topLeft.y <= s2.topLeft.y + s2.size.y && s2.topLeft.y <= s1.topLeft.y + s1.size.y) {
			double moveUp = s1.topLeft.y + s1.size.y - s2.topLeft.y; //this.MaxY - that.minY
			double moveDown = s1.topLeft.y - (s2.topLeft.y + s2.size.y); //this.MinY - that.maxY
			double moveLeft = s1.topLeft.x + s1.size.x - s2.topLeft.x; //this.maxX - that.minX
			double moveRight = s1.topLeft.x - (s2.topLeft.x + s2.size.x); //this.minX - that.minX
			return Vec2d.min(new Vec2d(-0.5* moveRight, 0), Vec2d.min(new Vec2d(-0.5* moveLeft, 0), Vec2d.min(new Vec2d(0, -0.5*moveUp), new Vec2d(0, -0.5*moveDown))));
		}
		return null;
	}

	@Override
	public Vec2d collision(AABShape s1, CircleShape s2) {
		Vec2d closest = new Vec2d(clamp(s1.topLeft.x, s2.center.x, s1.topLeft.x+s1.size.x), clamp(s1.topLeft.y,s2.center.y,  s1.topLeft.y+s1.size.y));
		Vec2d colliding = collision(s2, closest);
		if(colliding != null) {
			boolean containsCenter = (s1.topLeft.x <= s2.center.x && s2.center.x <= s1.topLeft.x+s1.size.x && s1.topLeft.y <= s2.center.y && s2.center.y <= s1.topLeft.y+s1.size.y);
			if (containsCenter) {
				double moveUp = Math.abs(s1.topLeft.y + s1.size.y - s2.center.y); //this.MaxY - that.minY
				double moveDown = Math.abs(s1.topLeft.y - s2.center.y); //this.MinY - that.maxY
				double moveLeft = Math.abs(s1.topLeft.x + s1.size.x - s2.center.x); //this.maxX - that.minX
				double moveRight = Math.abs(s1.topLeft.x - s2.center.x); //this.minX - that.minX
				double min = Math.min(moveLeft, Math.min(moveRight, Math.min(moveDown, moveUp)));
				if(min == moveUp) {return new Vec2d(0, -0.5*(moveUp + s2.radius));}
				else if(min == moveDown) {return new Vec2d(0, 0.5*(moveDown+s2.radius));}
				else if(min == moveRight) {return new Vec2d(.5*(moveRight + s2.radius), 0);}
				else return new Vec2d(-0.5* (moveLeft + s2.radius), 0);
			}
			else {
				Vec2d clampedCenter = closest; //Clamp circle center to the AAB (like what we do for Circle/AAB collisions)
				double distance = s2.radius - (Math.pow(Math.pow((clampedCenter.x - s2.center.x), 2) + Math.pow((clampedCenter.y - s2.center.y), 2), 0.5));//Length of MTV is radius - distance(center, clampedCenter)
				Vec2d dir = s2.center.minus(clampedCenter).normalize(); //MTV is parallel to the line connecting the two points
				return dir.smult(-0.5*(distance));
			}
		}
		return null;
	}

	@Override
	public Vec2d collision(AABShape s1, Vec2d s2) {
		if(s1.topLeft.x <= s2.x && s2.x <= s1.topLeft.x+ s1.size.x && s1.topLeft.y <= s2.y && s2.y <= s1.topLeft.y+s1.size.y){
			return new Vec2d(0, 0);
		}
		else{
			return null;
		}
	}

	@Override
	public Vec2d collision(AABShape s1, PolygonShape s2) {
		PolygonShape ss = new PolygonShapeDefine(s1.topLeft, new Vec2d(s1.topLeft.x, s1.topLeft.y+s1.size.y), new Vec2d(s1.topLeft.x+s1.size.x, s1.topLeft.y+s1.size.y),new Vec2d(s1.topLeft.x+s1.size.x, s1.topLeft.y));
		return collision(ss, s2);
	}
	// CIRCLES

	@Override
	public Vec2d collision(CircleShape s1, AABShape s2) {
		Vec2d f = collision(s2, s1);
		return f == null ? null : f.reflect();
	}

	@Override
	public Vec2d collision(CircleShape s1, CircleShape s2) {
		double rad = s1.radius + s2.radius;
		Vec2d dir = s2.center.minus(s1.center).normalize();
		double distance = (Math.pow(Math.pow((s1.center.x - s2.center.x), 2) + Math.pow((s1.center.y - s2.center.y), 2), 0.5));
		if(distance <= rad){
			return dir.smult(-0.5*(rad-distance));
		}
		return null;
	}

	@Override
	public Vec2d collision(CircleShape s1, Vec2d s2) {
		if(Math.pow(Math.pow((s1.center.x - s2.x), 2) + Math.pow((s1.center.y - s2.y), 2),0.5) <= s1.radius){
			return new Vec2d(0,0);
		}
		else {
			return null;
		}
	}

	@Override
	public Vec2d collision(CircleShape s1, PolygonShape s2) {
		Double minMagnitude = Double.MAX_VALUE;
		Vec2d mtv = null;
		ArrayList<Vec2d> edge = new ArrayList<>();
		double circDist = Double.MAX_VALUE;
		Vec2d closest = null;
		for(int i = 0; i < s2.points.length; i++){
			Vec2d e = s2.points[i].minus(s2.points[((i+1)%s2.points.length)]);
			double distance = e.dist(s1.center);
			if(distance < circDist) {
				closest = e.minus(s1.center);
				circDist = distance;
			}
			edge.add(e);
		}
		edge.add(closest);
		if(closest == null) {
			System.out.println("Something has gone wrong here.");
			return null;
		}
		for(Vec2d e : edge) {
			Vec2d axis = e.perpendicular().normalize();
			Double bMin = s2.getPoint(0).dot(axis);
			Double bMax = s2.getPoint(0).dot(axis);
			for(Vec2d bpoint: s2.points){
				Double ap = bpoint.dot(axis);
				bMin = Math.min(bMin, ap);
				bMax = Math.max(bMax, ap);
			}
			Interval aInterval = new Interval(s1.center.projectOnto(axis).dot(axis)-s1.radius, s1.center.projectOnto(axis).dot(axis)+s1.radius); //what is this
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

	// POLYGONS

	@Override
	public Vec2d collision(PolygonShape s1, AABShape s2) {
		Vec2d f = collision(s2, s1);
		return f == null ? null : f.reflect();
	}

	@Override
	public Vec2d collision(PolygonShape s1, CircleShape s2) {
		Vec2d f = collision(s2, s1);
		return f == null ? null : f.reflect();
	}

	@Override
	public Vec2d collision(PolygonShape s1, Vec2d s2) {
		for(int i = 0; i < s1.points.length; i++){
			Vec2d edge = s1.points[i].minus(s1.points[((i+1)%s1.points.length)]);
			Vec2d p = s1.points[i].minus(s2);
			double d = edge.cross(p);
			if(d > 0){
				return null;
			}
		}
		return new Vec2d(0,0);
	}


	Double intervalMTV(Interval a, Interval b) {
		Double aRight = b.max - a.min;
		Double aLeft = a.max - b.min;
		if (aLeft < 0 || aRight < 0) {
			return null;
		}
		if (aRight < aLeft) {
			return aRight;
		} else {
			return -aLeft;
		}
	}

	@Override
	public Vec2d collision(PolygonShape a, PolygonShape b) {
		Double minMagnitude = Double.MAX_VALUE;
		Vec2d mtv = null;
		ArrayList<Vec2d> edge = new ArrayList<>();
		for(int i = 0; i < a.points.length; i++){
			edge.add(a.points[((i+1)%a.points.length)].minus(a.points[i]));
		}
		for(int i = 0; i < b.points.length; i++){
			edge.add(b.points[((i+1)%b.points.length)].minus(b.points[i]));
		}

		for(Vec2d e : edge) {
			Vec2d axis = e.perpendicular().normalize();
			Double aMin = a.getPoint(0).dot(axis);
			Double aMax = a.getPoint(0).dot(axis);
			Double bMin = b.getPoint(0).dot(axis);
			Double bMax = b.getPoint(0).dot(axis);
			for(Vec2d aPoint : a.points){
				Double ap = aPoint.dot(axis);
				aMin = Math.min(aMin, ap);
				aMax = Math.max(aMax, ap);
			}
			for(Vec2d bpoint: b.points){
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

	// RAYCASTING
	
	@Override
	public float raycast(AABShape s1, Ray s2) {
		PolygonShape ss = new PolygonShapeDefine(s1.topLeft, new Vec2d(s1.topLeft.x, s1.topLeft.y+s1.size.y), new Vec2d(s1.topLeft.x+s1.size.x, s1.topLeft.y+s1.size.y),new Vec2d(s1.topLeft.x+s1.size.x, s1.topLeft.y));
		return raycast(ss, s2);
	}
	
	@Override
	public float raycast(CircleShape s1, Ray s2) {
		Vec2d v = s1.center.projectOntoLine(s2.src, s2.src.plus(s2.dir));
		if (collision(s1, s2.src) == null) { //are we outside/inside?
			// if projection positive and projection point in circle
			if (s1.center.minus(s2.src).dot(s2.dir) > 0) {
				if (collision(s1, v) != null) {
					double L = v.minus(s2.src).mag();
					double x = v.minus(s1.center).mag();
					return (float) (L - Math.pow(Math.pow(s1.radius, 2) - Math.pow(x, 2), 0.5));
				}
			}
		} else {
			// source inside
			double L = v.minus(s2.src).mag();
			double r = s1.radius;
			double x = v.minus(s1.center).mag();
			return (float) (L + Math.sqrt((r * r) - (x * x)));
		}
		return -1;
	}

	@Override
	public float raycast(PolygonShape s1, Ray s2) {
		float t = Float.POSITIVE_INFINITY;
		boolean found = false;
		Vec2d p = s2.src;
		Vec2d d = s2.dir;
		for (int i = 0; i < s1.getNumPoints(); i++) {
			//gave up and just did these because i was having glitches... now theres NO WAY it can be wrong
			Vec2d a = s1.getPoint(i);
			Vec2d b = s1.getPoint((i+1) % s1.getNumPoints());
			Vec2d m = b.minus(a).normalize();
			Vec2d n = m.perpendicular().normalize();
			//we straddling
			if (a.minus(p).cross(d) * b.minus(p).cross(d) <= 0) {
				float newt = (float) ((b.minus(p).dot(n)) / d.dot(n));
				found = true;
				if (newt < t) {
					t = newt;
				}
			}
		}
		if (found) {
			return t;
		} else {
			return -1;
		}
	}

}
