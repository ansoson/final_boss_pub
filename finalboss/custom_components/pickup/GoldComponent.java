package finalboss.custom_components.pickup;

import engine.hierarchy.GameObject;
import engine.components.Tag;
import finalboss.gameworlds.Overworld;

import java.util.ArrayList;

public class GoldComponent extends PickupComponent {
    public GoldComponent(GameObject g, Overworld gw) {
        super(g);
        this.tag = Tag.COLLISION;
        ArrayList<GameObject> e = new ArrayList<>();
        e.add(this.parent);
        gw.grid.add(e);

    }

    @Override
    public void pickup(){
    }
}
