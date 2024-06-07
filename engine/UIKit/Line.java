package engine.UIKit;

import engine.hierarchy.GameObject;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Line extends UIObject {

    double width;


    public Line(Vec2d pos, Vec2d size, double width, Vec2d def, Color c){
        super(c, size, pos, def);
        this.c = c;
        this.width=width;
    }

    public Line(UIObject parent, Vec2d pos, Vec2d size, Vec2d def, Color c){
        super(parent, pos, size, def);
        this.c=c;
    }

    @Override
    public void DrawElement(GraphicsContext g) {
        if (active){
            g.setStroke(c);
            g.setFill(c);
            g.setLineWidth(width);
            g.strokeLine(pos.x, pos.y, size.x, size.y); //use pos and size to just x1, x2

            for (UIObject o: this.children){
                if (o.active){
                    o.DrawElement(g);
                }
            }
        }

    }
}
