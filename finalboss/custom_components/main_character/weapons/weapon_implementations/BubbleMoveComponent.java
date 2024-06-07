package finalboss.custom_components.main_character.weapons.weapon_implementations;

import engine.Direction;
import engine.components.Component;
import engine.components.Tag;
import engine.components.TransformComponent;
import engine.hierarchy.GameObject;

import java.util.Random;

public class BubbleMoveComponent extends Component {
    Direction dir;
    Double mult;
    public BubbleMoveComponent(GameObject g, Direction d, double mult) {
        super(g);
        this.tag = Tag.UPDATE;
        this.dir = d;
        this.mult = mult;
    }

    @Override
    public void tick(long nanosSinceLastTick) {
        TransformComponent tc = parent.getTransform();
        Random rand = new Random();
        double r = rand.nextDouble();
        int l = 0;
        if(r > .75){l+=1;}
        if(r < .25){l-=1;}
        if(dir == Direction.LEFT) {
            parent.setTransform(tc.pos.plus((float) (-2*mult),l));
        }else{
            parent.setTransform(tc.pos.plus((float) (2*mult),l));
        }
    }
}
