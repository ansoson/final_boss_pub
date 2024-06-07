package engine.decisiontree_AI;

import java.util.ArrayList;

public class Sequence extends Composite {

    public Sequence(){
        this.children = new ArrayList<>();
    }

    public void addChild(BehaviorTreeNode c){children.add(c);}


    @Override
    public Status update(float seconds) {
        int i = children.indexOf(lastRunning);
        if(i == -1){ i=0;}
        for(int j = i; j < children.size(); j++) {
            BehaviorTreeNode n = children.get(j);
            Status stat = n.update(seconds);
            if (stat != Status.SUCCESS){
                lastRunning = n;
                return Status.FAIL;
            }
        }
        return Status.SUCCESS;
    }

}
