package engine.components;

import engine.hierarchy.GameObject;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.lang.reflect.InvocationTargetException;

public abstract class Component {

    protected Tag tag;
    public GameObject parent;
    public Component(Tag tag, GameObject g){
        this.tag = tag;
        this.parent = g;
    }

    public Component(GameObject g) {
        this.parent = g;
    }

    public Component(Element l, GameObject g){
        this.parent = g;
    }


    public void tick(long nanosSinceLastTick) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {}

    public void onDraw(GraphicsContext g){}

    public void onKeyPressed(KeyEvent e){}
    public void onKeyReleased(KeyEvent e){}


    /**
     * Called when the mouse is clicked.
     * @param e		Vec2d representing translated input.
     */
    public void onMouseClicked(Vec2d e, MouseEvent event) {}

    /**
     * Called when the mouse is pressed.
     * @param e		Vec2d representing translated input.
     */
    public void onMousePressed(Vec2d e) {}

    /**
     * Called when the mouse is released.
     * @param e		Vec2d representing translated input.
     */
    public void onMouseReleased(Vec2d e) {}

    public Element savify(Document d){
        return null;
    }

    /**
     * Called when the mouse is dragged.
     * @param e		Vec2d representing translated input.
     */
    public void onMouseDragged(Vec2d e) {}

    public Tag getTag(){return tag;}

    public void onLateTick() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {};


}
