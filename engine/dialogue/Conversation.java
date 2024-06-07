package engine.dialogue;

public class Conversation {

    private Line[] text;
    private Object condition;
    private Tone tone;
    private Speed speed;
    private boolean seen;
    private boolean replay;

    public Conversation(Line[] content, Object textConditions, Tone t, Speed s, boolean play) {
        this.text = content;
        this.condition = textConditions;
        this.tone = t;
        this.speed = s;
        this.seen = false;
        this.replay = play;

        for (Line line : content) {
            line.setParent(this);
        }
    }

    public Line[] getLines() {
        return this.text;
    }

    public Tone getTone() {
        return this.tone;
    }

    public Speed getSpeed() {
        return this.speed;
    }

    public Object getCondition() {
        return this.condition;
    }
    public void setSeen(boolean tf){
        this.seen = tf;
    }
    public boolean hasSeen(){
        return this.seen;
    }

    public boolean canPlay(){
        if(!seen || replay){
            return true;
        }
        return false;
    }
}
