package finalboss;

import engine.UIKit.TextBox;
import engine.dialogue.Conversation;
import engine.dialogue.DialogueModel;
import engine.dialogue.Speed;
import engine.dialogue.Tone;
import engine.dialogue.Line;
import engine.hierarchy.GameObject;
import engine.sprite.SoundResource;
import engine.sprite.SpriteResource;
import engine.systems.SoundSystem;
import finalboss.custom_components.main_character.weapons.WeaponComponent;
import finalboss.custom_components.main_character.weapons.WeaponType;
import finalboss.screens.WeaponStat;
import finalboss.dialogue.Conditions;
import finalboss.dialogue.FBDialogueControl;

import java.util.*;

public class FBChalkboard {

    public void loadSave(HashMap<WeaponType, HashMap<String, Integer>> levelMap,
            HashMap<Conditions, Boolean> conditionsMap) {
        for (Map.Entry<WeaponType, HashMap<String, Integer>> entry : levelMap.entrySet()) {
            WeaponType wp = entry.getKey();
            for (Map.Entry<String, Integer> e : entry.getValue().entrySet()) {
                WeaponStat s = WeaponStat.valueOf(e.getKey());
                Integer d = e.getValue();
                HashMap<WeaponType, Integer> wpp = levels.get(s);
                wpp.put(wp, d);
                levels.put(s, wpp);
            }
        }
        conditions.putAll(conditionsMap); // TODO: make this cheat resistant
        // or like, at least garbage input resistant... if its over 4 you should cap it
    }

    public boolean getFinal(WeaponType type) {
        return finals.get(type);
    }

    // things to track
    // 1. player character
    GameObject player;
    // 2. player health
    int health;

    // 3. conditions met/not met
    public HashMap<Conditions, Boolean> conditions;
    FBDialogueControl dialogueControl;
    public Boolean dialogueOn;
    Boolean controlsLocked;
    // 5. lefthand weapon
    WeaponComponent lefthand;
    // 6. righthand weapon
    WeaponComponent righthand;
    // 7. weapon levels
    HashMap<WeaponStat, HashMap<WeaponType, Integer>> levels;
    HashMap<WeaponStat, HashMap<WeaponType, ArrayList<Double>>> upgrades;

    HashMap<WeaponType, Boolean> finals;
    // 8. money
    int money;

    Conditions activeCondition;

    SpriteResource spriteResource;
    SoundResource soundResource;
    ArrayList<Conversation> dialog;
    public int gameNum;
    public int winSreak;
    public boolean hasWon;
    public boolean updateSoundSettings = false;
    public SoundSystem.SoundType soundToUpdate = SoundSystem.SoundType.MASTER;

    public HashMap<SoundSystem.SoundType, Float> soundsVolumeStat = new HashMap<>();
    public HashMap<SoundSystem.SoundType, Boolean> soundsMuteStatus = new HashMap<>();

    public FBChalkboard(int h) {
        this.gameNum = 0;
        this.winSreak = 0;
        this.hasWon = false;
        this.health = h;
        this.conditions = new HashMap<>();
        this.money = 0;
        this.righthand = null;
        this.lefthand = null;
        this.levels = new HashMap<>();
        this.activeCondition = Conditions.START;
        this.dialogueOn = false;
        this.controlsLocked = false;
        initializeHashmaps();
        this.loadSprites();
        this.loadSounds();
    }

    private void initializeHashmaps() {
        // add dialogues
        // add conditions
        // add weapons
        levels = createLevels();
        upgrades = createUpgrades();
        conditions = initConditions();
        this.initializeVolumeLevels();
        this.initializeMuteLevels();
        finals = initFinals();

    }

    public WeaponComponent getLefthand() {
        return lefthand;
    }

    public WeaponComponent getRighthand() {
        return righthand;
    }

    public void setLefthand(WeaponComponent lefthand) {
        this.lefthand = lefthand;
    }

    public void setRighthand(WeaponComponent righthand) {
        this.righthand = righthand;
    }

    public void setPlayer(GameObject player) {
        this.player = player;
    }

    public GameObject getPlayer() {
        return player;
    }

    public SpriteResource getSprites() {
        return this.spriteResource;
    }

    public SoundResource getSounds() {
        return this.soundResource;
    }

    public void SetDialogueControl(FBDialogueControl control) {
        this.dialogueControl = control;
    }

    public FBDialogueControl getDialogueControl() {
        return this.dialogueControl;
    }

    public void dialogueOn(Boolean tf) {
        if (tf) {
            this.dialogueControl.setActiveCond(this.activeCondition);
            this.dialogueControl.loadConversation();
            if (this.dialogueControl.getConversation().canPlay()) {
                this.dialogueOn = tf;
                this.controlsLocked = tf;
            }
        } else {
            this.dialogueOn = tf;
            this.controlsLocked = tf;
        }
    }

    public boolean isTalking() {
        return this.dialogueOn;
    }

    public void lockControls(Boolean tf) {
        this.controlsLocked = tf;
    }

    public boolean locked() {
        return this.controlsLocked;
    }

    public void setupDialogue(TextBox box) {

        dialog = new ArrayList<Conversation>();
        dialog.add(new Conversation(
                new Line[] {
                        new Line("Who the...?"),
                        new Line("How did you get in here?!", Tone.ANGRY),
                        new Line("I know I told someone to watch the front door!", Tone.ANGRY),
                        new Line("*SIGH*", Tone.ACTION),
                        new Line("No matter... this will be quick.") },
                Conditions.FIRSTGAME, Tone.NEUTRAL, Speed.DEFAULT, true));

        dialog.add(new Conversation(
                new Line[] {
                        new Line("You again?"),
                        new Line("I thought I dealt with you already."),
                        new Line("But here you are...?"),
                        new Line("...are you enjoying this???", Tone.ANGRY) },
                Conditions.SECGAME, Tone.NEUTRAL, Speed.DEFAULT, false));

        dialog.add(new Conversation(
                new Line[] {
                        new Line("Welcome back...") },
                Conditions.START, Tone.NEUTRAL, Speed.DEFAULT, true));

        dialog.add(new Conversation(
                new Line[] {
                        new Line("Back again already??"),
                        new Line("You know... absence makes the heart grow fonder...")
                     },
                Conditions.START, Tone.NEUTRAL, Speed.DEFAULT, true));

        dialog.add(new Conversation(
                new Line[] {
                        new Line("You really are relentless.") },
                Conditions.START, Tone.NEUTRAL, Speed.DEFAULT, false));

        dialog.add(new Conversation(
                new Line[] {
                        new Line("Here we go again...") },
                Conditions.START, Tone.NEUTRAL, Speed.DEFAULT, true));

        dialog.add(new Conversation(
                new Line[] {
                        new Line("You know, I'm starting to get concerned..."),
                        new Line("...are you...are you okay?", Tone.WHISPER) },
                Conditions.CONCERNED, Tone.NEUTRAL, Speed.DEFAULT, false));

        dialog.add(new Conversation(
                new Line[] {
                        new Line("*SIGH*", Tone.WHISPER, Speed.SLOW),
                        new Line("HAVE IT YOUR WAY!!!!!!", Tone.ANGRY, Speed.FAST)
                },
                Conditions.CONCERNED2, Tone.NEUTRAL, Speed.DEFAULT, false));

        dialog.add(new Conversation(
                new Line[] {
                        new Line("Why don't we pretend I didn't see you and you walk back out with your dignity..."),
                        new Line("...or what's left of it."),
                        new Line("...no?"),
                        new Line("don't say I didn't warn you", Tone.ANGRY) },
                Conditions.START, Tone.NEUTRAL, Speed.DEFAULT, false));

        dialog.add(new Conversation(
                new Line[] {
                        new Line("Fear not... this will all be over soon") },
                Conditions.PLAYERHP, Tone.NEUTRAL, Speed.DEFAULT, true));

        dialog.add(new Conversation(
                new Line[] {
                        new Line("ARGHHHHHHHHHHH"),
                        new Line("You insolent creature!"),
                        new Line("I may be wounded, but I will not go down so easily!") },
                Conditions.BOSSHP, Tone.ANGRY, Speed.FAST, true));

        dialog.add(new Conversation(
                new Line[] {
                        new Line("Did you just throw a watering can at me?", Tone.ANGRY),
                        new Line("I'm not giving that back."),
                        new Line("The only plants that will be watered today..."),
                        new Line("...are your bones!", Tone.ANGRY),
                        new Line("(Ugh, I need to workshop that one.)", Tone.WHISPER) },
                Conditions.WCHIT, Tone.NEUTRAL, Speed.DEFAULT, false));

        dialog.add(new Conversation(
                new Line[] {
                        new Line("...another one?", Speed.SLOW),
                        new Line("Where were you even hiding that...?"),
                        new Line("nevermind...") },
                Conditions.WCHIT2, Tone.NEUTRAL, Speed.DEFAULT, false));

        dialog.add(new Conversation(
                new Line[] {
                        new Line("Stop that! You're getting bubble fluid all over my lair!"),
                        new Line("Do you know what they put in those bottles?"),
                        new Line("Look, dish fluid is bad for the pH of my lava.", Tone.NEUTRAL) },
                Conditions.WANDHIT, Tone.ANGRY, Speed.DEFAULT, false));

        dialog.add(new Conversation(
                new Line[] {
                        new Line("Really? No weapons? Not one?"),
                        new Line("Do you want to go back outside and get something..."),
                        new Line("Look, it's not that I won't fight you. I'm just going to feel bad."),
                        new Line("A little bad."),
                        new Line("Do your worst???") },
                Conditions.UNARMED, Tone.NEUTRAL, Speed.DEFAULT, false));

        dialog.add(new Conversation(
                new Line[] {
                        new Line("So... hm."),
                        new Line("I... I wasn't trying!"),
                        new Line("Really!", Tone.ANGRY),
                        new Line("What? Are you not impressed? Then let's see how you handle this!") },
                Conditions.FIRSTWIN, Tone.NEUTRAL, Speed.DEFAULT, false));

        DialogueModel model = new DialogueModel(dialog);
        this.dialogueControl = new FBDialogueControl(box, model, Conditions.START, this);
        this.dialogueControl.setPriority(Conditions.values());
    }

    public void addOneWepDialogue() {
        String wep = "";
        if (lefthand.getType() != WeaponType.NONE) {
            wep = lefthand.getType().toString().toLowerCase();
        } else if (righthand.getType() != WeaponType.NONE) {
            wep = righthand.getType().toString().toLowerCase();
        }
        dialog.add(new Conversation(
                new Line[] {
                        new Line("One weapon? You must think yourself quite the master of the ..."),
                        new Line("..." + wep),
                        new Line("Well, I don't get it. But if you truly think you can beat me with it"),
                        new Line("you're welcome to try.") },
                Conditions.ONEWEP, Tone.NEUTRAL, Speed.DEFAULT, true));

        DialogueModel model = new DialogueModel(dialog);
        this.dialogueControl.setModel(model);

    }

    public void setActiveCond(Conditions cond) {
        this.activeCondition = cond;
        this.conditions.put(cond, true);
    }

    public Conditions getActiveCond() {
        return this.activeCondition;
    }

    public HashMap<WeaponType, Integer> initializeDamageLevels() {
        // check for load here
        HashMap<WeaponType, Integer> special = new HashMap<>();
        special.put(WeaponType.WATERING_CAN, 0);
        special.put(WeaponType.NET, 0);
        special.put(WeaponType.FISHING_POLE, 0);
        special.put(WeaponType.ROPE, 0);
        special.put(WeaponType.SHIELD, 0);
        special.put(WeaponType.WAND, 0);
        special.put(WeaponType.NONE, 0);
        return special;
    }

    public HashMap<WeaponType, Integer> initializeRangeLevels() {
        HashMap<WeaponType, Integer> special = new HashMap<>();
        special.put(WeaponType.WATERING_CAN, 0);
        special.put(WeaponType.NET, 0);
        special.put(WeaponType.FISHING_POLE, 0);
        special.put(WeaponType.ROPE, 0);
        special.put(WeaponType.SHIELD, 0);
        special.put(WeaponType.WAND, 0);
        special.put(WeaponType.NONE, 0);
        return special;
    }

    public void initializeMuteLevels() {
        this.soundsMuteStatus.put(SoundSystem.SoundType.MASTER, false);
        this.soundsMuteStatus.put(SoundSystem.SoundType.MUSIC, false);
        this.soundsMuteStatus.put(SoundSystem.SoundType.ENVIRONMENT, false);
        this.soundsMuteStatus.put(SoundSystem.SoundType.CREATURES, false);
        this.soundsMuteStatus.put(SoundSystem.SoundType.UI, false);
    }

    public void initializeVolumeLevels() {
        this.soundsVolumeStat.put(SoundSystem.SoundType.MASTER, 0.0F);
        this.soundsVolumeStat.put(SoundSystem.SoundType.MUSIC, 0.0F);
        this.soundsVolumeStat.put(SoundSystem.SoundType.ENVIRONMENT, 0.0F);
        this.soundsVolumeStat.put(SoundSystem.SoundType.CREATURES, 0.0F);
        this.soundsVolumeStat.put(SoundSystem.SoundType.UI, 0.0F);
    }

    public HashMap<WeaponType, Integer> initializeSpecialLevels() {
        HashMap<WeaponType, Integer> special = new HashMap<>();
        special.put(WeaponType.WATERING_CAN, 0);
        special.put(WeaponType.NET, 0);
        special.put(WeaponType.FISHING_POLE, 0);
        special.put(WeaponType.ROPE, 0);
        special.put(WeaponType.SHIELD, 0);
        special.put(WeaponType.WAND, 0);
        special.put(WeaponType.NONE, 0);
        return special;
    }

    public HashMap<WeaponType, Boolean> initFinals() {
        HashMap<WeaponType, Boolean> fin = new HashMap<>();
        fin.put(WeaponType.WATERING_CAN, false);
        fin.put(WeaponType.NET, false);
        fin.put(WeaponType.FISHING_POLE, false);
        fin.put(WeaponType.ROPE, false);
        fin.put(WeaponType.SHIELD, false);
        fin.put(WeaponType.WAND, false);
        fin.put(WeaponType.NONE, false);
        return fin;
    }

    public HashMap<WeaponStat, HashMap<WeaponType, Integer>> createLevels() {
        HashMap<WeaponStat, HashMap<WeaponType, Integer>> init = new HashMap<>();
        init.put(WeaponStat.RANGE, initializeRangeLevels());
        init.put(WeaponStat.DAMAGE, initializeDamageLevels());
        init.put(WeaponStat.SPECIAL, initializeSpecialLevels());
        return init;
    }

    public HashMap<WeaponType, ArrayList<Double>> getDamage() {
        HashMap<WeaponType, ArrayList<Double>> damage = new HashMap<>();
        ArrayList<Double> net = new ArrayList<>();
        net.add(1.5);
        net.add(3.0);
        net.add(4.5);
        net.add(6.0);
        damage.put(WeaponType.NET, net);
        ArrayList<Double> pole = new ArrayList<>();
        pole.add(2.25);
        pole.add(4.50);
        pole.add(6.0);
        pole.add(7.0);
        damage.put(WeaponType.FISHING_POLE, pole);
        ArrayList<Double> rope = new ArrayList<>();
        rope.add(1.0);
        rope.add(2.0);
        rope.add(3.0);
        rope.add(4.0);
        damage.put(WeaponType.ROPE, rope);
        ArrayList<Double> shield = new ArrayList<>();
        shield.add(2.0);
        shield.add(3.0);
        shield.add(4.0);
        shield.add(6.0);
        damage.put(WeaponType.SHIELD, shield);
        ArrayList<Double> wand = new ArrayList<>();
        wand.add(1.0);
        wand.add(2.0);
        wand.add(3.0);
        wand.add(4.0);
        damage.put(WeaponType.WAND, wand);
        ArrayList<Double> can = new ArrayList<>();
        can.add(12.0);
        can.add(15.0);
        can.add(18.0);
        can.add(20.0);
        damage.put(WeaponType.WATERING_CAN, can);
        ArrayList<Double> none = new ArrayList<>();
        none.add(1.0);
        none.add(1.0);
        none.add(1.0);
        none.add(1.0);
        damage.put(WeaponType.NONE, none);
        return damage;
    }

    public HashMap<WeaponType, ArrayList<Double>> getRange() {
        HashMap<WeaponType, ArrayList<Double>> range = new HashMap<>();
        ArrayList<Double> net = new ArrayList<>();
        net.add(1.0);
        net.add(1.1);
        net.add(1.2);
        net.add(1.3);
        range.put(WeaponType.NET, net);
        ArrayList<Double> pole = new ArrayList<>();
        pole.add(1.0);
        pole.add(1.1);
        pole.add(1.2);
        pole.add(1.3);
        range.put(WeaponType.FISHING_POLE, pole);
        ArrayList<Double> rope = new ArrayList<>();
        rope.add(1.0);
        rope.add(1.2);
        rope.add(1.4);
        rope.add(1.5);
        range.put(WeaponType.ROPE, rope);
        ArrayList<Double> shield = new ArrayList<>();
        shield.add(1.0);
        shield.add(1.1);
        shield.add(1.2);
        shield.add(1.3);
        range.put(WeaponType.SHIELD, shield);
        ArrayList<Double> wand = new ArrayList<>(); // bubble size multiplier
        wand.add(1.0);
        wand.add(1.2);
        wand.add(1.4);
        wand.add(1.6);
        range.put(WeaponType.WAND, wand);
        ArrayList<Double> can = new ArrayList<>();
        can.add(1.0);
        can.add(1.1);
        can.add(1.2);
        can.add(1.3);
        range.put(WeaponType.WATERING_CAN, can);
        ArrayList<Double> none = new ArrayList<>();
        none.add(0.0);
        none.add(0.0);
        none.add(0.0);
        none.add(0.0);
        range.put(WeaponType.NONE, none);
        return range;
    }

    private HashMap<WeaponType, ArrayList<Double>> getSpecial() {
        HashMap<WeaponType, ArrayList<Double>> special = new HashMap<>();
        ArrayList<Double> net = new ArrayList<>(); // cooldown
        net.add(30.0);
        net.add(25.0);
        net.add(15.0);
        net.add(10.0);
        special.put(WeaponType.NET, net);
        ArrayList<Double> pole = new ArrayList<>(); // activate hook damage
        pole.add(1.0);
        pole.add(2.0);
        pole.add(3.0);
        pole.add(4.0);
        special.put(WeaponType.FISHING_POLE, pole);
        ArrayList<Double> rope = new ArrayList<>(); // cooldown
        rope.add(30.0);
        rope.add(22.0);
        rope.add(20.0);
        rope.add(15.0);
        special.put(WeaponType.ROPE, rope);
        ArrayList<Double> shield = new ArrayList<>(); // invincibility time
        shield.add(4.0);
        shield.add(8.0);
        shield.add(12.0);
        shield.add(16.0);
        special.put(WeaponType.SHIELD, shield);
        ArrayList<Double> wand = new ArrayList<>(); // number of bubbles
        wand.add(1.0);
        wand.add(2.0);
        wand.add(3.0);
        wand.add(4.0);
        special.put(WeaponType.WAND, wand);
        ArrayList<Double> can = new ArrayList<>(); // residual damage
        can.add(0.0);
        can.add(1.01);
        can.add(2.01);
        can.add(3.01);
        special.put(WeaponType.WATERING_CAN, can);
        ArrayList<Double> none = new ArrayList<>();
        none.add(0.0);
        none.add(0.0);
        none.add(0.0);
        none.add(0.0);
        special.put(WeaponType.NONE, none);
        return special;
    }

    public HashMap<WeaponStat, HashMap<WeaponType, ArrayList<Double>>> createUpgrades() {
        HashMap<WeaponStat, HashMap<WeaponType, ArrayList<Double>>> upgrades = new HashMap<>();
        upgrades.put(WeaponStat.RANGE, getRange());
        upgrades.put(WeaponStat.DAMAGE, getDamage());
        upgrades.put(WeaponStat.SPECIAL, getSpecial());
        return upgrades;

    }

    public HashMap<Conditions, Boolean> initConditions() {
        HashMap<Conditions, Boolean> conditions = new HashMap<>();
        for (Conditions cond : Conditions.values()) {
            conditions.put(cond, false);
        }
        return conditions;
    }

    private void loadSprites() {
        List<String> p = new ArrayList<>();
        p.add("finalboss/sprites/heart.png"); // 0
        p.add("finalboss/sprites/tea.png"); // 1
        p.add("finalboss/sprites/gold.png"); // 2
        p.add("finalboss/sprites/diamond.png"); // 3
        p.add("finalboss/sprites/rope_UI.png"); // 4
        p.add("finalboss/sprites/net_UI.png"); // 5
        p.add("finalboss/sprites/shield_UI.png"); // 6
        p.add("finalboss/sprites/bubble_UI.png"); // 7
        p.add("finalboss/sprites/can_UI.png"); // 8
        p.add("finalboss/sprites/fishing_UI.png"); // 9
        p.add("finalboss/sprites/headshot.png"); // 10
        p.add("finalboss/sprites/fist_UI.png"); // 11
        p.add("finalboss/sprites/sound_on.png"); // 12
        p.add("finalboss/sprites/dash-icon.png"); // 13
        p.add("finalboss/sprites/headshot_ouch.png"); // 14


        this.spriteResource = new SpriteResource(p);
        this.spriteResource.loadResources();
    }

    private void loadSounds() {
        List<String> p = new ArrayList<>();
        p.add("finalboss/audio/ui-click.wav"); //0
        p.add("finalboss/audio/forest-ambience2.wav"); //1
        p.add("finalboss/audio/overworld-piano.wav"); //2
        p.add("finalboss/audio/battle-music.wav"); //3
        p.add("finalboss/audio/dialogue-sound.wav"); //4
        p.add("finalboss/audio/footsteps-grass.wav"); //5
        p.add("finalboss/audio/footsteps-stone.wav"); //6
        p.add("finalboss/audio/cave-sound-002.wav"); //7
        p.add("finalboss/audio/cave-sound-002.wav"); //8
        p.add("finalboss/audio/cave-sound-001.wav"); //9

        this.soundResource = new SoundResource(p);
        this.soundResource.loadResources();
    }

    public void setMoney(int money) {
        this.money = money;

    }

    public void makeFinal(WeaponType wep) {
        finals.put(wep, true);
    }

    public int getMoney() {
        return money;
    }

    public Double getValue(WeaponType w, WeaponStat s) {
        Integer retrieval = levels.get(s).get(w);
        // System.out.println("RETRIEVAL " + retrieval + "FOR STAT " + s.toString());
        return upgrades.get(s).get(w).get(retrieval); // currently assumes valid
    }

    public Integer getLevel(WeaponType w, WeaponStat s) {
        Integer retrieval = levels.get(s).get(w);
        // System.out.println("GET LEVEL " + retrieval + "FOR STAT " + s.toString());
        return retrieval;
    }

    public void setLevel(WeaponType w, WeaponStat stat, int value) {
        if (value > 3) {
            value = 3;
        }
        levels.get(stat).put(w, value); // assumes valid
    }

    public int getTotalLevels(WeaponType l, WeaponType r) {
        return levels.get(WeaponStat.DAMAGE).get(l) + levels.get(WeaponStat.RANGE).get(l)
                + levels.get(WeaponStat.SPECIAL).get(l) +
                levels.get(WeaponStat.DAMAGE).get(r) + levels.get(WeaponStat.RANGE).get(r)
                + levels.get(WeaponStat.SPECIAL).get(r);

    }

    public Set<WeaponType> getWeaponTypes() {
        return levels.get(WeaponStat.DAMAGE).keySet();
    }

    public boolean canFinalize(WeaponType weapon) {
        System.out.println(levels.get(WeaponStat.DAMAGE).get(weapon) + levels.get(WeaponStat.RANGE).get(weapon)
                + levels.get(WeaponStat.SPECIAL).get(weapon));
        System.out.println(levels.get(WeaponStat.DAMAGE).get(weapon) + levels.get(WeaponStat.RANGE).get(weapon)
                + levels.get(WeaponStat.SPECIAL).get(weapon) == 9);
        return levels.get(WeaponStat.DAMAGE).get(weapon) + levels.get(WeaponStat.RANGE).get(weapon)
                + levels.get(WeaponStat.SPECIAL).get(weapon) == 9;
    }

    public HashMap<Conditions, Boolean> getConditions() {
        return conditions;
    }

    public HashMap<WeaponType, Boolean> getFinals() {
        return finals;
    }
}
