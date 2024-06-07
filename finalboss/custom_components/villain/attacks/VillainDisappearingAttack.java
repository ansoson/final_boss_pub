package finalboss.custom_components.villain.attacks;

import engine.components.CollisionComponent;
import engine.components.Component;
import engine.components.Tag;
import engine.hierarchy.GameObject;
import engine.hierarchy.GameWorld;
import engine.shapes.ShapeEnum;

public class VillainDisappearingAttack extends Component {

    public VillainAttack attack;
    int disappear;
    int trigger;
    GameWorld gw;
    int current;

    boolean flipped;
    public VillainDisappearingAttack(GameObject g, int trigger, int disappear, GameWorld gw, VillainAttack a) {
        super(g);
        this.tag = Tag.VILL;
        this.trigger = trigger;
        this.disappear = disappear;
        this.current = 0;
        this.gw = gw;
        this.flipped = false;
        this.attack = a;
    }

    public void flip(){}

    public void tick(long nanosSinceLastTick){
        this.current += 1;
        if(current > trigger && !flipped){
            flip();
        }
        if(current > disappear){
            gw.throwAway(parent);
        }
    }



}
