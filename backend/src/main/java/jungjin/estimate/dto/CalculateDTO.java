package jungjin.estimate.dto;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Data
@NoArgsConstructor
public class CalculateDTO {
    private String name;
    private String standard;
    private String unit;
    private int amount;
    private int uPrice;
    private String type = "P";
    private int ePrice;
    private long total;
    private int sort;
}
