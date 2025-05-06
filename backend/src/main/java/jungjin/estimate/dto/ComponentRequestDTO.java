package jungjin.estimate.dto;

import lombok.*;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComponentRequestDTO {
    private long id;
    private int length;
    private int amount;
    private int width;
    private int height;
    private String type;
    private String subType;
    private String selectWh;
}
