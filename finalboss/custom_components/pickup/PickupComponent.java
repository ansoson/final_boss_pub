package finalboss.custom_components.pickup;

import engine.components.CollisionComponent;
import engine.hierarchy.GameObject;
import engine.support.Vec2d;
import engine.components.Tag;
import finalboss.gameworlds.Overworld;
import engine.shapes.ShapeEnum;

import java.lang.reflect.InvocationTargetException;

public class PickupComponent extends CollisionComponent {

    int respawnTimer;
    int threshhold;
    boolean pu;
    public PickupComponent(GameObject g) {
        super(g, ShapeEnum.AAB, 1, false);
        this.tag = Tag.COLLISION;
        this.pu = true;
        this.threshhold = 45;
        this.respawnTimer = 0;
    }

    @Override
    public void tick(long n){
        this.respawnTimer += 1;
        if(!pu && respawnTimer > threshhold){
            pu = true;
        }
    }

    public void pickup() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {}

    public void onCollision(GameObject x2, Vec2d collides) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if(pu) {
            if (x2.getComponent(Tag.INPUT) != null) {
                pickup();
            }
            pu = false;
            respawnTimer = 0;
        }
    }
}
