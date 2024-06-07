package engine.sprite;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SoundResource extends Resource<Clip>{

  public SoundResource(List<String> f) {
    super(f);
    this.resource = new ArrayList<>();
  }

  public Clip getClipFromString(String filepath)
      throws IOException, UnsupportedAudioFileException, LineUnavailableException {
    File soundFile = new File(filepath);
    AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);
    DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
    Clip clip = (Clip) AudioSystem.getLine(info);
    clip.open(sound);

    return clip;
  }

  @Override
  public void loadResources() {
    for(String s : filepaths){
      try {
        resource.add(this.getClipFromString(s));
      } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
        e.printStackTrace();
      }
    }
  }
}
