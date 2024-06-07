package engine.systems;

import engine.components.Tag;
import engine.hierarchy.GameObject;
import engine.hierarchy.GameSystem;
import engine.hierarchy.GameWorld;
import engine.support.Vec2d;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class InputSystem extends GameSystem {
    public InputSystem(String name, Vec2d defaultWindowSize, GameWorld parent) {
        super(name, defaultWindowSize, parent);
    }

    @Override
    public void onMousePressed(Vec2d g){
        for(GameObject o : gameObjects){
            if(o.getComponent(Tag.DRAGGING) != null) {
                o.getComponent(Tag.DRAGGING).onMousePressed(g);
            }
        }
    }
    public void onMouseDragged(Vec2d g){
        for(GameObject o : gameObjects){
            if(o.getComponent(Tag.DRAGGING) != null) {
                o.getComponent(Tag.DRAGGING).onMouseDragged(g);
            }
        }
    }

    @Override
    public void acceptObject(GameObject o){
        for(GameObject gg : this.gameObjects){
            if(gg.id == o.id){
                return;
            }
        }
        if(o.getComponent(Tag.DRAGGING) != null || o.getComponent(Tag.INPUT) != null){
            addGameObject(o);
        }
    }

    public void onMouseReleased(Vec2d g){
        for(GameObject o : gameObjects){
            if(o.getComponent(Tag.DRAGGING) != null) {
                o.getComponent(Tag.DRAGGING).onMouseReleased(g);
            }
        }
    }

    public void onMouseClicked(Vec2d g, MouseEvent event){
        for(GameObject o : gameObjects){
            if(o.getComponent(Tag.INPUT) != null) {
                o.getComponent(Tag.INPUT).onMouseClicked(g, event);
            }
        }
    }

    /**
     * Called when a key is pressed.
     * @param e		an FX {@link KeyEvent} representing the input event.
     */
    @Override
    public void onKeyPressed(KeyEvent e) {
        for(GameObject o : gameObjects){
            if(o.getComponent(Tag.INPUT) != null) {
                o.getComponent(Tag.INPUT).onKeyPressed(e);
            }
        }
    }

    /**
     * Called when a key is released.
     * @param e		an FX {@link KeyEvent} representing the input event.
     */
    @Override
    protected void onKeyReleased(KeyEvent e) {
        for(GameObject o : gameObjects){
            if(o.getComponent(Tag.INPUT) != null) {
                o.getComponent(Tag.INPUT).onKeyReleased(e);
            }
        }
    }

}
