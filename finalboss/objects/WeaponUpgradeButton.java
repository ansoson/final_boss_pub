package finalboss.objects;

import engine.UIKit.Button;
import engine.UIKit.HAlignment;
import engine.UIKit.StatsBar;
import engine.UIKit.Text;
import engine.UIKit.UIObject;
import engine.UIKit.VAlignment;
import engine.support.Vec2d;
import finalboss.custom_components.main_character.weapons.WeaponComponent;
import finalboss.custom_components.main_character.weapons.WeaponType;
import finalboss.screens.PauseView;
import finalboss.screens.WeaponStat;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class WeaponUpgradeButton extends Button {

  PauseView parent;
  WeaponComponent weapon;
  WeaponStat stat;
  int increment;
  StatsBar bar;
  boolean soundPlayed = false;

  public WeaponUpgradeButton(PauseView parent, UIObject uiParent, String name, Vec2d pos,
                             Vec2d size, Vec2d def, Color col,
                             HAlignment hAlignment, VAlignment vAlignment,
                             boolean scales, WeaponComponent weapon, WeaponStat stat,
                             int increment, StatsBar bar) {
    super(parent, uiParent, name, pos, size, def, col, hAlignment, vAlignment, scales);
    this.parent = parent;
    this.weapon = weapon;
    this.stat = stat;
    this.increment = increment;
    this.bar = bar;

    if (increment < 0){
      Text buttonTxt = new Text(this, Color.BLACK, new Vec2d(0, 0),
          13, def, "-", "Pixel",
          HAlignment.CENTER, VAlignment.CENTER, true);
    }

    else{
      Text buttonTxt = new Text(this, Color.BLACK, new Vec2d(0, 0),
          13, def, "+", "Pixel",
          HAlignment.CENTER, VAlignment.CENTER, true);
    }

  }

  @Override
  public void ButtonEvent(MouseEvent e)
      throws InvocationTargetException, NoSuchMethodException, InstantiationException,
      IllegalAccessException, IOException, TransformerException {


    //check if enough coins
    if (this.parent.fbchalk.getMoney() - Math.pow(5, this.parent.fbchalk.getLevel(
        weapon.getType(), stat) + 1) >= 0){

      //upgrade weapon
      this.parent.fbchalk.setMoney(this.parent.fbchalk.getMoney() -
          (int) Math.pow(5, this.parent.fbchalk.getLevel(
              weapon.getType(), stat) + 1));
      this.parent.coins.setText("Coins: " + this.parent.fbchalk.getMoney());
      this.parent.fbchalk.setLevel(weapon.getType(), stat,
          this.parent.fbchalk.getLevel(weapon.getType(), stat) + increment);


      this.parent.updateWeaponUI();
      this.parent.fbchalk.getSounds().resource.get(0).stop();
      this.parent.fbchalk.getSounds().resource.get(0).setMicrosecondPosition(0);
      this.parent.fbchalk.getSounds().resource.get(0).start();


      soundPlayed = true;
    }

    weapon.initVars();
    bar.updateStatsBar(increment);
    this.parent.updateWeaponUI();
  }

  @Override
  public void HoverEvent(MouseEvent e) {
    this.c = this.c.darker();
  }

  @Override
  public void EndHoverEvent(MouseEvent e) {
    this.c = this.c.brighter();
  }

  public void setWeapon(WeaponComponent w){
    this.weapon = w;
  }

  public void onTick(long nanos){
    if (this.parent.activeTab != PauseView.UIGroup.UPGRADES && active){
      this.active = false;
    }
    else if (this.parent.activeTab == PauseView.UIGroup.UPGRADES && !active
        && this.weapon.getType() != WeaponType.NONE){
      this.active = true;
    }


  }
}
