package engine.save;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import engine.components.*;
import engine.hierarchy.GameWorld;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class LoadManager {

    private final Map<String, Class<? extends Component>> correlator;
    private boolean success;
    protected GameWorld parent;

    public Document startup(String url) throws ParserConfigurationException, IOException, SAXException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            return docBuilder.parse(url);
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }
    }


    public int parse(Document d) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        d.getDocumentElement().normalize();

        NodeList list  = d.getElementsByTagName("object");

        for(int i = 0; i < list.getLength(); i++) {
            Node n = list.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                     Element e = (Element) n;
                     e.getAttribute("e");
                     e.getElementsByTagName("SpriteComponent");
                     e.getChildNodes();
                 }
            }
        return 0;
    }


    public Map<String, Class<? extends Component>> correlate() {
        Map<String, Class<? extends Component>> cor = new HashMap<>();
        cor.put("TransformComponent", TransformComponent.class);
        cor.put("AIComponent", AIComponent.class);
        cor.put("AnimationComponent", AnimationComponent.class);
        cor.put("CenterComponent", CenterComponent.class);
        cor.put("CollisionComponent", CollisionComponent.class);
        cor.put("DraggingComponent", TransformComponent.class);
        cor.put("GraphicsComponent", GraphicsComponent.class);
        cor.put("PickupComponent", PickupComponent.class);
        cor.put("SpriteComponent", SpriteComponent.class);
        cor.put("TimedDisappearanceComponent", TimedDisappearanceComponent.class);
        cor.put("TrackingComponent", TrackingComponent.class);
        return cor;
    }





    public LoadManager(GameWorld parent){
        this.parent = parent;
        this.correlator = correlate();
    }
    public LoadManager(GameWorld parent, String url) throws IOException, SAXException, ParserConfigurationException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        this.parent = parent;
        Document d = null;
        this.correlator = correlate();
        success = true;
        try {
            d = startup(url);
        }
        catch(FileNotFoundException e){
            System.out.println(e);
            success = false;

        }
        if(success){
            parse(d);
        }
    }
}
