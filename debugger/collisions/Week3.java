package debugger.collisions;

import debugger.support.Vec2d;
import debugger.support.interfaces.Week3Reqs;

import static com.sun.javafx.util.Utils.clamp;

/**
 * Fill this class in during Week 3. Make sure to also change the week variable in Display.java.
 */
public final class Week3 extends Week3Reqs {

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
}
