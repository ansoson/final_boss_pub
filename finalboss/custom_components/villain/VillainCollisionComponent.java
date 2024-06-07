package finalboss.custom_components.villain;

import engine.UIKit.StatsBar;
import engine.components.CollisionComponent;
import engine.components.Tag;
import engine.hierarchy.GameObject;
import engine.shapes.ColAAB;
import engine.shapes.ColCircle;
import engine.shapes.ShapeEnum;
import engine.support.Vec2d;
import finalboss.FBChalkboard;
import finalboss.custom_components.main_character.weapons.WeaponComponent;
import finalboss.custom_components.main_character.weapons.WeaponType;
import finalboss.custom_components.main_character.weapons.weapon_implementations.Wand;
import finalboss.dialogue.Conditions;
import finalboss.screens.BattleView;

public class VillainCollisionComponent extends CollisionComponent {

    StatsBar stats;
    int current;
    int cooldown;
    FBChalkboard chalk;

    public VillainCollisionComponent(GameObject g, ShapeEnum s, int layer, boolean stat, StatsBar stats,
            FBChalkboard fbchalk) {
        super(g, s, layer, stat);
        this.stats = stats;
        this.current = 0;
        this.cooldown = 30;
        this.chalk = fbchalk;
    }

    @Override
    public void onCollision(GameObject x2, Vec2d collides) {
        if (x2.getComponent(Tag.STAB) != null) {
            WeaponComponent wc = (WeaponComponent) x2.getComponent(Tag.WEAPON);
            boolean att = false;
            if (wc.getType() == WeaponType.NET && wc.isFinal()) {
                att = true;
            }
            if (current > cooldown || att) {
                stats.updateStatsBar(-wc.damage);
                System.out.println("You did " + wc.damage + " damage.");
                switch (wc.getType()) {
                    case FISHING_POLE:
                        if(wc.isFinal()){
                            VillainAttackComponent vac = (VillainAttackComponent) parent.getComponent(Tag.ENEMY);
                            vac.stun();
                        }
                        break;
                    case NET:
                        break;
                    case NONE:
                        break;
                    case ROPE:
                        break;
                    case SHIELD:
                        break;
                    case WAND:
                        Wand w = (Wand) wc;
                        w.pop();
                        if (!this.chalk.conditions.get(Conditions.WANDHIT)) {
                            this.triggerDialogue(Conditions.WANDHIT);
                        }
                        // else {
                        // chalk.setActiveCond(Conditions.WANDHIT2);
                        // chalk.dialogueOn(true);
                        // }
                        break;
                    case WATERING_CAN:
                        if (!this.chalk.conditions.get(Conditions.WCHIT)) {
                            this.triggerDialogue(Conditions.WCHIT);
                        } else if (!this.chalk.conditions.get(Conditions.WCHIT2)) {
                            this.triggerDialogue(Conditions.WCHIT2);
                        }
                        break;
                    default:
                        break;
                }
                current = 0;
            }
        }
    }

    @Override
    public void initShape() {
        if (s == ShapeEnum.AAB) {
            this.shape = new ColAAB(
                    new Vec2d(parent.getTransform().pos.x + .23 * parent.getTransform().size.x,
                            parent.getTransform().pos.y + .66 * parent.getTransform().size.y),
                    new Vec2d(parent.getTransform().size.x * .33, parent.getTransform().size.y * .33),
                    parent.getTransform().def);
        } else {
            this.shape = new ColCircle(parent.getTransform().pos, parent.getTransform().size);
        }
    }

    @Override
    public void tick(long nanos) {
        current += 1;
    }

    protected void triggerDialogue(Conditions cond) {

        chalk.setActiveCond(cond);
        ((BattleView)this.parent.parent.parent).showUI(false);
    }

}
