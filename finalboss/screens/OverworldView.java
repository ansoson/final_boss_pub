package finalboss.screens;

import engine.components.CenterComponent;
import engine.components.Tag;
import engine.hierarchy.GameWorld;
import engine.support.Vec2d;
import finalboss.App;
import finalboss.FBChalkboard;
import finalboss.custom_components.main_character.PlayerControllerComponent;
import finalboss.gameworlds.Overworld;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class OverworldView extends CharacterView {

    public OverworldView(String name, Vec2d defaultWindowSize, App parent, boolean active) {
        super(name, defaultWindowSize, parent, active);
    }

    Overworld ow;

    @Override
    public void onStartup() throws IOException, ParserConfigurationException, TransformerException, InvocationTargetException, SAXException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        worlds = new ArrayList<>();
        App a = (App) parent;
        this.ow = a.getLG();
        worlds.add(ow);
        for(GameWorld w : worlds){
            w.onStartup();
        }
    }

    @Override
    public void Advance(){
        fullTime += (int) Math.floor((time));
        this.fbChalkboard.setLefthand(ow.controller.getLefthand());
        this.fbChalkboard.setRighthand(ow.controller.getRighthand());
        BattleView bv = (BattleView) parent.screens.get(2);
        this.fbChalkboard.setPlayer(ow.playerObject);
        bv.Reset();
        bv.bw.levelParse();
        CenterComponent current = (CenterComponent) this.ow.playerObject.getComponent(Tag.CENTER);
        this.ow.playerObject.removeComponent(current);
        PlayerControllerComponent pcc = (PlayerControllerComponent) this.ow.playerObject.getComponent(Tag.INPUT);
        pcc.setGameWorld(bv.bw);
        this.ow.playerObject.addComponent((new CenterComponent(this.ow.playerObject, bv.bw.viewport)));
        //set correct screen active
        parent.screenOn += 1;
        parent.screens.get(parent.screenOn).setActive();
        this.active = false;
    }
}
