package finalboss.custom_components.villain.attacks;

import engine.components.CollisionComponent;
import engine.components.Tag;
import engine.hierarchy.GameObject;
import engine.shapes.ShapeEnum;
import engine.support.Vec2d;
import finalboss.gameworlds.LevelGameworld;

import java.util.Random;

public class MagmleCollisionComponent extends CollisionComponent {

    LevelGameworld gw;

    int hp;
    public MagmleCollisionComponent(GameObject g, ShapeEnum s, int layer, boolean stat, int hp) {
        super(g, s, layer, stat);
        this.tag = Tag.COLLISION;
        this.hp = hp;
    }

    public void onCollision(GameObject x2, Vec2d collides){
        Random r = new Random();
        if(x2.getComponent(Tag.STAB) != null){
            hp -= 1;
            if(hp <= 0) {
                gw.throwAway(this.parent);
            }
        }
    }
}
