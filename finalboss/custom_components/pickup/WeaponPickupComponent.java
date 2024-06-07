package finalboss.custom_components.pickup;

import engine.components.*;
import engine.hierarchy.GameObject;
import finalboss.FBChalkboard;
import finalboss.custom_components.main_character.PlayerControllerComponent;
import finalboss.custom_components.main_character.weapons.WeaponComponent;
import finalboss.custom_components.main_character.weapons.WeaponType;
import finalboss.custom_components.main_character.weapons.weapon_implementations.*;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class WeaponPickupComponent extends PickupComponent {

    PlayerControllerComponent pcc;
    WeaponType wp;

    Map<WeaponType, Class<? extends WeaponComponent>> correlator;

    SpriteComponent sp;
    boolean flipped;

    double damage;

    FBChalkboard chalk;

    public WeaponPickupComponent(GameObject g, PlayerControllerComponent pcc, WeaponType wp, int threshhold, SpriteComponent sp, FBChalkboard chalk) {
        super(g);
        this.tag = Tag.COLLISION;
        this.pcc = pcc;
        this.wp = wp;
        this.correlator = correlate();
        this.pu = true;
        this.respawnTimer = 0;
        this.threshhold = threshhold;
        this.sp = sp;
        this.flipped = false;
        this.damage = 1;
        this.chalk = chalk;
    }

    public boolean getPu(){return pu;}

    @Override
    public void tick(long n) {
        this.respawnTimer += 1;
        this.flipped = false;
        if(!pu && respawnTimer > threshhold){
            pu = true;
            sp.setActive(true);

        }
    }

    public Map<WeaponType, Class<? extends WeaponComponent>> correlate() {
        Map<WeaponType, Class<? extends WeaponComponent>> cor = new HashMap<>();
        cor.put(WeaponType.WAND, Wand.class);
        cor.put(WeaponType.WATERING_CAN, WateringCan.class);
        cor.put(WeaponType.NET, Net.class);
        cor.put(WeaponType.SHIELD, Shield.class);
        cor.put(WeaponType.ROPE, Rope.class);
        cor.put(WeaponType.FISHING_POLE, FishingPole.class);
        return cor;
    }

    @Override
    public void onLateTick() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if(flipped) {
            if (pcc.getRighthand().getType() == WeaponType.NONE && pcc.getRighthand().getType() != wp && pcc.getLefthand().getType() != wp) {
                WeaponComponent c = correlator.get(wp).getDeclaredConstructor(GameObject.class, PlayerControllerComponent.class, FBChalkboard.class).newInstance(pcc.parent, pcc, chalk);
                pcc.setRighthand(c);
            } else if (pcc.getLefthand().getType() != wp && pcc.getRighthand().getType() != wp) {
                WeaponComponent c = correlator.get(wp).getDeclaredConstructor(GameObject.class, PlayerControllerComponent.class, FBChalkboard.class).newInstance(pcc.parent, pcc, chalk);
                pcc.setLefthand(c);
            }
        }
    }

    @Override
    public void pickup() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        flipped = true;
        sp.setActive(false);
        pu = false;
    }
}
