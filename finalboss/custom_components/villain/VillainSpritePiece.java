package finalboss.custom_components.villain;

import engine.components.SpriteComponent;
import engine.sprite.SpriteCycle;
import engine.sprite.SpritePiece;
import javafx.scene.image.Image;

import java.util.Objects;

public class VillainSpritePiece extends SpritePiece {
    public VillainSpritePiece(Image spritePage, SpriteComponent sp, int row) {
        super(spritePage, sp, row);
        cycles.add(new SpriteCycle("IDLE", 0, 3, true, this, sp.vcelSize, 15));
        cycles.add(new SpriteCycle("LAVAATTACK", 4, 6, false, "LAVAATTACK1", this, sp.vcelSize, 8));
        cycles.add(new SpriteCycle("LAVAATTACK1", 6, 6, false, "LAVAATTACK2", this, sp.vcelSize, 5));
        cycles.add(new SpriteCycle("LAVAATTACK2", 7, 9, false, "LAVAATTACK3", this, sp.vcelSize, 6));
        cycles.add(new SpriteCycle("LAVAATTACK3", 10, 10, false, "IDLE", this, sp.vcelSize, 12));
        cycles.add(new SpriteCycle("LASERATTACK", 0, 3, false, "LASERATTACK1", this, sp.vcelSize, 5));
        cycles.add(new SpriteCycle("LASERATTACK1", 4, 4, false, "LASERATTACK2", this, sp.vcelSize, 3));
        cycles.add(new SpriteCycle("LASERATTACK2", 5, 7, false, "LASERATTACK3", this, sp.vcelSize, 10));
        cycles.add(new SpriteCycle("LASERATTACK3", 0, 0, false, "IDLE", this, sp.vcelSize, 12));
        cycles.add(new SpriteCycle("PLUMEATTACK", 0, 0, false, "PLUMEATTACK1", this, sp.vcelSize, 7));
        cycles.add(new SpriteCycle("PLUMEATTACK1", 1, 3, false, "PLUMEATTACK2", this, sp.vcelSize, 4));
        cycles.add(new SpriteCycle("PLUMEATTACK2", 4, 5, false, "PLUMEATTACK3", this, sp.vcelSize, 8));
        cycles.add(new SpriteCycle("PLUMEATTACK3", 6, 6, false, "IDLE", this, sp.vcelSize, 12));
        cycles.add(new SpriteCycle("BUBBLEATTACK", 0, 3, false, "BUBBLEATTACK1", this, sp.vcelSize, 4));
        cycles.add(new SpriteCycle("BUBBLEATTACK1", 4, 4, false, "BUBBLEATTACK2", this, sp.vcelSize, 10));
        cycles.add(new SpriteCycle("BUBBLEATTACK2", 5, 5, false, "BUBBLEATTACK3", this, sp.vcelSize, 3));
        cycles.add(new SpriteCycle("BUBBLEATTACK3", 6, 6, false, "IDLE", this, sp.vcelSize, 8));
    }

    public void tick(long nanos){
        for (SpriteCycle cycle : cycles){
            if (Objects.equals(cycle.tag, currentCycle)){
                cycle.tick(nanos);
            }
        }
        if (Objects.equals(this.currentCycle, "IDLE")){
            this.myrow = 0;
        }
        if (Objects.equals(this.currentCycle, "LASERATTACK")){
            this.myrow = 1;
        }
        if (Objects.equals(this.currentCycle, "PLUMEATTACK")){
            this.myrow = 2;
        }
        if (Objects.equals(this.currentCycle, "BUBBLEATTACK")){
            this.myrow = 3;
        }
    }

    public void runCycle(String tag){
        if (!Objects.equals(tag, currentCycle)) {
//            for (SpriteCycle cycle : cycles){
//                if (Objects.equals(cycle.tag, currentCycle)) {
//                    cycle.tickCount = 0;
//                }
//                if (Objects.equals("IDLE", cycle.tag)) {
//                    cycle.tickCount = 0;
//                }
//            }
            this.currentCycle = tag;
        }
        if (Objects.equals(this.currentCycle, "IDLE")){
            this.myrow = 0;
        }
        if (Objects.equals(this.currentCycle, "LASERATTACK")){
            this.myrow = 1;
        }
        if (Objects.equals(this.currentCycle, "PLUMEATTACK")){
            this.myrow = 2;
        }
        if (Objects.equals(this.currentCycle, "BUBBLEATTACK")){
            this.myrow = 3;
        }
    }

}
