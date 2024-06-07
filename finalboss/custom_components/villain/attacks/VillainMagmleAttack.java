package finalboss.custom_components.villain.attacks;

import engine.components.CollisionComponent;
import engine.components.Component;
import engine.components.Tag;
import engine.components.TimedDisappearanceComponent;
import engine.hierarchy.GameObject;
import engine.hierarchy.GameWorld;
import engine.shapes.ShapeEnum;

public class VillainMagmleAttack extends VillainDisappearingAttack {

    VillainAttack attack;
    public VillainMagmleAttack(GameObject g, int dis, GameWorld gw) {
        super(g, 0, dis, gw, VillainAttack.MAGMLE);
        tag = Tag.VILL;
        this.attack = VillainAttack.MAGMLE;

    }

    @Override
    public void tick(long nanosSinceLastTick){
        this.current += 1;
        if(current > disappear){
            gw.throwAway(parent);
        }

    }


}
