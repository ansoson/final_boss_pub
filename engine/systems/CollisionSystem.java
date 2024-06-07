package engine.systems;

import engine.components.CollisionComponent;
import engine.components.Tag;
import engine.hierarchy.GameObject;
import engine.hierarchy.GameSystem;
import engine.hierarchy.GameWorld;
import engine.support.Vec2d;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CollisionSystem extends GameSystem {

    protected HashMap<Integer, List<GameObject>> layers;
    protected HashMap<Integer, List<Integer>> colLayers;
    protected List<GameObject> staticObjects;

    public CollisionSystem(String name, Vec2d defaultWindowSize, GameWorld parent) {
        super(name, defaultWindowSize, parent);
        layers = new HashMap<>();
        colLayers = new HashMap<>();
        staticObjects = new ArrayList<>();
    }

    public void addStaticObject(GameObject o){
        staticObjects.add(o);
    }

    @Override
    public void addGameObject(GameObject o) {
        CollisionComponent a = (CollisionComponent) o.getComponent(Tag.COLLISION);
        if(!layers.containsKey(a.layer)) {
            addLayer(a.layer);
        }
        List<GameObject> e = layers.get(a.layer);
        e.add(o);
        layers.put(a.layer, e);
    }

    @Override
    public void acceptObject(GameObject o){
        for(GameObject gg : this.gameObjects){
            if(gg.id == o.id){
                return;
            }
        }
        if(o.getComponent(Tag.COLLISION) != null){
            CollisionComponent c = (CollisionComponent) o.getComponent(Tag.COLLISION);
            if(c.stat){
                addStaticObject(o);
            }
            else {
                addGameObject(o);
            }
        }
    }

    @Override
    public void removeGameObject(GameObject o){
        CollisionComponent c = (CollisionComponent) o.getComponent(Tag.COLLISION);
        if(c != null) {
            if (c.stat) {
                staticObjects.remove(o);
            } else {
                List<GameObject> l = layers.get(c.layer);
                l.remove(o);
                layers.put(c.layer, l);
            }
        }
    }

    public void addLayer(Integer i){
        ArrayList<Integer> l = new ArrayList<>();
        l.add(i);
        colLayers.put(i,l);
        layers.put(i, new ArrayList<>());

    }

    public void addCollisionLayer(Integer i1, Integer i2){
        if(!colLayers.containsKey(i1)){
            addLayer(i1);
        }
        if(!colLayers.containsKey(i2)){
            addLayer(i2);
        }
        List<Integer> i1list = colLayers.get(i1);
        List<Integer> i2list = colLayers.get(i2);
        i1list.add(i2);
        i2list.add(i1);
        colLayers.put(i1, i1list);
        colLayers.put(i2, i2list);
    }

    public void onTick(long nanosSinceLastTick) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        for(GameObject o: trash){
            removeGameObject(o);
        }
        trash.clear();
        for (Integer i : layers.keySet().stream().sorted().collect(Collectors.toList())) { //iterate through each layer
            for (GameObject g : layers.get(i)) { //iterate through each gameobject in layer
                collisionTickHelper(g, i);
//                for (Integer j : colLayers.get(i)) { //for each layer it can collide with...
//                        for (GameObject g2 : layers.get(j)) { //and for each game object in THOSE layers...
//                            if(!(g == g2)) {
//                                CollisionComponent x1 = (CollisionComponent) g.getComponent(Tag.COLLISION);
//                                CollisionComponent x2 = (CollisionComponent) g2.getComponent(Tag.COLLISION);
//                                if (x1.collides(x2) != null) {
//                                    x1.onCollision(g2, x1.collides(x2)); //call collision
//                                }
//                            }
//                        }
//                    }
//                for(GameObject g2 : staticObjects){ //now with the statics!
//                    CollisionComponent x1 = (CollisionComponent) g.getComponent(Tag.COLLISION);
//                    CollisionComponent x2 = (CollisionComponent) g2.getComponent(Tag.COLLISION);
//                    if (x1.collides(x2) != null) {
//                        //x1.onStaticCollision(g2, x1.collides(x2));
//                        x1.onCollision(g2, x1.collides(x2));
//                    }
//                }
            }
        }
    }

    public void collisionTickHelper(GameObject g, int i) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        for (Integer j : colLayers.get(i)) { //for each layer it can collide with...
            for (GameObject g2 : layers.get(j)) { //and for each game object in THOSE layers...
                if(!(g == g2)) {
                    CollisionComponent x1 = (CollisionComponent) g.getComponent(Tag.COLLISION);
                    CollisionComponent x2 = (CollisionComponent) g2.getComponent(Tag.COLLISION);
                    if (x1.collides(x2) != null) {
                        x1.onCollision(g2, x1.collides(x2)); //call collision
                    }
                }
            }
        }
        for(GameObject g2 : staticObjects){ //now with the statics!
            CollisionComponent x1 = (CollisionComponent) g.getComponent(Tag.COLLISION);
            CollisionComponent x2 = (CollisionComponent) g2.getComponent(Tag.COLLISION);
            if (x1.collides(x2) != null) {
                //x1.onStaticCollision(g2, x1.collides(x2));
                x1.onCollision(g2, x1.collides(x2));
            }
        }
    }


}
