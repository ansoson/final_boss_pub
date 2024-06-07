package engine.systems;

import engine.components.Component;
import engine.components.Tag;
import engine.hierarchy.GameObject;
import engine.hierarchy.GameSystem;
import engine.hierarchy.GameWorld;
import engine.support.Vec2d;
import finalboss.custom_components.main_character.weapons.WeaponComponent;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class TimerSystem extends GameSystem {
    public TimerSystem(String name, Vec2d defaultWindowSize, GameWorld parent) {
        super(name, defaultWindowSize, parent);
        accept = new ArrayList<>();
    }

    ArrayList<GameObject> accept;

    @Override
    public void acceptObject(GameObject o) {
        for (GameObject gg : this.gameObjects) {
            if (gg.id == o.id) {
                return;
            }
        }
        if (o.getComponent(Tag.UPDATE) != null || o.getComponent(Tag.INPUT) != null || o.getComponent(Tag.STAB) != null
                || o.getComponent(Tag.AI) != null || o.getComponent(Tag.PHYSICS) != null
                || o.getComponent(Tag.COLLISION) != null || o.getComponent(Tag.CENTER) != null
                || o.getComponent(Tag.PREPHYSICS) != null || o.getComponent(Tag.WEAPON) != null
                || o.getComponent(Tag.ENEMY) != null || o.getComponent(Tag.VILL) != null
            || o.getComponent(Tag.SOUND) != null) {
            addGameObject(o);
        }
    }

    //DO NOT use this function unless you know what you're doing
    public void lateGrab(GameObject e){
        accept.add(e);

    }

    public void onTick(long nanosSinceLastTick) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        for (GameObject o : trash) {
            this.gameObjects.remove(o);
        }
        trash.clear();
        for (GameObject o : gameObjects) {
            if (o.getComponent(Tag.UPDATE) != null) {
                List<Component> c = o.getComponents(Tag.UPDATE);
                for (Component cc : c) {
                    cc.tick(nanosSinceLastTick);
                }
            }
            if (o.getComponent(Tag.INPUT) != null) {
                o.getComponent(Tag.INPUT).tick(nanosSinceLastTick);
            }
            if (o.getComponent(Tag.WEAPON) != null) {
                List<Component> c = o.getComponents(Tag.WEAPON);
                for (Component cc : c) {
                    WeaponComponent w = (WeaponComponent) cc;
                    w.tick(nanosSinceLastTick);
                }
            }
            if (o.getComponent(Tag.STAB) != null) {
                o.getComponent(Tag.STAB).tick(nanosSinceLastTick);
            }
            if (o.getComponent(Tag.PREPHYSICS) != null) {
                o.getComponent(Tag.PREPHYSICS).tick(nanosSinceLastTick);
            }
            // TODO: temporary
            if (o.getComponent(Tag.PHYSICS) != null) {
                o.getComponent(Tag.PHYSICS).tick(nanosSinceLastTick);
            }
            if (o.getComponent(Tag.COLLISION) != null) {
                o.getComponent(Tag.COLLISION).tick(nanosSinceLastTick);
            }
            if (o.getComponent(Tag.ENEMY) != null) {
                o.getComponent(Tag.ENEMY).tick(nanosSinceLastTick);
            }
            if (o.getComponent(Tag.VILL) != null) {
                o.getComponent(Tag.VILL).tick(nanosSinceLastTick);
            }
        }
    }

    // ideal
    // 1. check if grounded using collision
    // 2. prephysics based on that grounded
    // 3. physics
    // 4. set not grounded again

    public void onLateTick()
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        for (GameObject o : gameObjects) {
            if (o.getComponent(Tag.CENTER) != null) {
                o.getComponent(Tag.CENTER).onLateTick();
            }
            if (o.getComponent(Tag.COLLISION) != null) {
                o.getComponent(Tag.COLLISION).onLateTick();
            }
            if(o.getComponent(Tag.ENEMY) != null){
                o.getComponent(Tag.ENEMY).onLateTick();
            }
            if (o.getComponent(Tag.WEAPON) != null) {
                List<Component> c = o.getComponents(Tag.WEAPON);
                for (Component cc : c) {
                    WeaponComponent w = (WeaponComponent) cc;
                    w.onLateTick();
                }
            }
        }
        for (GameObject o : accept) {
            this.gameObjects.add(o);
        }
        accept.clear();

    }
}
