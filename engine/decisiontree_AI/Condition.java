package engine.decisiontree_AI;

import engine.hierarchy.GameObject;

public abstract class Condition implements BehaviorTreeNode {
    //if ACTION (return TRUE/FALSE)
    //SUCCESS
    //FAIL
    public GameObject object;
    public abstract Status update(float seconds);

    public abstract void reset();
    public Condition(GameObject o){
        this.object = o;
    }

}

