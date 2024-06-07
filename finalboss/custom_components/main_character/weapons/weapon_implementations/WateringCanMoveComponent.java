package finalboss.custom_components.main_character.weapons.weapon_implementations;

import engine.Direction;
import engine.components.Component;
import engine.components.Tag;
import engine.components.TransformComponent;
import engine.hierarchy.GameObject;

public class WateringCanMoveComponent extends Component {

    Direction dir;
    Double special;
    public WateringCanMoveComponent(GameObject g, Direction d, Double special) {
        super(g);
        this.tag = Tag.UPDATE;
        this.dir = d;
        this.special = special;
    }

    @Override
    public void tick(long nanosSinceLastTick) {
        TransformComponent tc = parent.getTransform();
        if(dir == Direction.LEFT) {
            parent.setTransform(tc.pos.plus(-10,0));
        }else{
            parent.setTransform(tc.pos.plus(10,0));
        }
    }
}
