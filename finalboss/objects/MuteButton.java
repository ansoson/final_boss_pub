package finalboss.objects;

import engine.UIKit.Button;
import engine.UIKit.HAlignment;
import engine.UIKit.UIObject;
import engine.UIKit.UISprite;
import engine.UIKit.VAlignment;
import engine.hierarchy.Screen;
import engine.sprite.SpriteResource;
import engine.support.Vec2d;
import engine.systems.SoundSystem;
import finalboss.screens.PauseView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class MuteButton extends Button {

  public boolean muted;
  UISprite buttonIcon;
  SoundSystem.SoundType type;
  PauseView parent;

  public MuteButton(PauseView parent, UIObject uiParent, String name, Vec2d pos,
                    Vec2d size, Vec2d def, HAlignment hAlignment, VAlignment vAlignment,
                    boolean scales, SoundSystem.SoundType type) {
    super(parent, uiParent, name, pos, size, def, Color.GRAY, hAlignment, vAlignment, scales);

    this.muted = false;
    this.type = type;
    this.parent = parent;
    this.c = Color.GRAY.brighter();

    this.buttonIcon = new UISprite(this,
        new Vec2d(0, 0), size.smult(.8), def,
        parent.fbchalk.getSprites(), 12, 0, 0,
        512, 512, HAlignment.CENTER, VAlignment.CENTER, true);
  }

  @Override
  public void ButtonEvent(MouseEvent e)
      throws InvocationTargetException, NoSuchMethodException, InstantiationException,
      IllegalAccessException, IOException, TransformerException {

    //if the button is currently not muted
    if (!muted){
      buttonIcon.setSprite(13);
      this.parent.fbchalk.soundsMuteStatus.replace(type, true);
      muted = true;
      this.parent.fbchalk.soundToUpdate = type;
      this.parent.fbchalk.updateSoundSettings = true;

    }

    else{
      buttonIcon.setSprite(12);
      this.parent.fbchalk.soundsMuteStatus.replace(type, false);
      muted = false;
      this.parent.fbchalk.soundToUpdate = type;
      this.parent.fbchalk.updateSoundSettings = true;
    }



  }

  @Override
  public void HoverEvent(MouseEvent e) {
    this.c = c.brighter();
  }

  @Override
  public void EndHoverEvent(MouseEvent e) {
    this.c = c.darker();
  }
}
