package finalboss.custom_components.enemy;

import engine.components.Component;
import engine.hierarchy.GameObject;
import engine.components.Tag;

public class EnemyComponent extends Component {
    public EnemyComponent(GameObject g) {
        super(g);
        this.tag = Tag.ENEMY;
    }
}
