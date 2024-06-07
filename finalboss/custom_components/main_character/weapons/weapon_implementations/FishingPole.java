package finalboss.custom_components.main_character.weapons.weapon_implementations;

import engine.Direction;
import engine.components.AnimationComponent;
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
import javafx.scene.image.Image;

public class FishingPole extends WeaponComponent {


        public FishingPole(GameObject g, PlayerControllerComponent pcc, FBChalkboard fbChalkboard) {
            super(g, pcc, fbChalkboard);
            this.pcc = pcc;
            this.type = WeaponType.FISHING_POLE;
            this.tag = Tag.WEAPON;
            this.movementLock = 25;
            this.isFinal = false;
            initVars();
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
                GameObject violence = new GameObject(this.parent.parent, "pole", pos, new Vec2d(6*sprite_size*range, sprite_size), this.parent.parent.windowSize, 3.0);
                violence.addComponent(new PoleCollisionComponent(violence, ShapeEnum.AAB, 3, false, pcc.getLastDirect(), range));
                SpriteComponent s;
                if(pcc.getLastDirect() == Direction.LEFT) {
                    s = new PoleSpriteComponent(violence, r, 14, 0, 1, 48, 6 * 32, range);
                }
                else{
                    s = new SpriteComponent(violence, r, 14, 0, 0, 48, 6 * 32);

                }
                AnimationComponent ss = new AnimationComponent(violence, s, 11, 2);
                violence.addComponent(s);
                violence.addComponent(ss);
                FishingPole fp = new FishingPole(violence, pcc, chalk);
                violence.addComponent(fp);
                violence.addComponent(new TimedDisappearanceComponent(violence, 28, this.parent.parent));
                GameObject tip = new GameObject(this.parent.parent, "tip", pos, new Vec2d(sprite_size, sprite_size), this.parent.parent.windowSize, 3.0);
                tip.addComponent(new TimedDisappearanceComponent(tip, 22, this.parent.parent));
                tip.addComponent(new TipCollisionComponent(tip, ShapeEnum.AAB, 3, false, pcc.getLastDirect(), range));
                FishingPole tp = new FishingPole(violence, pcc, chalk);
                tp.setDamage(damage*special);
                tip.addComponent(tp);
                violence.callSystems();
                tip.callSystems();
        }

}
