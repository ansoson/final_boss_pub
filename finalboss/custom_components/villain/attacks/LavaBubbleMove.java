package finalboss.custom_components.villain.attacks;

import engine.Direction;
import engine.components.Component;
import engine.components.Tag;
import engine.components.TransformComponent;
import engine.hierarchy.GameObject;

import java.util.Random;

public class LavaBubbleMove extends Component {
     Direction dir;

     int createTimer = 0;
     public LavaBubbleMove(GameObject g, Direction d) {
            super(g);
            this.tag = Tag.UPDATE;
            this.dir = d;
     }

        @Override
        public void tick(long nanosSinceLastTick) {
         if (createTimer > 30){
             TransformComponent tc = parent.getTransform();
             if(dir == Direction.DOWNLEFT) {
                 parent.setTransform(tc.pos.plus(-20.0f, 7.5f));
             }else if(dir == Direction.UPLEFT){
                 parent.setTransform(tc.pos.plus(-20.0f, -7.5f));
             }
             else {
                 parent.setTransform(tc.pos.plus(-20.0f, 0.0f));
             }
         }
        createTimer++;
        }
}
