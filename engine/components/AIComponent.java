package engine.components;

import engine.decisiontree_AI.*;
import engine.hierarchy.GameObject;
import engine.support.Vec2d;
import engine.Direction;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AIComponent extends Component {

    public Queue<Direction> moveQueue;

    public GameObject target;

    public Composite root;
    public int moveSpeed;
    protected int current;
    public int sprite_size;
    public int[][] grid;

    public AIComponent(Element e, GameObject g){
        super(e,g);
        this.parent = g;
        this.tag = Tag.AI;
        this.moveQueue = new LinkedList<Direction>();
        //refresh movequeue
        this.root = new Selector();
        this.target = parent.parent.getGameObject(Integer.parseInt(e.getAttribute("target")));
        root.addChild(new Condition(this.parent) {
            @Override
            public Status update(float seconds) {
                AIComponent ai = (AIComponent) object.getComponent(Tag.AI);
                if(ai.moveQueue.isEmpty()) {
                    return Status.SUCCESS;
                }
                return Status.FAIL;
            }

            @Override
            public void reset() {

            }
        });
        root.addChild(new Action(this.parent) {
            @Override
            public Status update(float seconds) {
                astar a = new astar();
                AIComponent ai = (AIComponent) object.getComponent(Tag.AI);
                if(ai != null) {
                    List<Direction> l = a.call(ai.target.getTransform().pos, g.getTransform().pos, new sample_heuristic(), ai.grid, ai.sprite_size);
                    for(Direction d : l){
                        ai.moveQueue.offer(d);
                        ai.moveQueue.offer(d);
                        ai.moveQueue.offer(d);
                        ai.moveQueue.offer(d);
                        ai.moveQueue.offer(d);
                    }
                    return Status.SUCCESS;
                }
                return Status.FAIL;
            }

            @Override
            public void reset() {

            }
        });

        this.moveSpeed = 8; this.current = 0;
        this.sprite_size = Integer.parseInt(e.getAttribute("sprite_size"));
    }

    @Override
    public Element savify(Document d) {
        //Assumption: AI can be refilled queue-wise, at least for now
        Element e = d.createElement("AIComponent");
        e.setAttribute("sprite_size", String.valueOf(sprite_size));
        e.setAttribute("target", String.valueOf(target.id));
        return e;

    }

    public AIComponent(GameObject g, int spriteSize, GameObject target) {
        super(g);
        this.tag = Tag.AI;
        this.moveQueue = new LinkedList<Direction>();
        this.root = new Selector();
        this.target = target;
        root.addChild(new Condition(this.parent) {
            @Override
            public Status update(float seconds) {
                AIComponent ai = (AIComponent) object.getComponent(Tag.AI);
                if(ai.moveQueue.isEmpty()) {
                    return Status.SUCCESS;
                }
                return Status.FAIL;
            }

            @Override
            public void reset() {

            }
        });
        root.addChild(new Action(this.parent) {
            @Override
            public Status update(float seconds) {
                astar a = new astar();
                AIComponent ai = (AIComponent) object.getComponent(Tag.AI);
                if(ai != null) {
                    List<Direction> l = a.call(ai.target.getTransform().pos, g.getTransform().pos, new sample_heuristic(), ai.grid, ai.sprite_size);
                    for(Direction d : l){
                        ai.moveQueue.offer(d);
                        ai.moveQueue.offer(d);
                        ai.moveQueue.offer(d);
                        ai.moveQueue.offer(d);
                        ai.moveQueue.offer(d);
                    }
                    return Status.SUCCESS;
                }
                return Status.FAIL;
            }

            @Override
            public void reset() {

            }
        });

        this.moveSpeed = 8; this.current = 0;
        this.sprite_size = spriteSize;
    }

    public void updateTracking(int[][] grid){
        if(current > moveSpeed) {
            this.grid = grid;
            root.update(1);
            current = 0;
        }
        current++;

    }

    public void move(Direction d){
        if(d != Direction.NONE && d != null) {
            current += 1;
            if (current > moveSpeed) {
                Vec2d t = parent.getTransform().pos;
                if (d == Direction.UP) {
                    parent.setTransform(new Vec2d(t.x, t.y- (double) sprite_size /2));
                }
                if (d == Direction.DOWN) {
                    parent.setTransform(new Vec2d(t.x, t.y+ (double) sprite_size /2));
                }
                if (d == Direction.LEFT) {
                    parent.setTransform(new Vec2d(t.x- (double) sprite_size /2, t.y));
                }
                if (d == Direction.RIGHT) {
                    parent.setTransform(new Vec2d(t.x+ (double) sprite_size /2, t.y));
                }
                current = 0;
            }
        }
    }
}
