package finalboss.custom_components.enemy;

import engine.components.AIComponent;
import engine.decisiontree_AI.*;
import engine.hierarchy.GameObject;
import engine.components.Tag;
import engine.Direction;
import java.util.LinkedList;
import java.util.List;

public class HatmanAIComponent extends AIComponent {
    public HatmanAIComponent(GameObject g, int sprite_size, GameObject target) {
        super(g, sprite_size, target);
        this.tag = Tag.AI;
        this.moveQueue = new LinkedList<>();
        this.root = new Selector();
        this.target = target;
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
            HatmanAIComponent ai = (HatmanAIComponent) object.getComponent(Tag.AI);
            if(ai != null) {
                List<Direction> l = a.call(ai.target.getTransform().pos, g.getTransform().pos, new sample_heuristic(), ai.grid, ai.sprite_size);
                for(Direction d : l){
                    ai.moveQueue.offer(d);
                    ai.moveQueue.offer(d);
                    ai.moveQueue.offer(d);
                    ai.moveQueue.offer(d);
                    ai.moveQueue.offer(d);
                }
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
                HatmanAIComponent ai = (HatmanAIComponent) object.getComponent(Tag.AI);
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
                HatmanAIComponent ai = (HatmanAIComponent) object.getComponent(Tag.AI);
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
                HatmanAIComponent ai = (HatmanAIComponent) object.getComponent(Tag.AI);
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
                HatmanAIComponent ai = (HatmanAIComponent) object.getComponent(Tag.AI);
                if(!ai.moveQueue.isEmpty()){
                    ai.moveSpeed = 8;
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

}
