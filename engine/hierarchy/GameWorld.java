package engine.hierarchy;

import engine.sprite.Resource;
import engine.support.Vec2d;
import engine.systems.CollisionSystem;
import engine.systems.SoundSystem;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * It's screen time baby.
 */
public abstract class GameWorld {

    public List<GameSystem> systems = new ArrayList<>();

    public List<GameObject> trash = new ArrayList<>();

    public CollisionSystem thisCollision;

    public Screen parent;
    public String name;
    public Vec2d windowSize;
    public Vec2d mousePosition;
    public Resource<Image> r;

    public GameWorld(String name) {
        this.name = name;
    }

    public List<GameSystem> getSystems() {
        return systems;
    }

    public GameSystem getSystem(String s){
        for(GameSystem g : systems){
            if(g.name.equals(s)){
                return g;
            }
        }
        return null;
    }

    public GameWorld(String name, Screen parent) {
        this.name = name;
        this.parent = parent;
        this.trash = new ArrayList<>();
    }

    public GameWorld(String name, Vec2d defaultWindowSize, Screen parent) {
        this.name = name;
        this.windowSize = defaultWindowSize;
        this.parent = parent;
        this.trash = new ArrayList<>();
    }

    public GameObject getGameObject(int i){
        for(GameSystem sys : systems){
            for(GameObject o : sys.gameObjects){
                if(i == o.id){
                    return o;
                }
            }
        }
        return null;
    }

    /**
     * Called periodically and used to update the state of your game.
     * @param nanosSincePreviousTick	approximate number of nanoseconds since the previous call
     */
    public void onTick(long nanosSincePreviousTick) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException, TransformerException {
        for(GameObject o: trash){
            removeGameObject(o);
        }
        trash = new ArrayList<>();
        for(GameSystem o: systems){
            o.onTick(nanosSincePreviousTick);
        }
    }

    /**
     * Called after onTick().
     */
    protected void onLateTick() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        for(GameSystem o: systems){
            o.onLateTick();
        }
    }

    /**
     *  Called periodically and meant to draw graphical components.
     * @param g		a {@link GraphicsContext} object used for drawing.
     */
    public void onDraw(GraphicsContext g) {
        for(GameSystem o: systems){
            o.onDraw(g);
        }
    }

    /**
     * Called when a key is typed.
     * @param e		an FX {@link KeyEvent} representing the input event.
     */
    protected void onKeyTyped(KeyEvent e) {}

    /**
     * Called when a key is pressed.
     * @param e		an FX {@link KeyEvent} representing the input event.
     */
    public void onKeyPressed(KeyEvent e) {
        for(GameSystem o: systems){
            o.onKeyPressed(e);
        }
    }

    /**
     * Called when a key is released.
     * @param e		an FX {@link KeyEvent} representing the input event.
     */
    protected void onKeyReleased(KeyEvent e) {
        for(GameSystem o: systems){
            o.onKeyReleased(e);
        }
    }

    /**
     * Called when the mouse is clicked.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    protected void onMouseClicked(Vec2d g, MouseEvent event) {
        for(GameSystem o: systems){
            o.onMouseClicked(g, event);
        }
    }

    /**
     * Called when the mouse is pressed.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    protected void onMousePressed(Vec2d g) {
        for(GameSystem o: systems){
            o.onMousePressed(g);
        }
    }

    /**
     * Called when the mouse is released.
     * @param g		an FX {@link MouseEvent} representing the input event.
     */
    protected void onMouseReleased(Vec2d g) {
        for(GameSystem o: systems){
            o.onMouseReleased(g);
        }
    }

    /**
     * Called when the mouse is dragged.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    protected void onMouseDragged(Vec2d g) {
        for(GameSystem o: systems){
            o.onMouseDragged(g);
        }
    }

    /**
     * Called when the mouse is moved.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    protected void onMouseMoved(Vec2d g) {}

    /**
     * Called when the mouse wheel is moved.
     * @param e		an FX {@link ScrollEvent} representing the input event.
     */
    protected void onMouseWheelMoved(ScrollEvent e) {}


    /**
     * Called when the window is resized.
     * @param newSize	the new size of the drawing area.
     */
    public void onResize(Vec2d newSize) {}

    protected void removeGameObject(GameObject g){
        for(GameSystem s : systems){
            s.removeGameObject(g);
        }
    }

    public void throwAway(GameObject g){
        trash.add(g);
    }

    public abstract void onStartup() throws IOException, SAXException, ParserConfigurationException, TransformerException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
}
