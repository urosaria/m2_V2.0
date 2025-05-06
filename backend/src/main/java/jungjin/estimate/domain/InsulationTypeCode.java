package jungjin.estimate.domain;

public enum InsulationTypeCode {
    E("EPS"),
    G("그라스울"),
    W("우레탄");

    private final String label;

    InsulationTypeCode(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static InsulationTypeCode fromCode(String code) {
        for (InsulationTypeCode type : values()) {
            if (type.name().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
