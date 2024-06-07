package engine.hierarchy;

import engine.UIKit.UIObject;
import engine.support.FXFrontEnd;
import engine.support.Vec2d;
import finalboss.screens.ErrorView;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is your main Application class that you will contain your
 * 'draws' and 'ticks'. This class is also used for controlling
 * user input.
 */
public class Application extends FXFrontEnd {

  public List<Screen> screens;

  public int screenOn = 0;

  public GONumber n;
    public ErrorView errorScreen;

    public Application(String title) {
    super(title);
    this.n = new GONumber();
  }
  public Application(String title, Vec2d windowSize, boolean debugMode, boolean fullscreen) {
    super(title, windowSize, debugMode, fullscreen);
    this.n = new GONumber();
  }

  /**
   * Called periodically and used to update the state of your game.
   * @param nanosSincePreviousTick	approximate number of nanoseconds since the previous call
   */
  @Override
  protected void onTick(long nanosSincePreviousTick) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException, TransformerException {
    for (Screen s: screens){
      s.onTick(nanosSincePreviousTick);
    }
  }

  /**
   * Called after onTick().
   */
  @Override
  protected void onLateTick() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    for (Screen s: screens){
      if (s.active){
        s.onLateTick();
      }
    }
  }

  /**
   *  Called periodically and meant to draw graphical components.
   * @param g		a {@link GraphicsContext} object used for drawing.
   */
  @Override
  protected void onDraw(GraphicsContext g) {
    g.setImageSmoothing(false);
    for (Screen s: screens){
      if (s.active){
        s.onDraw(g);
      }
    }
    g.clearRect(DEFAULT_STAGE_SIZE.x, 0, g.getCanvas().getWidth(), g.getCanvas().getHeight());
    g.clearRect(0, DEFAULT_STAGE_SIZE.y, g.getCanvas().getWidth(), g.getCanvas().getHeight());
  }

  /**
   * Called when a key is typed.
   * @param e		an FX {@link KeyEvent} representing the input event.
   */
  @Override
  protected void onKeyTyped(KeyEvent e) {
    for (Screen s: screens){
      if (s.active){
        s.onKeyTyped(e);
      }
    }
  }

  /**
   * Called when a key is pressed.
   * @param e		an FX {@link KeyEvent} representing the input event.
   */
  @Override
  protected void onKeyPressed(KeyEvent e) {
    for (Screen s: screens){
      if (s.active){
        s.onKeyPressed(e);
      }
    }
  }

  /**
   * Called when a key is released.
   * @param e		an FX {@link KeyEvent} representing the input event.
   */
  @Override
  protected void onKeyReleased(KeyEvent e) {
    for (Screen s: screens){
      if (s.active){
        s.onKeyReleased(e);
      }
    }
  }

  /**
   * Called when the mouse is clicked.
   * @param e		an FX {@link MouseEvent} representing the input event.
   */
  @Override
  protected void onMouseClicked(MouseEvent e) throws IOException, InvocationTargetException, TransformerException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    for (Screen s: screens){
      if (s.active){
        s.onMouseClicked(e);
      }
    }
  }

  /**
   * Called when the mouse is pressed.
   * @param e		an FX {@link MouseEvent} representing the input event.
   */
  @Override
  protected void onMousePressed(MouseEvent e) {
    for (Screen s: screens){
      if (s.active){
        s.onMousePressed(e);
      }
    }
  }

  /**
   * Called when the mouse is released.
   * @param e		an FX {@link MouseEvent} representing the input event.
   */
  @Override
  protected void onMouseReleased(MouseEvent e) {
    for (Screen s: screens){
      if (s.active){
        s.onMouseReleased(e);
      }
    }
  }

  /**
   * Called when the mouse is dragged.
   * @param e		an FX {@link MouseEvent} representing the input event.
   */
  @Override
  protected void onMouseDragged(MouseEvent e) {
    for (Screen s: screens){
      if (s.active){
        s.onMouseDragged(e);
      }
    }
  }

  /**
   * Called when the mouse is moved.
   * @param e		an FX {@link MouseEvent} representing the input event.
   */
  @Override
  protected void onMouseMoved(MouseEvent e) {
    for (Screen s: screens){
      if (s.active){
        s.onMouseMoved(e);
      }
    }
  }

  /**
   * Called when the mouse wheel is moved.
   * @param e		an FX {@link ScrollEvent} representing the input event.
   */
  @Override
  protected void onMouseWheelMoved(ScrollEvent e) {
    for (Screen s: screens){
      if (s.active){
        s.onMouseWheelMoved(e);
      }
    }
  }

  /**
   * Called when the window's focus is changed.
   * @param newVal	a boolean representing the new focus state
   */
  @Override
  protected void onFocusChanged(boolean newVal) {
    for (Screen s: screens){
      if (s.active){
        s.onFocusChanged(newVal);
      }
    }
  }

  /**
   * Called when the window is resized.
   * @param newSize	the new size of the drawing area.
   */
  @Override
  protected void onResize(Vec2d newSize) {
    // double ratio = Math.min(newSize.x/MINIMUM_STAGE_SIZE.x, newSize.y/MINIMUM_STAGE_SIZE.y);
    // Vec2d gamespace = new Vec2d(MINIMUM_STAGE_SIZE.x*ratio, MINIMUM_STAGE_SIZE.y*ratio);
    // for (Screen s: screens){
    //   if (s.active){
    //     s.onResize(newSize);
    //   }
    // }
  }

  /**
   * Called when the app is shutdown.
   */
  @Override
  protected void onShutdown() {

  }

  /**
   * Called when the app is starting up.s
   */
  @Override
  protected void onStartup() throws IOException, ParserConfigurationException, TransformerException, InvocationTargetException, SAXException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    screens = new ArrayList<>();
    for(Screen s: screens){
      s.onStartup();
    }
  }

  public ErrorView getErrorScreen() {
    return errorScreen;
  }
}
