package engine.hierarchy;
import engine.support.Vec2d;
import javafx.geometry.Point2D;
import javafx.scene.transform.Affine;
import engine.hierarchy.Screen.dir;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Viewport {

    double viewportWidth;
    double viewportHeight;

    Vec2d scrn_ul;
    Vec2d game_ul;

    Vec2d def;
    protected Vec2d center;
    double zoom;

    dir panDir;


    public Viewport(Vec2d scrn_ul, Vec2d vp_size, Vec2d game_ul, Vec2d def){
        this.viewportHeight = vp_size.y;
        this.viewportWidth = vp_size.x;
        this.scrn_ul = scrn_ul;
        this.game_ul = game_ul;
        this.zoom = 1;
        this.def = def;
        this.panDir = dir.NONE;
        this.center = new Vec2d(vp_size.x/2, vp_size.y/2);
    }


    //used for interaction
    public Affine screenToGame(){
        Affine a = new Affine();
        a.appendTranslation(center.x, center.y);
        ///2. Multiply by scale
        a.appendScale(1/zoom, 1/zoom);
        ///3. Add viewport upper left
        a.appendTranslation(-viewportWidth/2, -viewportHeight/2);
        return a;
    }

    //drawing
    public Affine gameToScreen(){
        Affine a = new Affine();
        a.appendTranslation(viewportWidth/2, viewportHeight/2);
        ///2. Multiply by scale
        a.appendScale(zoom, zoom);
        ///3. Add viewport upper left
        a.appendTranslation(-center.x, -center.y);
        return a;
    }


    public void onZoom(double zvalue){
        zoom = clamp(zoom* (zvalue/1000.0 + 1));
    }
    double min = 0.8;
    double max = 2.0;

    public double clamp(double d){
        if (d < this.min){
            return this.min;
        }

        else if (d > this.max){
            return this.max;
        }

        return d;
    }

    public void startPan(dir d){
        if(d == dir.UP){
            this.panDir = dir.UP;
        }
        else if(d == dir.LEFT){
            this.panDir = dir.LEFT;
        }
        else if(d == dir.RIGHT){
            this.panDir = dir.RIGHT;
        }
        else if(d == dir.DOWN){
            this.panDir = dir.DOWN;
        }
    }
    public void onPan(){
        if (!(this.panDir == dir.NONE)){
            if(this.panDir == dir.UP){
                if(this.center.y > -def.y){
                    double y = max(this.center.y-10, -def.y);
                    this.center = new Vec2d(this.center.x, y);
                }
            }
            else if(this.panDir == dir.DOWN){
                if(this.center.y < 2 * def.y){
                    double y = min(this.center.y+10, 2*def.y);
                    this.center = new Vec2d(this.center.x, y);

                }
            }
            else if(this.panDir == dir.LEFT){
                if(this.center.x > -def.x){
                    double x = max(this.center.x-10, -def.x);
                    this.center = new Vec2d(x, this.center.y);
                }
            }
            else if(this.panDir == dir.RIGHT){
                if(this.center.x < 2*def.x){
                    double x = min(this.center.x+10, 2*def.x);
                    this.center = new Vec2d(x, this.center.y);
                }
            }

        }
    }

    public void stopPan(){this.panDir = dir.NONE;}

}
