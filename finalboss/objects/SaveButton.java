package finalboss.objects;

import engine.UIKit.Button;
import engine.hierarchy.Screen;
import engine.support.Vec2d;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class SaveButton extends Button {

    FBSaveManager s;

    public SaveButton(Screen parent, String name, Vec2d pos, Vec2d size, Vec2d def, Color c, FBSaveManager s) {
        super(name, pos, size, def, c);
        this.c = c;
        this.parent = parent;
        this.hovering = false;
        this.on = true;
        this.s = s;
    }
    @Override
    public void HoverEvent(MouseEvent e) {}

    @Override
    public void EndHoverEvent(MouseEvent e) {}

    @Override
    public void onMouseMoved(MouseEvent e) {}
    public void ButtonEvent(MouseEvent e) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException, TransformerException {
        s.save();
    }
}

