package finalboss.custom_components.villain.attacks;

import engine.components.CollisionComponent;
import engine.components.Tag;
import engine.hierarchy.GameObject;
import engine.hierarchy.GameWorld;
import engine.shapes.ShapeEnum;
import javafx.scene.paint.Color;

public class VillainDoubleTriggerAttack extends VillainDisappearingAttack{

    int trigger_two;
    boolean flipped_twice;
    public VillainDoubleTriggerAttack(GameObject g, int trigger, int trigger_two, int disappear, GameWorld gw, VillainAttack a) {
        super(g, trigger, disappear, gw, a);
        this.trigger_two = trigger_two;
        this.tag = Tag.VILL;
        this.current =0;
        this.attack = a;
        flipped= false;
        flipped_twice = false;
    }


    public void twoflip(){
        CollisionComponent c = new CollisionComponent(this.parent, ShapeEnum.AAB, 3, true);
        this.parent.addComponent(c);
        this.parent.callSystems();
        this.flipped_twice = true;
    }

    @Override
    public void tick(long nanosSinceLastTick){
        this.current += 1;
        if(current > trigger && !flipped){
            flip();
        }
        if(current > trigger_two && !flipped_twice){
            twoflip();
        }
        if(current > disappear){
            gw.throwAway(parent);
        }
    }
}
