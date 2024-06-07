package finalboss.custom_components.main_character;

import engine.components.CollisionComponent;
import engine.hierarchy.GameObject;
import engine.shapes.ColAAB;
import engine.shapes.ColCircle;
import engine.shapes.ShapeEnum;
import engine.support.Vec2d;
import engine.components.Tag;
import finalboss.custom_components.pickup.WeaponPickupComponent;
import finalboss.custom_components.villain.attacks.VillainDisappearingAttack;
import finalboss.screens.CharacterView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class MainCharacterCollision extends CollisionComponent {

    CharacterView mv;
    public MainCharacterCollision(GameObject g, ShapeEnum s, int layer, CharacterView mv) {
        super(g, s, layer, false);
        this.mv = mv;
        this.tag = Tag.COLLISION;
    }

    public void onCollision(GameObject x2, Vec2d collides){
        CollisionComponent wc = (CollisionComponent) x2.getComponent(Tag.COLLISION);
        if(wc.getClass().equals(WeaponPickupComponent.class)){
            WeaponPickupComponent wcc = (WeaponPickupComponent) x2.getComponent(Tag.COLLISION);
            if(!wcc.getPu()){
                return; //basically-- do not transform if collisionComponent is "inactive"
            }
        }
        if(x2.getComponent(Tag.VILL) == null) {
            Vec2d trans = parent.getTransform().pos;
            trans = trans.minus(collides);
            parent.setTransform(trans);
            if(x2.getComponent(Tag.ENEMY) != null){
                mv.TakeDamage(-20);
            }
        }
        else{
            System.out.println(x2.name + " fuckin killed");
            mv.TakeDamage(-10);
        }
    }

    public MainCharacterCollision(Element e, GameObject g){
        super(e,g);
        this.parent = g;
        this.tag = Tag.COLLISION;
        this.s = ShapeEnum.AAB;
        this.layer = Integer.parseInt(e.getAttribute("layer"));
        this.stat = Boolean.parseBoolean(e.getAttribute("static"));
        this.mv = (CharacterView) parent.parent.parent;
        initShape();
    }
    //@Override
    public Element savify(Document d) {
        Element e = d.createElement("MainCharacterCollisionComponent");
        e.setAttribute("layer", String.valueOf(layer));
        e.setAttribute("static", String.valueOf(stat));
        e.setAttribute("shape", s.name());
        return e;
    }
@Override
    public void initShape(){
        if(s == ShapeEnum.AAB) {
            this.shape = new ColAAB(new Vec2d(parent.getTransform().pos.x + .15 * parent.getTransform().size.x,parent.getTransform().pos.y + .5 * parent.getTransform().size.y ),
                    new Vec2d(parent.getTransform().size.x * .7,  parent.getTransform().size.y * .5),
                    parent.getTransform().def);
        }
        else {
            this.shape = new ColCircle(parent.getTransform().pos, parent.getTransform().size);
        }
    }

}
