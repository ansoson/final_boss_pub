package finalboss.screens;

import engine.UIKit.Rectangle;
import engine.UIKit.Text;
import engine.hierarchy.Application;
import engine.hierarchy.Screen;
import engine.support.Vec2d;
import javafx.scene.paint.Color;

public class ErrorView extends Screen {

    String s;

    public ErrorView(String name, Vec2d defaultWindowSize, Application parent, boolean active) {
        super(name, defaultWindowSize, parent, null, active);
        this.s = "sample text";
    }

    public void setS(String s) {
        this.s = s;
    }

    public void onStartup() {
        Rectangle bg = new Rectangle(new Vec2d(0,0), windowSize, windowSize, Color.BLACK);
        Text title = new Text(Color.BLUE, new Vec2d(0, windowSize.y/4), 40,windowSize, "you threw this error, and now you're gonna have a baaaad time", "Comic Sans");
        Text b= new Text(Color.BLUE.desaturate(), new Vec2d(0, 4*windowSize.y/5), 40,windowSize, s, "Comic Sans");
        ui.add(bg);
        ui.add(title);
        ui.add(b);
    }
}
