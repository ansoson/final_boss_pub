package engine.components;

import engine.hierarchy.GameObject;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GraphicsComponent extends Component {

    protected Vec2d pos;
    protected Vec2d size;
    protected Vec2d def;
    Vec2d ogPos;
    Vec2d ogSize;
    public Color c;
    protected boolean active;

    public void setActive(boolean a){this.active = a;}
    public boolean getActive(){return active;}


    public GraphicsComponent(Element n, GameObject g){
        super(n, g);
        this.tag = Tag.GRAPHICS;
        this.parent = g;
        this.pos =ogPos= g.getTransform().pos;
        this.size =ogSize= g.getTransform().size;
        this.def = g.getTransform().def;
        this.active = true;
        this.c = Color.BLACK; //assuming we're doing all sprite graphics for now
    }

    @Override
    public Element savify(Document d) {
        Element e = d.createElement("GraphicsComponent");
        e.setAttribute("color", c.toString());
        e.setAttribute("active", String.valueOf(this.active));
        return super.savify(d);
    }

    public GraphicsComponent(GameObject g){
        super(g);
    }

    public GraphicsComponent(GameObject g, Color c) {
        super(g);
        this.tag = Tag.GRAPHICS;
        this.pos = g.getTransform().pos;
        this.size = g.getTransform().size;
        this.def = g.getTransform().def;
        this.ogPos = pos;
        this.ogSize = size;
        this.c = c;
        this.active = true;
    }

    /**
     *  Called periodically and meant to draw graphical components.
     * @param g		a {@link GraphicsContext} object used for drawing.
     */
    public void onDraw(GraphicsContext g) {
        if (active) {
            draw(g);
           }
    }

    protected void draw(GraphicsContext g) {
        g.setStroke(c);
        g.setFill(c);
        g.setLineWidth(1);
        g.fillRect(parent.getTransform().pos.x, parent.getTransform().pos.y, parent.getTransform().size.x, parent.getTransform().size.y);
        //Point2D godless = g.getTransform().transform(parent.getTransform().pos.x, parent.getTransform().pos.y);
        //System.out.println("DRAW COORDS: " + godless.getX() + ", " + godless.getY());
        //System.out.println("GO: " + parent.getTransform().pos.x + "," + parent.getTransform().pos.y);
    }

    /**
     * Called when the window is resized.
     * @param newSize	the new size of the drawing area.
     */
    protected void onResize(Vec2d newSize) {
        this.pos = new Vec2d(newSize.x * ogPos.x, newSize.y * ogPos.y);
        this.size = new Vec2d(newSize.x * ogSize.x, newSize.y * ogSize.y);
    }

}
