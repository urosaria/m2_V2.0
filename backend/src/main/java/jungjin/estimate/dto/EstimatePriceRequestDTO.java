package jungjin.estimate.dto;

import jungjin.estimate.domain.Price;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Getter
@Setter
@NoArgsConstructor
public class EstimatePriceRequestDTO {
    
    @NotBlank(message = "Gubun is required")
    private String gubun;
    
    @NotBlank(message = "SubGubun is required")
    private String subGubun;
    
    @NotBlank(message = "Type is required")
    private String type;
    
    @NotBlank(message = "SubType is required")
    private String subType;
    
    @NotNull(message = "Start price is required")
    @PositiveOrZero(message = "Start price must be 0 or greater")
    private Integer startPrice;

    @NotNull(message = "Gap price is required")
    @PositiveOrZero(message = "Gap price must be 0 or greater")
    private Integer gapPrice;

    @PositiveOrZero(message = "Max thick price must be 0 or greater")
    private Integer maxThickPrice = 0;

    @PositiveOrZero(message = "Standard price must be 0 or greater")
    private Integer standardPrice = 0;

    @PositiveOrZero(message = "E price must be 0 or greater")
    private Integer ePrice = 0;

    public Price toEntity() {
        Price price = new Price();
        updateEntity(price);
        return price;
    }

    public void updateEntity(Price price) {
        price.setGubun(this.gubun)
            .setSubGubun(this.subGubun)
            .setType(this.type)
            .setSubType(this.subType)
            .setStartPrice(this.startPrice)
            .setGapPrice(this.gapPrice)
            .setMaxThickPrice(this.maxThickPrice)
            .setStandardPrice(this.standardPrice)
            .setEPrice(this.ePrice);
    }
}
