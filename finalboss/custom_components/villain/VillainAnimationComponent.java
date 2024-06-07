package finalboss.custom_components.villain;

import engine.components.AnimationComponent;
import engine.components.SpriteComponent;
import engine.components.Tag;
import engine.hierarchy.GameObject;
import finalboss.custom_components.main_character.MainCharacterLayeredAnimationComponent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class VillainAnimationComponent extends AnimationComponent {

    public SpriteComponent s;
    public VillainAnimationComponent(GameObject g, SpriteComponent s, int max) {
        super(g, s, max);
        this.s = s;
        this.tag = Tag.UPDATE;
        this.s.LAC = new VillainLayeredAnimationComponent(g, s);
    }

    public VillainAnimationComponent(Element e, GameObject g){
        super(e,g);
        this.parent = g;
        this.tag = Tag.UPDATE;
        this.s = (SpriteComponent) this.parent.getComponent(Tag.GRAPHICS);
        this.max = Integer.parseInt(e.getAttribute("max"));
        this.interval = Integer.parseInt(e.getAttribute("interval"));
    }

    @Override
    public Element savify(Document d) {
        Element e = d.createElement("VillainAnimationComponent");
        e.setAttribute("max", String.valueOf(max));
        e.setAttribute("interval", String.valueOf(interval));
        return e;
    }

    public void iterate(){
        int sx = s.getVcel();
        sx = sx + 1;
        if(sx > this.max){
            sx = 0;
        }
        s.setVcel(sx);
    }
}
