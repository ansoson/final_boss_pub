package engine.UIKit;

import engine.hierarchy.Screen;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class Button extends UIObject {
    List<UIObject> uis;

    protected Color c;

    protected boolean hovering;

    protected Screen parent;
    protected String name;

    public Button(String name, Vec2d pos, Vec2d size, Vec2d def, Color c){
        super(c, pos, size, def);

        this.hovering = false;
        this.name = name;
    }
    public Button(Screen parent, String name, Vec2d pos, Vec2d size, Vec2d def, Color c) {
        super(c, pos, size, def);

        this.name = name;
        this.parent = parent;
        this.c=c;
        this.hovering = false;
    }

    public Button(Screen parent, String name, Vec2d pos, Vec2d size, Vec2d def) {
        super(pos, size, def);

        this.name = name;
        this.hovering = false;
    }

    public Button(String name, Vec2d pos, Vec2d size, Vec2d def, Color c,
                  HAlignment hAlignment, VAlignment vAlignment, boolean scales){
        super(c, pos, size, def, hAlignment, vAlignment, scales);

        this.hovering = false;
        this.name = name;
        this.on = true;
    }

    public Button(Screen parent, UIObject uiParent, String name, Vec2d pos, Vec2d size, Vec2d def, Color c,
                            HAlignment hAlignment, VAlignment vAlignment, boolean scales){
        super(uiParent, c, pos, size, def, hAlignment, vAlignment, scales);

        this.c = c;
        this.name = name;
        this.hovering = false;
        this.on = true;
    }

    public Button(Screen parent, String name, Vec2d pos, Vec2d size, Vec2d def, Color c,
                  HAlignment hAlignment, VAlignment vAlignment, boolean scales){
        super(c, pos, size, def, hAlignment, vAlignment, scales);

        this.c = c;
        this.name = name;
        this.hovering = false;
        this.on = true;
    }

    public Button(Screen parent, String name, Vec2d pos, Vec2d size, Vec2d def,
                  HAlignment hAlignment, VAlignment vAlignment, boolean scales){
        super(pos, size, def, hAlignment, vAlignment, scales);
        this.alignUI(pos, size);

        this.parent = parent;
        this.name = name;
        this.hovering = false;
        this.on = true;
    }

    public void onMousePressed(MouseEvent e){
        if(active) {
            if (this.pos.y <= e.getSceneY() && e.getSceneY() <= this.pos.y + this.size.y) {
                if (this.pos.x <= e.getSceneX() && e.getSceneX() <= this.pos.x + this.size.x) {
                    DEvent();
                }
            }
        }
    }
    //hover behavior here
    public void onMouseMoved(MouseEvent e) {
        if(active) {
            MouseMovement(e);
        }
    }

    public void onMouseDragged(MouseEvent e) {
        if(active) {
            MouseMovement(e);
            if (this.pos.y <= e.getSceneY() && e.getSceneY() <= this.pos.y + this.size.y) {
                if (this.pos.x <= e.getSceneX() && e.getSceneX() <= this.pos.x + this.size.x) {
                    DEvent();
                }
            }
        }
    }

    public void DEvent(){}


    private void MouseMovement(MouseEvent e) {
        if (active){
            if (this.pos.y <= e.getSceneY() && e.getSceneY() <= this.pos.y + this.size.y) {
                if (this.pos.x <= e.getSceneX() && e.getSceneX() <= this.pos.x + this.size.x) {
                    if (!hovering) {
                        HoverEvent(e);
                        hovering = true;
                    }
                } else {
                    if (hovering) {
                        EndHoverEvent(e);
                        hovering = false;
                    }
                }
            } else {
                if (hovering) {
                    EndHoverEvent(e);
                    hovering = false;
                }
            }
        }

    }


    public void onMouseClicked(MouseEvent e) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException, TransformerException {
        if(active) {
            if (this.pos.y <= e.getSceneY() && e.getSceneY() <= this.pos.y + this.size.y) {
                if (this.pos.x <= e.getSceneX() && e.getSceneX() <= this.pos.x + this.size.x) {
                    ButtonEvent(e);
                }
            }
        }
    }

    public abstract void ButtonEvent(MouseEvent e) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException, TransformerException;

    public void onResize(Vec2d newSize){
        this.pos = new Vec2d(newSize.x * ogPos.x, newSize.y * ogPos.y);
        this.size = new Vec2d(newSize.x * ogSize.x, newSize.y * ogSize.y);
    };

    public abstract void HoverEvent(MouseEvent e);
    public abstract void EndHoverEvent(MouseEvent e);

    @Override
    public void DrawElement(GraphicsContext g) {
        if (active){
            g.setStroke(c);
            g.setFill(c);
            g.setLineWidth(1);
            g.fillRect(pos.x, pos.y, size.x, size.y);

            for (UIObject o: this.children){
                if (o.active){
                    o.DrawElement(g);
                }
            }
        }
    }
}
