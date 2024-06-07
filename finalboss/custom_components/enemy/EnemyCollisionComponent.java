package finalboss.custom_components.enemy;

import engine.components.CollisionComponent;
import engine.components.SpriteComponent;
import engine.hierarchy.GameObject;
import engine.physics.PhysicsComponent;
import engine.shapes.ShapeEnum;
import engine.sprite.SpriteResource;
import engine.support.Vec2d;
import engine.components.Tag;
import finalboss.custom_components.pickup.TeaComponent;
import finalboss.gameworlds.LevelGameworld;
import finalboss.gameworlds.Overworld;

import java.util.Random;

public class EnemyCollisionComponent extends CollisionComponent {

    LevelGameworld gw;
    SpriteResource resource;
    public EnemyCollisionComponent(GameObject g, LevelGameworld parent, SpriteResource r, ShapeEnum s, int layer) {
        super(g, s, layer, false);
        this.tag = Tag.COLLISION;
        this.gw = parent;
        this.resource = r;
    }

    public void onCollision(GameObject x2, Vec2d collides){
        Vec2d trans = parent.getTransform().pos;
        trans = trans.minus(collides);
        parent.setTransform(trans);
        Random r = new Random();
        if(x2.getComponent(Tag.STAB) != null){
            gw.throwAway(this.parent);
            if(r.nextBoolean()){
                GameObject e = new GameObject(this.parent.parent, "bean", parent.getTransform().pos, parent.getTransform().size, parent.getTransform().def, 1.0);
                //e.addComponent(new TeaComponent(e, (Overworld) this.parent.parent));
                e.addComponent(new SpriteComponent(e, resource, 8, 0, 0, 32, 32));
                e.addComponent(new PhysicsComponent(e, 1, false));
                e.callSystems();
            }
        }
    }
}
