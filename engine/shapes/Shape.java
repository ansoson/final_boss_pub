package engine.shapes;

import debugger.collisions.Interval;
import engine.support.Vec2d;

public interface Shape {

        Vec2d collides(Shape o);
        Vec2d collidesCircle(ColCircle c);
        Vec2d collidesAAB(ColAAB aab);

        Vec2d collidesPoint(Vec2d point);

        Vec2d collidesPolygon(ColPolygon poly);

        default Double intervalMTV(Interval a, Interval b) {
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
}


