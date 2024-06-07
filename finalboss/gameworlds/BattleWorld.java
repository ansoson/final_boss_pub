package finalboss.gameworlds;

import engine.components.BackgroundSoundComponent;
import engine.components.CenterComponent;
import engine.components.ConditionalSoundComponent;
import engine.components.PositionalSoundComponent;
import engine.components.Tag;
import engine.hierarchy.GameObject;
import engine.hierarchy.GameSystem;
import engine.shapes.ShapeEnum;
import engine.sprite.SpriteResource;
import engine.support.Vec2d;
import engine.systems.*;
import finalboss.custom_components.main_character.MainCharacterCollision;
import finalboss.custom_components.main_character.PlayerControllerComponent;
import finalboss.custom_components.main_character.weapons.WeaponComponent;
import finalboss.custom_components.main_character.weapons.WeaponType;
import finalboss.custom_components.villain.VillainAttackComponent;
import finalboss.levels.LevelComponents;
import finalboss.objects.FBSaveManager;
import finalboss.objects.FileParser;
import finalboss.screens.BattleView;
import finalboss.screens.CharacterView;
import finalboss.screens.PCViewport;
import finalboss.screens.PostbattleView;
import finalboss.FBChalkboard;

import javax.sound.sampled.ReverbType;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class BattleWorld extends LevelGameworld {
        public GameObject playerObject;
        public GameObject bossObject;

        public PCViewport viewport;

        private CharacterView pparent;
        public VillainAttackComponent vac;

        FBSaveManager saver;
        boolean ticked;

        GameObject battleMusic;
        GameObject dialogueSound;
        public ConditionalSoundComponent dialogueSoundComp;
        SoundSystem ss;

        public BattleWorld(String name, BattleView parent, Vec2d defaultWindowSize) {
                super(name, parent, defaultWindowSize);
                this.parent = parent; // TODO: this is spaghetti as hell
                this.pparent = parent;
                this.viewport = (PCViewport) parent.viewport;
                this.num_enemies = 0;
                this.sprite_size = 50;
                this.start_position = new Vec2d(11 * sprite_size, 8 * sprite_size);
                this.r = getSpriteResource();
                this.goldNum = 0;
                this.ticked = false;
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
                ss = new SoundSystem("SS", windowSize, this);
                systems.add(ss);

                // TEMPORARY REMOVAL
                PathfindingSystem p = new PathfindingSystem("PS", windowSize, this, 25, 25);
                systems.add(p);
                cc.addCollisionLayer(1, 2);
                cc.addCollisionLayer(2, 3);
                cc.addCollisionLayer(1,4);
                this.thisCollision = cc;
                this.addSoundObjects();
        }

        public void levelParse() {
                LevelComponents lvl = new LevelComponents("finalboss/levels/battleworld.txt", 0, 0, 90,
                                new Vec2d(19 * sprite_size, sprite_size),
                                new Vec2d(4 * sprite_size, 7 * sprite_size), playerObject);
                FileParser f = new FileParser(lvl, r, this, sprite_size, start_position, fbChalkboard);
                this.grid = f.parse();
                for (List<GameObject> list : this.grid) {
                        for (GameObject obj : list) {
                                if (obj.name == "our special guy") {
                                        this.bossObject = obj;
                                }
                        }
                }
                // this.bossObject.callSystems();
                this.vac = (VillainAttackComponent) bossObject.getComponent(Tag.ENEMY);
                this.playerObject = fbChalkboard.getPlayer();
                this.playerObject.parent = this;
                this.pparent.giltText.setText(Integer.toString(fbChalkboard.getMoney()));
                // screen dependent components
                this.controller = (PlayerControllerComponent) this.playerObject.getComponent(Tag.INPUT);
                this.pparent.setMCCONTROLER(controller);
                this.playerObject.removeComponent(this.playerObject.getComponent(Tag.COLLISION));
                this.playerObject.addComponent(
                                new MainCharacterCollision(this.playerObject, ShapeEnum.AAB, 1, this.pparent));
                this.playerObject.setTransform(this.start_position);
                this.playerObject.callSystems();
                this.lefthand = fbChalkboard.getLefthand();
                this.righthand = fbChalkboard.getRighthand();
                System.out.println(this.lefthand.getType() + ", " + this.righthand.getType());
                this.pparent.postWeapons(lefthand, righthand);


        }

        @Override
        public void setChalkboard(FBChalkboard chalkboard) throws ParserConfigurationException {
                this.fbChalkboard = chalkboard;
                this.saver = new FBSaveManager(this, "finalboss/files/save_data.xml", fbChalkboard);
        }

        protected SpriteResource getSpriteResource() {
                List<String> paths = new ArrayList<>();
                paths.add("finalboss/sprites/rock_sprite_tile.png"); // 0
                paths.add("finalboss/sprites/rock_wall.png"); // 1
                paths.add("finalboss/sprites/theMainBoy.png"); // 2
                paths.add("finalboss/sprites/gash.png"); // 3
                paths.add("finalboss/sprites/rock_spire.png"); // 4
                paths.add("finalboss/sprites/lavatile.png"); // 5
                paths.add("finalboss/sprites/lavatile_wall_above.png"); // 6
                paths.add("finalboss/sprites/bed.png"); // 7
                paths.add("finalboss/sprites/item_tile.png"); // 8
                paths.add("finalboss/sprites/red_carpet.png"); // 9
                paths.add("finalboss/sprites/lava_banner.png"); // 10
                paths.add("finalboss/sprites/tempRopeAttack.png"); // 11
                paths.add("finalboss/sprites/tempShieldAttack.png"); // 12
                paths.add("finalboss/sprites/tempNetAttack.png"); // 13
                paths.add("finalboss/sprites/newPoleAttack.png"); // 14
                paths.add("finalboss/sprites/tempWateringCanAttack.png"); // 15
                paths.add("finalboss/sprites/tempWandAttack.png"); // 16
                paths.add("finalboss/sprites/jump_rope.png"); // 17
                paths.add("finalboss/sprites/net.png"); // 18
                paths.add("finalboss/sprites/shield.png"); // 19
                paths.add("finalboss/sprites/bubble_wand.png"); // 20
                paths.add("finalboss/sprites/watering_can.png"); // 21
                paths.add("finalboss/sprites/fishing_pole.png"); // 22
                paths.add("finalboss/sprites/new_boss.png"); // 23
                paths.add("finalboss/sprites/magmle.png"); //24
                paths.add("finalboss/sprites/fire_pillar_anim-Sheet.png"); //25
                paths.add("finalboss/sprites/fire_ball_anim-Sheet.png"); //26
                paths.add("finalboss/sprites/lava_lava_anim.png"); //27
                paths.add("finalboss/sprites/lava_lake_anim.png"); //28
                paths.add("finalboss/sprites/lava_laser_anim.png"); //29
                SpriteResource resource = new SpriteResource(paths);
                resource.loadResources();
                return resource;
        }

        private void payout(int a, int b, int c, boolean won, PostbattleView finalScreen) {
                if(won){
                        a = (int) Math.floor(Math.max(0, (pparent.maxTime - pparent.time)/5));
                        b = fbChalkboard.winSreak*25;
                        c = 0;
                        if(lefthand.getType() == WeaponType.NONE){c+= 50;}
                        if(righthand.getType() == WeaponType.NONE){c+= 50;}
                        finalScreen.payout(a, b, c, "Time Bonus", "Winstreak Bonus", "Single/Handless Bonus", true);
                }else{
                        a = (int) Math.floor((pparent.bossHealth.statValue)/20);
                        b = (int) Math.floor(pparent.time);
                        c = (18 - getFbChalkboard().getTotalLevels(lefthand.getType(), righthand.getType()));
                        finalScreen.payout(a, b, c, "Damage Dealt", "Time Survived", "Beginner Bonus", false);
                }
                fbChalkboard.setMoney(fbChalkboard.getMoney() + a + b + c);
        }

        public void levelAdvance(int i) throws IOException, TransformerException {
                System.out.println("LEVEL WAS ADVANCED");
                for (List<GameObject> g : grid) {
                        for (GameObject h : g) {
                                throwAway(h);
                                System.out.println(h.name);
                        }
                }
                for (GameSystem s : systems) {
                        for (GameObject g : s.gameObjects) {
                                if (g.getComponent(Tag.STAB) != null || g.getComponent(Tag.VILL) != null) {
                                        throwAway(g); // this should PROBABLY do it for NOW
                                }
                        }

                }
                vac.cleanup();
                this.bossObject.removeComponent(vac);
                if (i == 0) {
                        PostbattleView finalScreen = (PostbattleView) parent.parent.screens.get(3);
                        payout(1, 2, 3, false, finalScreen); // we can set up more complex logic based on other variables
                        fbChalkboard.winSreak = 0;
                } else if (i == 1) {
                        PostbattleView finalScreen = (PostbattleView) parent.parent.screens.get(3);
                        payout(10, 15, 20, true, finalScreen);
                        fbChalkboard.winSreak++;
                } else {
                        System.out.println("hey dont do that");
                }
                grid.clear();
                this.lefthand = new WeaponComponent(this.playerObject, this.controller, fbChalkboard);
                this.righthand = new WeaponComponent(this.playerObject, this.controller, fbChalkboard);
                this.controller.makeEepyAgain();
                this.saver.save();
                BattleView pp = (BattleView) this.pparent;
                CenterComponent current = (CenterComponent) playerObject.getComponent(Tag.CENTER);
                playerObject.removeComponent(current);
                playerObject.addComponent((new CenterComponent(playerObject, viewport)));
                pp.Advance();
                this.pparent.Reset();
                pp.mcHealth.updateStatsBar(0.0);
                pp.bossHealth.updateStatsBar(0.0);
                this.ticked = false;
        }

        @Override
        public void onTick(long nanosSincePreviousTick) throws InvocationTargetException, NoSuchMethodException,
                        InstantiationException, IllegalAccessException, IOException, TransformerException {
//                if (controller.)

                for (GameObject o : trash) {
                        removeGameObject(o);
                }
                trash = new ArrayList<>();
                for (GameSystem o : systems) {
                        o.onTick(nanosSincePreviousTick);
                }
                if (this.pparent.bossHealth.statValue <= 0.0) {
                        advance = true;
                        levelAdvance(1);
                } else if (this.pparent.mcHealth.statValue <= 0.0) { // at some point these will diverge lmao
                        advance = true;
                        levelAdvance(0);
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

        public void triggerSecondPhase(){
                this.vac.setEnraged(true);
        }

        @Override
        protected void onLateTick() throws InvocationTargetException, NoSuchMethodException, InstantiationException,
                        IllegalAccessException {
                super.onLateTick();
                try {
                        if (!ticked) {
                                this.ticked = true;
                                this.onTick(0);
                        }
                } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                } catch (TransformerException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
        }

        public void addSoundObjects(){
                this.battleMusic = new GameObject(this, "battle music");
                BackgroundSoundComponent battleSoundComp = new BackgroundSoundComponent(this.battleMusic,
                    this.parent, this.fbChalkboard.getSounds().resource.get(3), - 5F,
                    SoundSystem.SoundType.MUSIC);
                this.battleMusic.addComponent(battleSoundComp);
                this.battleMusic.callSystems();
                battleSoundComp.mute.setValue(true);

                this.dialogueSound = new GameObject(this, "dialogue sound");
                this.dialogueSoundComp =
                    new ConditionalSoundComponent(this.dialogueSound,
                        this.fbChalkboard.getSounds().resource.get(4), -10F,
                        SoundSystem.SoundType.UI);
                this.dialogueSound.addComponent(dialogueSoundComp);
                this.dialogueSound.callSystems();

                GameObject caveSounds = new GameObject(this, "caveEnv");
                BackgroundSoundComponent caveSoundComp = new BackgroundSoundComponent(caveSounds,
                        this.parent, this.fbChalkboard.getSounds().resource.get(8), - 10F,
                    SoundSystem.SoundType.ENVIRONMENT);
                caveSounds.addComponent(caveSoundComp);
                caveSounds.callSystems();
                caveSoundComp.mute.setValue(true);

                GameObject caveEcho = new GameObject(this, "caveEnv");
                BackgroundSoundComponent caveEchoComp = new BackgroundSoundComponent(caveSounds,
                    this.parent, this.fbChalkboard.getSounds().resource.get(9), - 20F,
                    SoundSystem.SoundType.ENVIRONMENT);
                caveSounds.addComponent(caveSoundComp);
                caveEcho.callSystems();
                caveEchoComp.mute.setValue(true);
                caveEchoComp.c.setMicrosecondPosition(500000);
        }
}
