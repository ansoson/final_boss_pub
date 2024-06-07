package engine.decisiontree_AI;

import engine.components.Component;

import java.util.ArrayList;

public class Selector extends Composite {

    public Selector(){
        this.children = new ArrayList<>();
    }

    public void addChild(BehaviorTreeNode c){children.add(c);}


    @Override
    public Status update(float seconds) {
        for(BehaviorTreeNode c : children){
            Status stat = c.update(seconds);
            if(stat != Status.FAIL){
                lastRunning = c;
                return Status.RUNNING;
            }
        }
        return Status.SUCCESS;
    }
}
