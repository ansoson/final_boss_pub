package engine.sprite;

import engine.components.SpriteComponent;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class SpriteCycle {

    public int startCell;
    public int endCell;
    public int currentCell;
    public int cellSize;
    public boolean doesItCycle;
    public String returnSpriteCycle;
    public int tickCount;
    public int spritesPerCycle;
    public String tag;
    public int ticksPerAnimFrame = 15;
    public SpritePiece myPiece;
    public int jitterX;
    public int jitterY;
    public ArrayList<Integer> jittercycle;

    public SpriteCycle(String cycleTag, int start, int end, boolean cycling, SpritePiece piece, int cellSize, int tpf){
        this.tag = cycleTag;
        this.startCell = start;
        this.endCell = end;
        this.currentCell = startCell;
        this.doesItCycle = cycling;
        this.tickCount = 0;
        this.spritesPerCycle = end - start + 1;
        this.myPiece = piece;
        this.cellSize = cellSize;
        this.ticksPerAnimFrame = tpf;
        this.jitterY = 0;
        this.jitterX = 0;
    }

    public SpriteCycle(String cycleTag, int start, int end, boolean cycling, String toReturnTag, SpritePiece piece, int cellSize, int tpf){
        this.tag = cycleTag;
        this.startCell = start;
        this.endCell = end;
        this.currentCell = startCell;
        this.doesItCycle = cycling;
        this.returnSpriteCycle = toReturnTag;
        this.tickCount = 0;
        this.spritesPerCycle = end - start + 1;
        this.myPiece = piece;
        this.cellSize = cellSize;
        this.ticksPerAnimFrame = tpf;
    }

    public void setJittercycle(ArrayList<Integer> cycle){
        this.jittercycle = cycle;
    }

    public void tick(long nanos){
        tickCount++;
        if (tickCount >= ticksPerAnimFrame * spritesPerCycle) {
            if (this.returnSpriteCycle != null) {
                myPiece.currentCycle = returnSpriteCycle;
                tickCount = 0;
            }else {
                tickCount = 0;
            }
        }
        currentCell = startCell + tickCount/ticksPerAnimFrame;
    }

    public void draw(GraphicsContext g) {
        if (jittercycle != null){
            g.drawImage(myPiece.spriteSheet,
                    currentCell*cellSize,
                    myPiece.myrow*cellSize,
                    cellSize,
                    cellSize,
                    myPiece.myComponent.parent.getTransform().pos.x + jitterX + jittercycle.get(currentCell - startCell),
                    myPiece.myComponent.parent.getTransform().pos.y + jitterY,
                    myPiece.myComponent.parent.getTransform().size.x,
                    myPiece.myComponent.parent.getTransform().size.y);
        } else {
            g.drawImage(myPiece.spriteSheet,
                    currentCell*cellSize,
                    myPiece.myrow*cellSize,
                    cellSize,
                    cellSize,
                    myPiece.myComponent.parent.getTransform().pos.x + jitterX,
                    myPiece.myComponent.parent.getTransform().pos.y + jitterY,
                    myPiece.myComponent.parent.getTransform().size.x,
                    myPiece.myComponent.parent.getTransform().size.y);
        }

    }

    public void setJitter(int x, int y){
        this.jitterX = jitterX + x;
        this.jitterY = jitterY + y;
    }

    public void resetJitter(int x, int y){
        this.jitterX = x;
        this.jitterY = y;
    }


}
