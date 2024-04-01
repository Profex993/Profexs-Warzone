package shared.enums;

public enum PlayerModel {
    BANDIT("bandit"),
    BANDIT_2("bandit_2"),
    BANDIT_3("bandit_3"),
    DEFAULT("default"),
    HAZMAT_SUIT("hazmat_suit"),
    REBEL("rebel"),
    SCAVENGER("scavenger"),
    SCIENTIST("scientist"),
    SCIENTIST_2("scientist_2"),
    SCIENTIST_3("scientist_3"),
    SCOUT("scout"),
    SOLDIER("soldier"),
    SOLDIER_2("soldier_2");


    public final String name;

    PlayerModel(String name) {
        this.name = name;
    }

}
