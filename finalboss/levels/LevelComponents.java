package finalboss.levels;

import engine.hierarchy.GameObject;
import engine.support.Vec2d;

public class LevelComponents {
    public String url;
    public int randomJava;
    public int randomSuns;
    public Vec2d startingPositionHatman;

    public Vec2d bedPosition;

    public int time;

    public GameObject player;

    public LevelComponents(String URL, int randomJava, int randomSuns, int time, Vec2d bedPosition){
        this.url = URL;
        this.randomJava = randomJava;
        this.randomSuns = randomSuns;
        this.time = time;
        this.bedPosition = bedPosition;
        this.startingPositionHatman = null;
        this.player = null;

    }

    public LevelComponents(String URL, int randomJava, int randomSuns, int time, Vec2d bedPosition, Vec2d startingPositionHatman, GameObject player){
        this.url = URL;
        this.randomJava = randomJava;
        this.randomSuns = randomSuns;
        this.time = time;
        this.bedPosition = bedPosition;
        this.startingPositionHatman = startingPositionHatman;
        this.player = player;
    }
}
