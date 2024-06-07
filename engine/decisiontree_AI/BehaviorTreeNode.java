package engine.decisiontree_AI;

public interface BehaviorTreeNode {
    Status update(float seconds);
    void reset();
}

