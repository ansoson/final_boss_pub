package engine.decisiontree_AI;

import engine.support.Vec2d;

public interface Heuristic {
    public double call(Vec2d pos, Vec2d target, int grid_size);
}
