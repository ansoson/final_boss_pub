package finalboss.objects;

import engine.UIKit.Button;
import engine.UIKit.HAlignment;
import engine.UIKit.Text;
import engine.UIKit.UIObject;
import engine.UIKit.VAlignment;
import engine.hierarchy.Screen;
import engine.support.Vec2d;
import finalboss.screens.PauseView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class ApplyUpgradesButton extends Button {

  PauseView parent;


  public ApplyUpgradesButton(PauseView parent, UIObject uiParent,
                             String name,
                             Vec2d pos, Vec2d size,
                             Vec2d def,
                             Color c, HAlignment hAlignment,
                             VAlignment vAlignment, boolean scales) {
    super(parent, uiParent, name, pos, size, def, c, hAlignment, vAlignment, scales);
    Text buttonText = new Text(this, Color.BLACK, new Vec2d(0, 0), 15,
        new Vec2d(20, 20), "Apply upgrades", "Pixel", HAlignment.CENTER,
        VAlignment.CENTER, true);
    this.parent = parent;
  }

  @Override
  public void ButtonEvent(MouseEvent e)
      throws InvocationTargetException, NoSuchMethodException, InstantiationException,
      IllegalAccessException, IOException, TransformerException {

  }

  @Override
  public void HoverEvent(MouseEvent e) {
    this.c = this.c.darker();
  }

  @Override
  public void EndHoverEvent(MouseEvent e) {
    this.c = this.c.brighter();
  }

}
