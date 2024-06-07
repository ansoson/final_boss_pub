package finalboss.gameworlds;

import engine.components.BackgroundSoundComponent;
import engine.components.CenterComponent;
import engine.components.ConditionalSoundComponent;
import engine.components.PositionalSoundComponent;
import engine.components.SpriteComponent;
import engine.components.Tag;
import engine.hierarchy.GameObject;
import engine.hierarchy.GameSystem;
import engine.hierarchy.Screen;
import engine.physics.PhysicsComponent;
import engine.shapes.ShapeEnum;
import engine.sprite.SpriteResource;
import engine.systems.*;
import engine.support.Vec2d;
import engine.Direction;
import finalboss.FBChalkboard;
import finalboss.custom_components.main_character.MainCharacterAnimationComponent;
import finalboss.custom_components.main_character.MainCharacterCollision;
import finalboss.custom_components.main_character.PlayerControllerComponent;
import finalboss.custom_components.main_character.weapons.WeaponComponent;
import finalboss.levels.LevelComponents;
import finalboss.objects.FBLoadManager;
import finalboss.objects.FileParser;
import finalboss.screens.CharacterView;
import finalboss.screens.OverworldView;
import finalboss.screens.PCViewport;
import org.xml.sax.SAXException;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Overworld extends LevelGameworld {

    public Vec2d windowSize;
    public GameObject playerObject;
    public PCViewport viewport;

    public int num_enemies;

    int level;

    Vec2d start_position;

    public SpriteResource r;

    public int goldNum;
    WeaponComponent lefthand;
    WeaponComponent righthand;

    public Screen parent;

    public GameObject ambientForest;
    public GameObject forestInstrumental;
    public ConditionalSoundComponent grassFootsteps;
    public ConditionalSoundComponent stoneFootsteps;
    public GameObject caveSounds;

    public SoundSystem ss;

    FBLoadManager loader;

    public Overworld(String name, OverworldView parent, Vec2d defaultWindowSize) throws IOException, ParserConfigurationException, InvocationTargetException, SAXException, NoSuchMethodException, InstantiationException, IllegalAccessException { //TODO: switch to OverworldView
        super(name, parent, defaultWindowSize);
        this.parent = parent; //TODO: this is spaghetti as hell
        this.pparent = parent;
        this.viewport = (PCViewport) parent.viewport;
        this.num_enemies = 0;
        this.level = 0;
        this.sprite_size = 50;
        this.start_position = new Vec2d(5,10);
        this.r = getSpriteResource();
        this.playerObject = new GameObject(this, "player", new Vec2d(start_position.x*sprite_size, start_position.y*sprite_size), new Vec2d(sprite_size, sprite_size), windowSize, 3.0);
        this.lefthand = new WeaponComponent(this.playerObject, controller, fbChalkboard);
        this.righthand = new WeaponComponent(this.playerObject, controller, fbChalkboard);
        SpriteComponent ss = new SpriteComponent(this.playerObject, r, 2, 0, 0,48, 48);
        this.playerObject.addComponent(ss);
        MainCharacterAnimationComponent mc = new MainCharacterAnimationComponent(this.playerObject, ss, 3);
        this.playerObject.addComponent(mc);
        this.controller = new PlayerControllerComponent(this.playerObject, mc, 2, sprite_size, r, this, lefthand, righthand);
        System.out.println("controller set");
        this.pparent.setMCCONTROLER(controller);
        mc.s.LAC.setMyController(this.controller);
        this.goldNum = 0;
        this.loader = new FBLoadManager(this, "finalboss/files/save_data.xml",fbChalkboard);
    }

    public void attemptLoader() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        int success = this.loader.parser();
        if(success == 0 && this.loader.levelMap != null && this.loader.conditionsMap != null){
            fbChalkboard.loadSave(this.loader.levelMap, this.loader.conditionsMap);
            fbChalkboard.setMoney(this.loader.money);
            System.out.println("SUCCESSFUL LOAD!");
        }else if (!(success == 0)){
            System.out.println("Something went wrong while loading. Resuming from a blank save file.");
        } else{
            System.out.println("One of the hashmaps failed to initialize. Resuming from a blank save file.");
        }

    }

    public void levelParse(){
        LevelComponents lvl = new LevelComponents("finalboss/levels/overworld.txt",0, 0, 90,new Vec2d(19*sprite_size, sprite_size), new Vec2d(7*sprite_size, 7*sprite_size), playerObject);
        FileParser f = new FileParser(lvl, r, this, sprite_size, start_position, fbChalkboard);
        this.grid = f.parse();
        this.playerObject.callSystems();
    }


    @Override
    public void onStartup() {
        GraphicsSystem g = new GraphicsSystem("GS", windowSize, this);
        systems.add(g);
        TimerSystem t = new TimerSystem("TS", windowSize, this);
        systems.add(t);
        InputSystem i = new InputSystem("IS", windowSize, this);
        systems.add(i);
        CollisionSystem cc = new CollisionSystem("CS", windowSize, this);
        systems.add(cc);
        this.ss = new SoundSystem("SS", windowSize, this);
        systems.add(ss);

        //TEMPORARY REMOVAL
        PathfindingSystem p = new PathfindingSystem("PS", windowSize, this, 25, 25);
        systems.add(p);
        this.playerObject.addComponent(new MainCharacterCollision(this.playerObject, ShapeEnum.AAB, 1, (CharacterView) parent));
        this.playerObject.addComponent((new CenterComponent(this.playerObject, viewport)));
        this.playerObject.addComponent(controller);
        //this.playerObject.addComponent(new TrackingComponent(this.playerObject, sprite_size));
        this.playerObject.addComponent(new PhysicsComponent(this.playerObject, 1, false));

        //add player sounds
        this.grassFootsteps = new ConditionalSoundComponent(this.playerObject,
            this.fbChalkboard.getSounds().resource.get(5), 6, SoundSystem.SoundType.ENVIRONMENT);
        this.stoneFootsteps = new ConditionalSoundComponent(this.playerObject,
            this.fbChalkboard.getSounds().resource.get(6), SoundSystem.SoundType.ENVIRONMENT);
        this.playerObject.addComponent(grassFootsteps);
        this.playerObject.addComponent(stoneFootsteps);

        this.playerObject.callSystems();
        cc.addCollisionLayer(1, 2);
        cc.addCollisionLayer(2, 3);
        this.thisCollision = cc;
        levelParse();
        this.viewport.onZoom(500);
        this.addSoundObjects();
    }

    protected SpriteResource getSpriteResource() {
        List<String> paths = new ArrayList<>();
        paths.add("finalboss/sprites/grass_sprite_tile.png"); //0
        paths.add("finalboss/sprites/tree.png"); //1
        paths.add("finalboss/sprites/theMainBoy.png"); //2
        paths.add("finalboss/sprites/gash.png"); //3
        paths.add("finalboss/sprites/rock_spire.png"); //4
        paths.add("finalboss/sprites/lavatile.png"); //5
        paths.add("finalboss/sprites/lavatile_wall_above.png"); //6
        paths.add("finalboss/sprites/arena_entrance.png"); //7
        paths.add("finalboss/sprites/item_tile.png"); //8
        paths.add("finalboss/sprites/grass-starting-place.png"); //9
        paths.add("finalboss/sprites/arena_entrance_2.png"); //10
        paths.add("finalboss/sprites/tempRopeAttack.png"); //11
        paths.add("finalboss/sprites/tempShieldAttack.png"); //12
        paths.add("finalboss/sprites/tempNetAttack.png"); //13
        paths.add("finalboss/sprites/newPoleAttack.png"); //14
        paths.add("finalboss/sprites/tempWateringCanAttack.png"); //15
        paths.add("finalboss/sprites/tempWandAttack.png"); //16
        paths.add("finalboss/sprites/jump_rope.png"); //17
        paths.add("finalboss/sprites/net.png"); //18
        paths.add("finalboss/sprites/shield.png"); //19
        paths.add("finalboss/sprites/bubble_wand.png"); //20
        paths.add("finalboss/sprites/watering_can.png"); //21
        paths.add("finalboss/sprites/fishing_pole.png"); //22
        SpriteResource resource = new SpriteResource(paths);
        resource.loadResources();
        return resource;
    }

    @Override
    public void setChalkboard(FBChalkboard chalkboard) throws ParserConfigurationException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        this.fbChalkboard = chalkboard;
        attemptLoader();
    }

    public void addSoundObjects(){

        this.ambientForest = new GameObject(this, "forest ambience");
        BackgroundSoundComponent forestSound = new BackgroundSoundComponent(this.ambientForest,
            this.parent, this.fbChalkboard.getSounds().resource.get(1), -20F,
            SoundSystem.SoundType.ENVIRONMENT);
        this.ambientForest.addComponent(forestSound);
        this.ambientForest.callSystems();
        forestSound.mute.setValue(true);

        this.forestInstrumental = new GameObject(this, "forest instrumental");
        BackgroundSoundComponent forestMusic = new BackgroundSoundComponent(this.forestInstrumental,
            this.parent, this.fbChalkboard.getSounds().resource.get(2), - 10F,
            SoundSystem.SoundType.MUSIC);
        this.ambientForest.addComponent(forestMusic);
        this.forestInstrumental.callSystems();
        forestMusic.mute.setValue(true);

        this.caveSounds = new GameObject(this, "sonicCaveFloor",
            new Vec2d(25 * sprite_size, 7 * sprite_size),
            new Vec2d(2 * sprite_size, sprite_size), parent.windowSize, 2.0);
        this.caveSounds.addComponent(new PositionalSoundComponent(this.caveSounds,
            this.fbChalkboard.getSounds().resource.get(7), 6F,
            SoundSystem.SoundType.ENVIRONMENT, 300, this.playerObject));

        caveSounds.callSystems();
    }

    @Override
    public void onTick(long nanos){

        if (fbChalkboard.updateSoundSettings){
            this.ss.setMuteForType(SoundSystem.SoundType.MASTER,
                fbChalkboard.soundsMuteStatus.get(SoundSystem.SoundType.MASTER));
            this.ss.setMuteForType(SoundSystem.SoundType.MUSIC,
                fbChalkboard.soundsMuteStatus.get(SoundSystem.SoundType.MUSIC));

            this.fbChalkboard.updateSoundSettings = false;
        }

        for(GameObject o: trash){
            removeGameObject(o);
        }
        trash = new ArrayList<>();
        //CharacterView m = (CharacterView) this.parent;
        //m.updateCoins(goldNum);
        if(advance){
            advance = false;
            levelAdvance();
        }
        for(GameSystem o: systems){
            try {
                o.onTick(nanos);
            } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if (fbChalkboard.updateSoundSettings){
            this.ss.setMuteForType(this.fbChalkboard.soundToUpdate,
                this.fbChalkboard.soundsMuteStatus.get(this.fbChalkboard.soundToUpdate));
            this.ss.setVolForType(this.fbChalkboard.soundToUpdate,
                this.fbChalkboard.soundsVolumeStat.get(this.fbChalkboard.soundToUpdate));
            System.out.println("SOUND SYSTEM UPDATED " + this.fbChalkboard.soundToUpdate.toString());
            this.fbChalkboard.updateSoundSettings = false;
        }
    }

}
