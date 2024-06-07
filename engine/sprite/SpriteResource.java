package engine.sprite;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class SpriteResource extends Resource<Image> {
    public SpriteResource(List<String> f) {
        super(f);
        this.resource = new ArrayList<>();
    }

    @Override
    public void loadResources() {
        for(String s : filepaths){
            Image newImage = new Image(s, true);
            resource.add(newImage);
        }
    }
}
