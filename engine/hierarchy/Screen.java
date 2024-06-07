package engine.hierarchy;

import engine.UIKit.UIObject;
import engine.support.Vec2d;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.transform.Affine;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * It's screen time baby.
 */
public class Screen {

  public List<GameWorld> worlds = new ArrayList<>();
  public List<UIObject> ui = new ArrayList<>();
  public Viewport viewport;
  public Application parent;
  public String name;
  public Vec2d windowSize;
  public Vec2d mousePosition;
  public boolean active;

  private boolean _is_panning;

  public enum dir {
    UP,
    LEFT,
    RIGHT,
    DOWN,
    NONE
  }

  private dir _dir = dir.NONE;

  public Screen(String name, Vec2d defaultWindowSize, boolean active) {
    this.name = name;
    this.windowSize = defaultWindowSize;
    this.active = active;
  }

  public Screen(String name, Vec2d defaultWindowSize, Application parent, Viewport vp, boolean active) {
    this.name = name;
    this.windowSize = defaultWindowSize;
    this.parent = parent;
    this.viewport = vp;
    this.active = active;
    if (vp == null) {
      init_vp();
    }
  }

  // feel free to override if viewpoint isn't the whole screen
  public void init_vp() {
    this.viewport = new Viewport(new Vec2d(0, 0), windowSize, new Vec2d(0, 0), windowSize);
  }

  /**
   * Called periodically and used to update the state of your game.
   * 
   * @param nanosSincePreviousTick approximate number of nanoseconds since the
   *                               previous call
   */
  public void onTick(long nanosSincePreviousTick)
      throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException,
      IOException, TransformerException {
    if (active) {
//      System.out.println("Screen " + this.name + " active");
      viewport.onPan();
      for (GameWorld o : worlds) {
        o.onTick(nanosSincePreviousTick);
      }

      for (UIObject ui: this.ui){
        ui.onTick(nanosSincePreviousTick);
      }
    }

    //if screen is not active, only tick sound system
    if (!active){
      for (GameWorld o : worlds) {
        if (o.getSystem("SS") != null){
          o.getSystem("SS").onTick(nanosSincePreviousTick);
        }
      }
    }
  }

  /**
   * Called after onTick().
   */
  protected void onLateTick()
      throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    if (active) {
      for (GameWorld o : worlds) {
        o.onLateTick();
      }
    }

  }

  /**
   * Called periodically and meant to draw graphical components.
   * 
   * @param g a {@link GraphicsContext} object used for drawing.
   */
  protected void onDraw(GraphicsContext g) {
    if (active) {
      g.setTransform(viewport.gameToScreen());
      for (GameWorld o : worlds) {
        o.onDraw(g);
      }
      g.setTransform(new Affine());
      for (UIObject u : ui) {
        u.onDraw(g);
      }
    }

  }

  /**
   * Called when a key is typed.
   * 
   * @param e an FX {@link KeyEvent} representing the input event.
   */
  protected void onKeyTyped(KeyEvent e) {
    if (active) {
      attemptPan(e);
        for (GameWorld o : worlds) {
          o.onKeyTyped(e);
        }
    }

  }

  /**
   * Called when a key is pressed.
   * 
   * @param e an FX {@link KeyEvent} representing the input event.
   */
  public void onKeyPressed(KeyEvent e) {
    if (active) {
      attemptPan(e);
        for (GameWorld o : worlds) {
          o.onKeyPressed(e);
      }
    }

  }

  /**
   * Called when a key is released.
   * 
   * @param e an FX {@link KeyEvent} representing the input event.
   */
  protected void onKeyReleased(KeyEvent e) {

    if (active) {
      attemptStopPan(e);

      for (GameWorld o : worlds) {
        o.onKeyReleased(e);
      }
    }

  }

  /**
   * Called when the mouse is clicked.
   * 
   * @param e an FX {@link MouseEvent} representing the input event.
   */
  public void onMouseClicked(MouseEvent e) throws IOException, InvocationTargetException, TransformerException,
      NoSuchMethodException, InstantiationException, IllegalAccessException {

    if (active) {
      for (UIObject u : ui) {
        u.onMouseClicked(e);
      }
      Point2D p = viewport.screenToGame().transform(e.getSceneX(), e.getSceneY());
      Point2D g = viewport.gameToScreen().transform(e.getSceneX(), e.getSceneY());
      Vec2d ptd = new Vec2d(p.getX(), p.getY());
      for (GameWorld o : worlds) {
        o.onMouseClicked(ptd, e);
      }
    }
  }

  /**
   * Called when the mouse is pressed.
   * 
   * @param e an FX {@link MouseEvent} representing the input event.
   */
  protected void onMousePressed(MouseEvent e) {
    if (active) {
      for (UIObject u : ui) {
        u.onMousePressed(e);
      }
      Point2D p = viewport.screenToGame().transform(e.getSceneX(), e.getSceneY());
      Vec2d ptd = new Vec2d(p.getX(), p.getY());
      for (GameWorld o : worlds) {
        o.onMousePressed(ptd);
      }
    }
  }

  /**
   * Called when the mouse is released.
   * 
   * @param e an FX {@link MouseEvent} representing the input event.
   */
  protected void onMouseReleased(MouseEvent e) {
    if (active) {
      for (UIObject u : ui) {
        u.onMouseReleased(e);
      }
      Point2D p = viewport.screenToGame().transform(e.getSceneX(), e.getSceneY());
      Vec2d ptd = new Vec2d(p.getX(), p.getY());
      for (GameWorld o : worlds) {
        o.onMouseReleased(ptd);
      }
    }

  }

  /**
   * Called when the mouse is dragged.
   * 
   * @param e an FX {@link MouseEvent} representing the input event.
   */
  protected void onMouseDragged(MouseEvent e) {
    if (active) {
      for (UIObject u : ui) {
        u.onMouseDragged(e);
      }
      Point2D p = viewport.screenToGame().transform(e.getSceneX(), e.getSceneY());
      Vec2d ptd = new Vec2d(p.getX(), p.getY());
      for (GameWorld o : worlds) {
        o.onMouseDragged(ptd);
      }
    }

  }

  /**
   * Called when the mouse is moved.
   * 
   * @param e an FX {@link MouseEvent} representing the input event.
   */
  protected void onMouseMoved(MouseEvent e) {
    if (active) {
      for (UIObject u : ui) {
        u.onMouseMoved(e);
      }
      Point2D p = viewport.screenToGame().transform(e.getSceneX(), e.getSceneY());
      Vec2d ptd = new Vec2d(p.getX(), p.getY());
      for (GameWorld o : worlds) {
        o.onMouseMoved(ptd);
      }
    }

  }

  /**
   * Called when the mouse wheel is moved.
   * 
   * @param e an FX {@link ScrollEvent} representing the input event.
   */
  protected void onMouseWheelMoved(ScrollEvent e) {
    if (active) {
      viewport.onZoom(e.getDeltaY());
      for (GameWorld o : worlds) {
        o.onMouseWheelMoved(e);
      }
    }

  }

  /**
   * Called when the window's focus is changed.
   * 
   * @param newVal a boolean representing the new focus state
   */
  protected void onFocusChanged(boolean newVal) {

  }

  /**
   * Called when the window is resized.
   * 
   * @param newSize the new size of the drawing area.
   */
  public void onResize(Vec2d newSize) {
    for (GameWorld o : worlds) {
      o.onResize(newSize);
    }
    for (UIObject u : ui) {
      u.onResize(newSize);
    }
  }

  public void onStartup() throws IOException, ParserConfigurationException, TransformerException,
      InvocationTargetException, SAXException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    for (GameWorld o : worlds) {
      o.onStartup();
    }
  }

  private void attemptPan(KeyEvent e) {
    if (e.getCode() == KeyCode.UP) {
      viewport.startPan(dir.UP);
    } else if (e.getCode() == KeyCode.DOWN) {
      viewport.startPan(dir.DOWN);
    } else if (e.getCode() == KeyCode.LEFT) {
      viewport.startPan(dir.LEFT);
    } else if (e.getCode() == KeyCode.RIGHT) {
      viewport.startPan(dir.RIGHT);
    }
  }

  private void attemptStopPan(KeyEvent e) {
    viewport.stopPan();
  }

  public void setActive(){
    this.active = true;
  }
}
