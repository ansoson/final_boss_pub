package finalboss.objects;

import engine.hierarchy.GameWorld;
import engine.save.SaveManager;
import finalboss.FBChalkboard;
import finalboss.custom_components.main_character.weapons.WeaponType;
import finalboss.dialogue.Conditions;

import finalboss.screens.WeaponStat;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FBSaveManager extends SaveManager {

    Document d;
    String url;
    FBChalkboard chalk;
    public FBSaveManager(GameWorld parent, String url, FBChalkboard chalk) throws ParserConfigurationException {
        super(parent, url);
        this.url = url;
        this.d = startup();
        this.chalk = chalk;
    }


    @Override
    public void save() throws IOException, TransformerException {
        Element rootElement = d.getDocumentElement();
        if (rootElement != null) {
            while (rootElement.hasChildNodes()) {
                rootElement.removeChild(rootElement.getFirstChild());
            }
        }
        else{
            rootElement = d.createElement("root");
            d.appendChild(rootElement);
        }

        for (WeaponType w: chalk.getWeaponTypes()) {
            Element n = d.createElement("weapon");
            n.setAttribute("name", w.name());
            n.setAttribute("damageLevel", String.valueOf(chalk.getLevel(w, WeaponStat.DAMAGE)));
            n.setAttribute("specialLevel", String.valueOf(chalk.getLevel(w, WeaponStat.SPECIAL)));
            n.setAttribute("rangeLevel", String.valueOf(chalk.getLevel(w, WeaponStat.RANGE)));
            rootElement.appendChild(n);
        }

        Element n = d.createElement("money");
        n.setAttribute("earned", Integer.toString(chalk.getMoney()));
        rootElement.appendChild(n);
        d.setDocumentURI(url);
        DOMSource source = new DOMSource(d);
        FileWriter writer = new FileWriter(url);
        StreamResult result = new StreamResult(writer);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(source, result);
    }
}
