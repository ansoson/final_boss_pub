package engine.shapes;

import debugger.collisions.PolygonShape;
import debugger.support.shapes.PolygonShapeDefine;
import engine.support.Vec2d;

import static com.sun.javafx.util.Utils.clamp;

public class ColAAB implements Shape {

    Vec2d pos;
    Vec2d size;
    Vec2d def;

    boolean stat;

    public ColAAB(Vec2d pos, Vec2d size, Vec2d def) {
        this.pos = pos;
        this.size = size;
        this.def = def;
        this.stat = false;
    }

    public ColAAB(Vec2d pos, Vec2d size, Vec2d def, boolean stat) {
        this.pos = pos;
        this.size = size;
        this.def = def;
        this.stat = stat;
    }

    public Vec2d collides(Shape o) {
        return o.collidesAAB(this);
    }


    public Vec2d collidesCircle(ColCircle s2) {
		Vec2d closest = new Vec2d(clamp(this.pos.x, s2.center.x, this.pos.x+this.size.x), clamp(this.pos.y,s2.center.y,  this.pos.y+this.size.y));
		Vec2d colliding = s2.collidesPoint(closest);
        double statMultiplier = 0.5;
        if(stat){
            statMultiplier = 1;
        }
		if(colliding != null) {
			boolean containsCenter = (this.pos.x <= s2.center.x && s2.center.x <= this.pos.x+this.size.x && this.pos.y <= s2.center.y && s2.center.y <= this.pos.y+this.size.y);
			if (containsCenter) {
				double moveUp = Math.abs(this.pos.y + this.size.y - s2.center.y); //this.MaxY - that.minY
				double moveDown = Math.abs(this.pos.y - s2.center.y); //this.MinY - that.maxY
				double moveLeft = Math.abs(this.pos.x + this.size.x - s2.center.x); //this.maxX - that.minX
				double moveRight = Math.abs(this.pos.x - s2.center.x); //this.minX - that.minX
				double min = Math.min(moveLeft, Math.min(moveRight, Math.min(moveDown, moveUp)));
				if(min == moveUp) {return new Vec2d(0, -statMultiplier*(moveUp + s2.radius));}
				else if(min == moveDown) {return new Vec2d(0, statMultiplier*(moveDown+s2.radius));}
				else if(min == moveRight) {return new Vec2d(statMultiplier*(moveRight + s2.radius), 0);}
				else return new Vec2d(-statMultiplier* (moveLeft + s2.radius), 0);
			}
			else {
                Vec2d clampedCenter = closest; //Clamp circle center to the AAB (like what we do for Circle/AAB collisions)
				double distance = s2.radius - (Math.pow(Math.pow((clampedCenter.x - s2.center.x), 2) + Math.pow((clampedCenter.y - s2.center.y), 2), 0.5));//Length of MTV is radius - distance(center, clampedCenter)
				Vec2d dir = s2.center.minus(clampedCenter).normalize(); //MTV is parallel to the line connecting the two points
				return dir.smult(-statMultiplier*(distance));
			}
		}
		return null;
	}


    public Vec2d collidesAAB(ColAAB s2) {
        double statMultiplier = 0.5;
        if(stat){
            statMultiplier = 1;
        }
		 if(this.pos.x <= s2.pos.x + s2.size.x && s2.pos.x <= this.pos.x + this.size.x &&
				this.pos.y <= s2.pos.y + s2.size.y && s2.pos.y <= this.pos.y + this.size.y) {
			 double moveUp = this.pos.y + this.size.y - s2.pos.y; //this.MaxY - that.minY
			 double moveDown = this.pos.y - (s2.pos.y + s2.size.y); //this.MinY - that.maxY
			 double moveLeft = this.pos.x + this.size.x - s2.pos.x; //this.maxX - that.minX
			 double moveRight = this.pos.x - (s2.pos.x + s2.size.x); //this.minX - that.minX
			 return Vec2d.min(new Vec2d(-statMultiplier* moveRight, 0), Vec2d.min(new Vec2d(-statMultiplier* moveLeft, 0), Vec2d.min(new Vec2d(0, -statMultiplier*moveUp), new Vec2d(0, -statMultiplier*moveDown))));
		 }
		return null;
    }

    public Vec2d collidesPoint(Vec2d s2) {
        if (this.pos.x <= s2.x && s2.x <= this.pos.x + this.size.x && this.pos.y <= s2.y && s2.y <= this.pos.y + this.size.y) {
            return new Vec2d(0, 0);
        } else {
            return null;
        }
    }

    @Override
    public Vec2d collidesPolygon(ColPolygon poly) {
        ColPolygon ss = new ColPolygon(this.pos, new Vec2d(this.pos.x, this.pos.y+this.size.y), new Vec2d(this.pos.x+this.size.x, this.pos.y+this.size.y),new Vec2d(this.pos.x+this.size.x, this.pos.y));
        return poly.collidesPolygon(ss);
    }
}

