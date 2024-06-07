package engine.decisiontree_AI;

import engine.support.Vec2d;
import engine.Direction;

import java.util.*;

public class astar {

    private static class recursive_cost implements Comparable<recursive_cost> {

        Vec2d pos;
        double cost;
        Direction d;
        int level;
        public recursive_cost(Vec2d pos, double cost, Direction d, int level){
            this.pos = pos;
            this.cost = cost;
            this.d = d;
            this.level = level;
        }

        @Override
        public int compareTo(recursive_cost r) {
            return Double.compare(cost, r.cost);
        }


    }



    public List<Direction> call(Vec2d pos, Vec2d goal, Heuristic heur, int[][] grid, int sprite_size) {
        pos = floor_vector(pos, sprite_size);
        goal = floor_vector(goal, sprite_size);
        PriorityQueue<recursive_cost> q = new PriorityQueue<>();
        recursive_cost origin = new recursive_cost(pos, 0, Direction.NONE, 0);
        HashMap<Vec2d, recursive_cost> parents = new HashMap<Vec2d, recursive_cost>();
        q.add(origin);
        parents.put(pos, new recursive_cost(new Vec2d(Integer.MAX_VALUE, Integer.MAX_VALUE), 0, Direction.NONE, 0));
        while (!q.isEmpty()) {
            recursive_cost x = q.poll();
            if(x.pos.x == goal.x && x.pos.y == goal.y) {
                Deque<Direction> d = new ArrayDeque<Direction>();
                while (x.d != Direction.NONE) {
                    d.addFirst(x.d);
                    x = parents.get(x.pos);
                }
                return new ArrayList<>(d);
            }
            HashMap<Vec2d, Direction> stock = get_neighbors(x.pos, sprite_size, grid);
            for (Map.Entry<Vec2d, Direction> entry : stock.entrySet()) {
                if(!parents.containsKey(entry.getKey())) {
                    double a = heur.call(entry.getKey(), goal, sprite_size);
                    recursive_cost n = new recursive_cost(entry.getKey(), x.level + 1 + a, entry.getValue(), x.level + 1);
                    q.add(n);
                    parents.put(entry.getKey(), x);
                }
            }
        }
        return new ArrayList<>();
    }

    HashMap<Vec2d, Direction> get_neighbors(Vec2d vec, int sprite_size, int[][] grid){
        int gl = grid.length;
        int gh = grid[0].length;
        HashMap<Vec2d, Direction> vn = new HashMap<>();
        Vec2d up = new Vec2d(vec.x, vec.y - sprite_size);
        Vec2d down = new Vec2d(vec.x, vec.y + sprite_size);
        Vec2d left = new Vec2d(vec.x-sprite_size, vec.y);
        Vec2d right = new Vec2d(vec.x+sprite_size, vec.y);
        if(!(up.x/sprite_size < 0 || up.x/sprite_size >= gh) && !(up.y/sprite_size < 0 || up.y/sprite_size >= gh)) {
            if (grid[(int) (up.x / sprite_size)][(int) (up.y / sprite_size)] != 1) {
                vn.put(up, Direction.DOWN);
            }
        }
        if(!(down.x/sprite_size < 0 || down.x/sprite_size >= gh) && !(down.y/sprite_size < 0 || down.y/sprite_size >= gh)) {
            if (grid[(int) (down.x / sprite_size)][(int) (down.y / sprite_size)] != 1) {
                vn.put(down, Direction.UP);
            }
        }
        if(!(left.x/sprite_size < 0 || left.x/sprite_size >= gl) && !(left.y/sprite_size < 0 || left.y/sprite_size >= gl)) {
            if (grid[(int) (left.x / sprite_size)][(int) (left.y / sprite_size)] != 1) {
                vn.put(left, Direction.RIGHT);
            }
        }
        if(!(right.x/sprite_size < 0 || right.x/sprite_size >= gl) && !(right.y/sprite_size < 0 || right.y/sprite_size >= gl)) {
            if (grid[(int) (right.x / sprite_size)][(int) (right.y / sprite_size)] != 1) {
                vn.put(right, Direction.LEFT);
            }
        }
        return vn;
    }

    //floor
    Vec2d floor_vector(Vec2d v, int sprite_size){
        double x = Math.floor(v.x/sprite_size)*sprite_size;
        double y = Math.floor(v.y/sprite_size)*sprite_size;
        return new Vec2d(x,y);
    }

}
