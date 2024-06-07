package finalboss.objects;

import engine.UIKit.Button;
import engine.UIKit.HAlignment;
import engine.UIKit.UIObject;
import engine.UIKit.VAlignment;
import engine.hierarchy.Screen;
import engine.support.Vec2d;
import finalboss.gameworlds.LevelGameworld;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class IntructButton extends Button {

    LevelGameworld g;
    Color baseColor;
    Color hoverColor;

    public IntructButton(Screen parent, String name, Vec2d pos, Vec2d size, Vec2d def, Color c,
                         Color hoverColor, LevelGameworld g) {
        super(parent, name, pos, size, def, c, HAlignment.CENTER, VAlignment.UPPER, true);
        this.g = g;
        this.baseColor = c;
        this.hoverColor = hoverColor;
        this.parent = parent;
    }

    public void ButtonEvent(MouseEvent e){
        
        parent.parent.screenOn = 6;
        parent.parent.screens.get(parent.parent.screenOn).setActive();;

        for (Screen s: parent.parent.screens){
            if (parent.parent.screens.indexOf(s) != parent.parent.screenOn){
                s.active = false;
            }
        }

        g.gameReset();
    }


    @Override
    public void HoverEvent(MouseEvent e) {
        this.c = hoverColor;
    }

    @Override
    public void EndHoverEvent(MouseEvent e) {
        this.c = baseColor;
    }
}
