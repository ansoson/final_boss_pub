package engine.sprite;

import engine.components.SpriteComponent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Objects;

public class SpritePiece {

    public SpritePiece(Image spritePage, SpriteComponent sp, int row){
        myComponent = sp;
        currentCycle = "EEPY";
        this.spriteSheet = spritePage;
        cycles = new ArrayList<>();
        myrow = row;
    }
    public Image spriteSheet;
    public SpriteComponent myComponent;
    protected int myrow;
    public ArrayList<SpriteCycle> cycles;
    public String currentCycle;
    public int jitterX;
    public int jitterY;

    public void Draw(GraphicsContext g) {
        for (SpriteCycle cycle : cycles){
            if (Objects.equals(cycle.tag, currentCycle)) {
                cycle.draw(g);
            }
        }
    }

    public void runCycle(String tag){
        if (!Objects.equals(tag, currentCycle)) {
            for (SpriteCycle cycle : cycles){
                if (Objects.equals(cycle.tag, currentCycle)) {
//                    cycle.tickCount = 0;
                }
            }
            this.currentCycle = tag;
        }
    }

    public void tick(long nanos){
        for (SpriteCycle cycle : cycles){
            if (Objects.equals(cycle.tag, currentCycle) || cycle.doesItCycle){
                cycle.tick(nanos);
            }
        }
    }

    public void setMyrow(int r ){
        myrow = r;
    }
    public int getMyrow(){
        return myrow;
    }

    public void setJitter(int x, int y){
        this.jitterX = jitterX + x;
        this.jitterY = jitterY + y;
    }

    public void resetJitter(int x, int y){
        this.jitterX = x;
        this.jitterY = y;
        for (SpriteCycle cycle : cycles){
            cycle.resetJitter(x, y);
        }
    }

    public void jittercycleSet(ArrayList<Integer> l) {
        for (SpriteCycle cycle : cycles){
            cycle.setJittercycle(l);
        }
    }

}
