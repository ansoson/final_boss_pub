package engine.components;

import engine.hierarchy.GameObject;
import engine.sprite.SpritePiece;
import finalboss.custom_components.main_character.PlayerControllerComponent;
import javafx.scene.canvas.GraphicsContext;
import org.w3c.dom.Element;

import java.util.ArrayList;

public class LayeredAnimationComponent extends Component{
    public LayeredAnimationComponent(Element l, GameObject g, SpriteComponent s, int numLayers) {
        super(l, g);
        layers = numLayers;
        mySprite = s;
        // CREATE SPRITE PIECES INSIDE OF COMPONENT
    }

    public LayeredAnimationComponent(GameObject g, SpriteComponent s, int numLayers) {
        super(g);
        layers = numLayers;
        mySprite = s;
        // CREATE SPRITE PIECES INSIDE OF COMPONENT
    }

    protected ArrayList<SpritePiece> sprites;
    int layers;
    protected SpriteComponent mySprite;

    @Override
    public void onDraw(GraphicsContext g) {
        for (SpritePiece sprite: sprites) {
            sprite.Draw(g);
        }
    }

    public void tick(long nanos){
        for (SpritePiece sprite: sprites) {
            sprite.tick(nanos);
        }
    }

    public void cycleSwap(String tag) {
        for (SpritePiece sprite: sprites) {
            sprite.runCycle(tag);
        }
    }

    public void setMyController(PlayerControllerComponent p){
    }

}
