package engine.dialogue;

public class Line {

    private String text;
    private Tone tone;
    private Conversation parent;
    private Speed speed;

    public Line(String content) {
        this.text = content;
    }

    public Line(String content, Tone lineTone) {
        this.text = content;
        this.tone = lineTone;
    }

    public Line(String content, Speed sped) {
        this.text = content;
        this.speed = sped;
    }

    public Line(String content, Tone lineTone, Speed sped) {
        this.text = content;
        this.tone = lineTone;
        this.speed = sped;
    }

    public String getText() {
        return this.text;
    }

    public Tone getTone() {
        if (this.tone == null) {
            this.tone = parent.getTone();
        }
        return this.tone;
    }

    public Speed getSpeed() {
        if (this.speed == null) {
            this.speed = parent.getSpeed();
        }
        return this.speed;
    }

    public void setParent(Conversation conv){
        this.parent = conv;
    }
}
