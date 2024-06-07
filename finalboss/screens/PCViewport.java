package finalboss.screens;

import engine.hierarchy.Screen;
import engine.hierarchy.Viewport;
import engine.support.Vec2d;

public class PCViewport extends Viewport {

    public PCViewport(Vec2d scrn_ul, Vec2d vp_size, Vec2d game_ul, Vec2d def) {
        super(scrn_ul, vp_size, game_ul, def);
    }

    @Override
    public void startPan(Screen.dir d){}

    @Override
    public void onPan(){}

    @Override
    public void stopPan(){}

    public void setCenter(Vec2d c){
        this.center = c;
    }
}
