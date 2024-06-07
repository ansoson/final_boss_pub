package engine.hierarchy;

import engine.components.Component;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Archetypal gameSystem.
 */
public abstract class GameSystem {

    //removal queue add here
    public List<GameObject> gameObjects = new ArrayList<>();

    public GameWorld parent;
    public String name;
    public Vec2d windowSize;
    public Vec2d mousePosition;
    public List<GameObject> trash = new ArrayList<>();



    public GameSystem(String name, Vec2d defaultWindowSize){
        this.name = name;
        this.windowSize = defaultWindowSize;
        this.trash = new ArrayList<>();

    }
    public GameSystem(String name, Vec2d defaultWindowSize, GameWorld parent) {
        this.name = name;
        this.windowSize = defaultWindowSize;
        this.parent = parent;
        this.trash = new ArrayList<>();
    }

    public void addGameObject(GameObject o){
        this.gameObjects.add(o);
    }

    public abstract void acceptObject(GameObject o);

    public void throwAway(GameObject g){
        trash.add(g);
    }

    protected void removeGameObject(GameObject g){
        //for(GameObject o : trash){
            gameObjects.remove(g);
        //}
    }


    protected void onCall(){
        for(GameObject g: gameObjects){
            action(g);
        }
    }

    private void action(GameObject g){}

    /**
     * Called periodically and used to update the state of your game.
     * @param nanosSincePreviousTick	approximate number of nanoseconds since the previous call
     */
    public void onTick(long nanosSincePreviousTick) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        for(GameObject o: gameObjects){
            o.onTick(nanosSincePreviousTick);
        }
    }

    /**
     * Called after onTick().
     */
    protected void onLateTick() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {}

    /**
     *  Called periodically and meant to draw graphical components.
     * @param g		a {@link GraphicsContext} object used for drawing.
     */
    protected void onDraw(GraphicsContext g) {}
    protected void onMousePressed(Vec2d g){}
    protected void onMouseDragged(Vec2d g){}
    protected void onMouseReleased(Vec2d g){}

    protected void onMouseClicked(Vec2d g, MouseEvent event){}

    protected void onKeyPressed(KeyEvent e){}
    protected void onKeyReleased(KeyEvent e){}



}
