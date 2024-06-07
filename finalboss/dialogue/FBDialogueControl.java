package finalboss.dialogue;

import engine.UIKit.TextBox;
import engine.dialogue.DialogueController;
import engine.dialogue.DialogueModel;
import finalboss.FBChalkboard;
import finalboss.custom_components.main_character.weapons.WeaponType;

public class FBDialogueControl extends DialogueController {
    private FBChalkboard fbChalkboard;

    public FBDialogueControl(TextBox box, DialogueModel model, Object act, FBChalkboard chalkboard) {
        super(box, model, act);
        this.fbChalkboard = chalkboard;
    }

    public void dialogueStart() {

        if (fbChalkboard.gameNum == 1) {
            this.fbChalkboard.setActiveCond(Conditions.FIRSTGAME);
        } else if (fbChalkboard.gameNum == 2) {
            this.fbChalkboard.setActiveCond(Conditions.SECGAME);
        } else if (fbChalkboard.gameNum == 4) {
            this.fbChalkboard.setActiveCond(Conditions.CONCERNED);
        } else {
            if (!fbChalkboard.hasWon && fbChalkboard.winSreak == 1) {
                this.fbChalkboard.setActiveCond(Conditions.FIRSTWIN);
            } else if (fbChalkboard.getLefthand().getType() == WeaponType.NONE
                    && fbChalkboard.getRighthand().getType() == WeaponType.NONE) {
                this.fbChalkboard.setActiveCond(Conditions.UNARMED);
            } else if (fbChalkboard.getLefthand().getType() == WeaponType.NONE
                    || fbChalkboard.getRighthand().getType() == WeaponType.NONE) {
                fbChalkboard.addOneWepDialogue();
                this.fbChalkboard.setActiveCond(Conditions.ONEWEP);
            } else {
                this.fbChalkboard.setActiveCond(Conditions.START);
            }
        }
    }

}
