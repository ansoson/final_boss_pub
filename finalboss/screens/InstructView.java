package finalboss.screens;

import engine.UIKit.HAlignment;
import engine.UIKit.VAlignment;
import finalboss.gameworlds.Overworld;
import finalboss.objects.IntructButton;
import finalboss.objects.MenuButton;
import finalboss.objects.RestartButton;
import engine.UIKit.Button;
import engine.UIKit.Rectangle;
import engine.UIKit.Text;
import engine.hierarchy.Application;
import engine.hierarchy.Screen;
import engine.support.Vec2d;
import javafx.scene.paint.Color;

public class InstructView extends Screen {

        Overworld overworld;

        public InstructView(String name, Vec2d defaultWindowSize, Application parent, Overworld og, boolean active) {
                super(name, defaultWindowSize, parent, null, active);
                this.overworld = og;
        }

        public void onStartup() {
                Rectangle bg = new Rectangle(new Vec2d(0, 0), windowSize, windowSize, Color.GRAY);

                Text title = new Text(Color.BLACK, new Vec2d(0, 20),
                                70, windowSize, "HOW TO PLAY", "Pixel",
                                HAlignment.CENTER, VAlignment.UPPER, true);

                Button back = new MenuButton(this, "BACK",
                                new Vec2d(-250, 450),
                                new Vec2d(200, 50), windowSize, Color.ORANGE, Color.ORANGE.darker(),
                                overworld);

                Text b = new Text(back, Color.BLACK, new Vec2d(0, 5),
                                30, windowSize, "BACK", "Pixel",
                                HAlignment.CENTER, VAlignment.CENTER, true);

                Button begin = new RestartButton(this, "START",
                                new Vec2d(250, 450),
                                new Vec2d(200, 50), windowSize, Color.ORANGE, Color.ORANGE.darker(),
                                overworld);

                Text b2 = new Text(begin, Color.BLACK, new Vec2d(0, 5),
                                30, windowSize, "BEGIN", "Pixel",
                                HAlignment.CENTER, VAlignment.CENTER, true);

                Rectangle divide = new Rectangle(new Vec2d(80, 125), new Vec2d(800, 400), windowSize, Color.BLACK);

                ui.add(bg);
                ui.add(title);
                ui.add(divide);
                ui.add(back);
                ui.add(begin);
                ui.add(b2);

                int spacing = 60;

                ui.add(new Text(divide, Color.WHITESMOKE, new Vec2d(20, 20),
                                30, windowSize, "Move -> WASD or Arrow Keys", "Pixel",
                                HAlignment.LEFT, VAlignment.UPPER, true));

                ui.add(new Text(divide, Color.WHITESMOKE, new Vec2d(20, 20 + spacing),
                                30, windowSize, "Dash -> Space Bar", "Pixel",
                                HAlignment.LEFT, VAlignment.UPPER, true));

                ui.add(new Text(divide, Color.WHITESMOKE, new Vec2d(20, 20 + spacing * 2),
                                30, windowSize, "Attack -> Z and X or L and R Click", "Pixel",
                                HAlignment.LEFT, VAlignment.UPPER, true));

                ui.add(new Text(divide, Color.WHITESMOKE, new Vec2d(20, 20 + spacing * 3),
                                30, windowSize, "Switch weapons -> C", "Pixel",
                                HAlignment.LEFT, VAlignment.UPPER, true));

                ui.add(new Text(divide, Color.WHITESMOKE, new Vec2d(20, 20 + spacing * 4),
                                30, windowSize, "Open menu -> ESC", "Pixel",
                                HAlignment.LEFT, VAlignment.UPPER, true));
        }

}
