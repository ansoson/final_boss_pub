package engine.components;

import engine.hierarchy.GameObject;
import engine.hierarchy.Screen;
import engine.systems.SoundSystem;

import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class BackgroundSoundComponent extends SoundComponent{

  GameObject o;
  Screen parentedScreen;

  /**
   * This component behaves in the following way:
   * It is parented to a screen. The
   * @param o
   * @param s
   */
  public BackgroundSoundComponent(GameObject o, Screen s, Clip c, SoundSystem.SoundType t) {
    super(o, c, t);
    this.c = c;
    this.parentedScreen = s;
    this.o = o;
    this.prepClip();
  }

  public  BackgroundSoundComponent(GameObject o, Screen s, Clip c, float baseVolume,
                                  SoundSystem.SoundType t) {
    super(o, c, baseVolume, t);
    this.c = c;
    this.parentedScreen = s;
    this.o = o;
    this.prepClip();
  }

  public void prepClip(){
    c.loop(Clip.LOOP_CONTINUOUSLY);
    this.volume.setValue(this.baseVolume);
    this.mute.setValue(true);
  }

  @Override
  public void tick(long nanos){
    //if the parent is not active and the sound is NOT muted, set the muted value to true
    if (!parentedScreen.active && !this.mute.getValue()){
      this.mute.setValue(true);
      System.out.println("MUTING SOUND " + o.name);
    }

    //if the screen is active but the sound is muted
    else if (parentedScreen.active && this.mute.getValue()) {
      //unmute
      this.c.setMicrosecondPosition(0);
      this.mute.setValue(false);
    }
  }
}
