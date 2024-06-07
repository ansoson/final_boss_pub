package engine.sprite;

import java.util.List;

public abstract class Resource<E> {
    protected List<String> filepaths;
    public List<E> resource;

    public Resource(List<String> f){
        this.filepaths = f;
    }

    public void loadResources(){}
}
