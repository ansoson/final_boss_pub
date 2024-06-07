package finalboss.custom_components.villain;

import engine.components.LayeredAnimationComponent;
import engine.components.SpriteComponent;
import engine.hierarchy.GameObject;
import engine.sprite.SpritePiece;
import finalboss.custom_components.main_character.MainCharacterSpritePiece;
import finalboss.custom_components.main_character.PlayerControllerComponent;
import finalboss.custom_components.main_character.weapons.WeaponType;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class VillainLayeredAnimationComponent extends LayeredAnimationComponent {
    VillainAttackComponent myController;
    public VillainLayeredAnimationComponent(Element l, GameObject g, SpriteComponent s) {
        super(l, g, s, 4);
    }

    public VillainLayeredAnimationComponent(GameObject g, SpriteComponent s) {
        super(g, s, 4);
        this.sprites = new ArrayList<>();
        sprites.add(new VillainSpritePiece(s.r.resource.get(23), s, 0));
        this.cycleSwap("IDLE");
    }

    public void setMyController(VillainAttackComponent p){
        myController = p;
    }

}
