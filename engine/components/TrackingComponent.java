package engine.components;

import engine.hierarchy.GameObject;
import engine.support.Vec2d;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TrackingComponent extends Component {

    int gridSize;
    public TrackingComponent(GameObject g, int gridSize) {
        super(g);
        this.tag = Tag.TRACKING;
        this.gridSize = gridSize;
    }

    public TrackingComponent(Element e, GameObject g){
        super(e,g);
        this.gridSize = Integer.parseInt(e.getAttribute("grid_size"));
    }

    @Override
    public Element savify(Document d) {
        Element e = d.createElement("TrackingComponent");
        e.setAttribute("grid_size", String.valueOf(gridSize));
        return e;
    }

    public Vec2d track(){
        Vec2d pos = parent.getTransform().pos; //making assumption (correctly for now) that size doesn't factor in
        double x = Math.floor(pos.x/(double)gridSize);
        double y = Math.floor(pos.y/(double)gridSize);
        return new Vec2d(x,y);
    }
}
