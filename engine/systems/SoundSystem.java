package engine.systems;

import engine.components.Component;
import engine.components.SoundComponent;
import engine.components.Tag;
import engine.hierarchy.GameObject;
import engine.hierarchy.GameSystem;
import engine.hierarchy.GameWorld;
import engine.support.Vec2d;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class SoundSystem extends GameSystem {

  public float masterVol = 0;
  public float musicVol = 0;
  public float envVol = 0;
  public float creaturesVol = 0;
  public float uiVol = 0;

  public boolean updateSound = false;
  public SoundType typeToUpdate = SoundType.MASTER;

  public ArrayList<SoundComponent> musicSound = new ArrayList<>();
  public ArrayList<SoundComponent> envSound = new ArrayList<>();
  public ArrayList<SoundComponent> creatureSound = new ArrayList<>();
  public ArrayList<SoundComponent> uiSound = new ArrayList<>();


  public SoundSystem(String name, Vec2d defaultWindowSize) {
    super(name, defaultWindowSize);
  }

  public SoundSystem(String name, Vec2d defaultWindowSize, GameWorld parent) {
    super(name, defaultWindowSize, parent);
  }

  @Override
  public void acceptObject(GameObject o) {
    for (GameObject gg : this.gameObjects) {
      if (gg.id == o.id) {
        return;
      }
    }

    if (o.getComponent(Tag.SOUND) != null) {
      addGameObject(o);

      //add sound to corresponding list
      SoundComponent s = (SoundComponent) o.getComponent(Tag.SOUND);
      if (s.soundType == SoundType.MUSIC){
        this.musicSound.add(s);
      }
      else if (s.soundType == SoundType.ENVIRONMENT){
        this.envSound.add(s);
      }
      else if (s.soundType == SoundType.CREATURES){
        this.creatureSound.add(s);
      }
      else if (s.soundType == SoundType.UI){
        this.uiSound.add(s);
      }
    }
  }

  public void setMuteForType(SoundType t, boolean b){
    if (t == SoundType.MASTER || t == SoundType.MUSIC){
      for (SoundComponent s: musicSound){
        s.mute.setValue(b);
      }
    }

    if (t == SoundType.MASTER || t == SoundType.ENVIRONMENT){
      for (SoundComponent s: envSound){
        s.mute.setValue(b);
      }
    }

    if (t == SoundType.MASTER || t == SoundType.CREATURES){
      for (SoundComponent s: creatureSound){
        s.mute.setValue(b);
      }
    }

    if (t == SoundType.MASTER || t == SoundType.UI){
      for (SoundComponent s: uiSound){
        s.mute.setValue(b);
      }
    }
  }

  public void setVolForType(SoundType t, float f){
    if (t == SoundType.MASTER || t == SoundType.MUSIC){
      for (SoundComponent s: musicSound){
        this.musicVol = f;
        s.volume.setValue(f);
      }
    }

    if (t == SoundType.MASTER || t == SoundType.ENVIRONMENT){
      for (SoundComponent s: envSound){
        this.envVol = f;
        s.volume.setValue(f);
      }
    }

    if (t == SoundType.MASTER || t == SoundType.CREATURES){
      for (SoundComponent s: creatureSound){
        this.creaturesVol = f;
        s.volume.setValue(f);
      }
    }

    if (t == SoundType.MASTER || t == SoundType.UI){
      for (SoundComponent s: uiSound){
        this.uiVol = f;
        s.volume.setValue(f);
      }
    }
  }

  @Override
  public void onTick(long nanos)  {
    for (GameObject o : trash) {
      this.gameObjects.remove(o);
    }
    trash.clear();

    for (GameObject o: this.gameObjects){
      if (o.getComponent(Tag.SOUND) != null){
        List<Component> c = o.getComponents(Tag.SOUND);
        for (Component cc : c) {
          try {
            cc.tick(nanos);
//            System.out.println("SOUND SYSTEM TICKING " + o.name + "'s SOUND COMP");
          } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }




  public enum SoundType {
    MASTER, MUSIC, ENVIRONMENT, CREATURES, UI
  }
}
