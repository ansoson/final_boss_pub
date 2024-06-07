package finalboss.custom_components;

import engine.components.CollisionComponent;
import engine.hierarchy.GameObject;
import engine.shapes.ShapeEnum;
import engine.support.Vec2d;
import engine.components.Tag;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class UnderfootComponent extends CollisionComponent {

    LinkageComponent link;
    public UnderfootComponent(GameObject g, ShapeEnum s, int layer, boolean stat, LinkageComponent l) {
        super(g, s, layer, stat);
        this.tag = Tag.COLLISION;
        this.link = l;
        this.s = s;
    }

    public UnderfootComponent(Element e, GameObject g){
        super(e,g);
        this.parent = g;
        this.tag = Tag.COLLISION;
        this.s = ShapeEnum.valueOf(e.getAttribute("shape"));
        this.layer = Integer.parseInt(e.getAttribute("layer"));
        this.stat = Boolean.parseBoolean(e.getAttribute("static"));
        this.link = (LinkageComponent) parent.getComponent(Tag.UPDATE);
    }

    @Override
    public Element savify(Document d) {
        Element e = d.createElement("UnderfootComponent");
        e.setAttribute("shape", s.name());
        e.setAttribute("layer", String.valueOf(layer));
        e.setAttribute("static", String.valueOf(stat));
        return e;
    }

    @Override
    public void onCollision(GameObject x2, Vec2d collides) {
        GravityComponent g = (GravityComponent) link.g2.getComponent(Tag.PREPHYSICS);
        g.setGrounded(true);
    }

   // @Override
    public void onStaticCollision(GameObject x2, Vec2d collides) {
        GravityComponent g = (GravityComponent) link.g2.getComponent(Tag.PREPHYSICS);
        g.setGrounded(true);
    }

    @Override
    public void tick(long n){
        GravityComponent g = (GravityComponent) link.g2.getComponent(Tag.PREPHYSICS);
        g.setGrounded(false);
    }
}
