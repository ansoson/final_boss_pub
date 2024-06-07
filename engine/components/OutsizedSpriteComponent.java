package engine.components;

import engine.hierarchy.GameObject;
import engine.sprite.Resource;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.w3c.dom.Element;

public class OutsizedSpriteComponent extends SpriteComponent{
    public OutsizedSpriteComponent(Element e, GameObject g) {
        super(e, g);
    }

    public OutsizedSpriteComponent(SpriteComponent spc) {
        super(spc);
    }

    public OutsizedSpriteComponent(GameObject g, Resource<Image> rs, int spNum, int hcel, int vcel, int hcelSize, int vcelSize, double extraScaleX, double extraScaleY) {
        super(g, rs, spNum, hcel, vcel, hcelSize, vcelSize);
        this.outsizedRatioX = extraScaleX;
        this.outsizedRatioY = extraScaleY;
    }

    double outsizedRatioX;
    double outsizedRatioY;

    @Override
    public void onDraw(GraphicsContext g) {
        g.drawImage(r.resource.get(sprite),
                vcel*vcelSize,
                hcel*hcelSize,
                vcelSize,
                hcelSize,
                parent.getTransform().pos.x - (parent.getTransform().size.x * outsizedRatioX - parent.getTransform().size.x)/2,
                parent.getTransform().pos.y - (parent.getTransform().size.y * outsizedRatioY - parent.getTransform().size.y),
                parent.getTransform().size.x * outsizedRatioX,
                parent.getTransform().size.y * outsizedRatioY);
    }
}
