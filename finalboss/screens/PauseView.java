package finalboss.screens;

import engine.UIKit.HAlignment;
import engine.UIKit.Rectangle;
import engine.UIKit.SliderBar;
import engine.UIKit.StatsBar;
import engine.UIKit.Text;
import engine.UIKit.UIObject;
import engine.UIKit.UISprite;
import engine.UIKit.VAlignment;
import engine.hierarchy.Application;
import engine.hierarchy.GameWorld;
import engine.hierarchy.Screen;
import engine.hierarchy.Viewport;
import engine.support.Vec2d;
import engine.systems.SoundSystem;
import finalboss.FBChalkboard;
import finalboss.custom_components.main_character.weapons.WeaponComponent;
import finalboss.custom_components.main_character.weapons.WeaponType;
import finalboss.objects.*;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

public class PauseView extends Screen {

  // access resources
  public FBChalkboard fbchalk;
  HashMap<WeaponType, Integer> cor;
  WeaponComponent primary;
  WeaponComponent secondary;

  public HashMap<UIGroup, UIObject> tabContainers = new HashMap<>();

  //general screen ui
  PauseTabsButton upgradesTab;
  Rectangle upgradeContainer;
  PauseTabsButton settingsTab;
  Rectangle settingsContainer;

  public Text coins;
  public UIGroup activeTab = UIGroup.UPGRADES;

  //primary weapon info
  //primary weapon info
  Text w1Text;
  Text w1Info;
  UISprite w1Sprite;
  Rectangle levelBoxW1;
  Text w1LevelText;
  StatsBar w1AttackBar;
  StatsBar w1RangeBar;
  StatsBar w1SpecialBar;
  StatsBar w1OverallBar;
  WeaponUpgradeButton w1AttackButtPlus;
  WeaponUpgradeButton w1RangeButtPlus;
  WeaponUpgradeButton w1SpecialButtPlus;
  Text w1AttackCost;
  Text w1RangeCost;
  Text w1SpecialCost;

  FinalButton w1FinalButt;

  //secondary weapon info
  Text w2Text;
  Text w2Info;
  UISprite w2Sprite;
  Rectangle levelBoxW2;
  Text w2LevelText;
  StatsBar w2AttackBar;
  StatsBar w2RangeBar;
  StatsBar w2SpecialBar;
  StatsBar w2OverallBar;
  WeaponUpgradeButton w2AttackButtPlus;
  WeaponUpgradeButton w2RangeButtPlus;
  WeaponUpgradeButton w2SpecialButtPlus;
  Text w2AttackCost;
  Text w2RangeCost;
  Text w2SpecialCost;

  FinalButton w2FinalButt;

  //SETTINGS UI
  SliderBar masterSoundSlider;
  MuteButton masterMuteButton;
  SliderBar musicSoundSlider;
  MuteButton musicMuteButton;
  SliderBar envSoundSlider;
  MuteButton envMuteButton;
  SliderBar creaturesSoundSlider;
  MuteButton creaturesMuteButton;
  SliderBar uiSoundSlider;
  MuteButton uiMuteButton;

  public HashMap<WeaponType, String> weaponDescriptions = new HashMap<>();
  public HashMap<WeaponType, String> weaponNames = new HashMap<>();

  public PauseView(String name, Vec2d defaultWindowSize, Application parent,
                   Viewport vp, boolean active) {
    super(name, defaultWindowSize, parent, vp, active);
  }

  public void setChalkboard(FBChalkboard chalkboard) {
    this.fbchalk = chalkboard;
  }

  public void initializeAllUI() {
    cor = this.correlate();
    this.initializeWeaponData();

    // general UI
    Rectangle lighterBackground = new Rectangle(new Vec2d(0, 0), this.windowSize,
        this.windowSize, new Color(0, 0, 0, .5), HAlignment.LEFT,
        VAlignment.UPPER, true);
    Rectangle background = new Rectangle(lighterBackground, new Vec2d(0, 0),
        this.windowSize.smult(.95), this.windowSize, Color.BLACK,
        HAlignment.CENTER, VAlignment.CENTER, true);

    Rectangle coinsContainer = new Rectangle(background, new Vec2d(-10, 10),
        new Vec2d(windowSize.x * 3 / 4 / 4, 50),
        this.windowSize, Color.GOLDENROD, HAlignment.RIGHT,
        VAlignment.UPPER, true);
    coins = new Text(coinsContainer, Color.BLACK, new Vec2d(0, 0), 20,
        new Vec2d(20, 20), "Coins: " + this.fbchalk.getMoney(), "Pixel",
        HAlignment.CENTER, VAlignment.CENTER, true);
    upgradeContainer = new Rectangle(background, new Vec2d(0, -10),
        new Vec2d(background.size.x - 20, background.size.y - 60), this.windowSize,
        Color.GRAY, HAlignment.CENTER, VAlignment.LOWER, true);
    settingsContainer = new Rectangle(background, new Vec2d(0, -10),
        new Vec2d(background.size.x - 20, background.size.y - 60), this.windowSize,
        Color.GRAY, HAlignment.CENTER, VAlignment.LOWER, true);

    tabContainers.put(UIGroup.UPGRADES, upgradeContainer);
    tabContainers.put(UIGroup.SETTINGS, settingsContainer);

    upgradesTab = new PauseTabsButton(this, background, "upgrades tab",
        new Vec2d(10, 10), new Vec2d(windowSize.x * 3 / 4 / 4, 40), windowSize,
        Color.GRAY, HAlignment.LEFT, VAlignment.UPPER, true, UIGroup.UPGRADES);
    settingsTab = new PauseTabsButton(this, background, "settings tab",
        new Vec2d(20 + windowSize.x * 3 / 4 / 4, 10),
        new Vec2d(windowSize.x * 3 / 4 / 4, 40), windowSize,
        Color.GRAY, HAlignment.LEFT, VAlignment.UPPER, true, UIGroup.SETTINGS);

    this.ui.add(upgradesTab);
    this.ui.add(settingsTab);


    Text upgradesTitle = new Text(upgradesTab, Color.BLACK, new Vec2d(0, 0), 22, windowSize,
        "UPGRADES ", "Pixel",
        HAlignment.CENTER, VAlignment.CENTER, true);
    Text settingsTitle = new Text(settingsTab, Color.BLACK, new Vec2d(0, 0), 22, windowSize,
        "SETTINGS ", "Pixel",
        HAlignment.CENTER, VAlignment.CENTER, true);


    // add all general ui to list
    this.ui.add(lighterBackground);
    this.ui.add(upgradesTab);
    this.ui.add(background);
    // UI FOR INFO AND ACTUAL WEAPON UPGRADING

    //PRIMARY UPGRADES
    Rectangle w1Background = new Rectangle(upgradeContainer,
        new Vec2d(10, 10), background.size.pmult(.95F, .41F), this.windowSize,
        Color.WHITE.darker(), HAlignment.LEFT, VAlignment.UPPER, true);
    Rectangle w2Background = new Rectangle(upgradeContainer,
        new Vec2d(10, -10), background.size.pmult(.95F, .41F), this.windowSize,
        Color.WHITE.darker(), HAlignment.LEFT, VAlignment.LOWER, true);

    w1AttackBar = new StatsBar(upgradeContainer, new Vec2d(365, 53.5),
        new Vec2d(305, 15), windowSize, Color.INDIANRED,
        HAlignment.LEFT, VAlignment.UPPER, true, 3, 0);
    w1RangeBar = new StatsBar(upgradeContainer, new Vec2d(365, 93.5 - 5),
        new Vec2d(305, 15), windowSize, Color.INDIANRED,
        HAlignment.LEFT, VAlignment.UPPER, true, 3, 0);
    w1SpecialBar = new StatsBar(upgradeContainer, new Vec2d(365, 133.5 - 10),
        new Vec2d(305, 15), windowSize, Color.INDIANRED,
        HAlignment.LEFT, VAlignment.UPPER, true, 3, 0);
    w1OverallBar = new StatsBar(upgradeContainer, new Vec2d(365, 175 - 15),
        new Vec2d(325, 30), windowSize, Color.INDIANRED.brighter(),
        HAlignment.LEFT, VAlignment.UPPER, true, 9, 0);
    //text describing different possible upgrades
    Text w1Attack = new Text(upgradeContainer, Color.BLACK, new Vec2d(300, 50), 15, windowSize,
        "Attack: ", "Pixel",
        HAlignment.LEFT, VAlignment.UPPER, true);
    w1AttackCost = new Text(upgradeContainer, Color.BLACK, new Vec2d(560, 70), 10, windowSize,
        "Upgrade cost: 10", "Pixel", HAlignment.LEFT, VAlignment.UPPER, true);

    Text w1Range = new Text(upgradeContainer, Color.BLACK, new Vec2d(300, 85), 15, windowSize,
        "Range: ", "Pixel",
        HAlignment.LEFT, VAlignment.UPPER, true);
    w1RangeCost = new Text(upgradeContainer, Color.BLACK, new Vec2d(560, 105), 10, windowSize,
        "Upgrade cost: 10", "Pixel", HAlignment.LEFT, VAlignment.UPPER, true);

    Text w1Special = new Text(upgradeContainer, Color.BLACK, new Vec2d(290, 120), 15, windowSize,
        "Special: ", "Pixel",
        HAlignment.LEFT, VAlignment.UPPER, true);
    w1SpecialCost = new Text(upgradeContainer, Color.BLACK, new Vec2d(560, 140), 10, windowSize,
        "Upgrade cost: 10", "Pixel", HAlignment.LEFT, VAlignment.UPPER, true);

    Text w1Overall =
        new Text(upgradeContainer, Color.BLACK, new Vec2d(265, 175 - 15), 20, windowSize,
            "Overall: ", "Pixel",
            HAlignment.LEFT, VAlignment.UPPER, true);

    // title text for primary weapon
    w1Text = new Text(upgradeContainer, Color.BLACK, new Vec2d(265, 20), 20, windowSize,
        "PRIMARY WEAPON:  " + this.weaponNames.get(WeaponType.NONE), "Pixel",
        HAlignment.LEFT, VAlignment.UPPER, true);

    // primary weapon sprite
    w1Sprite = new UISprite(upgradeContainer, new Vec2d(25, 25),
        new Vec2d(180, 180), windowSize, fbchalk.getSprites(),
        cor.get(WeaponType.NONE), 0, 0,
        48, 48, HAlignment.LEFT, VAlignment.UPPER, true);

    // primary weapon level container
    levelBoxW1 = new Rectangle(w1Sprite, new Vec2d(0, 0),
        new Vec2d(50, 25), this.windowSize,
        Color.WHITE, HAlignment.RIGHT, VAlignment.UPPER, true);
    w1LevelText = new Text(levelBoxW1, Color.BLACK, new Vec2d(0, 0), 15, windowSize,
        "lvl 1", "Pixel",
        HAlignment.CENTER, VAlignment.CENTER, true);

    //upgrade buttons for adding levels
    w1AttackButtPlus = new WeaponUpgradeButton(this, upgradeContainer,
        "w1AttackButt", new Vec2d(675, 53.5), new Vec2d(15, 15), windowSize,
        Color.WHITE, HAlignment.LEFT, VAlignment.UPPER, true,
        this.fbchalk.getRighthand(), WeaponStat.DAMAGE, 1, w1AttackBar);
    w1RangeButtPlus = new WeaponUpgradeButton(this, upgradeContainer,
        "w1AttackButt", new Vec2d(675, 93.5 - 5), new Vec2d(15, 15), windowSize,
        Color.WHITE, HAlignment.LEFT, VAlignment.UPPER, true,
        this.fbchalk.getRighthand(), WeaponStat.RANGE, 1, w1RangeBar);
    w1SpecialButtPlus = new WeaponUpgradeButton(this, upgradeContainer,
        "w1AttackButt", new Vec2d(675, 133.5 - 10), new Vec2d(15, 15), windowSize,
        Color.WHITE, HAlignment.LEFT, VAlignment.UPPER, true,
        this.fbchalk.getRighthand(), WeaponStat.SPECIAL, 1, w1SpecialBar);


    w1Info = new Text(upgradeContainer, Color.BLACK, new Vec2d(-20, -120),
        15, windowSize, this.weaponDescriptions.get(WeaponType.NONE), "Pixel",
        HAlignment.RIGHT, VAlignment.UPPER, true);

    //SECONDARY
    //title text for secondary weapon
    w2Text = new Text(upgradeContainer, Color.BLACK, new Vec2d(265, 240), 20, windowSize,
        "SECONDARY WEAPON:  " + this.weaponNames.get(WeaponType.NONE), "Pixel",
        HAlignment.LEFT, VAlignment.UPPER, true);

    w2AttackBar = new StatsBar(upgradeContainer, new Vec2d(365, 273.5),
        new Vec2d(305, 15), windowSize, Color.CORNFLOWERBLUE,
        HAlignment.LEFT, VAlignment.UPPER, true, 3, 0);
    w2RangeBar = new StatsBar(upgradeContainer, new Vec2d(365, 313.5 - 5),
        new Vec2d(305, 15), windowSize, Color.CORNFLOWERBLUE,
        HAlignment.LEFT, VAlignment.UPPER, true, 3, 0);
    w2SpecialBar = new StatsBar(upgradeContainer, new Vec2d(365, 353.5 - 10),
        new Vec2d(305, 15), windowSize, Color.CORNFLOWERBLUE,
        HAlignment.LEFT, VAlignment.UPPER, true, 3, 0);
    w2OverallBar = new StatsBar(upgradeContainer, new Vec2d(365, 395 - 15),
        new Vec2d(325, 30), windowSize, Color.CORNFLOWERBLUE.brighter(),
        HAlignment.LEFT, VAlignment.UPPER, true, 9, 0);

    //text describing different possible upgrades
    Text w2Attack = new Text(upgradeContainer, Color.BLACK, new Vec2d(300, 270), 15, windowSize,
        "Attack: ", "Pixel",
        HAlignment.LEFT, VAlignment.UPPER, true);
    w2AttackCost = new Text(upgradeContainer, Color.BLACK, new Vec2d(560, 290), 10, windowSize,
        "Upgrade cost: 10", "Pixel", HAlignment.LEFT, VAlignment.UPPER, true);
    Text w2Range = new Text(upgradeContainer, Color.BLACK, new Vec2d(300, 310 - 5), 15, windowSize,
        "Range: ", "Pixel",
        HAlignment.LEFT, VAlignment.UPPER, true);
    w2RangeCost = new Text(upgradeContainer, Color.BLACK, new Vec2d(560, 330 - 5), 10, windowSize,
        "Upgrade cost: 10", "Pixel", HAlignment.LEFT, VAlignment.UPPER, true);
    Text w2Special = new Text(upgradeContainer, Color.BLACK, new Vec2d(290, 340), 15, windowSize,
        "Special: ", "Pixel",
        HAlignment.LEFT, VAlignment.UPPER, true);
    w2SpecialCost = new Text(upgradeContainer, Color.BLACK, new Vec2d(560, 360), 10, windowSize,
        "Upgrade cost: 10", "Pixel", HAlignment.LEFT, VAlignment.UPPER, true);
    Text w2Overall = new Text(upgradeContainer, Color.BLACK, new Vec2d(265, 380), 20, windowSize,
        "Overall: ", "Pixel",
        HAlignment.LEFT, VAlignment.UPPER, true);

    //secondary weapon sprite
    w2Sprite = new UISprite(upgradeContainer, new Vec2d(25, 245),
        new Vec2d(180, 180), windowSize, fbchalk.getSprites(),
        cor.get(WeaponType.NONE), 0, 0,
        48, 48, HAlignment.LEFT, VAlignment.UPPER, true);

    // secondary weapon level container
    levelBoxW2 = new Rectangle(w2Sprite, new Vec2d(0, 0),
        new Vec2d(50, 25), this.windowSize,
        Color.WHITE, HAlignment.RIGHT, VAlignment.UPPER, true);
    w2LevelText = new Text(levelBoxW2, Color.BLACK, new Vec2d(0, 0), 15, windowSize,
        "lvl 1", "Pixel",
        HAlignment.CENTER, VAlignment.CENTER, true);

    //upgrade buttons for adding levels
    w2AttackButtPlus = new WeaponUpgradeButton(this, upgradeContainer,
        "w2AttackButt", new Vec2d(675, 273.5), new Vec2d(15, 15), windowSize,
        Color.WHITE, HAlignment.LEFT, VAlignment.UPPER, true,
        fbchalk.getLefthand(), WeaponStat.DAMAGE, 1, w2AttackBar);
    w2RangeButtPlus = new WeaponUpgradeButton(this, upgradeContainer,
        "w2AttackButt", new Vec2d(675, 313.5 - 5), new Vec2d(15, 15), windowSize,
        Color.WHITE, HAlignment.LEFT, VAlignment.UPPER, true,
        fbchalk.getLefthand(), WeaponStat.RANGE, 1, w2RangeBar);
    w2SpecialButtPlus = new WeaponUpgradeButton(this, upgradeContainer,
        "w2AttackButt", new Vec2d(675, 353.5 - 10), new Vec2d(15, 15), windowSize,
        Color.WHITE, HAlignment.LEFT, VAlignment.UPPER, true,
        fbchalk.getLefthand(), WeaponStat.SPECIAL, 1, w2SpecialBar);

    //final buttons

    w1FinalButt = new FinalButton(this, w1Sprite, "finalButtW1", new Vec2d(0, 10),
            new Vec2d(150, 30), windowSize, Color.BLACK, HAlignment.CENTER,
            VAlignment.LOWER, true, fbchalk.getLefthand());
    w2FinalButt = new FinalButton(this, w2Sprite, "finalButtW1", new Vec2d(0, 10),
        new Vec2d(150, 30), windowSize, Color.BLACK, HAlignment.CENTER,
        VAlignment.LOWER, true, fbchalk.getRighthand());

    this.ui.add(w1AttackButtPlus);
    this.ui.add(w1RangeButtPlus);
    this.ui.add(w1SpecialButtPlus);
    this.ui.add(w2AttackButtPlus);
    this.ui.add(w2RangeButtPlus);
    this.ui.add(w2SpecialButtPlus);
    this.ui.add(w1FinalButt);
    this.ui.add(w2FinalButt);

    w2Info = new Text(upgradeContainer, Color.BLACK, new Vec2d(-20, 100),
        15, windowSize, this.weaponDescriptions.get(WeaponType.NONE), "Pixel",
        HAlignment.RIGHT, VAlignment.UPPER, true);


    //SETTINGS UI

    Rectangle soundBackground = new Rectangle(settingsContainer,
        new Vec2d(10, 10), background.size.pmult(.47F, .8437F), this.windowSize,
        Color.WHITE.darker(), HAlignment.LEFT, VAlignment.UPPER, true);

    Text volumeControls = new Text(settingsContainer, Color.BLACK, new Vec2d(30, 20),
        20, windowSize, "VOLUME CONTROLS", "Pixel",
        HAlignment.LEFT, VAlignment.UPPER, true);

    Text masterVolumeTitle = new Text(settingsContainer, Color.BLACK, new Vec2d(30, 60),
        20, windowSize, "Master Volume", "Pixel",
        HAlignment.LEFT, VAlignment.UPPER, true);

    this.masterMuteButton = new MuteButton(this, settingsContainer, "master mute button",
        new Vec2d(350, 90), new Vec2d(50, 50), windowSize, HAlignment.LEFT,
        VAlignment.UPPER, true, SoundSystem.SoundType.MASTER);
    this.ui.add(masterMuteButton);
//    public MuteButton(PauseView parent, String name, Vec2d pos,
//        Vec2d size, Vec2d def,
//        HAlignment hAlignment, VAlignment vAlignment,
//    boolean scales, UISprite buttonIcon, SoundSystem ss, SoundSystem.SoundType type)


    masterSoundSlider = new VolumeSlider(settingsContainer, new Vec2d(30, 95),
        new Vec2d(305, 40), windowSize, Color.WHITE,
        HAlignment.LEFT, VAlignment.UPPER, true, -6, 6, 0,
        SoundSystem.SoundType.MASTER, fbchalk);
    this.ui.add(masterSoundSlider);

    musicMuteButton = new MuteButton(this, settingsContainer, "music mute button",
        new Vec2d(350, 170), new Vec2d(40, 40), windowSize, HAlignment.LEFT,
        VAlignment.UPPER, true, SoundSystem.SoundType.MUSIC);
    this.ui.add(musicMuteButton);
    Text musicVolTitle = new Text(settingsContainer, Color.BLACK, new Vec2d(30, 150),
        18, windowSize, "Music", "Pixel",
        HAlignment.LEFT, VAlignment.UPPER, true);
    musicSoundSlider = new VolumeSlider(settingsContainer, new Vec2d(30, 180),
        new Vec2d(305, 20), windowSize, Color.WHITE,
        HAlignment.LEFT, VAlignment.UPPER, true, -6, 6, 0,
        SoundSystem.SoundType.MUSIC, fbchalk);
    this.ui.add(musicSoundSlider);

    Text envVolTitle = new Text(settingsContainer, Color.BLACK, new Vec2d(30, 220),
        18, windowSize, "Environment", "Pixel",
        HAlignment.LEFT, VAlignment.UPPER, true);
    envSoundSlider = new VolumeSlider(settingsContainer, new Vec2d(30, 250),
        new Vec2d(305, 20), windowSize, Color.WHITE,
        HAlignment.LEFT, VAlignment.UPPER, true, -6, 6, 0,
        SoundSystem.SoundType.ENVIRONMENT, fbchalk);
    envMuteButton = new MuteButton(this, settingsContainer, "env mute button",
        new Vec2d(350, 240), new Vec2d(40, 40), windowSize, HAlignment.LEFT,
        VAlignment.UPPER, true, SoundSystem.SoundType.ENVIRONMENT);
    this.ui.add(envSoundSlider);
    this.ui.add(envMuteButton);

    Text creaturesVolTitle = new Text(settingsContainer, Color.BLACK, new Vec2d(30, 290),
        18, windowSize, "Creatures", "Pixel",
        HAlignment.LEFT, VAlignment.UPPER, true);
    creaturesSoundSlider = new VolumeSlider(settingsContainer, new Vec2d(30, 320),
        new Vec2d(305, 20), windowSize, Color.WHITE,
        HAlignment.LEFT, VAlignment.UPPER, true, -6, 6, 0,
        SoundSystem.SoundType.CREATURES, fbchalk);
    creaturesMuteButton = new MuteButton(this, settingsContainer, "creatures mute button",
        new Vec2d(350, 310), new Vec2d(40, 40), windowSize, HAlignment.LEFT,
        VAlignment.UPPER, true, SoundSystem.SoundType.CREATURES);
    this.ui.add(creaturesSoundSlider);
    this.ui.add(creaturesMuteButton);

    Text uiVolTitle = new Text(settingsContainer, Color.BLACK, new Vec2d(30, 360),
        18, windowSize, "User Interface", "Pixel",
        HAlignment.LEFT, VAlignment.UPPER, true);
    uiSoundSlider = new VolumeSlider(settingsContainer, new Vec2d(30, 390),
        new Vec2d(305, 20), windowSize, Color.WHITE,
        HAlignment.LEFT, VAlignment.UPPER, true, -6, 6, 0,
        SoundSystem.SoundType.UI, fbchalk);
    uiMuteButton = new MuteButton(this, settingsContainer, "env mute button",
        new Vec2d(350, 380), new Vec2d(40, 40), windowSize, HAlignment.LEFT,
        VAlignment.UPPER, true, SoundSystem.SoundType.MUSIC);
    this.ui.add(uiSoundSlider);
    this.ui.add(uiMuteButton);

    Rectangle controlsBackground = new Rectangle(settingsContainer,
        new Vec2d(-10, 10), background.size.pmult(.475F, .56F), this.windowSize,
        Color.GRAY.brighter(), HAlignment.RIGHT, VAlignment.UPPER, true);

    Text controlsTitle = new Text(controlsBackground, Color.BLACK, new Vec2d(20, 20),
        20, windowSize, "KEY CONTROLS", "Pixel",
        HAlignment.LEFT, VAlignment.UPPER, true);
    Text moveText = new Text(controlsBackground, Color.BLACK, new Vec2d(20, 55),
        18, windowSize, "Move \t\t\t\t→\tWASD", "Pixel",
        HAlignment.LEFT, VAlignment.UPPER, true);
    Text moveText2 = new Text(controlsBackground, Color.BLACK, new Vec2d(20, 85),
        18, windowSize, "\t\t\t\t\t\tArrow Keys", "Pixel",
        HAlignment.LEFT, VAlignment.UPPER, true);
    Text dashText = new Text(controlsBackground, Color.BLACK, new Vec2d(20, 115),
        18, windowSize, "Dash \t\t\t\t→\tSpace Bar", "Pixel",
        HAlignment.LEFT, VAlignment.UPPER, true);
    Text attackText = new Text(controlsBackground, Color.BLACK, new Vec2d(20, 145),
        18, windowSize, "Attack \t\t\t→\tZ and X", "Pixel",
        HAlignment.LEFT, VAlignment.UPPER, true);
    Text attackText2 = new Text(controlsBackground, Color.BLACK, new Vec2d(20, 175),
        18, windowSize, "\t\t\t\t\t\tL and R Click", "Pixel",
        HAlignment.LEFT, VAlignment.UPPER, true);
    Text switchWeapons = new Text(controlsBackground, Color.BLACK, new Vec2d(20, 205),
        18, windowSize, "Switch weapons \t→\tC", "Pixel",
        HAlignment.LEFT, VAlignment.UPPER, true);
    Text menuText = new Text(controlsBackground, Color.BLACK, new Vec2d(20, 235),
        18, windowSize, "Menu \t\t\t\t→\tESC", "Pixel",
        HAlignment.LEFT, VAlignment.UPPER, true);
  }

  public void updateWeaponUI() {
    try {
      w1AttackBar
          .setStatsBar(fbchalk.getLevel(fbchalk.getRighthand().getType(), WeaponStat.DAMAGE));
      w1RangeBar.setStatsBar(fbchalk.getLevel(fbchalk.getRighthand().getType(), WeaponStat.RANGE));
      w1SpecialBar
          .setStatsBar(fbchalk.getLevel(fbchalk.getRighthand().getType(), WeaponStat.SPECIAL));
      w1OverallBar.setStatsBar(fbchalk.getLevel(
          fbchalk.getRighthand().getType(), WeaponStat.DAMAGE) +
          fbchalk.getLevel(fbchalk.getRighthand().getType(), WeaponStat.RANGE) +
          fbchalk.getLevel(fbchalk.getRighthand().getType(), WeaponStat.SPECIAL));

      if (fbchalk.getLevel(
          fbchalk.getRighthand().getType(), WeaponStat.DAMAGE).equals(3) ||
          fbchalk.getRighthand().getType() == WeaponType.NONE) {
        w1AttackCost.active = false;
        w1AttackButtPlus.active = false;
      } else {
        w1AttackCost.active = true;
        w1AttackButtPlus.active = true;
        w1AttackCost.setText("Upgrade cost: " + ((int) Math.pow(5, fbchalk.getLevel(
            fbchalk.getRighthand().getType(), WeaponStat.DAMAGE) + 1)));
      }

      if (fbchalk.getLevel(
          fbchalk.getRighthand().getType(), WeaponStat.RANGE).equals(3) ||
          fbchalk.getRighthand().getType() == WeaponType.NONE) {
        w1RangeCost.active = false;
        w1RangeButtPlus.active = false;
      } else {
        w1RangeCost.active = true;
        w1RangeButtPlus.active = true;
        w1RangeCost.setText("Upgrade cost: " + ((int) Math.pow(5, fbchalk.getLevel(
            fbchalk.getRighthand().getType(), WeaponStat.RANGE) + 1)));
      }

      if (fbchalk.getLevel(
          fbchalk.getRighthand().getType(), WeaponStat.SPECIAL).equals(3) ||
          fbchalk.getRighthand().getType() == WeaponType.NONE) {
        w1SpecialCost.active = false;
        w1SpecialButtPlus.active = false;
      } else {
        w1SpecialCost.active = true;
        w1SpecialButtPlus.active = true;
        w1SpecialCost.setText("Upgrade cost: " + +((int) Math.pow(5, fbchalk.getLevel(
            fbchalk.getRighthand().getType(), WeaponStat.SPECIAL) + 1)));
      }

      w2AttackBar.setStatsBar(fbchalk.getLevel(fbchalk.getLefthand().getType(), WeaponStat.DAMAGE));
      w2RangeBar.setStatsBar(fbchalk.getLevel(fbchalk.getLefthand().getType(), WeaponStat.RANGE));
      w2SpecialBar
          .setStatsBar(fbchalk.getLevel(fbchalk.getLefthand().getType(), WeaponStat.SPECIAL));
      w2OverallBar.setStatsBar(fbchalk.getLevel(
          fbchalk.getLefthand().getType(), WeaponStat.DAMAGE) +
          fbchalk.getLevel(fbchalk.getLefthand().getType(), WeaponStat.RANGE) +
          fbchalk.getLevel(fbchalk.getLefthand().getType(), WeaponStat.SPECIAL));

      w1Text.setText("PRIMARY WEAPON:  " + this.weaponNames.get(fbchalk.getRighthand().getType()));
      w1Sprite.setSprite(cor.get(fbchalk.getRighthand().getType()));
      w1LevelText
          .setText("lvl " + (fbchalk.getLevel(fbchalk.getRighthand().getType(), WeaponStat.DAMAGE)
              + fbchalk.getLevel(fbchalk.getRighthand().getType(), WeaponStat.RANGE)
              + fbchalk.getLevel(fbchalk.getRighthand().getType(), WeaponStat.SPECIAL)) / 3);
      w1Info.setText(this.weaponDescriptions.get(fbchalk.getRighthand().getType()));

      w2Text.setText("SECONDARY WEAPON:  " + this.weaponNames.get(fbchalk.getLefthand().getType()));
      w2Sprite.setSprite(cor.get(fbchalk.getLefthand().getType()));
      w2LevelText
          .setText("lvl " + (fbchalk.getLevel(fbchalk.getLefthand().getType(), WeaponStat.DAMAGE)
              + fbchalk.getLevel(fbchalk.getLefthand().getType(), WeaponStat.RANGE)
              + fbchalk.getLevel(fbchalk.getLefthand().getType(), WeaponStat.SPECIAL)) / 3);
      w2Info.setText(this.weaponDescriptions.get(fbchalk.getLefthand().getType()));

      if (fbchalk.getLevel(
          fbchalk.getLefthand().getType(), WeaponStat.DAMAGE).equals(3) ||
          fbchalk.getLefthand().getType() == WeaponType.NONE) {
        w2AttackCost.active = false;
        w2AttackButtPlus.active = false;
      } else {
        w2AttackCost.active = true;
        w2AttackButtPlus.active = true;
        w2AttackCost.setText("Upgrade cost: " + ((int) Math.pow(5, fbchalk.getLevel(
            fbchalk.getLefthand().getType(), WeaponStat.DAMAGE) + 1)));
      }

      if (fbchalk.getLevel(
          fbchalk.getLefthand().getType(), WeaponStat.RANGE).equals(3) ||
          fbchalk.getLefthand().getType() == WeaponType.NONE) {
        w2RangeCost.active = false;
        w2RangeButtPlus.active = false;
      } else {
        w2RangeCost.active = true;
        w2RangeButtPlus.active = true;
        w2RangeCost.setText("Upgrade cost: " + ((int) Math.pow(5, fbchalk.getLevel(
            fbchalk.getLefthand().getType(), WeaponStat.RANGE) + 1)));
      }

      if (fbchalk.getLevel(
          fbchalk.getLefthand().getType(), WeaponStat.SPECIAL).equals(3) ||
          fbchalk.getLefthand().getType() == WeaponType.NONE) {
        w2SpecialCost.active = false;
        w2SpecialButtPlus.active = false;
      } else {
        w2SpecialCost.active = true;
        w2SpecialButtPlus.active = true;
        w2SpecialCost.setText("Upgrade cost: " + +((int) Math.pow(5, fbchalk.getLevel(
            fbchalk.getLefthand().getType(), WeaponStat.SPECIAL) + 1)));
      }

        w2Info.active = !fbchalk.getLefthand().getType().equals(fbchalk.getRighthand().getType());
        w1FinalButt.active = fbchalk.canFinalize(fbchalk.getRighthand().getType()) && !fbchalk.getFinal(fbchalk.getRighthand().getType());
        w2FinalButt.active = fbchalk.canFinalize(fbchalk.getLefthand().getType()) && !fbchalk.getFinal(fbchalk.getLefthand().getType());


      this.w1AttackButtPlus.setWeapon(fbchalk.getRighthand());
      this.w1RangeButtPlus.setWeapon(fbchalk.getRighthand());
      this.w1SpecialButtPlus.setWeapon(fbchalk.getRighthand());
      this.w1FinalButt.setWeapon(fbchalk.getRighthand());
      this.w2AttackButtPlus.setWeapon(fbchalk.getLefthand());
      this.w2RangeButtPlus.setWeapon(fbchalk.getLefthand());
      this.w2SpecialButtPlus.setWeapon(fbchalk.getLefthand());
      this.w2FinalButt.setWeapon(fbchalk.getLefthand());

    } catch (NullPointerException e) {
      System.out.println("nulled weapon (currently), should resolve in a few frames");
    }
  }

  public HashMap<WeaponType, Integer> correlate() {
    HashMap<WeaponType, Integer> cor = new HashMap<>();
    cor.put(WeaponType.NONE, 11);
    cor.put(WeaponType.WAND, 7);
    cor.put(WeaponType.WATERING_CAN, 8);
    cor.put(WeaponType.NET, 5);
    cor.put(WeaponType.SHIELD, 6);
    cor.put(WeaponType.ROPE, 4);
    cor.put(WeaponType.FISHING_POLE, 9);
    return cor;
  }

  @Override
  public void onKeyPressed(KeyEvent e) {
    updateWeaponUI();



    if (e.getCode() == KeyCode.ESCAPE && !this.active && this.parent.screenOn != 0) {

      active = true;
      this.fbchalk.lockControls(true);
      coins.setText("Coins: " + this.fbchalk.getMoney());
    } else if (e.getCode() == KeyCode.ESCAPE) {
      active = false;
      this.fbchalk.lockControls(false);
    }
  }

  public void initializeWeaponData() {
    // DESCRIPTIONS
    weaponDescriptions.put(WeaponType.NONE,
        "You wield nothing, \n" +
            "except your raw \n " +
            "strength (which\n" +
            "is not much...). \n" +
            "No amount of\n" +
            "coins can help\n" +
            "help you here.");
    weaponDescriptions.put(WeaponType.NET,
        " A well-loved \n" +
            "butterfly net. \n" +
            "The fraying web-\n" +
            "bing is just a \n" +
            "sign it is wiz-\n" +
            "ened by years.");
    weaponDescriptions.put(WeaponType.WAND,
        "My son's bubble \n" +
            "wand? How did \n" +
            "that get here???");
    weaponDescriptions.put(WeaponType.WATERING_CAN,
        "A watering can \n" +
            "isn't really a \n" +
            "weapon, but \n" +
            "nothing else \n" +
            "here is, \n" +
            "either.");
    weaponDescriptions.put(WeaponType.FISHING_POLE,
        "This old fishing \n" +
            "pole might just \n" +
            "have enough life \n" +
            "in it to catch\n" +
            "one or two\n" +
            "goldfish.");
    weaponDescriptions.put(WeaponType.ROPE,
        "The only one \n" +
            "hopping over this \n" +
            "fearsome jump-\n" +
            "rope will be \n" +
            "your foes.");
    weaponDescriptions.put(WeaponType.SHIELD,
        "Who left their \n" +
            "gross trash can \n" +
            "lid in this beauti-\n" +
            "ful forest??? \n" +
            "It's slightly \n" +
            "sticky.");

    // NAMES
    weaponNames.put(WeaponType.NONE, "Bare Fist");
    weaponNames.put(WeaponType.NET, "Butterfly Net");
    weaponNames.put(WeaponType.WAND, "Bubble Wand");
    weaponNames.put(WeaponType.WATERING_CAN, "Watering Can");
    weaponNames.put(WeaponType.FISHING_POLE, "Fishing Pole");
    weaponNames.put(WeaponType.ROPE, "Jump-rope");
    weaponNames.put(WeaponType.SHIELD, "Trash Can Lid");
  }

  @Override
  public void onTick(long nanos) {
    settingsTab.tickButton();
    upgradesTab.tickButton();
    w1AttackButtPlus.onTick(nanos);
    w2AttackButtPlus.onTick(nanos);
    w1RangeButtPlus.onTick(nanos);
    w2RangeButtPlus.onTick(nanos);
    w1SpecialButtPlus.onTick(nanos);
    w2SpecialButtPlus.onTick(nanos);
    w1FinalButt.onTick(nanos);
    w2FinalButt.onTick(nanos);

    if (masterSoundSlider.active != settingsContainer.active) {
      masterSoundSlider.active = settingsContainer.active;
      masterMuteButton.active = settingsContainer.active;
      musicSoundSlider.active = settingsContainer.active;
      musicMuteButton.active = settingsContainer.active;
      envSoundSlider.active = settingsContainer.active;
      envMuteButton.active = settingsContainer.active;
      creaturesSoundSlider.active = settingsContainer.active;
      creaturesMuteButton.active = settingsContainer.active;
      uiSoundSlider.active = settingsContainer.active;
      uiMuteButton.active = settingsContainer.active;
    }
  }


  public enum UIGroup {
    UPGRADES, SETTINGS
  }
}
