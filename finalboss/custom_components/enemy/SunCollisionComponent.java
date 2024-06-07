package finalboss.custom_components.enemy;

import engine.components.SpriteComponent;
import engine.hierarchy.GameObject;
import engine.physics.PhysicsComponent;
import engine.shapes.ShapeEnum;
import engine.sprite.SpriteResource;
import engine.support.Vec2d;
import engine.components.Tag;
import finalboss.custom_components.pickup.GoldComponent;
import finalboss.gameworlds.LevelGameworld;
import finalboss.gameworlds.Overworld;

import java.util.Random;

public class SunCollisionComponent extends EnemyCollisionComponent{

    SunMoveComponent ss;
    public SunCollisionComponent(GameObject g, LevelGameworld parent, SpriteResource r, ShapeEnum s, int layer, SunMoveComponent ss) {
        super(g, parent, r, s, layer);
        this.tag = Tag.COLLISION;
        this.ss = ss;
    }

    public void onCollision(GameObject x2, Vec2d collides){
        if(x2.getComponent(Tag.STAB) != null){
            Random r = new Random();
            gw.throwAway(this.parent);
            if(r.nextBoolean()){
                //GameObject e = new GameObject(this.parent.parent, "sun", parent.getTransform().pos, parent.getTransform().size, parent.getTransform().def, 1);
                //e.addComponent(new GoldComponent(e, (Overworld) this.parent.parent));
                //e.addComponent(new SpriteComponent(e, resource, 9, 0, 0, 32, 32));
                //e.addComponent(new PhysicsComponent(e, 1, false));
                //e.callSystems();
            }
        } else{
            ss.reverse();
        }
    }
}
