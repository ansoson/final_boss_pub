package engine.UIKit;

import engine.support.Vec2d;
import javafx.scene.paint.Color;

public class DialogueBox extends UIObject{

  public DialogueBox(UIObject parent, Vec2d pos, Vec2d size, Vec2d def,
                     HAlignment hAlignment, VAlignment vAlignment, boolean scales) {
    super(parent, pos, size, def, hAlignment, vAlignment, scales);

  }

  public DialogueBox(Vec2d pos, Vec2d size, Vec2d def, HAlignment hAlignment,
                     VAlignment vAlignment, boolean scales) {
    super(pos, size, def, hAlignment, vAlignment, scales);

  }

  public DialogueBox(Color c, Vec2d pos, Vec2d size, Vec2d def, HAlignment hAlignment,
                     VAlignment vAlignment, boolean scales) {
    super(c, pos, size, def, hAlignment, vAlignment, scales);

  }
}
