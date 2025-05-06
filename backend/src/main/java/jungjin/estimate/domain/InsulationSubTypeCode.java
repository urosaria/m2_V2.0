package jungjin.estimate.domain;

public enum InsulationSubTypeCode {
    E1("비난연"),
    E2("난연"),
    E3("가등급"),
    G1("48K"),
    G2("64K"),
    W1("난연");

    private final String label;

    InsulationSubTypeCode(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static InsulationSubTypeCode fromCode(String code) {
        for (InsulationSubTypeCode subType : values()) {
            if (subType.name().equals(code)) {
                return subType;
            }
        }
        return null;
    }
}