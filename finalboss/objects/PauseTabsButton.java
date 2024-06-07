package finalboss.objects;

import engine.UIKit.Button;
import engine.UIKit.HAlignment;
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
import java.util.ArrayList;

public class PauseTabsButton extends Button {

  PauseView.UIGroup currentTab;
  PauseView parent;
  Color baseColor;

  public PauseTabsButton(PauseView parent, UIObject uiParent, String name, Vec2d pos, Vec2d size,
                         Vec2d def, Color c, HAlignment hAlignment, VAlignment vAlignment, boolean scales,
                         PauseView.UIGroup currentTab) {
    super(parent, uiParent, name, pos, size, def, c, hAlignment, vAlignment, scales);

    this.parent = parent;
    this.currentTab = currentTab;
    this.baseColor = c;

    if (parent.activeTab != this.currentTab){
      this.parent.tabContainers.get(currentTab).active = false;
      this.c = this.baseColor.darker();
    }
  }

  @Override
  public void ButtonEvent(MouseEvent e)
      throws InvocationTargetException, NoSuchMethodException, InstantiationException,
      IllegalAccessException, IOException, TransformerException {
    this.c = baseColor;
    this.parent.tabContainers.get(this.parent.activeTab).active = false;
    this.parent.tabContainers.get(currentTab).active = true;
    this.parent.activeTab = currentTab;
  }

  @Override
  public void HoverEvent(MouseEvent e) {
    System.out.println("PAUSE BUTTON HOVER EVENT");

    if (parent.activeTab != currentTab){
      this.c = this.c.brighter().brighter();
    }
  }



  @Override
  public void EndHoverEvent(MouseEvent e) {
    if (parent.activeTab != currentTab){
      this.c = this.c.darker();
    }
  }

  public void tickButton(){
    if (this.parent.activeTab != this.currentTab && !this.hovering){
      this.c = this.baseColor.darker();
    }
    if (this.parent.activeTab == this.currentTab && !this.hovering){
      this.c = this.baseColor;
    }
  }
}