package finalboss.custom_components.villain.attacks;

import engine.components.CollisionComponent;
import engine.components.Component;
import engine.components.Tag;
import engine.hierarchy.GameObject;
import engine.hierarchy.GameWorld;
import engine.shapes.ShapeEnum;

public class VillainDisappearingStaticAttack extends VillainDisappearingAttack {

    VillainAttack attack;
    GameWorld gw;
    ShapeEnum s;
    public VillainDisappearingStaticAttack(GameObject g, int trigger, int disappear, GameWorld gw, ShapeEnum s, VillainAttack a) {
        super(g, trigger, disappear, gw, a);
        this.tag = Tag.VILL;
        this.trigger = trigger;
        this.disappear = disappear;
        this.current = 0;
        this.gw = gw;
        this.s = s;
        this.flipped = false;
        this.attack = a;
    }

    @Override
    public void flip(){
        CollisionComponent c = new CollisionComponent(this.parent, s, 3, true);
        this.parent.addComponent(c);
        this.parent.callSystems();
        this.flipped = true;
    }




}
