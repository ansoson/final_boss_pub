package finalboss.custom_components;

import engine.components.CollisionComponent;
import engine.hierarchy.GameObject;
import engine.shapes.ShapeEnum;
import engine.support.Vec2d;
import engine.components.Tag;
import finalboss.gameworlds.LevelGameworld;
import finalboss.gameworlds.Overworld;

public class LevelEndComponent extends CollisionComponent {

    boolean used;
    LevelGameworld gw;
    public LevelEndComponent(GameObject g, ShapeEnum s, int layer, LevelGameworld gw){
        super(g, s, layer, false);
        this.tag = Tag.COLLISION;
        this.gw = gw;
        this.used = false;
    }

    @Override
    public void onCollision(GameObject x2, Vec2d collides) {
        if(x2.getComponent(Tag.INPUT) != null && !used){
            gw.advance = true; //no animation for now
            used = true;
        }
    }
}
