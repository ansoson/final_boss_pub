package finalboss.screens;

import engine.UIKit.HAlignment;
import engine.UIKit.VAlignment;
import finalboss.gameworlds.Overworld;
import finalboss.objects.IntructButton;
import finalboss.objects.RestartButton;
import engine.UIKit.Button;
import engine.UIKit.Rectangle;
import engine.UIKit.Text;
import engine.UIKit.UIObject;
import engine.hierarchy.Application;
import engine.hierarchy.GameWorld;
import engine.hierarchy.Screen;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

public class MenuView extends Screen {

        Overworld overworld;
        Image bg2;

        public MenuView(String name, Vec2d defaultWindowSize, Application parent, Overworld og, boolean active) {
                super(name, defaultWindowSize, parent, null, active);
                this.overworld = og;
        }

        public void onStartup() {
                //Rectangle bg = new Rectangle(new Vec2d(0, 0), windowSize, windowSize, Color.RED.brighter());
                bg2 = new Image("finalboss/sprites/title.png");

                Button begin = new RestartButton(this, "START",
                                new Vec2d(0, 300),
                                new Vec2d(300, 100), windowSize, Color.ORANGE, Color.ORANGE.darker(),
                                overworld);

                Button instruct = new IntructButton(this, "INSTRUCTIONS",
                                new Vec2d(0, 410),
                                new Vec2d(300, 55), windowSize, Color.ORANGE, Color.ORANGE.darker(),
                                overworld);

                // Text title = new Text(Color.BLACK, new Vec2d(0, windowSize.y / 3.5),
                //                 100, windowSize, "FINAL BOSS", "Pixel",
                //                 HAlignment.CENTER, VAlignment.UPPER, true);

                Text b = new Text(begin, Color.BLACK, new Vec2d(0, 10),
                                50, windowSize, "BEGIN", "Pixel",
                                HAlignment.CENTER, VAlignment.CENTER, true);

                Text i = new Text(instruct, Color.BLACK, new Vec2d(0, 5),
                                30, windowSize, "INSTRUCTIONS", "Pixel",
                                HAlignment.CENTER, VAlignment.CENTER, true);

                //ui.add(bg);
                ui.add(begin);
                ui.add(instruct);
                //ui.add(title);
                ui.add(b);
        }

        @Override
        protected void onDraw(GraphicsContext g) {
                if (active) {
                        g.drawImage(bg2, 0,0, 965, 540);
                        g.setTransform(viewport.gameToScreen());
                        for (GameWorld o : worlds) {
                                o.onDraw(g);
                        }
                        g.setTransform(new Affine());
                        for (UIObject u : ui) {
                                u.onDraw(g);
                        }
                }

        }
}
