package finalboss.custom_components.pickup;

import engine.hierarchy.GameObject;
import engine.components.Tag;
import finalboss.gameworlds.Overworld;

import java.util.ArrayList;

public class TeaComponent extends PickupComponent {
    public TeaComponent(GameObject g) {
        super(g);
        this.tag = Tag.COLLISION;
        ArrayList<GameObject> e = new ArrayList<>();
        e.add(this.parent);

    }

    @Override
    public void pickup(){
    }
}
