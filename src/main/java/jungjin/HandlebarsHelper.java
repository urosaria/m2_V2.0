package jungjin;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Options;
import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HandlebarsHelper {
    private static final Logger logger = LoggerFactory.getLogger(HandlebarsHelper.class);

    public CharSequence equals(Object obj1, Options options) throws IOException {
        Object obj2 = options.param(0);
        return Objects.equal(obj1, obj2) ? options.fn() : options.inverse();
    }

    public CharSequence notEquals(Object obj1, Options options) throws IOException {
        Object obj2 = options.param(0);
        return !Objects.equal(obj1, obj2) ? options.fn() : options.inverse();
    }

    public static CharSequence formatDate(LocalDateTime date, String dateFormat) {
        if (date == null)
            return "";
        if (dateFormat == null)
            dateFormat = "yyyy-MM-dd";
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        return df.format(fromLdt(date));
    }

    public static Date fromLdt(LocalDateTime ldt) {
        ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());
        GregorianCalendar cal = GregorianCalendar.from(zdt);
        return cal.getTime();
    }

    public String comma(long val) {
        NumberFormat format = NumberFormat.getNumberInstance();
        return format.format(val);
    }

    public static String cityNameKr(String code) {
        String cityNameKr = "";
        if (code.equals("10")) {
            cityNameKr = "서울";
        } else if (code.equals("20")) {
            cityNameKr = "강원";
        } else if (code.equals("30")) {
            cityNameKr = "대전";
        } else if (code.equals("31")) {
            cityNameKr = "충남";
        } else if (code.equals("33")) {
            cityNameKr = "세종";
        } else if (code.equals("36")) {
            cityNameKr = "충북";
        } else if (code.equals("40")) {
            cityNameKr = "인천";
        } else if (code.equals("41")) {
            cityNameKr = "경기";
        } else if (code.equals("50")) {
            cityNameKr = "광주";
        } else if (code.equals("51")) {
            cityNameKr = "전남";
        } else if (code.equals("56")) {
            cityNameKr = "전북";
        } else if (code.equals("60")) {
            cityNameKr = "부산";
        } else if (code.equals("62")) {
            cityNameKr = "경남";
        } else if (code.equals("68")) {
            cityNameKr = "울산";
        } else if (code.equals("69")) {
            cityNameKr = "제주";
        } else if (code.equals("70")) {
            cityNameKr = "대구";
        } else if (code.equals("71")) {
            cityNameKr = "경북";
        } else if (code.equals("99")) {
            cityNameKr = "기타";
        }
        return cityNameKr;
    }

    public String typeNameKr(String code) {
        String typeNameKr = "";
        if (code.equals("AT")) {
            typeNameKr = "A 트러스";
        } else if (code.equals("BT")) {
            typeNameKr = "B 트러스";
        } else if (code.equals("BE")) {
            typeNameKr = "B 처마형";
        } else if (code.equals("BB")) {
            typeNameKr = "B 박스형";
        } else if (code.equals("AB")) {
            typeNameKr = "A 박스형";
        } else if (code.equals("AE")) {
            typeNameKr = "A 처마형";
        } else if (code.equals("AG")) {
            typeNameKr = "A 기역자";
        } else if (code.equals("BG")) {
            typeNameKr = "B 기역자";
        } else if (code.equals("AX")) {
            typeNameKr = "A 확장";
        } else if (code.equals("BX")) {
            typeNameKr = "B 확장";
        } else if (code.equals("BBX")) {
            typeNameKr = "B 박스확장";
        } else if (code.equals("SL")) {
            typeNameKr = "슬라브";
        }
        return typeNameKr;
    }

    public String pictureStatus(String code) {
        String typeNameKr = "";
        if (code.equals("S1")) {
            typeNameKr = "결제대기중";
        } else if (code.equals("S2")) {
            typeNameKr = "결제완료";
        } else if (code.equals("S3")) {
            typeNameKr = "진행중";
        } else if (code.equals("S4")) {
            typeNameKr = "완료";
        } else if (code.equals("D")) {
            typeNameKr = "삭제";
        }
        return typeNameKr;
    }

    public String pictureStatusUser(String code) {
        String typeNameKr = "";
        if (code.equals("S1")) {
            typeNameKr = "결제대기중";
        } else if (code.equals("S2")) {
            typeNameKr = "결제완료";
        } else if (code.equals("S3")) {
            typeNameKr = "진행중";
        } else if (code.equals("S4")) {
            typeNameKr = "다운로드";
        }
        return typeNameKr;
    }

    public String pictureStatusCss(String code) {
        String typeNameKr = "";
        if (code.equals("S1")) {
            typeNameKr = "payment";
        } else if (code.equals("S2")) {
            typeNameKr = "payment";
        } else if (code.equals("S3")) {
            typeNameKr = "ing";
        } else if (code.equals("S4")) {
            typeNameKr = "complete";
        }
        return typeNameKr;
    }

    public CharSequence pictureStatusUpdate(String code, Options options) throws IOException {
        Map<String, Object> paginationInfoMap = pictureStatusUpdateMake(code);
        return options.fn(paginationInfoMap);
    }

    public Map<String, Object> pictureStatusUpdateMake(String code) {
        Map<String, Object> nextCodeInfo = Maps.newHashMap();
        if (code.equals("S1")) {
            nextCodeInfo.put("code", "S2");
            nextCodeInfo.put("codeValue", "결제완료");
        } else if (code.equals("S2")) {
            nextCodeInfo.put("code", "S3");
            nextCodeInfo.put("codeValue", "진행중");
        } else if (code.equals("S3")) {
            nextCodeInfo.put("code", "S4");
            nextCodeInfo.put("codeValue", "완료");
        }
        return nextCodeInfo;
    }

    public String standardKr(String type, String subType) {
        String typeKr = "";
        String subTypeKr = "";
        if (type.equals("E")) {
            typeKr = "EPS";
        } else if (type.equals("G")) {
            typeKr = "그라스울";
        } else if (type.equals("W")) {
            typeKr = "우레탄";
        }
        if (subType.equals("E1")) {
            subTypeKr = "비난연";
        } else if (subType.equals("E2")) {
            subTypeKr = "난연";
        } else if (subType.equals("E3")) {
            subTypeKr = "가등급";
        } else if (subType.equals("G1")) {
            subTypeKr = "48K";
        } else if (subType.equals("G2")) {
            subTypeKr = "64K";
        } else if (subType.equals("W1")) {
            subTypeKr = "난연";
        }
        return typeKr + "/" + subTypeKr + "/";
    }

    public String statusKr(String code) {
        String typeNameKr = "";
        if (code.equals("S")) {
            typeNameKr = "기본";
        } else if (code.equals("Q")) {
            typeNameKr = "견적요청";
        } else if (code.equals("R")) {
            typeNameKr = "견적완료";
        } else if (code.equals("D")) {
            typeNameKr = "삭제";
        }
        return typeNameKr;
    }

    public String statusUserKr(String code) {
        String typeNameKr = "";
        if (code.equals("S")) {
            typeNameKr = "견적요청";
        } else if (code.equals("Q")) {
            typeNameKr = "견적요청중";
        } else if (code.equals("R")) {
            typeNameKr = "견적완료";
        } else if (code.equals("D")) {
            typeNameKr = "삭제";
        }
        return typeNameKr;
    }

    public CharSequence contentsReplace(CharSequence text) {
        text = Handlebars.Utils.escapeExpression(text);
        text = text.toString();
        text = text.toString().replace(System.getProperty("line.separator"), "<br />");
        return (CharSequence)new Handlebars.SafeString(text);
    }

    public String rowNum(long count, int pageIndex, int index) {
        Long returnRn = Long.valueOf(0L);
        try {
            returnRn = Long.valueOf(count - (10 * (pageIndex - 1)) - index);
        } catch (Exception e) {
            returnRn = Long.valueOf(0L);
        }
        return returnRn.toString();
    }
}
