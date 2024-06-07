package engine.UIKit;

import engine.dialogue.Tone;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TextBox extends UIObject {
    private Rectangle rect;
    private Text line;
    private Tone tone;

    public TextBox(Vec2d pos, Vec2d size, Vec2d def) {
        super(pos, size, def);
        this.rect = new Rectangle(this, Vec2d.ORIGIN, size, def, Color.BLACK);
        this.addChild(rect);
        this.line = new Text(rect, Color.WHITE, new Vec2d(30, 30), 20, Vec2d.ORIGIN, "", "Pixel");
        this.addChild(this.line);
        this.tone = Tone.NEUTRAL;
        new Text(rect, Color.GRAY, new Vec2d(30, 100), 12, Vec2d.ORIGIN, "press space to advance dialogue", "Pixel");
    }

    public void DrawElement(GraphicsContext g) {
        for (UIObject o : this.children) {
            o.DrawElement(g);
        }
    }

    public void setTone(Tone t) {
        this.tone = t;
        switch (this.tone) {
            case ANGRY:
                this.line.setSize(25);
                this.line.setColor(Color.RED);
                break;
            case WHISPER:
                this.line.setSize(12);
                this.line.setColor(Color.LIGHTBLUE);
                break;
            case ACTION:
                this.line.setSize(18);
                this.line.setColor(Color.LIGHTBLUE);
            default:
                this.line.setSize(18);
                this.line.setColor(Color.WHITE);
                break;
        }
    }

    public void setLine(String text) {
        this.line.setText(text);
    }
    public void setPosition(Vec2d newPos){
        
        this.pos = newPos;
        this.rect.pos = this.pos;
    }
    public Vec2d getPosition() {
        return this.pos;
    }

    public Vec2d getOGPosition() {
        return this.ogPos;
    }

}
