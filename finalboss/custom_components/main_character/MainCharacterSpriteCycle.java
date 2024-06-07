package finalboss.custom_components.main_character;

import engine.sprite.SpriteCycle;
import engine.sprite.SpritePiece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class MainCharacterSpriteCycle extends SpriteCycle {
    public MainCharacterSpriteCycle(String cycleTag, int start, int end, boolean cycling, SpritePiece piece, int cellSize, int tpf) {
        super(cycleTag, start, end, cycling, piece, cellSize, tpf);
    }

    public MainCharacterSpriteCycle(String cycleTag, int start, int end, boolean cycling, String toReturnTag, SpritePiece piece, int cellSize, int tpf) {
        super(cycleTag, start, end, cycling, toReturnTag, piece, cellSize, tpf);
    }

    public void tick(long nanos){
        tickCount++;
        if (tickCount >= ticksPerAnimFrame * spritesPerCycle) {
            if (this.returnSpriteCycle != null) {
                if (myPiece.getMyrow() == 8) {
                    if (Objects.equals(this.tag, "LEFTATTACK1")){
                        myPiece.currentCycle = "LEFTATTACK2";
                        myPiece.jittercycleSet(new ArrayList<Integer>(Arrays.asList(0, -3, -7, -0, -0, -0)));
                        tickCount = 0;
                    } else if (Objects.equals(this.tag, "RIGHTATTACK1")){
                        myPiece.currentCycle = "RIGHTATTACK2";
                        myPiece.jittercycleSet(new ArrayList<Integer>(Arrays.asList(0, 3, 7, 0, -0, -0)));
                        tickCount = 0;
                    } else {
                        myPiece.currentCycle = returnSpriteCycle;
                        tickCount = 0;
                    }
                } else if (myPiece.getMyrow() == 13){
                    if (Objects.equals(this.tag, "LEFTATTACK1")){
                        myPiece.currentCycle = "LEFTATTACK2";
                        myPiece.jittercycleSet(new ArrayList<Integer>(Arrays.asList(0, -6, -11, -5, 0, 0)));
                        tickCount = 0;
                    } else if (Objects.equals(this.tag, "RIGHTATTACK1")){
                        myPiece.jittercycleSet(new ArrayList<Integer>(Arrays.asList(0, 6, 11, 5, 0, 0)));
                        myPiece.currentCycle = "RIGHTATTACK2";
                        tickCount = 0;
                    } else {
                        myPiece.currentCycle = returnSpriteCycle;
                        tickCount = 0;
                    }
                }else {
                    myPiece.currentCycle = returnSpriteCycle;
                    tickCount = 0;
                }
            }else {
                tickCount = 0;
            }
        }
        currentCell = startCell + tickCount/ticksPerAnimFrame;
    }
}
