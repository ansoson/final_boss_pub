package finalboss.custom_components.villain.attacks;

import engine.components.CollisionComponent;
import engine.components.Tag;
import engine.hierarchy.GameObject;
import engine.hierarchy.GameWorld;
import engine.shapes.ShapeEnum;

public class VillainDisappearingMovingAttack extends VillainDisappearingAttack {
    VillainAttack attack;

    ShapeEnum s;
    public VillainDisappearingMovingAttack(GameObject g, int trigger, int disappear, GameWorld gw, ShapeEnum s, VillainAttack a) {
        super(g, trigger, disappear, gw, a);
        this.tag = Tag.VILL;
        this.attack = a;
        this.trigger = trigger;
        this.disappear = disappear;
        this.current = 0;
        this.gw = gw;
        this.s = s;
        this.flipped = false;
    }


    @Override
    public void flip(){
        CollisionComponent c = new CollisionComponent(this.parent, s, 4, false);
        this.parent.addComponent(c);
        this.parent.callSystems();
        this.flipped = true;
    }
}
