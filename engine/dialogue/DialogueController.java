package engine.dialogue;

import java.util.ArrayList;

import engine.support.Vec2d;
import engine.UIKit.TextBox;

public class DialogueController {
    private TextBox tb;
    private DialogueModel dialogue;
    private int lineIndex;
    private double animationTimer;
    private Object active;
    private Conversation currentConv;
    private Line currentLine;
    private String currentSubLine;
    private Boolean convoOver;
    private Boolean lineFinished;
    private int lineCounter;
    private Boolean tick;
    private double secs;
    private Vec2d startPos;
    private double typeDelay;
    private Object[] priority;

    public DialogueController(TextBox box, DialogueModel model, Object act) {
        this.tb = box;
        this.dialogue = model;
        this.lineIndex = 0;
        this.secs = 0;
        this.active = act;
        this.tick = false;
        this.lineCounter = 0;
        this.lineFinished = false;
        this.startPos = this.tb.getPosition();
        this.setPriority(this.dialogue.getConditions().toArray());
    }
    public void setPriority(Object[] arr){
        priority = arr;
    }
    public void animateTB() {
        if (this.animationTimer > .1 && this.animationTimer < .2) {
            this.tb.setPosition(this.startPos.minus(3, 0));
        } else if (this.animationTimer > .2 && this.animationTimer < .3) {
            this.tb.setPosition(this.startPos.minus(-3, 0));
        } else if (this.animationTimer > .3 && this.animationTimer < .4) {
            this.tb.setPosition(this.startPos.minus(0, 3));
        } else if (this.animationTimer > .4 && this.animationTimer < .5) {
            this.tb.setPosition(this.startPos.minus(0, -3));
        } else if (this.animationTimer > .5) {
            animationTimer = 0;
        }
    }

    public void loadConversation() {
        System.out.println("CONVO LOADED");
        this.tb.setPosition(this.startPos);
        this.lineIndex = 0;
        this.convoOver = false;
        ArrayList<Conversation> convs = this.dialogue.getConvs(active);
        int index = (int) (Math.random() * convs.size());
        this.currentConv = convs.get(index);
        //this.playConversation();
    }

    public void playConversation() {
        tb.setTone(this.currentConv.getTone());
        tb.setLine("");

        this.setSpeed(this.currentConv.getSpeed());

        this.getNextLine();
        this.tb.setOn(true);
    }

    public Conversation getConversation() {
        return this.currentConv;
    }

    public void setSpeed(Speed sped) {
        switch (sped) {
            case FAST:
                this.typeDelay = .015;
                break;
            case DEFAULT:
                this.typeDelay = .04;
                break;
            case SLOW:
                this.typeDelay = .15;
                break;
        }
    }

    public void getNextLine() {
        this.lineCounter = 0;
        if (this.lineIndex < this.currentConv.getLines().length) {
            this.currentLine = this.currentConv.getLines()[this.lineIndex];
            this.lineIndex++;
            this.secs = 0;
            tb.setLine("");
            this.lineFinished = false;
            tb.setTone(this.currentLine.getTone());
            this.setSpeed(this.currentLine.getSpeed());
        }
        if (this.lineIndex >= this.currentConv.getLines().length) {
            this.convoOver = true;
            this.currentConv.setSeen(true);
        }
    }

    public void onTick(long nanosSincePreviousTick) {
        if (!this.tick) {

            this.tick = true;

        } else {

            long longMultiplier = (long) Math.pow(10, 8);
            this.secs = this.secs + ((double) nanosSincePreviousTick / longMultiplier / 10.0);

            if (this.currentLine.getTone() == Tone.ANGRY) {
                this.animationTimer = this.animationTimer + ((double) nanosSincePreviousTick / longMultiplier / 10.0);
                this.animateTB();
            }
            if (this.currentLine != null && this.secs > this.typeDelay && !this.lineFinished) {
                this.currentSubLine = this.currentLine.getText().substring(0, this.lineCounter);
                this.lineCounter++;
                this.updateTB(this.currentSubLine);
                this.secs = 0;
            }
            if (this.lineCounter > this.currentLine.getText().length()) {
                this.lineFinished = true;
                this.lineCounter = 0;
            }
        }
    }

    public void finishLine() {
        this.lineCounter = this.currentLine.getText().length();
    }

    public void updateTB(String text) {
        if (this.currentLine.getTone() == Tone.ANGRY) {

            tb.setLine(text.toUpperCase());

        } else {

            tb.setLine(text);

        }
    }

    public void setActiveCond(Object conditions) {
        this.active = conditions;
    }

    public Object getActiveCond() {
        return this.active;
    }

    public Boolean lineDone() {
        return this.lineFinished;
    }

    public Boolean isOver() {
        return this.convoOver;
    }

    public void setModel(DialogueModel dmod) {
        this.dialogue = dmod;
    }
}
