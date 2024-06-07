package finalboss.objects;

import engine.UIKit.Button;
import engine.hierarchy.Screen;
import engine.support.Vec2d;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import finalboss.gameworlds.Overworld;

public class StartButton extends Button {

    Overworld g;

    public StartButton(Screen parent, String name, Vec2d pos, Vec2d size, Vec2d def, Color c, Overworld g) {
        super(parent, name, pos, size, def, c);
        this.g = g;
    }

    public void ButtonEvent(MouseEvent e){
        parent.parent.screenOn = 1;
    }
    @Override
    public void HoverEvent(MouseEvent e) {}

    @Override
    public void EndHoverEvent(MouseEvent e) {}
}
