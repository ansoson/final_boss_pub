package finalboss.screens;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import engine.UIKit.TextBox;
import engine.components.CenterComponent;
import engine.components.Tag;
import engine.hierarchy.GameWorld;
import engine.support.Vec2d;

import finalboss.App;
import finalboss.custom_components.main_character.PlayerControllerComponent;
import finalboss.custom_components.main_character.weapons.WeaponType;
import finalboss.dialogue.Conditions;
import finalboss.dialogue.FBDialogueControl;
import finalboss.gameworlds.BattleWorld;
import finalboss.gameworlds.Overworld;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class BattleView extends CharacterView {
    private TextBox tb;
    private FBDialogueControl control;
    private ArrayList<Conditions> conds;
    BattleWorld bw;
    Vec2d def;
    boolean flipped;
    WeaponType challengeMode;

    public BattleView(String name, Vec2d defaultWindowSize, App parent, boolean active) {
        super(name, defaultWindowSize, parent, active);
        this.def = defaultWindowSize;
        this.conds = new ArrayList<>();
        this.challengeMode = WeaponType.NONE;
    }

    public void setUpBattleUI() {
        this.setUpUI();
        this.tb = new TextBox(new Vec2d(0, 400), new Vec2d(def.x, def.y - 400),
                new Vec2d(100, 100));
        ui.add(this.tb);
        this.tb.setOn(false);
        this.fbChalkboard.setupDialogue(this.tb);
        this.control = this.fbChalkboard.getDialogueControl();
    }

    public void showUI(Boolean show) {
        this.upperUIBox.setOn(show);
        this.lowerUIBox.setOn(show);
        this.showWeps(show);
        this.tb.setOn(!show);
        this.fbChalkboard.dialogueOn(!show);
        if (!show) {
            this.control.playConversation();
            System.out.println("TRIGGERED DIALOGUE");
        }
    }

    @Override
    public void onTick(long nanosSincePreviousTick)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException,
            IOException, TransformerException {
        if (flipped) {
            this.control.dialogueStart();

            this.showUI(false);

            this.flipped = false;
            if (!fbChalkboard.hasWon && fbChalkboard.winSreak == 1) {
                this.bw.triggerSecondPhase();
            }
        }
        if (this.active) {
            if (!this.fbChalkboard.locked()) {
                for (GameWorld o : worlds) {
                    o.onTick(nanosSincePreviousTick);
                }
                updateTimer(nanosSincePreviousTick);
                cooldown += 1;
            }
            this.control.onTick(nanosSincePreviousTick);
            bw.dialogueSoundComp.tick(nanosSincePreviousTick);
            if (bossHealth.bar.size.x < 100 && !this.conds.contains(Conditions.BOSSHP)) {
                this.fbChalkboard.setActiveCond(Conditions.BOSSHP);
                this.conds.add(Conditions.BOSSHP);
                this.bw.triggerSecondPhase();
                this.showUI(false);
            }
            if (mcHealth.bar.size.x < 100 && !this.conds.contains(Conditions.PLAYERHP)) {
                this.fbChalkboard.setActiveCond(Conditions.PLAYERHP);
                this.conds.add(Conditions.PLAYERHP);
                this.showUI(false);
            }
        }

        if (!this.control.lineDone() && !this.bw.dialogueSoundComp.condition && this.active) {
            this.bw.dialogueSoundComp.condition = true;
        }

        else if (this.control.lineDone()) {
            this.bw.dialogueSoundComp.condition = false;
        }

        // if screen is not active, only tick sound system
        if (!active) {
            for (GameWorld o : worlds) {
                if (o.getSystem("SS") != null) {
                    o.getSystem("SS").onTick(nanosSincePreviousTick);
                }
            }
        }
    }

    @Override
    public void onKeyPressed(KeyEvent e) {

        if (this.active) {
            if (!this.fbChalkboard.isTalking()) {
                for (GameWorld o : worlds) {
                    o.onKeyPressed(e);
                }
            }
            if (this.fbChalkboard.isTalking() && e.getCode() == KeyCode.SPACE) {
                if (!this.control.lineDone()) {
                    this.control.finishLine();
                } else if (this.control.isOver()) {
                    this.showUI(true);
                } else {
                    this.control.getNextLine();
                }
            }
        }

    }

    @Override
    public void onStartup()
            throws IOException, ParserConfigurationException, TransformerException, InvocationTargetException,
            SAXException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        worlds = new ArrayList<>();
        App a = (App) parent;
        this.bw = a.getBW();
        worlds.add(bw);
        for (GameWorld w : worlds) {
            w.onStartup();
        }
    }

    @Override
    public void Advance() {
        fullTime += (int) Math.floor((time));
        parent.screenOn += 1;
        CenterComponent current = (CenterComponent) this.bw.playerObject.getComponent(Tag.CENTER);
        this.bw.playerObject.removeComponent(current);
        App a = (App) parent;
        Overworld lg = a.getLG();
        this.bw.playerObject.addComponent((new CenterComponent(this.bw.playerObject, lg.viewport)));
        PlayerControllerComponent pcc = (PlayerControllerComponent) this.bw.playerObject.getComponent(Tag.INPUT);
        pcc.setGameWorld(lg);
        parent.screens.get(parent.screenOn).setActive();
        this.active = false;
    }

    @Override
    public void Reset() {
        ui.clear();
        ui.add(upperUIBox);
        ui.add(lowerUIBox);
        ui.add(this.tb);
        cooldown = 0;
        this.time = 0;
        this.fullTime = 0;
        bossHealth.ResetHealth();
        mcHealth.ResetHealth();
    }

    @Override
    public void setActive() {
        this.active = true;
        this.flipped = true;
        this.fbChalkboard.gameNum++;

    }
}