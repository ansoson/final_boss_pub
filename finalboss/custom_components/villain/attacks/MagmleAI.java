package finalboss.custom_components.villain.attacks;

import engine.Direction;
import engine.components.AIComponent;
import engine.components.SpriteComponent;
import engine.components.Tag;
import engine.decisiontree_AI.*;
import engine.hierarchy.GameObject;
import engine.support.Vec2d;

import java.util.LinkedList;
import java.util.List;

public class MagmleAI extends AIComponent {

    SpriteComponent sc;
    public MagmleAI(GameObject g, int sprite_size, GameObject target, SpriteComponent sc) {
        super(g, sprite_size, target);
        this.tag = Tag.AI;
        this.moveQueue = new LinkedList<>();
        this.root = new Selector();
        this.target = target;
        this.sc = sc;
        sc.setVcel(1);
        Sequence close = new Sequence();
        Sequence con = new Sequence();
        Sequence far = new Sequence();
        root.addChild(close);
        root.addChild(con);
        root.addChild(far);
        far.addChild(new Condition(this.parent) {
            @Override
            public Status update(float seconds) {
                return Status.SUCCESS;
            }

            @Override
            public void reset() {

            }
        });
        far.addChild(new Action(this.parent) {
            @Override
            public Status update(float seconds) {
                astar a = new astar();
                MagmleAI ai = (MagmleAI) object.getComponent(Tag.AI);
                if(ai != null) {
                    List<Direction> l = a.call(ai.target.getTransform().pos, g.getTransform().pos, new sample_heuristic(), ai.grid, ai.sprite_size);
                    for(int i = 0; i < 5; i++) {
                        if(l.size() > i) {
                            ai.moveQueue.offer(l.get(i));
                            ai.moveQueue.offer(l.get(i));
                        }
                    }
                    l.clear();
                }
                return Status.SUCCESS;
            }

            @Override
            public void reset() {

            }
        });
        close.addChild(new Condition(this.parent) {
            @Override
            public Status update(float seconds) {
                MagmleAI ai = (MagmleAI) object.getComponent(Tag.AI);
                if (Math.pow(Math.pow((ai.target.getTransform().pos.y- object.getTransform().pos.y)/ai.sprite_size, 2)+ Math.pow((ai.target.getTransform().pos.x-object.getTransform().pos.x)/sprite_size, 2), 0.5) < 3){
                    if(!ai.moveQueue.isEmpty()) {
                        return Status.SUCCESS;
                    }
                }
                return Status.FAIL;
            }

            @Override
            public void reset() {

            }
        });
        close.addChild(new Action(this.parent) {
            @Override
            public Status update(float seconds) {
                MagmleAI ai = (MagmleAI) object.getComponent(Tag.AI);
                ai.moveSpeed = 1;
                Direction d = ai.moveQueue.poll();
                ai.move(d);
                return Status.SUCCESS;
            }
            @Override
            public void reset() {
            }
        });
        con.addChild(new Condition(this.parent) {
            @Override
            public Status update(float seconds) {
                MagmleAI ai = (MagmleAI) object.getComponent(Tag.AI);
                if(!ai.moveQueue.isEmpty()){
                    return Status.SUCCESS;
                }
                return Status.FAIL;
            }

            @Override
            public void reset() {

            }
        });
        con.addChild(new Action(this.parent) {
            @Override
            public Status update(float seconds) {
                MagmleAI ai = (MagmleAI) object.getComponent(Tag.AI);
                if(!ai.moveQueue.isEmpty()){
                    ai.moveSpeed = 4;
                    Direction d = ai.moveQueue.poll();
                    ai.move(d);
                    return Status.SUCCESS;
                }
                return Status.FAIL;
            }
            @Override
            public void reset() {
            }
        });
    }

    @Override
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
                    sc.setVcel(1);
                }
                if (d == Direction.RIGHT) {
                    parent.setTransform(new Vec2d(t.x+ (double) sprite_size /2, t.y));
                    sc.setVcel(0);
                }
                current = 0;
            }
        }
    }

}