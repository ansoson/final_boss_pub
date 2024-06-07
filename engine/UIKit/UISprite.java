package engine.UIKit;

import engine.sprite.Resource;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class UISprite extends UIObject{

    Resource<Image> r;
    protected int hcel;
    protected int vcel;
    int vcelSize;
    int hcelSize;
    public int sprite;

    public UISprite(Vec2d pos, Vec2d size, Vec2d def, Resource<Image> r, int spNum, int hcel,
                    int vcel, int hcelSize, int vcelSize) {
        super(pos, size, def);
        this.alignUI(pos, size);

        this.r = r;
        this.hcel = hcel;
        this.vcel = vcel;
        this.hcelSize = hcelSize;
        this.vcelSize = vcelSize;
        this.sprite = spNum;
    }

    public UISprite(UIObject parent, Vec2d pos, Vec2d size, Vec2d def, Resource<Image> r,
                    int spNum, int hcel, int vcel, int hcelSize, int vcelSize) {
        super(parent, pos, size, def);
        this.alignUI(pos, size);

        this.r = r;
        this.hcel = hcel;
        this.vcel = vcel;
        this.hcelSize = hcelSize;
        this.vcelSize = vcelSize;
        this.sprite = spNum;
    }

    public UISprite(Vec2d pos, Vec2d size, Vec2d def, Resource<Image> r,
                    int spNum, int hcel, int vcel, int hcelSize, int vcelSize,
                    HAlignment hAlignment, VAlignment vAlignment, boolean scales) {
        super(pos, size, def, hAlignment, vAlignment, scales);
        this.alignUI(pos, size);

        this.r = r;
        this.hcel = hcel;
        this.vcel = vcel;
        this.hcelSize = hcelSize;
        this.vcelSize = vcelSize;
        this.sprite = spNum;
    }

    public UISprite(UIObject parent, Vec2d pos, Vec2d size, Vec2d def, Resource<Image> r,
                    int spNum, int hcel, int vcel, int hcelSize, int vcelSize,
                    HAlignment hAlignment, VAlignment vAlignment, boolean scales) {
        super(parent, pos, size, def, hAlignment, vAlignment, scales);
        this.alignUI(pos, size);

        this.r = r;
        this.hcel = hcel;
        this.vcel = vcel;
        this.hcelSize = hcelSize;
        this.vcelSize = vcelSize;
        this.sprite = spNum;
    }






    public void DrawElement(GraphicsContext g) {
        if (active){
            g.drawImage(r.resource.get(sprite), vcel*vcelSize, hcel*hcelSize, vcelSize, hcelSize, pos.x, pos.y, size.x, size.y);

            for (UIObject o: this.children){
                o.DrawElement(g);
            }
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
