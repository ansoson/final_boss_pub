package engine.components;

import engine.hierarchy.GameObject;
import engine.hierarchy.GameWorld;
import engine.sprite.Resource;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SpriteComponent extends GraphicsComponent{

    public Resource<Image> r;
    protected int hcel;
    protected int vcel;
    public int vcelSize;
    protected int hcelSize;
    protected int sprite;
    public LayeredAnimationComponent LAC;

    public SpriteComponent(Element e, GameObject g) {
        super(e, g);
        this.parent = g;
        this.tag = Tag.GRAPHICS;
        this.ogPos = g.getTransform().pos;
        this.ogSize = g.getTransform().size;
        this.def = g.getTransform().def;
        this.active = true;
        GameWorld gw = parent.parent;
        this.r = gw.r;
        this.hcel = Integer.parseInt(e.getAttribute("hcel"));
        this.vcel = Integer.parseInt(e.getAttribute("vcel"));
        this.hcelSize = Integer.parseInt(e.getAttribute("hcel_size"));
        this.vcelSize = Integer.parseInt(e.getAttribute("vcelSize"));
        this.sprite = Integer.parseInt(e.getAttribute("sprite"));
    }

    public SpriteComponent(SpriteComponent spc) {
        super(spc.parent);
        this.tag = Tag.GRAPHICS;
        this.ogPos = spc.parent.getTransform().pos;
        this.ogSize = spc.parent.getTransform().size;
        this.def = spc.parent.getTransform().def;
        this.active = false;
        this.r = spc.r;
        this.hcel = spc.hcel;
        this.vcel = spc.vcel;
        this.hcelSize = spc.hcelSize;
        this.vcelSize = spc.vcelSize;
        this.sprite = spc.sprite;

    }

    @Override
    public Element savify(Document d) {
        Element e = d.createElement("SpriteComponent");
        e.setAttribute("hcel", String.valueOf(this.hcel));
        e.setAttribute("vcel", String.valueOf(this.vcel));
        e.setAttribute("hcel_size", String.valueOf(this.hcelSize));
        e.setAttribute("vcelSize", String.valueOf(this.vcelSize));
        e.setAttribute("sprite", String.valueOf(this.sprite));
        return e;
    }

    public SpriteComponent(GameObject g, Resource<Image> rs, int spNum, int hcel, int vcel, int hcelSize, int vcelSize) {
        super(g);
        this.tag = Tag.GRAPHICS;
        this.ogPos = g.getTransform().pos;
        this.ogSize = g.getTransform().size;
        this.def = g.getTransform().def;
        this.active = true;
        this.r = rs;
        this.hcel = hcel;
        this.vcel = vcel;
        this.hcelSize = hcelSize;
        this.vcelSize = vcelSize;
        this.sprite = spNum;
    }


    protected void draw(GraphicsContext g) {
        if (LAC != null) {
            LAC.onDraw(g);
        } else {
            g.drawImage(r.resource.get(sprite), vcel*vcelSize, hcel*hcelSize, vcelSize, hcelSize, parent.getTransform().pos.x, parent.getTransform().pos.y, parent.getTransform().size.x, parent.getTransform().size.y);
        }
    }

    protected void onTick(long nanos){
        if (LAC != null) {
            LAC.tick(nanos);
        }

    }

    public void setSprite(int i){
        sprite = i;
    }
    public int getHcel(){return hcel;}

    public void setHcel(int hcel) {
        this.hcel = hcel;
    }

    public int getVcel() {return this.vcel;}
    public void setVcel(int v) {this.vcel = v;}
}
