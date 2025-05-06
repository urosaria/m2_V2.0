package jungjin.estimate.domain;

public enum PictureStatus {
    S1("결제대기중"),
    S2("결제완료"),
    S3("진행중"),
    S4("완료"),
    D("삭제");

    private final String description;

    PictureStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static PictureStatus fromCode(String code) {
        for (PictureStatus status : values()) {
            if (status.name().equalsIgnoreCase(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown picture status code: " + code);
    }
}
