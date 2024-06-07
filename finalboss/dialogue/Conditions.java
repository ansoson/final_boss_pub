package finalboss.dialogue;

public enum Conditions {
    FIRSTGAME(Type.START),
    SECGAME(Type.START),
    START(Type.START),
    CONCERNED(Type.START),
    CONCERNED2(Type.MID),
    BOSSHP(Type.MID),
    PLAYERHP(Type.MID),
    FIRSTWIN(Type.START),
    WINSTREAK(Type.START),
    UNARMED(Type.START),
    ONEWEP(Type.START),
    WCHIT(Type.MID),
    WCHIT2(Type.MID),
    WANDHIT(Type.MID),
    WANDHIT2(Type.MID),
    DASH(Type.MID),
    MISSEDWC(Type.MID),
    WEAPON(Type.START);

    private final Type type;

    private Conditions(final Type type) {
        this.type = type;
    }

    public static Conditions parseConditions(String cond) {
        switch (cond) {
            case"FIRSTGAME":
                return FIRSTGAME;
            case "SECGAME":
                return SECGAME;
            case "START":
                return START;
            case "BOSSHP":
                return BOSSHP;
            case "DASH":
                return DASH;
            case "FIRSTWIN":
                return FIRSTWIN;
            case "MISSEDWC":
                return MISSEDWC;
            case "ONEWEP":
                return ONEWEP;
            case "PLAYERHP":
                return PLAYERHP;
            case "UNARMED":
                return UNARMED;
            case "WANDHIT":
                return WANDHIT;
            case "WCHIT":
                return WCHIT;
            case "WANDHIT2":
                return WANDHIT2;
            case "WCHIT2":
                return WCHIT2;
            case "WEAPON":
                return WEAPON;
            case "WINSTREAK":
                return WINSTREAK;
            default:
                return null;
        }
    }

    public String stringify() {
        switch (this) {
            case FIRSTGAME:
                return "FIRSTGAME";
            case START:
                return "START";
            case SECGAME:
                return "SECGAME";
            case BOSSHP:
                return "BOSSHP";
            case DASH:
                return "DASH";
            case FIRSTWIN:
                return "FIRSTWIN";
            case MISSEDWC:
                return "MISSEDWC";
            case ONEWEP:
                return "ONEWEP";
            case PLAYERHP:
                return "PLAYERHP";
            case UNARMED:
                return "UNARMED";
            case WANDHIT:
                return "WANDHIT";
            case WCHIT:
                return "WCHIT";
            case WANDHIT2:
                return "WANDHIT2";
            case WCHIT2:
                return "WCHIT2";
            case WEAPON:
                return "WEAPON";
            case WINSTREAK:
                return "WINSTREAK";
            default:
                return null;
        }
    }

    public Type getType() {
        return type;
    }
}
