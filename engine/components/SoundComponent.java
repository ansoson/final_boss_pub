package engine.components;

import engine.hierarchy.GameObject;
import engine.systems.SoundSystem;
import org.w3c.dom.Element;

import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.EnumControl;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.ReverbType;

public class SoundComponent extends Component{

  public Clip c;
  public FloatControl volume;
  public FloatControl pan;
  public EnumControl reverb;
  public BooleanControl mute;
  public FloatControl distortion;
  public float baseVolume = 0;
  public SoundSystem.SoundType soundType;
  boolean echo = false;

  public SoundComponent(GameObject g, Clip c, float baseVolume, SoundSystem.SoundType t) {
    super(g);
    this.tag = Tag.SOUND;
    this.resetClip(c);
    this.baseVolume = baseVolume;
    this.soundType = t;
    this.resetClip(c);
  }

  public SoundComponent(GameObject g, Clip c, SoundSystem.SoundType t) {
    super(g);
    this.tag = Tag.SOUND;
    this.soundType = t;
    this.resetClip(c);
  }

  public SoundComponent(Element l, GameObject g, Clip c, SoundSystem.SoundType t) {
    super(l, g);
    this.tag = Tag.SOUND;
    this.c = c;
    this.soundType = t;
    this.createControls();
  }

  public void resetClip(Clip c){
    this.c = c;
    this.createControls();
    this.c.setMicrosecondPosition(0);
    this.volume.setValue(baseVolume);
  }

  public void createControls(){
    this.volume = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
    this.pan = (FloatControl) c.getControl(FloatControl.Type.PAN);
    this.mute = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
  }


}