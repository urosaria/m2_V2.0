package jungjin.estimate.dto;

import lombok.*;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculateDTO {
    private long id;
    private String name;
    private String standard;
    private String unit;
    private int amount;
    private int uPrice;
    private String type;
    private int ePrice;
    private long total;
    private int sort;
}