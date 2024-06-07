package engine.UIKit;

import engine.hierarchy.GameObject;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle extends UIObject {



    Color c;


    public Circle(Vec2d pos, Vec2d size, Vec2d def, Color c){
        super(c, pos, size, def);

        this.c = c;
    }

    public Circle(UIObject parent, Vec2d pos, Vec2d size, Vec2d def, Color c){
        super(parent, pos, size, def);

        this.c=c;
    }

    public Circle(Vec2d pos, Vec2d size, Vec2d def, Color c,
                  HAlignment hAlignment, VAlignment vAlignment, boolean scales){
        super(c, pos, size, def, hAlignment, vAlignment, scales);

        this.c = c;
    }

    public Circle(UIObject parent, Vec2d pos, Vec2d size, Vec2d def, Color c,
                  HAlignment hAlignment, VAlignment vAlignment, boolean scales){
        super(parent, pos, size, def, hAlignment, vAlignment, scales);

        this.c=c;
    }

    @Override
    public void DrawElement(GraphicsContext g) {

        if (active){
            g.setStroke(c);
            g.setFill(c);
            g.setLineWidth(8);
            g.strokeOval(pos.x, pos.y, size.x, size.y);

            for (UIObject o: this.children){
                if (o.active){
                    o.DrawElement(g);
                }
            }
        }

    }
}
