package engine.UIKit;

import debugger.collisions.Interval;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class StatsBar extends UIObject{

  boolean horizontal = true;
  public Rectangle bar;
  Rectangle background;
  public double statValue;
  public double statRatio;
  public double maxStat;
  public double startingValue;

  /**
   * Simplest constructor
   * @param pos
   * @param size
   * @param def
   * @param barColor
   */
  public StatsBar(Vec2d pos, Vec2d size, Vec2d def, Color barColor, double maxStat,
                  double startingVal) {
    super(pos, size, def);
    this.maxStat = maxStat;
    statValue = startingVal;
    statRatio = startingVal/maxStat;
    this.background = new Rectangle(pos, size, def,
        new Color(barColor.getRed() * .5, barColor.getGreen() * .5,
            barColor.getBlue() * .5, barColor.getOpacity() * .5));
    this.bar = new Rectangle(this.background, new Vec2d(0, 0), size, def, barColor);
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
  public StatsBar(UIObject parent, Vec2d pos, Vec2d size, Vec2d def, Color barColor,
                  double maxStat, double startingVal) {
    super(parent, pos, size, def);
    this.maxStat = maxStat;
    statValue = startingVal;
    startingValue = startingVal;
    statRatio = startingVal/maxStat;
    this.background = new Rectangle(pos, size, def,
        new Color(barColor.getRed() * .5, barColor.getGreen() * .5,
            barColor.getBlue() * .5, barColor.getOpacity() * .5));
    this.bar = new Rectangle(this.background, new Vec2d(0, 0), size, def, barColor);
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
  public StatsBar(Vec2d pos, Vec2d size, Vec2d def, Color barColor,
                  HAlignment hAlignment, VAlignment vAlignment, boolean scales,
                  double maxStat, double startingVal) {
    super(pos, size, def, hAlignment, vAlignment, scales);
    this.alignUI(pos, size);
    this.maxStat = maxStat;
    statValue = startingVal;
    startingValue = startingVal;
    statRatio = startingVal/maxStat;
    this.background = new Rectangle(pos, size, def,
        new Color(barColor.getRed() * .5, barColor.getGreen() * .5,
            barColor.getBlue() * .5, barColor.getOpacity() * .5),
        hAlignment, vAlignment, scales);
    this.bar = new Rectangle(this.background, new Vec2d(0, 0), size, def, barColor,
        hAlignment, vAlignment, scales);
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
  public StatsBar(UIObject parent, Vec2d pos, Vec2d size, Vec2d def, Color barColor,
                  HAlignment hAlignment, VAlignment vAlignment, boolean scales,
                  double maxStat, double startingVal) {
    super(parent, pos, size, def, hAlignment, vAlignment, scales);
    this.maxStat = maxStat;
    statValue = startingVal;
    startingValue = startingVal;
    statRatio = startingVal/maxStat;
    this.background = new Rectangle(parent, pos, size, def,
        new Color(barColor.getRed() * .5, barColor.getGreen() * .5,
            barColor.getBlue() * .5, barColor.getOpacity() * .5),
        hAlignment, vAlignment, scales);
    this.bar = new Rectangle(this.background, new Vec2d(0, 0), size, def, barColor,
        hAlignment, vAlignment, scales);
    this.addChild(background);
  }

  public void ResetHealth(){
    this.statValue = startingValue;
  }

  public void updateStatsBar(double increment){
    this.statValue += increment;
    statRatio = statValue/maxStat;
    System.out.println("STAT VALUE" + this.statValue);
    if (horizontal){
      Interval i = new Interval(0.0, background.size.x);
      bar.size = new Vec2d(i.clamp(this.background.size.x * this.statRatio), bar.size.y);
    }

    else {
      Interval i = new Interval(0.0, background.size.y);
      bar.size = new Vec2d(bar.size.x, i.clamp(this.background.size.y * this.statRatio));
    }
  }

  public void setStatsBar(double value){
    this.statValue = value;
    statRatio = statValue/maxStat;

    if (horizontal){
      Interval i = new Interval(0.0, background.size.x);
      bar.size = new Vec2d(i.clamp(this.background.size.x * this.statRatio), bar.size.y);

    }
    else {
      Interval i = new Interval(0.0, background.size.y);
      bar.size = new Vec2d(bar.size.x, i.clamp(this.background.size.y * this.statRatio));
    }
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
}
