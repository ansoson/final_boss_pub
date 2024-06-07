package finalboss;

import engine.hierarchy.GONumber;
import engine.hierarchy.GameWorld;
import finalboss.gameworlds.BattleWorld;
import finalboss.gameworlds.Overworld;
import finalboss.screens.*;
import engine.hierarchy.Application;
import engine.hierarchy.Screen;
import engine.support.Vec2d;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static java.lang.System.exit;

/**
 * This is your Wiz top-level, App class.
 * This class will contain every other object in your game.
 */
public class App extends Application {

  public PostbattleView winView;
  public OverworldView overworld;
  Overworld lg;

  public BattleView battle;
  BattleWorld bw;

  PauseView pause;
  FBChalkboard chalk;

  public App(String title) {
    super(title);
    this.n = new GONumber();
  }

  public App(String title, Vec2d windowSize, boolean debugMode, boolean fullscreen) {
    super(title, windowSize, debugMode, fullscreen);
    this.n = new GONumber();
    this.lg = null;
  }

  @Override
  protected void onKeyPressed(KeyEvent e) {

    if (e.getCode() == KeyCode.P) {
      exit(0);
    } else if (!this.chalk.isTalking()) {
      for (Screen s : this.screens) {
        s.onKeyPressed(e);
      }
    } else if (this.chalk.isTalking()) {
      battle.onKeyPressed(e);
    }
  }

  @Override
  protected void onStartup() throws IOException, ParserConfigurationException, TransformerException,
      InvocationTargetException, SAXException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    screens = new ArrayList<>();

    // initialize chalkboard
    this.chalk = new FBChalkboard(40);

    // set up all views & worlds
    this.overworld = new OverworldView("Main View", DEFAULT_STAGE_SIZE, this, false);
    this.lg = new Overworld("Main", this.overworld, DEFAULT_STAGE_SIZE);
    chalk.setPlayer(lg.playerObject);
    this.battle = new BattleView("BattleView", DEFAULT_STAGE_SIZE, this, false);
    this.bw = new BattleWorld("BattleWorld", this.battle, DEFAULT_STAGE_SIZE);
    this.errorScreen = new ErrorView("Error View", DEFAULT_STAGE_SIZE, this, false);
    this.pause = new PauseView("Pause Menu", DEFAULT_STAGE_SIZE, this, null, false);
    this.winView = new PostbattleView("PostbattleView", DEFAULT_STAGE_SIZE, this, lg, false);

    // set chalkboard for all screens
    this.lg.setChalkboard(chalk);
    this.overworld.setChalkboard(chalk);
    this.battle.setChalkboard(chalk);
    this.bw.setChalkboard(chalk);
    this.pause.setChalkboard(chalk);

    // set up all UI for views
    this.overworld.setUpUI();
    this.battle.setUpBattleUI();
    this.pause.initializeAllUI();

    // add all screens to app
    screens.add(new MenuView("Menu View", DEFAULT_STAGE_SIZE, this, lg, true)); // 0
    screens.add(this.overworld); // 1
    screens.add(this.battle); // 2
    screens.add(this.winView); // 3
    screens.add(this.errorScreen); // 4
    screens.add(this.pause); // 5
    screens.add(new InstructView("Instructions View", DEFAULT_STAGE_SIZE, this, lg, false)); // 6

    for (Screen s : screens) {
      s.onStartup();
    }
  }

  public Overworld getLG() {
    return this.lg;
  }

  public BattleWorld getBW() {
    return this.bw;
  }

  @Override
  protected void onMouseClicked(MouseEvent e) throws IOException, InvocationTargetException, TransformerException,
      NoSuchMethodException, InstantiationException, IllegalAccessException {
    if (!chalk.isTalking()) {
      if (chalk.controlsLocked && pause.active) {
        pause.onMouseClicked(e);
      } else {
        super.onMouseClicked(e);
      }
    }
  }
}
