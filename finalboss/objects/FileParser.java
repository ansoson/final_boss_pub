package finalboss.objects;

import engine.components.*;
import engine.hierarchy.GameObject;
import engine.hierarchy.GameSystem;
import engine.physics.PhysicsComponent;
import engine.shapes.ShapeEnum;
import engine.sprite.SpriteResource;
import engine.support.Vec2d;
import engine.systems.CollisionSystem;
import engine.systems.PathfindingSystem;
import finalboss.FBChalkboard;
import finalboss.custom_components.LevelEndComponent;
import finalboss.custom_components.enemy.*;
import finalboss.custom_components.main_character.MainCharacterAnimationComponent;
import finalboss.custom_components.main_character.weapons.WeaponType;
import finalboss.custom_components.pickup.WeaponPickupComponent;
import finalboss.custom_components.villain.VillainAnimationComponent;
import finalboss.custom_components.villain.VillainAttackComponent;
import finalboss.custom_components.villain.VillainCollisionComponent;
import finalboss.gameworlds.LevelGameworld;
import finalboss.gameworlds.Overworld;
import finalboss.levels.LevelComponents;
import finalboss.screens.ErrorView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class FileParser {

    String url;
    LevelComponents level;
    SpriteResource r;
    LevelGameworld parent;
    int sprite_size;
    Vec2d start_position;
    FBChalkboard chalk;
    public FileParser(LevelComponents level, SpriteResource r, LevelGameworld parent, int sprite_size, Vec2d st, FBChalkboard chalk){
        this.url = level.url;
        this.level = level;
        this.r =r;
        this.parent = parent;
        this.sprite_size = sprite_size;
        this.start_position = st;
        this.chalk = chalk;
    }


    public List<List<GameObject>> parse() {
        int num = 0;
        int l = 0; //updating the level
        int i_perm;

        List<List<GameObject>> g; //grid
        List<GameObject> enemies = new ArrayList<>(); //enemies list
        GameSystem graphics = parent.getSystem("GS");
        CollisionSystem col = (CollisionSystem) parent.getSystem("CS");
        GameSystem timer = parent.getSystem("TS");
        //GameSystem p = parent.getSystem("PS");

        //CODE FOR WEAPON GENERATION
        ArrayList<Integer> choice = new ArrayList<>();
        choice.add(0);
        choice.add(0);
        choice.add(0);
        while (Objects.equals(choice.get(0), choice.get(1)) || Objects.equals(choice.get(0), choice.get(2)) || Objects.equals(choice.get(1), choice.get(2))) {
            Random r = new Random();
            choice.clear();
            choice.add(r.nextInt(6));
            choice.add(r.nextInt(6));
            choice.add(r.nextInt(6));
        }
        try {
            g = new ArrayList<>();
            FileReader in = new FileReader(url);
            BufferedReader br = new BufferedReader(in);
            String line;
            i_perm = 0;
            while ((line = br.readLine()) != null) {
                ArrayList<GameObject> inner = new ArrayList<>();
                for (int i = 0; i < line.length(); i++) {

                    Vec2d pos = new Vec2d((i - 6) * sprite_size, (l - 6) * sprite_size);
                    Vec2d size = new Vec2d(sprite_size, sprite_size);
                    Vec2d def = parent.windowSize;
                    GameObject obj = new GameObject(parent, "tile", pos, size, def, 0.0);
                    int tile = Integer.parseInt(String.valueOf(line.charAt(i)));
                    if (tile > r.resource.size()) {
                        l = l + 1;
                        throw new FileParserException("Calling for invalid tile " + tile + " on line " + l);
                    }
                    obj.addComponent(new SpriteComponent(obj, r, tile, 0, 0, sprite_size, sprite_size));
                    if (tile == 1) {
                        obj = new GameObject(parent, "tile", pos, size, def, 0.0);
                        obj.addComponent(new SpriteComponent(obj, r, 0, 0, 0, sprite_size, sprite_size));
                        GameObject obj2 = new GameObject(parent, "tile", pos, size, def, 3.0);
                        if (level.url.equals("finalboss/levels/battleworld.txt")) {
                            obj2.addComponent(new OutsizedSpriteComponent(obj2, r, tile, 0, 0, 144, 144, 3, 3));
                        } else {
                            obj2.addComponent(new OutsizedSpriteComponent(obj2, r, tile, 0, 0, 144, 144, 3, 3));
                        }
                        obj2.addComponent(new CollisionComponent(obj2, ShapeEnum.AAB, 1, true));
                        col.addStaticObject(obj2);
                        graphics.addGameObject(obj2);
                    }
                    if (tile == 2) {
                        obj = new GameObject(parent, "tile", pos, size, def, 0.0);
                        obj.addComponent(new SpriteComponent(obj, r, 0, 0, 0, sprite_size, sprite_size));
                        GameObject obj2 = new GameObject(parent, "tile", pos, size, def, 2.0);
                        obj2.addComponent(new OutsizedSpriteComponent(obj, r, 8, 0, 0, 144, 144, 3, 3));
                        graphics.addGameObject(obj2);
                    }
                    if (tile == 4) {
                        obj = new GameObject(parent, "tile", pos, size, def, 3.0);
                        obj.addComponent(new OutsizedSpriteComponent(obj, r, tile, 0, 0, 144, 144, 3, 3));
                        obj.addComponent(new CollisionComponent(obj, ShapeEnum.AAB, 1, true));
                        col.addStaticObject(obj);
                    }
                    if (tile == 5) {
                        obj = new GameObject(parent, "tile", pos, size, def, 2.0);
                        obj.addComponent(new SpriteComponent(obj, r, 5, 0, 0, 48, 48));
                        obj.addComponent(new CollisionComponent(obj, ShapeEnum.AAB, 1, true));
                        col.addStaticObject(obj);
                    }
                    if (tile == 6) {
                        obj = new GameObject(parent, "tile", pos, size, def, 2.0);
                        obj.addComponent(new SpriteComponent(obj, r, 6, 0, 0, 48, 48));
                        obj.addComponent(new CollisionComponent(obj, ShapeEnum.AAB, 1, true));
                        col.addStaticObject(obj);
                    }
                    if (tile == 7) {
                        obj = new GameObject(parent, "tile", pos, size, def, 2.0);
                        obj.addComponent(new SpriteComponent(obj, r, 5, 0, 0, 48, 48));
                        obj.addComponent(new CollisionComponent(obj, ShapeEnum.AAB, 1, true));
                        col.addStaticObject(obj);
                        GameObject obj2 = new GameObject(parent, "tile", pos, size, def, 3.0);
                        obj2.addComponent(new OutsizedSpriteComponent(obj2, r, 10, 0, 0, 144, 144, 3, 3));
                        obj2.addComponent(new CollisionComponent(obj2, ShapeEnum.AAB, 1, true));
                        col.addStaticObject(obj2);
                        graphics.addGameObject(obj2);
                    } else {
                        if (level.url.equals("finalboss/levels/overworld.txt")) {
                            if (i == 13) {
                                if (l == 9) {
                                    if (choice.get(0) == 1 || choice.get(1) == 1 || choice.get(2) == 1) {
                                        GameObject enemy = new GameObject(parent, "rope", pos, size, def, 3.1);
                                        SpriteComponent s = new SpriteComponent(enemy, r, 17, 0, 0, 48, 48);
                                        enemy.addComponent(s);
                                        enemy.addComponent(new WeaponPickupComponent(enemy, parent.controller, WeaponType.ROPE, 30, s, parent.getFbChalkboard()));
                                        enemy.callSystems();
                                        num += 1;
                                        enemies.add(enemy);
                                    }
                                } else if (l == 12) {
                                    if (choice.get(0) == 2 || choice.get(1) == 2 || choice.get(2) == 2) {
                                        GameObject enemy = new GameObject(parent, "net", pos, size, def, 3.1);
                                        SpriteComponent s = new SpriteComponent(enemy, r, 18, 0, 0, 48, 48);
                                        enemy.addComponent(s);
                                        enemy.addComponent(new WeaponPickupComponent(enemy, parent.controller, WeaponType.NET, 30, s, parent.getFbChalkboard()));
                                        enemy.callSystems();
                                        num += 1;
                                        enemies.add(enemy);
                                    }
                                }
                            }
                            if (i == 16) {
                                if (l == 9) {
                                    if (choice.get(0) == 3 || choice.get(1) == 3 || choice.get(2) == 3) {
                                        GameObject enemy = new GameObject(parent, "shield", pos, size, def, 3.1);
                                        SpriteComponent s = new SpriteComponent(enemy, r, 19, 0, 0, 48, 48);
                                        enemy.addComponent(s);
                                        enemy.addComponent(new WeaponPickupComponent(enemy, parent.controller, WeaponType.SHIELD, 30, s, parent.getFbChalkboard()));
                                        enemy.callSystems();
                                        num += 1;
                                        enemies.add(enemy);
                                    }
                                } else if (l == 12) {
                                    if (choice.get(0) == 4 || choice.get(1) == 4 || choice.get(2) == 4) {
                                        GameObject enemy = new GameObject(parent, "wand", pos, size, def, 3.1);
                                        SpriteComponent s = new SpriteComponent(enemy, r, 20, 0, 0, 48, 48);
                                        enemy.addComponent(s);
                                        enemy.addComponent(new WeaponPickupComponent(enemy, parent.controller, WeaponType.WAND, 30, s, parent.getFbChalkboard()));
                                        enemy.callSystems();
                                        num += 1;
                                        enemies.add(enemy);
                                    }
                                }
                            }
                            if (i == 19) {
                                if (l == 9) {
                                    if (choice.get(0) == 5 || choice.get(1) == 5 || choice.get(2) == 5) {
                                        GameObject enemy = new GameObject(parent, "wateringcan", pos, size, def, 3.1);
                                        SpriteComponent s = new SpriteComponent(enemy, r, 21, 0, 0, 48, 48);
                                        enemy.addComponent(s);
                                        enemy.addComponent(new WeaponPickupComponent(enemy, parent.controller, WeaponType.WATERING_CAN, 30, s, parent.getFbChalkboard()));
                                        enemy.callSystems();
                                        num += 1;
                                        enemies.add(enemy);
                                    }
                                } else if (l == 12) {
                                    if (choice.get(0) == 0 || choice.get(1) == 0 || choice.get(2) == 0) {
                                        GameObject enemy = new GameObject(parent, "fishingpole", pos, size, def, 3.1);
                                        SpriteComponent s = new SpriteComponent(enemy, r, 22, 0, 0, 48, 48);
                                        enemy.addComponent(s);
                                        enemy.addComponent(new WeaponPickupComponent(enemy, parent.controller, WeaponType.FISHING_POLE, 30, s, parent.getFbChalkboard()));
                                        enemy.callSystems();
                                        num += 1;
                                        enemies.add(enemy);
                                    }
                                }
                            }
                        }
                    }
                    graphics.addGameObject(obj);
                    inner.add(obj);
                    if (i > i_perm) {
                        i_perm = i;
                    }
                }
                g.add(inner);
                l++;
            }
            in.close();
            //add bed
            if (level.url.equals("finalboss/levels/overworld.txt")) {
                GameObject bed = new GameObject(parent, "bed", new Vec2d(33 * sprite_size, 5 * sprite_size), new Vec2d(3 * sprite_size, 3 * sprite_size), parent.windowSize, 3.0);
                GameObject grassBed = new GameObject(parent, "grassBed", new Vec2d(33.5 * sprite_size, 7 * sprite_size), new Vec2d(sprite_size, sprite_size), parent.windowSize, 2.0);
                GameObject bigBeautifulWall = new GameObject(parent, "grassBed2", new Vec2d(33.5 * sprite_size, 4.1 * sprite_size), new Vec2d(sprite_size, sprite_size), parent.windowSize, 3.0);
                SpriteComponent s = new OutsizedSpriteComponent(grassBed, r, 7, 0, 0, 800, 800, 16, 16);
                SpriteComponent ss = new OutsizedSpriteComponent(bigBeautifulWall, r, 10, 0, 0, 800, 800, 16, 16);
                graphics.addGameObject(grassBed);
                grassBed.addComponent(s);
                graphics.addGameObject(bigBeautifulWall);
                bigBeautifulWall.addComponent(ss);
                bed.addComponent(new LevelEndComponent(bed, ShapeEnum.AAB, 1, parent));
                bed.callSystems();
                num += 1;
                enemies.add(bed);
            } //TODO: HACK. HACK!
            if (level.url.equals("finalboss/levels/overworld.txt")) {
                GameObject grassBed = new GameObject(parent, "grassBed", new Vec2d(2 * sprite_size, 3 * sprite_size), new Vec2d(240, 240), parent.windowSize, 2.0);
                SpriteComponent s = new SpriteComponent(grassBed, r, 9, 0, 0, 240, 240);
                graphics.addGameObject(grassBed);
                grassBed.addComponent(s);
            } //TODO: HACK. HACK!
            else if (level.url.equals("finalboss/levels/battleworld.txt")) {
                GameObject grassBed = new GameObject(parent, "carpet", new Vec2d(1 * sprite_size, 4 * sprite_size), new Vec2d(640, 480), parent.windowSize, 2.0);
                SpriteComponent ss = new SpriteComponent(grassBed, r, 9, 0, 0, 480, 640);
                graphics.addGameObject(grassBed);
                grassBed.addComponent(ss);
                GameObject villain = new GameObject(parent, "our special guy", new Vec2d(16 * sprite_size, 4 * sprite_size), new Vec2d(6 * sprite_size, 6 * sprite_size), parent.windowSize, 3.0);
                SpriteComponent s = new SpriteComponent(villain, r, 23, 0, 0, 288, 288);
                VillainAnimationComponent vac = new VillainAnimationComponent(villain, s, 3);
                villain.addComponent(vac);
                villain.addComponent(s);
                villain.addComponent(new VillainCollisionComponent(villain, ShapeEnum.AAB, 3, false, this.parent.pparent.bossHealth, chalk));
                villain.addComponent(new VillainAttackComponent(Tag.ENEMY, villain, this.parent.pparent, chalk, r, vac));
                villain.callSystems();
                num += 1;
                enemies.add(villain);
            }
        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            parent.parent.parent.screenOn = 4;
            ErrorView err = parent.parent.parent.getErrorScreen();
            String s = "Threw " + e.getClass() + ": " + e.getMessage() + " at " + e.getStackTrace()[0];
            err.setS(s);
            parent.num_enemies = 0;
            return null;
        }
        g.add(enemies);
        PathfindingSystem p = (PathfindingSystem) parent.getSystem("PS");
        p.updateLevel(l, i_perm);
        parent.num_enemies = num;
        return g;
    }
}
