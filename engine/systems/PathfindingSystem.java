package engine.systems;

import engine.components.AIComponent;
import engine.components.Tag;
import engine.components.TrackingComponent;
import engine.hierarchy.GameObject;
import engine.hierarchy.GameSystem;
import engine.hierarchy.GameWorld;
import engine.support.Vec2d;

public class PathfindingSystem extends GameSystem {
    int sizeVert;
    int sizeHor;

    int[][] grid;
    public PathfindingSystem(String name, Vec2d defaultWindowSize, GameWorld parent, int sizeVert, int sizeHor) {
        super(name, defaultWindowSize, parent);
        this.sizeHor = sizeHor;
        this.sizeVert = sizeVert;
    }

    public void updateLevel(int sizeVert, int sizeHor){this.sizeVert = sizeVert; this.sizeHor=sizeHor;}

    @Override
    public void acceptObject(GameObject o){
        for(GameObject gg : this.gameObjects){
            if(gg.id == o.id){
                return;
            }
        }
        if(o.getComponent(Tag.TRACKING) != null || o.getComponent(Tag.AI) != null){
            addGameObject(o);
        }
    }

    @Override
    public void onTick(long nanosSinceLastTick){
        grid = new int[sizeHor][sizeVert];
        for(GameObject o : this.gameObjects){
            if(o.getComponent(Tag.TRACKING) != null){
                TrackingComponent t = (TrackingComponent) o.getComponent(Tag.TRACKING);
                Vec2d trackPos = t.track();
                grid[(int) trackPos.x][(int) trackPos.y] = 1;
            }
        }
        for(GameObject o: this.gameObjects) {
            if (o.getComponent(Tag.AI) != null) {
                AIComponent a = (AIComponent) o.getComponent(Tag.AI);
                a.updateTracking(grid);
            }
        }

    }

}
