package finalboss.objects;

import engine.components.*;
import engine.components.TimedDisappearanceComponent;
import engine.hierarchy.GameObject;
import engine.hierarchy.GameWorld;
import engine.save.LoadManager;
import finalboss.FBChalkboard;
import finalboss.custom_components.*;
import engine.physics.PhysicsComponent;
import finalboss.custom_components.main_character.MainCharacterAnimationComponent;
import finalboss.custom_components.main_character.MainCharacterCollision;
import finalboss.custom_components.main_character.PlayerControllerComponent;
import finalboss.custom_components.main_character.weapons.WeaponType;
import finalboss.dialogue.Conditions;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FBLoadManager extends LoadManager {

    public boolean success;
    private Map<String, Class<? extends Component>> correlator;

    public HashMap<WeaponType, HashMap<String, Integer>> levelMap;
    public HashMap<Conditions, Boolean> conditionsMap;
    public int money;

    Document d;
    FBChalkboard chalk;
    String url;


    public Document getD() {
        return d;
    }

    public FBLoadManager(GameWorld parent, String url, FBChalkboard chalk) throws IOException, SAXException, ParserConfigurationException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        super(parent);
        this.parent = parent;
        this.d = null;
        this.chalk = chalk;
        this.url = url;
        levelMap = null;
        conditionsMap = null;
    }

    public int parser() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        try {
            this.success = true;
            d = startup(url);
        }
        catch(ParserConfigurationException | IOException | SAXException e){
            System.out.println(e.getMessage());
            this.success = false;
        }
        if(success) {
            assert d != null;
            return parse(d);
        } else {
            return 1;
        }
    }

    @Override
    public int parse(Document d) {
        try {
            d.getDocumentElement().normalize();
            NodeList nList = d.getElementsByTagName("weapon");
            HashMap<WeaponType, HashMap<String, Integer>> levelMap = new HashMap<>();
            if (nList.getLength() != 7) {
                System.out.println("nlist wrong length-- " + nList.getLength());
                return 1;
            }
;            for (int i = 0; i < nList.getLength(); i++) {
                Element o = (Element) nList.item(i);
                WeaponType wp = WeaponType.valueOf(o.getAttribute("name"));
                System.out.println(wp);
                Integer damage = Integer.parseInt(o.getAttribute("damageLevel"));
                Integer range = Integer.parseInt(o.getAttribute("rangeLevel"));
                Integer special = Integer.parseInt(o.getAttribute("specialLevel"));
                HashMap<String, Integer> c = new HashMap<>();
                c.put("DAMAGE", damage); c.put("RANGE", range); c.put("SPECIAL", special);
                levelMap.put(wp, c);
            }
            HashMap<Conditions, Boolean> conditions = new HashMap<>();
            NodeList lList = d.getElementsByTagName("conditions");
            for (int i = 0; i < lList.getLength(); i++) {
                Element o = (Element) lList.item(i);
                Conditions condition = Conditions.parseConditions(o.getAttribute("name"));
                Boolean done = Boolean.parseBoolean(o.getAttribute("boolean"));
                conditions.put(condition, done);
            }
            NodeList money = d.getElementsByTagName("money");
            Element e = (Element) money.item(0);
            int i = Integer.parseInt(e.getAttribute("earned"));
            this.levelMap = levelMap;
            this.conditionsMap = conditions;
            this.money = i;
            return 0;
        }
        catch(NullPointerException | IllegalArgumentException e){
            System.out.println("an error occurred of class " + e.getClass());
            System.out.println(e.getMessage());
            return 1;
        }
    }
}
