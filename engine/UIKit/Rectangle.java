package engine.UIKit;

import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle extends UIObject {


    public Rectangle(UIObject parent, Vec2d pos, Vec2d size, Vec2d def, Color c){
        super(parent, c, pos, size, def);
        this.c=c;
    }

    public Rectangle(Vec2d pos, Vec2d size, Vec2d def, Color c){
        super(c, pos, size, def);
        this.c=c;
    }

    public Rectangle(UIObject parent, Vec2d pos, Vec2d size, Vec2d def, Color c,
                     HAlignment hAlignment, VAlignment vAlignment, boolean scales){
        super(parent, c, pos, size, def, hAlignment, vAlignment, scales);
        this.c=c;
    }

    public Rectangle(Vec2d pos, Vec2d size, Vec2d def, Color c,
                     HAlignment hAlignment, VAlignment vAlignment, boolean scales){
        super(c, pos, size, def, hAlignment, vAlignment, scales);
        this.c=c;
    }

    @Override
    public void DrawElement(GraphicsContext g) {
        if (active){
            g.setStroke(c);
            g.setFill(c);
            g.setLineWidth(1);
            g.fillRect(this.pos.x, this.pos.y, this.size.x, this.size.y);

            for (UIObject o: this.children){
                if(o.on) {
                    if (o.active) {
                        o.DrawElement(g);
                    }
                }
            }
        }

    }

}
