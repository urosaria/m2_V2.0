package jungjin.estimate.domain;

public enum StructureTypeCode {
    AT("A 트러스"),
    BT("B 트러스"),
    BE("B 처마형"),
    AB("A 박스형"),
    BB("B 박스형"),
    AE("A 처마형"),
    AG("A 기역자"),
    BG("B 기역자"),
    AX("A 확장"),
    BX("B 확장"),
    BBX("B 박스확장"),
    SL("슬라브");

    private final String koreanName;

    StructureTypeCode(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public static StructureTypeCode fromCode(String code) {
        for (StructureTypeCode type : values()) {
            if (type.name().equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown structure type: " + code);
    }
}