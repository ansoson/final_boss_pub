package finalboss.objects;

import engine.UIKit.Button;
import engine.hierarchy.Screen;
import engine.support.Vec2d;
import finalboss.gameworlds.LevelGameworld;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Debuggton extends Button {

    public Debuggton(Screen parent, String name, Vec2d pos, Vec2d size, Vec2d def, Color c) {
        super(parent, name, pos, size, def, c);
    }

    @Override
    public void ButtonEvent(MouseEvent e) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException, TransformerException {
        LevelGameworld g = (LevelGameworld) this.parent.worlds.get(0); //hacky
        g.goldNum += 1;
        g.pparent.updateCoins(g.goldNum);
        System.out.println(g.goldNum);

    }

    @Override
    public void HoverEvent(MouseEvent e) {

    }

    @Override
    public void EndHoverEvent(MouseEvent e) {

    }
}
