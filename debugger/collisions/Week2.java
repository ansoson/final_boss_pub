package debugger.collisions;

import debugger.support.Vec2d;
import debugger.support.interfaces.Week2Reqs;

import static com.sun.javafx.util.Utils.clamp;

/**
 * Fill this class in during Week 2.
 */
public final class Week2 extends Week2Reqs {

	// AXIS-ALIGNED BOXES
	
	@Override
	public boolean isColliding(AABShape s1, AABShape s2) {
		return (s1.topLeft.x <= s2.topLeft.x + s2.size.x && s2.topLeft.x <= s1.topLeft.x + s1.size.x) &&
				(s1.topLeft.y <= s2.topLeft.y + s2.size.y && s2.topLeft.y <= s1.topLeft.y + s1.size.y);
	}

	@Override
	public boolean isColliding(AABShape s1, CircleShape s2) {
		Vec2d closest = new Vec2d(clamp(s1.topLeft.x, s2.center.x, s1.topLeft.x+s1.size.x), clamp(s1.topLeft.y,s2.center.y,  s1.topLeft.y+s1.size.y));
		return isColliding(s2, closest);
	}

	@Override
	public boolean isColliding(AABShape s1, Vec2d s2) {
		return s1.topLeft.x <= s2.x && s2.x <= s1.topLeft.x+ s1.size.x && s1.topLeft.y <= s2.y && s2.y <= s1.topLeft.y+s1.size.y;
	}

	// CIRCLES
	
	@Override
	public boolean isColliding(CircleShape s1, AABShape s2) {
		return isColliding(s2, s1);
	}

	@Override
	public boolean isColliding(CircleShape s1, CircleShape s2) {
        return Math.pow(Math.pow((s1.center.x - s2.center.x), 2) + Math.pow((s1.center.y - s2.center.y), 2), 0.5) <= s1.radius + s2.radius;
    }

	@Override
	public boolean isColliding(CircleShape s1, Vec2d s2) {
		return Math.pow(Math.pow((s1.center.x - s2.x), 2) + Math.pow((s1.center.y - s2.y), 2),0.5) <= s1.radius;
	}

	
}
