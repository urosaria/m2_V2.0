package jungjin.admin.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatDTO {
    private long total;
    private long today;
}
