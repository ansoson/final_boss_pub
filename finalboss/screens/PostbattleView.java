package finalboss.screens;

import engine.UIKit.*;
import engine.hierarchy.Application;
import engine.hierarchy.Screen;
import engine.sprite.SpriteResource;
import engine.support.Vec2d;
import finalboss.gameworlds.BattleWorld;
import javafx.scene.paint.Color;
import finalboss.gameworlds.Overworld;
import finalboss.objects.RestartButton;

import java.util.ArrayList;
import java.util.List;

public class PostbattleView extends Screen {

    Overworld lg;
    BattleWorld bw;
    Text winText;
    SpriteResource r;
    Text conditionOneText;
    Text conditionTwoText;
    Text conditionThreeText;
    Text payoutOneText;
    Text payoutTwoText;
    Text payoutThreeText;
    Text total;
    Text totalNum;

    Text debugText;


    public PostbattleView(String name, Vec2d defaultWindowSize, Application parent,
                          Overworld lg, boolean active) {
        super(name, defaultWindowSize, parent, null, active);
        this.lg = lg;
        this.winText = new Text(Color.YELLOW, new Vec2d(windowSize.x/2-200, windowSize.y/4),
            70,windowSize, "DEBUG: 0:00", "Pixel");
        this.debugText = new Text(Color.YELLOW, new Vec2d(windowSize.x/2-200, windowSize.y/3),
            70,windowSize, "but u used debug rofl", "Comic Sans MS");
        debugText.setOn(false);
    }

    public void setWinText(String s) {
        this.winText.setText(s);
    }

    public void payout(int a, int b, int c, String d, String e, String f, boolean won){
        payoutOneText.setText(Integer.toString(a));
        payoutTwoText.setText(Integer.toString(b));
        payoutThreeText.setText(Integer.toString(c));
        totalNum.setText(Integer.toString(a+b+c));
        conditionOneText.setText(d);
        conditionTwoText.setText(e);
        conditionThreeText.setText(f);
        if(won){
            setWinText("You triumphed!");
        }else{
            setWinText("You receive a pittance...");
        }

    }


    private SpriteResource loadSprites(){
        List<String> p = new ArrayList<>();
        p.add("finalboss/sprites/win.png"); //0
        SpriteResource resource = new SpriteResource(p);
        resource.loadResources();
        return resource;

    }

    public void onStartup() {
        Button begin = new RestartButton(this, "Continue Button",
            new Vec2d(0, 400),
            new Vec2d(400, 80), windowSize, Color.GOLDENROD, Color.GOLDENROD.darker(), lg);
        Rectangle bg = new Rectangle(new Vec2d(0,0), windowSize, windowSize, Color.BLACK);
        Text b= new Text(begin, Color.BLACK, new Vec2d(0,0),
            30, windowSize, "Venture onwards?", "Pixel", HAlignment.CENTER, VAlignment.CENTER, true);
        this.winText = new Text(Color.WHITE, new Vec2d(0, 20),
                40, windowSize, "YOU DID SOMETHING!", "Pixel",
                HAlignment.CENTER, VAlignment.UPPER, true);
        this.r = loadSprites();
        this.conditionOneText = new Text(Color.GOLDENROD, new Vec2d(-100, -100), 20, windowSize, "Health Remaining:", "Pixel", HAlignment.CENTER, VAlignment.CENTER, true);
        this.conditionTwoText = new Text(Color.GOLDENROD, new Vec2d(-100, -50), 20, windowSize, "Bounty 2:", "Pixel", HAlignment.CENTER, VAlignment.CENTER, true);
        this.conditionThreeText = new Text(Color.GOLDENROD, new Vec2d(-100, 0), 20, windowSize, "Bounty 3:", "Pixel", HAlignment.CENTER, VAlignment.CENTER, true);
        this.payoutOneText = new Text(Color.GOLDENROD, new Vec2d(150, -100), 20, windowSize, "0", "Pixel", HAlignment.CENTER, VAlignment.CENTER, true);
        this.payoutTwoText = new Text(Color.GOLDENROD, new Vec2d(150, -50), 20, windowSize, "0", "Pixel", HAlignment.CENTER, VAlignment.CENTER, true);
        this.payoutThreeText = new Text(Color.GOLDENROD, new Vec2d(150, 0), 20, windowSize, "0", "Pixel", HAlignment.CENTER, VAlignment.CENTER, true);
        this.total = new Text(Color.GOLDENROD, new Vec2d(-100, -150), 35, windowSize, "TOTAL:", "Pixel", HAlignment.CENTER, VAlignment.LOWER, true);
        this.totalNum = new Text(Color.GOLDENROD, new Vec2d(150, -150), 35, windowSize, "0", "Pixel", HAlignment.CENTER, VAlignment.LOWER, true);

        ui.add(bg);
        ui.add(begin);
        ui.add(b);
        ui.add(winText);
        ui.add(conditionOneText);
        ui.add(conditionTwoText);
        ui.add(conditionThreeText);
        ui.add(payoutOneText);
        ui.add(payoutTwoText);
        ui.add(payoutThreeText);
        ui.add(total);
        ui.add(totalNum);
    }
}
