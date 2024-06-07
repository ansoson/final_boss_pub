package engine.decisiontree_AI;

import engine.hierarchy.GameObject;

public abstract class Action implements BehaviorTreeNode {
    //if ACTION
    //SUCCESS
    //FAIL
    //RUNNING

    public GameObject object;

    public Action(GameObject o){
        this.object = o;
    }
    public abstract Status update(float seconds);

    public abstract void reset();
}
