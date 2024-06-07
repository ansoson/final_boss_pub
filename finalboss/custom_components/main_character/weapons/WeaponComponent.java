package finalboss.custom_components.main_character.weapons;

import engine.Direction;
import engine.components.*;
import engine.hierarchy.GameObject;
import engine.shapes.ShapeEnum;
import engine.sprite.Resource;
import engine.support.Vec2d;
import finalboss.FBChalkboard;
import finalboss.custom_components.TimedDisappearanceComponent;
import finalboss.custom_components.main_character.PlayerControllerComponent;
import finalboss.custom_components.main_character.weapons.weapon_implementations.Shield;
import finalboss.custom_components.main_character.weapons.weapon_implementations.ShieldCollisionComponent;
import finalboss.dialogue.Conditions;
import finalboss.screens.BattleView;
import finalboss.screens.WeaponStat;
import javafx.scene.image.Image;

public class WeaponComponent extends Component {

    protected WeaponType type;
    protected PlayerControllerComponent pcc;

    public Double damage;
    protected Double range;
    protected Double special;
    protected int cooldown;

    protected int current;

    protected FBChalkboard chalk;
    protected int movementLock;
    protected boolean isFinal;

    public WeaponComponent(GameObject g, PlayerControllerComponent pcc, FBChalkboard chalk) {
        super(g);
        this.tag = Tag.WEAPON;
        this.type = WeaponType.NONE;
        this.pcc = pcc;
        this.cooldown = 30;
        this.current = 0;
        this.chalk = chalk;
        this.movementLock = -1;
        this.isFinal = false;
    }

    public void initVars() {
        this.damage = chalk.getValue(this.type, WeaponStat.DAMAGE);
        this.range = chalk.getValue(this.type, WeaponStat.RANGE);
        this.special = chalk.getValue(this.type, WeaponStat.SPECIAL);
        this.isFinal = chalk.getFinal(this.type);
    }

    public WeaponType getType() {
        return type;
    }

    public void Attack(PlayerControllerComponent pcc, boolean primary) {

        initVars();
        if (current > cooldown) {
            spawnViolence(pcc);
            if (primary) {
                if (pcc.getLastDirect() == Direction.RIGHT) {
                    pcc.c.s.LAC.cycleSwap("BACKRIGHTATTACK1");
                } else {
                    pcc.c.s.LAC.cycleSwap("BACKLEFTATTACK1");
                }
            } else {
                if (pcc.getLastDirect() == Direction.RIGHT) {
                    pcc.c.s.LAC.cycleSwap("FRONTRIGHTATTACK1");
                } else {
                    pcc.c.s.LAC.cycleSwap("FRONTLEFTATTACK1");
                }
            }
            current = 0;
            pcc.movLocked = this.movementLock;
        }
        if (chalk.getActiveCond() == Conditions.CONCERNED) {
            chalk.setActiveCond(Conditions.CONCERNED2);
            ((BattleView) this.parent.parent.parent).showUI(false);
        }
    }

    public void spawnViolence(PlayerControllerComponent pcc) {
        int sprite_size = pcc.getSpriteSize();
        Direction d = pcc.getLastDirect();
        Vec2d pos = parent.getTransform().pos;
        if (d == Direction.LEFT) {
            pos = pos.plus(new Vec2d(-sprite_size, 0));
        } else if (d == Direction.RIGHT) {
            pos = pos.plus(new Vec2d(sprite_size, 0));
        } else {
            System.out.println("Error, please fix."); // TODO: does this ever trigger
        }
        violenceFactory(pos, sprite_size, pcc);
    }

    public void violenceFactory(Vec2d pos, int sprite_size, PlayerControllerComponent pcc) {
        GameObject violence = new GameObject(this.parent.parent, "violence", pos,
                new Vec2d(sprite_size * 0.5, sprite_size * 0.5), this.parent.parent.windowSize, 3.0);
        violence.addComponent(new CollisionComponent(violence, ShapeEnum.AAB, 3, false));
        WeaponComponent wp = new WeaponComponent(violence, pcc, chalk);
        wp.initVars();
        violence.addComponent(wp);
        violence.addComponent(new TimedDisappearanceComponent(violence, 6, this.parent.parent));
        violence.callSystems();
    }

    protected void setDamage(double damage) {
        this.damage = damage;
    }

    @Override
    public void tick(long nanos) {
        current += 1;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setIsFinal(boolean f) {
        this.isFinal = f;
    }

    public boolean canAttack() {
        return current > cooldown;
    }
}
