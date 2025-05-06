package jungjin.estimate.domain;

public enum CityCode {
    SEOUL("10", "서울"),
    GANGWON("20", "강원"),
    DAEJEON("30", "대전"),
    CHUNGNAM("31", "충남"),
    SEJONG("33", "세종"),
    CHUNGBUK("36", "충북"),
    INCHEON("40", "인천"),
    GYEONGGI("41", "경기"),
    GWANGJU("50", "광주"),
    JEONNAM("51", "전남"),
    JEONBUK("56", "전북"),
    BUSAN("60", "부산"),
    GYEONGNAM("62", "경남"),
    ULSAN("68", "울산"),
    JEJU("69", "제주"),
    DAEGU("70", "대구"),
    GYEONGBUK("71", "경북"),
    ETC("99", "기타");

    private final String code;
    private final String name;

    CityCode(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getNameByCode(String code) {
        for (CityCode c : values()) {
            if (c.code.equals(code)) {
                return c.name;
            }
        }
        return ""; // or return "알 수 없음";
    }
}