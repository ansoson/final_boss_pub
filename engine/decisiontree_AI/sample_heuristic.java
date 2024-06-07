package engine.decisiontree_AI;

import engine.support.Vec2d;

public class sample_heuristic implements Heuristic {
    public double call(Vec2d target_pos, Vec2d current_pos, int grid_size){
        return Math.pow(Math.pow((target_pos.y-current_pos.y)/grid_size, 2)+ Math.pow((target_pos.x-current_pos.x)/grid_size, 2), 0.5);
    }
}
