package engine.dialogue;

import java.util.ArrayList;
import java.util.HashMap;

public class DialogueModel {

    private ArrayList<Conversation> conversations;
    private ArrayList<Object> conditions;
    HashMap<Object, ArrayList<Conversation>> dialogue;

    public DialogueModel(ArrayList<Conversation> convos) {
        this.conversations = convos;
        this.genConditions();
        this.buildModel();
    }

    public void buildModel() {
        this.dialogue = new HashMap<Object, ArrayList<Conversation>>();
        for (Object condition : conditions) {
            this.dialogue.put(condition, new ArrayList<>());
            for (Conversation convo : this.conversations) {
                if (convo.getCondition() == condition) {
                    this.dialogue.get(condition).add(convo);
                }
            }
        }
    }

    public void genConditions() {
        this.conditions = new ArrayList<Object>();
        for (Conversation convo : this.conversations) {
            if (!this.conditions.contains(convo.getCondition())) {
                this.conditions.add(convo.getCondition());
            }
        }
    }

    public HashMap<Object, ArrayList<Conversation>> getDialogue() {
        return this.dialogue;
    }

    public ArrayList<Conversation> getConvs(Object condition) {
        return this.dialogue.get(condition);
    }

    public ArrayList<Object> getConditions() {
        return this.conditions;
    }
}
