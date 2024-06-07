package finalboss.objects;

import engine.UIKit.Button;
import engine.hierarchy.GameObject;
import engine.hierarchy.GameSystem;
import engine.hierarchy.Screen;
import engine.support.Vec2d;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import finalboss.gameworlds.Overworld; //TODO: engine code should not reference front end

import java.lang.reflect.InvocationTargetException;

public class LoadButton extends Button {

    FBLoadManager l;

    boolean trashed;

    public LoadButton(Screen parent, String name, Vec2d pos, Vec2d size, Vec2d def, Color c, FBLoadManager l) {
        super(name, pos, size, def, c);
        this.c = c;
        this.parent = parent;
        this.hovering = false;
        this.on = true;
        this.l = l;
        trashed = false;
    }
    @Override
    public void HoverEvent(MouseEvent e) {}

    @Override
    public void EndHoverEvent(MouseEvent e) {}

    @Override
    public void onMouseMoved(MouseEvent e) {}


    public void ButtonEvent(MouseEvent e) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Overworld lgw = (Overworld) parent.worlds.get(0);
        for(GameSystem sys : lgw.systems){
            for(GameObject o: sys.gameObjects){
                lgw.throwAway(o);
            }
        }
        trashed = true;
    }

    public void tick(long nanos) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if(trashed){
            l.parse(l.d);
            trashed = false;
            Overworld lgw = (Overworld) parent.worlds.get(0);
        }
    }
}
