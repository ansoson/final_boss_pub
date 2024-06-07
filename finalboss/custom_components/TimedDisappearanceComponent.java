package finalboss.custom_components;

import engine.components.Component;
import engine.hierarchy.GameObject;
import engine.components.Tag;
import engine.hierarchy.GameWorld;

public class TimedDisappearanceComponent extends Component {

    int disappear;
    int current;
    GameWorld gw;
    public TimedDisappearanceComponent(GameObject g, int dis, GameWorld gw) {
        super(g);
        this.tag = Tag.STAB;
        this.disappear = dis;
        this.current = 0;
        this.gw = gw;
    }

    public void tick(long nanosSinceLastTick){
        this.current += 1;
        if(current > disappear){
            gw.throwAway(parent);
        }
    }
}
