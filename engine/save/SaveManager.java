package engine.save;


import engine.hierarchy.GameWorld;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

//DocumentBuilderFactory
//DocumentBuilder
//Document (org.w3c.dom NOT javax/swing)
//Element
//Node & NodeList

public class SaveManager {

    protected GameWorld parent;
    protected Document d;
    protected String url;

    public SaveManager(GameWorld parent, String url) throws ParserConfigurationException {
        this.d = startup();
        this.parent = parent;
        this.url = url;
    }

    public void save() throws IOException, TransformerException{

    }

    public Document startup() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        return docBuilder.newDocument();
    }

}





