package finalboss.custom_components.main_character.weapons.weapon_implementations;

import engine.Direction;
import engine.components.AnimationComponent;
import engine.components.CollisionComponent;
import engine.components.SpriteComponent;
import engine.components.Tag;
import engine.hierarchy.GameObject;
import engine.shapes.ShapeEnum;
import engine.sprite.Resource;
import engine.support.Vec2d;
import finalboss.FBChalkboard;
import finalboss.custom_components.TimedDisappearanceComponent;
import finalboss.custom_components.main_character.PlayerControllerComponent;
import finalboss.custom_components.main_character.weapons.WeaponComponent;
import finalboss.custom_components.main_character.weapons.WeaponType;
import finalboss.screens.WeaponStat;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Rope extends WeaponComponent {

    public Rope(GameObject g, PlayerControllerComponent pcc, FBChalkboard chalk) {
        super(g, pcc, chalk);
        this.type = WeaponType.ROPE;
        this.cooldown = 20;
        this.current = 0;
        this.tag = Tag.WEAPON;
        this.movementLock = 10;
        this.isFinal = false;
        initVars();
    }

    @Override
    public void initVars(){
        this.damage = chalk.getValue(this.type, WeaponStat.DAMAGE);
        this.range = chalk.getValue(this.type, WeaponStat.RANGE);
        this.special = chalk.getValue(this.type, WeaponStat.SPECIAL);
        this.cooldown = this.special.intValue();
        this.isFinal = chalk.getFinal(this.type);
    }


    public void spawnViolence(PlayerControllerComponent pcc){
        int sprite_size = pcc.getSpriteSize();
        Direction d = pcc.getLastDirect();
        Vec2d pos = parent.getTransform().pos;
        if(d == Direction.LEFT){
            pos = pos.plus(new Vec2d(-sprite_size*3*range, 0));

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
        GameObject violence = new GameObject(this.parent.parent, "rope", pos, new Vec2d(3*sprite_size*range, 2*sprite_size), this.parent.parent.windowSize, 3.0);
        violence.addComponent(new RopeCollisionComponent(violence, ShapeEnum.AAB, 3, false, pcc.getLastDirect(), range, isFinal));
        SpriteComponent s;
        if(pcc.getLastDirect() == Direction.LEFT) {
            s = new RopeSpriteComponent(violence, r, 11, 0, 1, 3*32, 2*32);
        } else{
            s = new RopeSpriteComponent(violence, r, 11, 0, 0, 3*32, 2*32);
        }
        AnimationComponent ss = new AnimationComponent(violence, s, 4, 2);
        violence.addComponent(s);
        violence.addComponent(ss);
        Rope fp = new Rope(violence, pcc, chalk);
        violence.addComponent(fp);
        violence.addComponent(new TimedDisappearanceComponent(violence, 5 * 2, this.parent.parent));
        violence.callSystems();
    }

}
