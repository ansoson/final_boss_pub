package engine.hierarchy;

import engine.components.Component;
import engine.components.TransformComponent;
import engine.support.Vec2d;
import engine.components.Tag;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class GameObject {


    public List<Component> components;
    private TransformComponent transform;
    public String name;
    public GameWorld parent;
    public Double z;

    public int id;

    public GameObject(GameWorld parent, String name, Vec2d pos, Vec2d size, Vec2d def, Double z) {
        this.name = name;
        this.parent = parent;
        this.components = new ArrayList<>();
        this.transform = new TransformComponent(this, pos, size, def);
        components.add(this.transform);
        this.id = this.parent.parent.parent.n.getGONumber();
        this.parent.parent.parent.n.iterateGONumber();
        this.z = z;
    }

    public GameObject(GameWorld parent, String name) {
        this.name = name;
        this.parent = parent;
        this.components = new ArrayList<>();

        //add nonexistent transform
        this.transform = new TransformComponent(this, new Vec2d(0, 0), new Vec2d(0, 0),
            new Vec2d(0, 0));
        components.add(this.transform);

        this.id = this.parent.parent.parent.n.getGONumber();
        this.parent.parent.parent.n.iterateGONumber();
    }

    public GameObject(GameWorld g, Element e) {
        this.parent = g;
        this.components = new ArrayList<>();
        this.name = e.getAttribute("name");
        this.id = Integer.parseInt(e.getAttribute("id"));
        this.z = Double.parseDouble(e.getAttribute("z"));
    }

    public void addComponent(Component c){
        components.add(c);
    }

    public void setTransformer(TransformComponent tc){
        this.transform = tc;
    }
    public void removeComponent(Component c){
        components.remove(c);
    }

    public Component getComponent(Tag tag) {
        for (Component c : components) {
            if (c.getTag().equals(tag)) {
                return c;
            }
        }
        return null;
    }


    @Override
    public boolean equals(Object obj) {
        return this.id == ((GameObject) obj).id;
    }

    public TransformComponent getTransform(){return transform;}
    public void setTransform(Vec2d vec){transform.setTransform(vec);}
    public void affixTransform(TransformComponent t){this.transform = t;}

    /**
     * Called periodically and used to update the state of your game.
     * @param nanosSincePreviousTick	approximate number of nanoseonds since the previous call
     */
    protected void onTick(long nanosSincePreviousTick) {}

    /**
     * Called after onTick().
     */
    protected void onLateTick() {}

    public void callSystems() {

        for(GameSystem s : parent.systems){
            s.acceptObject(this);
        }
    }

    public List<Component> getComponents(Tag t) {
        ArrayList<Component> compList = new ArrayList<Component>();
        for (Component c : components) {
            if (c.getTag().equals(t)) {
                compList.add(c);
            }
        }
        return compList;
    }
}
