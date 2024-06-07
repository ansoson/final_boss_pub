package finalboss.objects;

import engine.UIKit.HAlignment;
import engine.UIKit.SliderBar;
import engine.UIKit.UIObject;
import engine.UIKit.VAlignment;
import engine.support.Vec2d;
import engine.systems.SoundSystem;
import finalboss.FBChalkboard;
import javafx.scene.input.DragEvent;
import javafx.scene.paint.Color;

public class VolumeSlider extends SliderBar {

  SoundSystem.SoundType type;
  FBChalkboard fbChalkboard;


  public VolumeSlider(UIObject parent, Vec2d pos,
                      Vec2d size,
                      Vec2d def, Color barColor,
                      HAlignment hAlignment, VAlignment vAlignment,
                      boolean scales, double minStat, double maxStat, double startingVal,
                      SoundSystem.SoundType type, FBChalkboard fbChalkboard) {
    super(parent, pos, size, def, barColor, hAlignment, vAlignment, scales, minStat, maxStat,
        startingVal);

    this.type = type;
    this.fbChalkboard = fbChalkboard;
  }

  public void DragEvent(){

    //if the sound type is not muted
    if (fbChalkboard.soundsMuteStatus.get(type)){
      this.fbChalkboard.soundsVolumeStat.replace(type, (float) this.statValue);
      this.fbChalkboard.soundToUpdate = type;
      this.fbChalkboard.updateSoundSettings = true;

    }
  }



}
