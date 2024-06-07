package engine.systems;

import engine.components.Tag;
import engine.hierarchy.GameObject;
import engine.hierarchy.GameSystem;
import engine.hierarchy.GameWorld;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;

import java.util.*;

public class GraphicsSystem extends GameSystem {

    public HashMap<Double, HashMap< Double, GameObject>> drawOrder;

    public GraphicsSystem(String name, Vec2d defaultWindowSize, GameWorld parent) {
        super(name, defaultWindowSize, parent);
        this.drawOrder = new HashMap<Double, HashMap< Double, GameObject>>();
    }

    @Override
    public void addGameObject(GameObject o){
        this.gameObjects.add(o);
        if(!drawOrder.containsKey(o.z)) {
            addLayer(o.z);
        }
        double toaddDouble = o.getTransform().pos.y + o.getTransform().size.y;
        while (this.drawOrder.get(o.z).containsKey(toaddDouble)){
            toaddDouble = toaddDouble -.00001;
        }
        this.drawOrder.get(o.z).put(toaddDouble, o);
    }

    public void addLayer(Double i){
        drawOrder.put(i, new HashMap<>());
    }

    @Override
    public void removeGameObject(GameObject o){
        super.removeGameObject(o);
        if(drawOrder.containsKey(o.z)) {
            HashMap<Double, GameObject> list = drawOrder.get(o.z);
            if (list.containsValue(o)) {
                list.values().remove(o);
                drawOrder.put(o.z, list);
            }
        }
    }

    @Override
    public void acceptObject(GameObject o){
        for(GameObject gg : this.gameObjects){
            if(gg.id == o.id){
                return;
            }
        }
        if(o.getComponent(Tag.GRAPHICS) != null){
            addGameObject(o);
        }
    }


    public void onDraw(GraphicsContext g){
        for (Double layer: drawOrder.keySet()) {
            HashMap<Double, GameObject> objectsInThisLayer = drawOrder.get(layer);
            HashMap<Double, GameObject> newMapOfObjectsInThisLayer = new HashMap<>();
            ArrayList<Double> drawFor = new ArrayList<>();
            for(Double o : objectsInThisLayer.keySet()) {
                GameObject obj = objectsInThisLayer.get(o);
                Double y = obj.getTransform().pos.y;
                while (newMapOfObjectsInThisLayer.containsKey(y)){
                    y = y - .0001;
                }
                newMapOfObjectsInThisLayer.put(y, obj);
                drawFor.add(y);
            }
            Collections.sort(drawFor);
            for(Double o : drawFor) {
                newMapOfObjectsInThisLayer.get(o).getComponent(Tag.GRAPHICS).onDraw(g);
            }
        }
    }

}
