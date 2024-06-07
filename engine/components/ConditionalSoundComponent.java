package engine.components;

import engine.hierarchy.GameObject;
import engine.systems.SoundSystem;

import javax.sound.sampled.Clip;

public class ConditionalSoundComponent extends SoundComponent{

  public boolean condition = false;

  public ConditionalSoundComponent(GameObject g, Clip c,
                                   float baseVolume, SoundSystem.SoundType t) {
    super(g, c, baseVolume, t);
    this.prepClip();
  }

  public ConditionalSoundComponent(GameObject g, Clip c, SoundSystem.SoundType t) {
    super(g, c, t);
    this.prepClip();
  }

  public void prepClip(){
    c.loop(Clip.LOOP_CONTINUOUSLY);
    this.volume.setValue(this.baseVolume);
    this.mute.setValue(true);
  }

  @Override
  public void tick(long nanos){
    //if the condition is true and the sound is muted, unmute the sound
    if (condition && this.mute.getValue() && this.parent.parent.parent.active){
//      this.c.setMicrosecondPosition(0);
      this.mute.setValue(false);
    }

    //if the condition is not true but the sound is unmuted, mute it
    else if (!condition && !this.mute.getValue() || !this.parent.parent.parent.active){
      this.mute.setValue(true);
    }
  }


}
