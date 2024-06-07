package engine.components;

import engine.hierarchy.GameObject;

public class TimerComponent extends Component {
    public TimerComponent(GameObject g) {
        super(g);
        this.tag = Tag.UPDATE;
    }

    public void tick(long nanos){

    }
}
