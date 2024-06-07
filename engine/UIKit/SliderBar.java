package engine.UIKit;

import debugger.collisions.Interval;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


public abstract class SliderBar extends UIObject{

  boolean horizontal = true;
  public Rectangle bar;
  Rectangle background;
  public double statValue;
  public double statRatio;
  public double maxStat;
  public double minStat;
  public double startingValue;
  boolean hovering;
  Color barColor;

  /**
   * Simplest constructor
   * @param pos
   * @param size
   * @param def
   * @param barColor
   */
  public SliderBar(Vec2d pos, Vec2d size, Vec2d def, Color barColor, double minStat, double maxStat,
                   double startingVal) {
    super(pos, size, def);
    this.barColor = barColor;
    this.maxStat = maxStat;
    this.minStat = minStat;


    statValue = startingVal;
    statRatio = this.remap(startingVal, new Interval(minStat, maxStat), new Interval(0.0, 1.0));


    this.background = new Rectangle(pos, size, def,
        new Color(barColor.getRed() * .5, barColor.getGreen() * 5,
            barColor.getBlue() * .5, barColor.getOpacity() * .5));

    if (horizontal){
      this.bar = new Rectangle(this.background, new Vec2d(0, 0),
          new Vec2d(size.x * statRatio, size.y), def, barColor);
    }
    else{
      this.bar = new Rectangle(this.background, new Vec2d(0, 0),
          new Vec2d(size.x, size.y * statRatio), def, barColor);
    }

    this.addChild(background);
  }

  /**
   * Constructor with parent
   * @param parent
   * @param pos
   * @param size
   * @param def
   * @param barColor
   */
  public SliderBar(UIObject parent, Vec2d pos, Vec2d size, Vec2d def, Color barColor,
                   double minStat, double maxStat, double startingVal) {
    super(parent, pos, size, def);
    this.barColor = barColor;
    this.maxStat = maxStat;
    this.minStat = minStat;


    statValue = startingVal;
    statRatio = this.remap(startingVal, new Interval(minStat, maxStat), new Interval(0.0, 1.0));


    this.background = new Rectangle(parent, pos, size, def,
        new Color(barColor.getRed() * .5, barColor.getGreen() * 5,
            barColor.getBlue() * .5, barColor.getOpacity() * .5));

    if (horizontal){
      this.bar = new Rectangle(this.background, new Vec2d(0, 0),
          new Vec2d(size.x * statRatio, size.y), def, barColor);
    }
    else{
      this.bar = new Rectangle(this.background, new Vec2d(0, 0),
          new Vec2d(size.x, size.y * statRatio), def, barColor);
    }

    this.addChild(background);
  }

  /**
   * Constructor with alignment
   * @param pos
   * @param size
   * @param def
   * @param barColor
   * @param hAlignment
   * @param vAlignment
   * @param scales
   */
  public SliderBar(Vec2d pos, Vec2d size, Vec2d def, Color barColor,
                   HAlignment hAlignment, VAlignment vAlignment, boolean scales, double minStat,
                   double maxStat, double startingVal) {
    super(pos, size, def, hAlignment, vAlignment, scales);
    this.alignUI(pos, size);
    this.barColor = barColor;
    this.maxStat = maxStat;
    this.minStat = minStat;


    statValue = startingVal;
    statRatio = this.remap(startingVal, new Interval(minStat, maxStat), new Interval(0.0, 1.0));


    this.background = new Rectangle(pos, size, def,
        new Color(barColor.getRed() * .5, barColor.getGreen() * 5,
            barColor.getBlue() * .5, barColor.getOpacity() * .5));

    if (horizontal){
      this.bar = new Rectangle(this.background, new Vec2d(0, 0),
          new Vec2d(size.x * statRatio, size.y), def, barColor);
    }
    else{
      this.bar = new Rectangle(this.background, new Vec2d(0, 0),
          new Vec2d(size.x, size.y * statRatio), def, barColor);
    }

    this.addChild(background);
  }

  /**
   * Constructor with parent & alignment
   * @param parent
   * @param pos
   * @param size
   * @param def
   * @param barColor
   * @param hAlignment
   * @param vAlignment
   * @param scales
   */
  public SliderBar(UIObject parent, Vec2d pos, Vec2d size, Vec2d def, Color barColor,
                   HAlignment hAlignment, VAlignment vAlignment, boolean scales, double minStat,
                   double maxStat, double startingVal) {
    super(parent, pos, size, def, hAlignment, vAlignment, scales);
    this.alignUI(pos, size);
    this.barColor = barColor;
    this.maxStat = maxStat;
    this.minStat = minStat;


    statValue = startingVal;
    statRatio = this.remap(startingVal, new Interval(minStat, maxStat), new Interval(0.0, 1.0));


    this.background = new Rectangle(parent, pos, size, def,
        new Color(barColor.getRed() * .5, barColor.getGreen() * .5,
            barColor.getBlue() * .5, barColor.getOpacity() * .5));

    if (horizontal){
      this.bar = new Rectangle(this.background, new Vec2d(0, 0),
          new Vec2d(size.x * statRatio, size.y), def, barColor);
    }
    else{
      this.bar = new Rectangle(this.background, new Vec2d(0, 0),
          new Vec2d(size.x, size.y * statRatio), def, barColor);
    }

    this.addChild(background);
  }

  public void updateSlider(double increment){

  }

  public void setStatsBar(double value){
    this.statValue = value;
    statRatio = this.remap(value, new Interval(minStat, maxStat), new Interval(0.0, 1.0));

    if (horizontal){
      Interval i = new Interval(0.0, background.size.x);
      bar.size = new Vec2d(i.clamp(this.background.size.x * this.statRatio), bar.size.y);

    }
    else {
      Interval i = new Interval(0.0, background.size.y);
      bar.size = new Vec2d(bar.size.x, i.clamp(this.background.size.y * this.statRatio));
    }
  }

  public void onMouseDragged(MouseEvent e) {
    if(active) {
      if (this.pos.y <= e.getSceneY() && e.getSceneY() <= this.pos.y + this.size.y) {
        if (this.pos.x <= e.getSceneX() && e.getSceneX() <= this.pos.x + this.size.x) {

          if (horizontal){
            Interval i = new Interval(0.0, background.size.x);
            bar.size = new Vec2d(i.clamp(e.getSceneX() - this.pos.x), bar.size.y);
            this.statRatio = bar.size.x/background.size.x;
            this.statValue = this.remap(statRatio, new Interval(0.0, 1.0), new Interval(minStat, maxStat));
            this.DragEvent();
//            System.out.println("STAT VALUE = " + statValue);
          }

          else {
            Interval i = new Interval(0.0, background.size.y);
            bar.size = new Vec2d(bar.size.x, i.clamp(e.getSceneY() - this.pos.y));
            this.statRatio = bar.size.y/background.size.y;
            this.statValue = this.remap(statRatio, new Interval(0.0, 1.0), new Interval(minStat, maxStat));
          }
        }
      }
    }
  }

  public void onMouseMoved(MouseEvent e) {
    if (active){
      if (this.pos.y <= e.getSceneY() && e.getSceneY() <= this.pos.y + this.size.y) {
        if (this.pos.x <= e.getSceneX() && e.getSceneX() <= this.pos.x + this.size.x) {
          if (!hovering) {
            this.c = this.barColor.brighter();
            System.out.println("HOVER IN BUTTON DETECTED");
            hovering = true;
          }
        } else {
          if (hovering) {
            this.c = barColor;
            hovering = false;
          }
        }
      } else {
        if (hovering) {
          hovering = false;
        }
      }
    }

  }

  public void DragEvent(){

  }


  /**
   * Returns value between 0 and 1 representing how full the stats bar is
   * @return
   */
  public double getStat(){
    return this.statRatio;
  }

  @Override
  public void DrawElement(GraphicsContext g) {
    if (active){
      for (UIObject o: this.children){
        if (o.active){
          o.DrawElement(g);
        }
      }
    }

  }

  public double remap(double x, Interval inputRange, Interval outputRange){
    return (x - inputRange.min)/(inputRange.max - inputRange.min)
        * (outputRange.max - outputRange.min) + outputRange.min;
  }
}
