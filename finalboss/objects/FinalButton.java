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
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class FinalButton extends Button {

        PauseView parent;
        WeaponComponent weapon;
        int increment;
        StatsBar bar;
        boolean soundPlayed = false;
        Text buttonTxt;

        public FinalButton(PauseView parent, UIObject uiParent, String name, Vec2d pos,
                                   Vec2d size, Vec2d def, Color col,
                                   HAlignment hAlignment, VAlignment vAlignment,
                                   boolean scales, WeaponComponent weapon) {
            super(parent, uiParent, name, pos, size, def, col, hAlignment, vAlignment, scales);
            this.parent = parent;
            this.weapon = weapon;
            this.buttonTxt = new Text(this, Color.WHITE, new Vec2d(0, 1),
                        13, def, "FINAL UPGRADE", "Pixel",
                        HAlignment.CENTER, VAlignment.CENTER, true);
        }

        @Override
        public void ButtonEvent(MouseEvent e)
                throws InvocationTargetException, NoSuchMethodException, InstantiationException,
                IllegalAccessException, IOException, TransformerException {


            //check if enough coins
            if (this.parent.fbchalk.getMoney() - 500 >= 0){

                this.parent.fbchalk.setMoney(this.parent.fbchalk.getMoney() - 500);
                this.parent.coins.setText("Coins: " + this.parent.fbchalk.getMoney());
                //make final
                this.parent.fbchalk.makeFinal(weapon.getType());
                this.parent.updateWeaponUI();
                this.parent.fbchalk.getSounds().resource.get(0).stop();
                this.parent.fbchalk.getSounds().resource.get(0).setMicrosecondPosition(0);
                this.parent.fbchalk.getSounds().resource.get(0).start();


                soundPlayed = true;
            }

            weapon.initVars();
            this.parent.updateWeaponUI();
        }

        @Override
        public void HoverEvent(MouseEvent e) {
            this.c = Color.WHITE;
            this.buttonTxt.setColor(Color.BLACK);
        }

        @Override
        public void EndHoverEvent(MouseEvent e) {
            this.c = Color.BLACK;
          this.buttonTxt.setColor(Color.WHITE);
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
                if(!this.parent.fbchalk.getFinal(weapon.getType()) && this.parent.fbchalk.canFinalize(weapon.getType())) {
                    this.active = true;
                }
            }
    }

}
