package finalboss.custom_components.enemy;

import engine.components.Component;
import engine.hierarchy.GameObject;
import engine.support.Vec2d;
import engine.components.Tag;
import engine.Direction;

import java.util.Random;

public class SunMoveComponent extends Component {
    public SunMoveComponent(Tag tag, GameObject g) {
        super(tag, g);
    }
    int interval;
    int current;
    Direction currentDirection;
    int sprite_size;

    public SunMoveComponent(GameObject g, int interval, int spriteSize) {
        super(g);
        this.tag = Tag.UPDATE;
        this.interval = interval;
        this.current = 0;
        this.sprite_size = spriteSize;
        this.currentDirection = Direction.NONE;
        Random rand = new Random();
        int i = rand.nextInt(3);
        if(i == 0){
            this.currentDirection = Direction.UP;
        }
        else if(i == 1){
            this.currentDirection = Direction.LEFT;
        }
        else if(i == 2){
            this.currentDirection = Direction.RIGHT;
        }
        else{
            this.currentDirection = Direction.DOWN;
        }
    }

    public void reverse(){
        if(currentDirection == Direction.UP){
            this.currentDirection = Direction.DOWN;
        }
        else if(currentDirection == Direction.DOWN){
            this.currentDirection = Direction.UP;
        }
        else if(currentDirection == Direction.LEFT){
            this.currentDirection = Direction.RIGHT;
        }
        else if(currentDirection == Direction.RIGHT){
            this.currentDirection = Direction.LEFT;
        }
    }
    @Override
    public void tick(long nanosSinceLastTick){
        current += 1;
        if (current > interval) {
            Vec2d t = parent.getTransform().pos;
            if (currentDirection == Direction.UP) {
                parent.setTransform(new Vec2d(t.x, t.y- (double) sprite_size /5));
            }
            if (currentDirection == Direction.DOWN) {
                parent.setTransform(new Vec2d(t.x, t.y+ (double) sprite_size /5));
            }
            if (currentDirection == Direction.LEFT) {
                parent.setTransform(new Vec2d(t.x- (double) sprite_size /5, t.y));
            }
            if (currentDirection == Direction.RIGHT) {
                parent.setTransform(new Vec2d(t.x+(double)sprite_size/5, t.y));
            }
            current = 0;
        }
    }
}
