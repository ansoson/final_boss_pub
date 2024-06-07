package engine.components;

import engine.hierarchy.GameObject;
import engine.support.Vec2d;
import javafx.scene.input.MouseEvent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DraggingComponent extends Component {

    public boolean pressed;

    boolean brighter;
    public DraggingComponent(GameObject g) {
        super(g);
        this.tag = Tag.DRAGGING;
        this.pressed = false;
    }

    public DraggingComponent(Element l, GameObject g){
        super(l, g);
        this.parent = g;
        this.pressed = false;
        this.tag = Tag.DRAGGING;
        //we are going to assume that if you are holding an object when you save
        //no you are not
        //many such cases
    }

    @Override
    public Element savify(Document d) {
        return d.createElement(this.getClass().toString());
    }

    @Override
    public void onMouseDragged(Vec2d e) {if(pressed){UpdatePosition(e);}}

    @Override
    public void onMouseReleased(Vec2d e) {
        EndDragEvent(e);
        pressed = false;
    }

    @Override
    public void onMousePressed(Vec2d e){
        if(parent.getTransform().pos.y <= e.y && e.y <= parent.getTransform().pos.y+parent.getTransform().size.y){
            if(parent.getTransform().pos.x <= e.x && e.x <= parent.getTransform().pos.x+parent.getTransform().size.x){
                BeginDragEvent(e);
                pressed = true;
            }
        }
    }

    public void ClickEvent(Vec2d e){}

    @Override
    public void onMouseClicked(Vec2d e, MouseEvent event) {
        if (parent.getTransform().pos.y <= e.y && e.y <= parent.getTransform().pos.y + parent.getTransform().size.y) {
            if(parent.getTransform().pos.x <= e.x && e.x <= parent.getTransform().pos.x + parent.getTransform().size.x) {
                ClickEvent(e);
            }
        }
    }
    protected void UpdatePosition(Vec2d e){}

    protected void EndDragEvent(Vec2d e){}

    protected void BeginDragEvent(Vec2d e) {}


}
