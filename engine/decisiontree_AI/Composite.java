package engine.decisiontree_AI;

import engine.hierarchy.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Composite implements BehaviorTreeNode {

    List<BehaviorTreeNode> children;
    BehaviorTreeNode lastRunning;
    public GameObject object;


    public Composite(){
        this.children = new ArrayList<>();
    }

    public void addChild(BehaviorTreeNode c){children.add(c);}

    @Override
    public Status update(float seconds) {
        for(BehaviorTreeNode c : children){
            Status stat = c.update(seconds);
            if(stat == Status.RUNNING){
                lastRunning = c;
                return Status.RUNNING;
            }
        }
        return Status.SUCCESS;
    }

    @Override
    public void reset() {

    }
}
