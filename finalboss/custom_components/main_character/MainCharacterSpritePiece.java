package finalboss.custom_components.main_character;

import engine.components.SpriteComponent;
import engine.sprite.SpriteCycle;
import engine.sprite.SpritePiece;
import javafx.scene.image.Image;

import java.util.Objects;

public class MainCharacterSpritePiece extends SpritePiece {
    public MainCharacterSpritePiece(Image spritePage, SpriteComponent sp, int row) {
        super(spritePage, sp, row);
        cycles.add(new MainCharacterSpriteCycle("IDLERIGHT", 0, 1, true, this, sp.vcelSize, 15));
        cycles.add(new MainCharacterSpriteCycle("IDLELEFT", 6, 7, true, this, sp.vcelSize, 15));
        cycles.add(new MainCharacterSpriteCycle("RUNNINGRIGHT", 2, 5, true, this, sp.vcelSize, 5));
        cycles.add(new MainCharacterSpriteCycle("RUNNINGLEFT", 8, 11, true, this, sp.vcelSize, 5));
        cycles.add(new MainCharacterSpriteCycle("DASHINGRIGHT", 12, 12, true, this, sp.vcelSize, 5));
        cycles.add(new MainCharacterSpriteCycle("DASHINGLEFT", 13, 13, true, this, sp.vcelSize, 5));
        cycles.add(new MainCharacterSpriteCycle("EEPY", 16, 17, true, this, sp.vcelSize, 40));
        cycles.add(new MainCharacterSpriteCycle("LESSEEPY", 18, 19, false, "GETTINGUP", this, sp.vcelSize, 40));
        cycles.add(new MainCharacterSpriteCycle("GETTINGUP", 20, 21, false, "IDLERIGHT", this, sp.vcelSize, 10));
        cycles.add(new MainCharacterSpriteCycle("RIGHTATTACK1", 22, 24, false, "IDLERIGHT", this, sp.vcelSize, 4));
        cycles.add(new MainCharacterSpriteCycle("LEFTATTACK1", 25, 27, false, "IDLELEFT", this, sp.vcelSize, 4));
        cycles.add(new MainCharacterSpriteCycle("RIGHTATTACK2", 33, 36, false, "IDLERIGHT", this, sp.vcelSize, 4));
        cycles.add(new MainCharacterSpriteCycle("LEFTATTACK2", 29, 32, false, "IDLELEFT", this, sp.vcelSize, 4));
        cycles.add(new MainCharacterSpriteCycle("OUCH", 28, 28, true,  this, sp.vcelSize, 5));
        cycles.add(new MainCharacterSpriteCycle("LOCKATTACKRIGHT", 14, 14, true,  this, sp.vcelSize, 10));
        cycles.add(new MainCharacterSpriteCycle("LOCKATTACKLEFT", 15, 15, true,  this, sp.vcelSize, 10));
    }

}
