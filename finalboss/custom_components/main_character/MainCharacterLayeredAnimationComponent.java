package finalboss.custom_components.main_character;

import engine.components.LayeredAnimationComponent;
import engine.components.SpriteComponent;
import engine.hierarchy.GameObject;
import engine.sprite.SpritePiece;
import finalboss.custom_components.main_character.weapons.WeaponComponent;
import finalboss.custom_components.main_character.weapons.WeaponType;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class MainCharacterLayeredAnimationComponent extends LayeredAnimationComponent {
    PlayerControllerComponent myController;
    public MainCharacterLayeredAnimationComponent(Element l, GameObject g, SpriteComponent s) {
        super(l, g, s, 4);
    }

    public MainCharacterLayeredAnimationComponent(GameObject g, SpriteComponent s) {
        super(g, s, 4);
        this.sprites = new ArrayList<>();
        sprites.add(new MainCharacterSpritePiece(s.r.resource.get(2), s, 1));
        sprites.add(new MainCharacterSpritePiece(s.r.resource.get(2), s, 2));
        sprites.add(new MainCharacterSpritePiece(s.r.resource.get(2), s, 3));
        sprites.add(new MainCharacterSpritePiece(s.r.resource.get(2), s, 4));
    }

    public void hardSleepySet(){
        for (SpritePiece piece : sprites){
            piece.currentCycle = "EEPY";
        }
        sprites.get(0).setMyrow(1);
        sprites.get(1).setMyrow(2);
        sprites.get(2).setMyrow(3);
        sprites.get(3).setMyrow(4);
    }

    public void setMyController(PlayerControllerComponent p){
        myController = p;
    }

    @Override
    public void cycleSwap(String tag) {
        if (myController.righthand.getType() == WeaponType.NONE){
            sprites.get(3).setMyrow(4);
        }
        if (myController.righthand.getType() == WeaponType.NET){
            sprites.get(3).setMyrow(5);
            if (myController.righthand.isFinal()){
                sprites.get(3).setMyrow(17);
            }
        }
        if (myController.righthand.getType() == WeaponType.SHIELD){
            sprites.get(3).setMyrow(6);
            if (myController.righthand.isFinal()){
                sprites.get(3).setMyrow(18);
            }
        }
        if (myController.righthand.getType() == WeaponType.WAND){
            sprites.get(3).setMyrow(7);
            if (myController.righthand.isFinal()){
                sprites.get(3).setMyrow(19);
            }
        }
        if (myController.righthand.getType() == WeaponType.FISHING_POLE){
            sprites.get(3).setMyrow(8);
            if (myController.righthand.isFinal()){
                sprites.get(3).setMyrow(20);
            }
        }
        if (myController.righthand.getType() == WeaponType.WATERING_CAN){
            sprites.get(3).setMyrow(9);
            if (myController.righthand.isFinal()){
                sprites.get(3).setMyrow(21);
            }
        }
        if (myController.righthand.getType() == WeaponType.ROPE){
            sprites.get(3).setMyrow(10);
            if (myController.righthand.isFinal()){
                sprites.get(3).setMyrow(22);
            }
        }
        if (myController.lefthand.getType() == WeaponType.NONE){
            sprites.get(0).setMyrow(1);
        }
        if (myController.lefthand.getType() == WeaponType.NET){
            sprites.get(0).setMyrow(16);
            if (myController.lefthand.isFinal()){
                sprites.get(0).setMyrow(28);
            }
        }
        if (myController.lefthand.getType() == WeaponType.SHIELD){
            sprites.get(0).setMyrow(15);
            if (myController.lefthand.isFinal()){
                sprites.get(0).setMyrow(27);
            }
        }
        if (myController.lefthand.getType() == WeaponType.WAND){
            sprites.get(0).setMyrow(14);
            if (myController.lefthand.isFinal()){
                sprites.get(0).setMyrow(26);
            }
        }
        if (myController.lefthand.getType() == WeaponType.FISHING_POLE){
            sprites.get(0).setMyrow(13);
            if (myController.lefthand.isFinal()){
                sprites.get(0).setMyrow(25);
            }
        }
        if (myController.lefthand.getType() == WeaponType.WATERING_CAN){
            sprites.get(0).setMyrow(12);
            if (myController.lefthand.isFinal()){
                sprites.get(0).setMyrow(24);
            }
        }
        if (myController.lefthand.getType() == WeaponType.ROPE){
            sprites.get(0).setMyrow(11);
            if (myController.lefthand.isFinal()){
                sprites.get(0).setMyrow(23);
            }
        }
        if (Objects.equals(tag, "LOCKATTACKRIGHT")){
            sprites.get(1).runCycle(tag);
            sprites.get(2).runCycle(tag);
            if(sprites.get(3).cycles.get(0).jittercycle == null) {
                sprites.get(3).runCycle("IDLERIGHT");
                sprites.get(3).jittercycleSet(new ArrayList<Integer>(Arrays.asList(10, 10, 10, 10, 10)));
            }
            if(sprites.get(0).cycles.get(0).jittercycle == null){
                sprites.get(0).runCycle("IDLERIGHT");
                sprites.get(0).jittercycleSet(new ArrayList<Integer>(Arrays.asList(10, 10, 10, 10, 10)));
            }
        }
        else if (Objects.equals(tag, "LOCKATTACKLEFT")){
            sprites.get(1).runCycle(tag);
            sprites.get(2).runCycle(tag);
            if(sprites.get(3).cycles.get(0).jittercycle == null) {
                sprites.get(3).jittercycleSet(new ArrayList<Integer>(Arrays.asList(-10, -10, -10, -10, -10)));
                sprites.get(3).runCycle("IDLELEFT");
            }
            if(sprites.get(0).cycles.get(0).jittercycle == null){
                sprites.get(0).runCycle("IDLELEFT");
                sprites.get(0).jittercycleSet(new ArrayList<Integer>(Arrays.asList(-10, -10, -10, -10, -10)));
            }
        }
        else if (Objects.equals(tag, "FRONTRIGHTATTACK1")){
            sprites.get(3).runCycle("RIGHTATTACK1");
            sprites.get(3).jittercycleSet(new ArrayList<Integer>(Arrays.asList(3, 7, 0, 0, 0)));
        } else if (Objects.equals(tag, "FRONTLEFTATTACK1")){
            sprites.get(3).runCycle("LEFTATTACK1");
            sprites.get(3).jittercycleSet(new ArrayList<Integer>(Arrays.asList(-3, -7, -0, -0, -0)));
        } else if (Objects.equals(tag, "BACKRIGHTATTACK1")){
            sprites.get(0).runCycle("RIGHTATTACK1");
            sprites.get(0).jittercycleSet(new ArrayList<Integer>(Arrays.asList(6, 11, 5, 0, 0)));
        } else if (Objects.equals(tag, "BACKLEFTATTACK1")){
            sprites.get(0).runCycle("LEFTATTACK1");
            sprites.get(0).jittercycleSet(new ArrayList<Integer>(Arrays.asList(-6, -11, -5, 0, 0)));
        } else {
            for (SpritePiece sprite: sprites) {
                if (!Objects.equals(sprite.currentCycle, "RIGHTATTACK1") && !Objects.equals(sprite.currentCycle, "RIGHTATTACK2") && !Objects.equals(sprite.currentCycle, "LEFTATTACK1") && !Objects.equals(sprite.currentCycle, "LEFTATTACK2")){
                    sprite.runCycle(tag);
                    sprite.jittercycleSet(null);
                }
                if (Objects.equals(tag, "OUCH")){
                    sprite.runCycle(tag);
                    sprite.jittercycleSet(null);
                }
            }
        }
    }

}
