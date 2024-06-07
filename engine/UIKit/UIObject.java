package engine.UIKit;

import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class UIObject {

    protected UIObject parent;
    protected ArrayList<UIObject> children = new ArrayList<>();

    protected boolean on = true;
    protected Color c;
    Vec2d pos;
    public Vec2d size;

    Vec2d def;

    Vec2d ogPos;
    Vec2d ogSize;

    //values for resizing
    // (default is to position relative to the upper left of parent/screen position)
    VAlignment vAlignment = VAlignment.UPPER;
    HAlignment hAlignment = HAlignment.LEFT;
    boolean scales = true;

    public boolean active = true;

    /**
     * Simplest possible UI Object contruction
     * @param pos
     * @param size
     * @param def
     */
    public UIObject(Vec2d pos, Vec2d size, Vec2d def) {
        this.ogPos = pos;
        this.ogSize = size;
        this.size = size;
        this.def = def;
        this.c = Color.TRANSPARENT;

        this.alignUI(pos, size);
    }

    /**
     * create UIObject with custom parent and horizontal and vertical alignment values, plus
     * option to scale or not
     */
    public UIObject(UIObject parent, Vec2d pos, Vec2d size, Vec2d def,
                    HAlignment hAlignment, VAlignment vAlignment, boolean scales) {

        this.parent = parent;
        this.parent.addChild(this);

        this.ogPos = pos;
        this.ogSize = size;
        this.size = size;
        this.vAlignment = vAlignment;
        this.hAlignment = hAlignment;
        this.scales = scales;
        this.def = def;
        this.c = Color.TRANSPARENT;

        this.alignUI(pos, size);
    }

    /**
     * create UIObject with parent
     */
    public UIObject(UIObject parent, Vec2d pos, Vec2d size, Vec2d def) {

        this.parent = parent;
        this.parent.addChild(this);

        this.ogPos = pos;
        this.ogSize = size;
        this.size = size;
        this.def = def;
        this.c = Color.TRANSPARENT;

        this.alignUI(pos, size);
    }

    /**
     * Constructor where ui object has no color
     */
    public UIObject(Vec2d pos, Vec2d size, Vec2d def,
                    HAlignment hAlignment, VAlignment vAlignment, boolean scales) {

        this.ogPos = pos;
        this.ogSize = size;
        this.size = size;
        this.vAlignment = vAlignment;
        this.hAlignment = hAlignment;
        this.scales = scales;
        this.def = def;
        this.c = Color.TRANSPARENT;

        this.alignUI(pos, size);
    }


    public void setOn(boolean on){this.on = on;}
    public boolean getOn(){return on;}

    /**
     * Contructor w/o parent and no alignment
     * @param c
     * @param pos
     * @param size
     * @param def
     */
    public UIObject(Color c, Vec2d pos, Vec2d size, Vec2d def) {
        this.ogPos = pos;
        this.ogSize = size;
        this.size = size;
        this.def = def;
        this.c = c;

        this.alignUI(pos, size);
    }

    /**
     * Constructor w parent, color, and default alignment
     * @param parent
     * @param c
     * @param pos
     * @param size
     * @param def
     */
    public UIObject(UIObject parent, Color c, Vec2d pos, Vec2d size, Vec2d def) {
        this.parent = parent;
        this.parent.addChild(this);

        this.ogPos = pos;
        this.ogSize = size;
        this.size = size;
        this.def = def;
        this.c = c;

        this.alignUI(pos, size);
    }

    /**
     * Contructor w parent, color and alignment
     * @param c
     * @param pos
     * @param size
     * @param def
     */
    public UIObject(UIObject parent, Color c, Vec2d pos, Vec2d size, Vec2d def,
                    HAlignment hAlignment, VAlignment vAlignment, boolean scales) {
        this.parent = parent;
        this.parent.addChild(this);

        this.ogPos = pos;
        this.ogSize = size;
        this.size = size;
        this.vAlignment = vAlignment;
        this.hAlignment = hAlignment;
        this.scales = scales;
        this.def = def;
        this.c = c;

        this.alignUI(pos, size);
    }

    /**
     * Contructor w/o parent, with color and alignment
     * @param c
     * @param pos
     * @param size
     * @param def
     */
    public UIObject(Color c, Vec2d pos, Vec2d size, Vec2d def,
                    HAlignment hAlignment, VAlignment vAlignment, boolean scales) {

        this.ogPos = pos;
        this.ogSize = size;
        this.size = size;
        this.vAlignment = vAlignment;
        this.hAlignment = hAlignment;
        this.scales = scales;
        this.def = def;
        this.c = c;

        this.alignUI(pos, size);
    }

    /**
     * This method aligns the UI element according to the given alignment values
     */
    public void alignUI(Vec2d pos, Vec2d size){

        //calculate shift based off parent's position
        Vec2d parentPos = new Vec2d(0, 0);
        Vec2d parentSz = this.def;
        if (parent != null){
            parentPos = parent.pos;
            parentSz = parent.size;
        }

        //FIRST, HORIZONTAL alignment
        if (hAlignment == HAlignment.LEFT){
            this.pos = new Vec2d(parentPos.plus(pos).x, 0);
        }
        else if (hAlignment == HAlignment.RIGHT){
            this.pos = new Vec2d(parentPos.plus(pos).plus(parentSz).minus(size).x, 0);
        }
        else if (hAlignment == HAlignment.CENTER){
            this.pos = new Vec2d(parentPos.plus(pos).
                plus(parentSz.smult(.5)).minus(size.smult(.5)).x, 0);
        }

        //SECOND, VERTICAL alignment
        if (vAlignment == VAlignment.UPPER){
            this.pos = new Vec2d(this.pos.x, parentPos.plus(pos).y);
        }
        else if (vAlignment == VAlignment.LOWER){
            this.pos = new Vec2d(this.pos.x,
                parentPos.plus(pos).plus(parentSz).minus(size).y);
        }
        else if (hAlignment == HAlignment.CENTER){
            this.pos = new Vec2d(this.pos.x,
                parentPos.plus(pos).plus(parentSz.smult(.5)).minus(size.smult(.5)).y);
        }

    }

    public void onTick(long nanos){

    }

    public void onDraw(GraphicsContext g){if(on){DrawElement(g);}}
    public void DrawElement(GraphicsContext g){}
    public void onResize(Vec2d newSize){
        if (scales){
            this.pos = new Vec2d(newSize.x * pos.x, newSize.y * pos.y);
            this.size = new Vec2d(newSize.x * size.x, newSize.y * size.y);
        }
    }

    public void onMouseMoved(MouseEvent e) {}

    public void onMouseDragged(MouseEvent e) {}

    public void onMouseReleased(MouseEvent e) {}

    public void onMousePressed(MouseEvent e) {
    }

    public void onMouseClicked(MouseEvent e) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException, TransformerException {
    }

    public void addChild(UIObject o){
        children.add(o);
    }


}
