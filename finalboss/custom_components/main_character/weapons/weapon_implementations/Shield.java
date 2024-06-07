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
import finalboss.gameworlds.LevelGameworld;
import finalboss.screens.CharacterView;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Shield extends WeaponComponent {

    private final CharacterView characterView;

    public Shield(GameObject g, PlayerControllerComponent pcc, FBChalkboard chalk) {
        super(g, pcc, chalk);
        this.type = WeaponType.SHIELD;
        this.tag = Tag.WEAPON;
        this.isFinal = false;
        initVars();
        this.movementLock = 10;
        this.characterView = (CharacterView) parent.parent.parent; //yeah baby
    }

    public void spawnViolence(PlayerControllerComponent pcc){
        int sprite_size = pcc.getSpriteSize();
        Direction d = pcc.getLastDirect();
        Vec2d pos = parent.getTransform().pos;
        if(isFinal){
            pos = pos.plus(new Vec2d(-sprite_size*range, 0));
            violenceFactory(pos, sprite_size, pcc, Direction.LEFT);
            pos = parent.getTransform().pos;
            pos = pos.plus(new Vec2d(sprite_size, 0));
            violenceFactory(pos, sprite_size, pcc, Direction.RIGHT);
        }else {
            if (d == Direction.LEFT) {
                pos = pos.plus(new Vec2d(-sprite_size * range, 0));

            } else if (d == Direction.RIGHT) {
                pos = pos.plus(new Vec2d(sprite_size, 0));
            } else {
                System.out.println("Error, please fix."); //TODO: does this ever trigger
            }
            violenceFactory(pos, sprite_size, pcc, d);
        }
    }

    public void violenceFactory(Vec2d pos, int sprite_size, PlayerControllerComponent pcc, Direction d) {
        Resource<Image> r = pcc.r;
        GameObject violence = new GameObject(this.parent.parent, "violence", pos, new Vec2d(sprite_size*range, sprite_size*range), this.parent.parent.windowSize, 3.0);
        CharacterView lgw = (CharacterView) pcc.parent.parent.parent;
        violence.addComponent(new ShieldCollisionComponent(violence, ShapeEnum.AAB, 3, false,d, range, lgw, special));
        SpriteComponent s;
        if(d == Direction.LEFT) {
            s = new SpriteComponent(violence, r, 12, 0, 1, 32, 32);
        } else{
            s = new SpriteComponent(violence, r, 12, 0, 0, 32, 32);
        }
        AnimationComponent ss = new AnimationComponent(violence, s, 4, 2);
        violence.addComponent(s);
        violence.addComponent(ss);
        Shield fp = new Shield(violence, pcc, chalk);
        violence.addComponent(fp);
        violence.addComponent(new TimedDisappearanceComponent(violence, 8, this.parent.parent));
        violence.callSystems();
    }

}
