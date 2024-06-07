package engine.components;

import engine.hierarchy.GameObject;
import engine.shapes.Interval;
import engine.support.Vec2d;
import engine.systems.SoundSystem;
import javafx.scene.input.KeyEvent;

import javax.sound.sampled.Clip;


public class PositionalSoundComponent extends SoundComponent{

  double audibleRadius;
  GameObject soundReceivingObj;
  GameObject soundSource;

  public PositionalSoundComponent(GameObject soundSource, Clip  c,
                                  float baseVolume,
                                  SoundSystem.SoundType t,
                                  float audibleRadius, GameObject soundReceivingObj) {
    super(soundSource, c, baseVolume, t);

    this.tag = Tag.SOUND;

    this.audibleRadius = audibleRadius;
    this.soundReceivingObj = soundReceivingObj;
    this.soundSource = soundSource;
    this.prepClip();
  }

  public void prepClip(){
    c.loop(Clip.LOOP_CONTINUOUSLY);
    this.volume.setValue(this.baseVolume);
    this.mute.setValue(true);
  }


  @Override
  public void tick(long nanos){
    this.getPositionalAudioLevels();
  }


  /**
   * This method assumes both objs are rectangles...
   */
  public void getPositionalAudioLevels(){
    Vec2d sourceCenter = soundSource.getTransform().pos.
        plus(soundSource.getTransform().size.smult(.5));
    Vec2d receiverCenter = soundReceivingObj.getTransform().pos.
        plus(soundReceivingObj.getTransform().size.smult(.5));

    Interval soundSourceX = new Interval(sourceCenter.x - audibleRadius,
        sourceCenter.x + audibleRadius);
    Interval soundSourceY = new Interval(sourceCenter.y - audibleRadius,
        sourceCenter.y + audibleRadius);

    //if the sound receiving obj's position is in the audible radius of the sound source
    if (soundSourceX.containsNum(receiverCenter.x)
        && soundSourceY.containsNum(receiverCenter.y)){

      if (this.mute.getValue()){
        this.mute.setValue(false);
      }

      System.out.println("MC IN CAVE's AUDIBLE RANGE");
      float rightEarDist =
          (float) receiverCenter.plus(soundReceivingObj.getTransform().size.smult(.5)).
              dist(sourceCenter);
      float leftEarDist =
          (float) receiverCenter.minus(soundReceivingObj.getTransform().size.smult(.5)).
              dist(sourceCenter);
      float generalDist = (float) sourceCenter.dist(receiverCenter);


      this.volume.setValue(this.remap(receiverCenter.x,
          new Interval(sourceCenter.x - audibleRadius, sourceCenter.x + audibleRadius),
          new Interval((double) this.volume.getMinimum(), (double) this.volume.getMaximum())));
      System.out.println("CAVE VOL = " + this.volume.getValue());

      this.pan.setValue(this.remap(receiverCenter.x,
          new Interval(sourceCenter.x - audibleRadius, sourceCenter.x + audibleRadius),
          new Interval(-1., 1.)));

      System.out.println("PAN VOL = " + this.pan.getValue());
    }

    else if (!this.mute.getValue()){
      this.mute.setValue(true);
    }

    else if (!this.soundSource.parent.parent.active && !this.mute.getValue()){
      this.mute.setValue(true);
    }
  }


  public float remap(double x, Interval inputRange, Interval outputRange){
    return (float) ((x - inputRange.min)/(inputRange.max - inputRange.min)
            * (outputRange.max - outputRange.min) + outputRange.min);
  }




}
