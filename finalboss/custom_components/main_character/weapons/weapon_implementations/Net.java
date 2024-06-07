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

public class Net extends WeaponComponent {

    public Net(GameObject g, PlayerControllerComponent pcc, FBChalkboard chalk) {
        super(g, pcc, chalk);
        this.type = WeaponType.NET;
        this.pcc = pcc;
        this.tag = Tag.WEAPON;
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
            pos = pos.plus(new Vec2d(-sprite_size*range, -30));
        }
        else if(d == Direction.RIGHT){
            pos = pos.plus(new Vec2d(sprite_size, -30));
        }
        else{
            System.out.println("Error, please fix."); //TODO: does this ever trigger
        }
        violenceFactory(pos, sprite_size, pcc);
    }

    public void violenceFactory(Vec2d pos, int sprite_size, PlayerControllerComponent pcc) {
        Resource<Image> r = pcc.r;
        GameObject violence = new GameObject(this.parent.parent, "violence", pos, new Vec2d(sprite_size*range, sprite_size*2*range), this.parent.parent.windowSize, 3.0);
        violence.addComponent(new NetCollisionComponent(violence, ShapeEnum.AAB, 3, false, pcc.getLastDirect(), this.range));
        SpriteComponent s;
        if(pcc.getLastDirect() == Direction.LEFT) {
            s = new SpriteComponent(violence, r, 13, 0, 1, 32 * 2, 48);
        }else{
            s = new SpriteComponent(violence, r, 13, 0, 0, 32 * 2, 48);
        }
        AnimationComponent ss = new AnimationComponent(violence, s, 4, 2);
        violence.addComponent(s);
        violence.addComponent(ss);
        Net fp = new Net(violence, pcc, chalk);
        violence.addComponent(fp);
        violence.addComponent(new TimedDisappearanceComponent(violence, 5 * 2, this.parent.parent));
        violence.callSystems();
    }

}
