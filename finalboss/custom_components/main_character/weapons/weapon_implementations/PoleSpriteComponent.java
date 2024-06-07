package finalboss.custom_components.main_character.weapons.weapon_implementations;

import engine.components.SpriteComponent;
import engine.hierarchy.GameObject;
import engine.sprite.Resource;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class PoleSpriteComponent extends SpriteComponent {
    //i made a whole class to draw the sprite in a different location
    //are you proud of me
    //this can be removed if i get around to it
    double range;
    public PoleSpriteComponent(GameObject g, Resource<Image> rs, int spNum, int hcel, int vcel, int hcelSize, int vcelSize, double range) {
        super(g, rs, spNum, hcel, vcel, hcelSize, vcelSize);
        this.range = range;
    }

    protected void draw(GraphicsContext g) {
        if (LAC != null) {
            LAC.onDraw(g);
        } else {
            g.drawImage(r.resource.get(sprite), vcel*vcelSize, hcel*hcelSize, vcelSize, hcelSize, parent.getTransform().pos.x-(5*50*range), parent.getTransform().pos.y, parent.getTransform().size.x, parent.getTransform().size.y);
        }
    }
}
