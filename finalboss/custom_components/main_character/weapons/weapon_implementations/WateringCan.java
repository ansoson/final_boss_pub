package finalboss.custom_components.main_character.weapons.weapon_implementations;

import engine.Direction;
import engine.components.AnimationComponent;
import engine.components.CollisionComponent;
import engine.components.SpriteComponent;
import engine.components.Tag;
import engine.decisiontree_AI.Condition;
import engine.hierarchy.GameObject;
import engine.shapes.ShapeEnum;
import engine.sprite.Resource;
import engine.support.Vec2d;
import finalboss.FBChalkboard;
import finalboss.custom_components.TimedDisappearanceComponent;
import finalboss.custom_components.main_character.PlayerControllerComponent;
import finalboss.custom_components.main_character.weapons.WeaponComponent;
import finalboss.custom_components.main_character.weapons.WeaponType;
import finalboss.dialogue.Conditions;
import finalboss.screens.CharacterView;
import finalboss.screens.WeaponStat;
import javafx.scene.image.Image;

public class WateringCan extends WeaponComponent {

    double sspec;
    public WateringCan(GameObject g, PlayerControllerComponent pcc, FBChalkboard chalk) {
        super(g, pcc, chalk);
        this.type = WeaponType.WATERING_CAN;
        this.tag = Tag.WEAPON;
        this.movementLock = 25;
        this.isFinal = false;
        initVars();
    }


    @Override
    public void initVars(){
        this.damage = chalk.getValue(this.type, WeaponStat.DAMAGE);
        this.range = chalk.getValue(this.type, WeaponStat.RANGE);
        if(this.special == null || chalk.getValue(this.type, WeaponStat.SPECIAL) != sspec) {
            this.special = chalk.getValue(this.type, WeaponStat.SPECIAL);
            this.sspec = this.special;
        }
        this.isFinal = chalk.getFinal(this.type);
    }
    public void spawnViolence(PlayerControllerComponent pcc){
        int sprite_size = pcc.getSpriteSize();
        Direction d = pcc.getLastDirect();
        Vec2d pos = parent.getTransform().pos;
        if(d == Direction.LEFT){
            pos = pos.plus(new Vec2d(-sprite_size*range, 0));

        }
        else if(d == Direction.RIGHT){
            pos = pos.plus(new Vec2d(sprite_size, 0));
        }
        else{
            System.out.println("Error, please fix."); //TODO: does this ever trigger
        }
        violenceFactory(pos, sprite_size, pcc);
    }

    public void violenceFactory(Vec2d pos, int sprite_size, PlayerControllerComponent pcc) {
        Resource<Image> r = pcc.r;
        GameObject violence = new GameObject(this.parent.parent, "violence", pos, new Vec2d(sprite_size*range, sprite_size*range), this.parent.parent.windowSize, 3.0);
        violence.addComponent(new CollisionComponent(violence, ShapeEnum.AAB, 3, false));
        SpriteComponent s = new SpriteComponent(violence, r, 15, 0, 0, 32, 32);
        AnimationComponent ss = new AnimationComponent(violence, s, 3, 2);
        violence.addComponent(s);
        violence.addComponent(ss);
        WateringCan fp = new WateringCan(violence, pcc, chalk);
        violence.addComponent(fp);
        violence.addComponent(new WateringCanMoveComponent(violence, pcc.getLastDirect(), this.special));
        violence.addComponent(new TimedDisappearanceComponent(violence, 90, this.parent.parent));
        violence.callSystems();
        if(!isFinal) {
            this.special -= 1;
            if (this.special <= 0.0) {
                if (pcc.getRighthand().getType() == WeaponType.WATERING_CAN) {
                    pcc.setRighthand(new WeaponComponent(pcc.parent, pcc, chalk));
                }
                if (pcc.getLefthand().getType() == WeaponType.WATERING_CAN) {
                    pcc.setLefthand(new WeaponComponent(pcc.parent, pcc, chalk));
                }
                CharacterView cv = (CharacterView) parent.parent.parent;
                cv.postWeapons(pcc.getLefthand(), pcc.getRighthand());
            }
        }
    }


}
