package engine.UIKit;

import engine.support.FontMetrics;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.awt.*;

public class Text extends UIObject{

    String text;
    String fontName;
    Font font;
    double fontSize;
    FontMetrics fm;


    public void setText(String t){this.text=t;}
    public String getText(){return this.text;}

    public Text(Color c, Vec2d pos, double fontSize, Vec2d def, String text, String fontName) {
        super(c, pos, new Vec2d(0, 0), def);

        this.fontName = fontName;
        this.text = text;
        this.fontSize = fontSize;

        //load font
        if (fontName.equals("Pixel")){
            font = Font.loadFont("file:engine/ressources/Minecraftia-Regular.ttf", fontSize);
        }

        else{
            this.font = new Font(fontName, fontSize);
        }
        this.fm = new FontMetrics(this.text, font);


        this.size = new Vec2d(fm.width, fm.height);
        this.alignUI(pos, this.size);

        this.c=c;
    }

    public Text(UIObject parent, Color c, Vec2d pos, double fontSize, Vec2d def, String text, String fontName) {
        super(parent, pos, new Vec2d(0, 0), def);

        this.fontName = fontName;
        this.text = text;
        this.fontSize = fontSize;

        //load font
        if (fontName.equals("Pixel")){
            font = Font.loadFont("file:engine/ressources/Minecraftia-Regular.ttf", fontSize);
        }

        else{
            this.font = new Font(fontName, fontSize);
        }
        this.fm = new FontMetrics(this.text, font);


        this.size = new Vec2d(fm.width, fm.height);
        this.alignUI(pos, this.size);

        this.c=c;
    }

    public Text(Color c, Vec2d pos, double fontSize, Vec2d def, String text, String fontName,
                HAlignment hAlignment, VAlignment vAlignment, boolean scales) {
        super(c, pos, new Vec2d(0, 0), def, hAlignment, vAlignment, scales);

        this.fontName = fontName;
        this.text = text;
        this.fontSize = fontSize;

        //load font
        if (fontName.equals("Pixel")){
            font = Font.loadFont("file:engine/ressources/Minecraftia-Regular.ttf", fontSize);
        }

        else{
            this.font = new Font(fontName, fontSize);
        }
        this.fm = new FontMetrics(this.text, font);


        this.size = new Vec2d(fm.width, fm.height);
        this.alignUI(pos, this.size);

        this.c=c;
    }

    public Text(UIObject parent, Color c, Vec2d pos, double fontSize, Vec2d def, String text,
                String fontName, HAlignment hAlignment, VAlignment vAlignment, boolean scales) {
        super(parent, pos, new Vec2d(0, 0), def, hAlignment, vAlignment, scales);

        this.fontName = fontName;
        this.text = text;
        this.fontSize = fontSize;

        //load font
        if (fontName.equals("Pixel")){
            font = Font.loadFont("file:engine/ressources/Minecraftia-Regular.ttf", fontSize);
        }

        else{
            this.font = new Font(fontName, fontSize);
        }
        this.fm = new FontMetrics(this.text, font);


        this.size = new Vec2d(fm.width, fm.height);
        this.alignUI(pos, this.size);

        this.c=c;
    }

    public void setColor(Color color){
        this.c = color;
    }

    @Override
    public void DrawElement(GraphicsContext g) {

        if (active){
            g.setFont(font);
            g.setStroke(c);
            g.setFill(c);
            g.setLineWidth(1);
            g.fillText(text, pos.x, pos.y + fm.height);

            for (UIObject o: this.children){
                o.DrawElement(g);
            }
        }


    }

    @Override
    public void onResize(Vec2d newSize){
        this.pos = new Vec2d(newSize.x * ogPos.x, newSize.y * ogPos.y);
        this.size = new Vec2d(newSize.x * ogSize.x, newSize.y * ogSize.y);
        this.fontSize = 36.0 * size.x * (def.x/def.y) / Toolkit.getDefaultToolkit().getScreenResolution();
    }

    public void setSize(double siz) {
        if (fontName.equals("Pixel")) {
            font = Font.loadFont("file:engine/ressources/Minecraftia-Regular.ttf", siz);
        }
    }
}
