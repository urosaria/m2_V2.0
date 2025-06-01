package jungjin.admin.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardResponseDTO {
    private StatDTO estimates;
    private StatDTO pictures;
    private StatDTO users;
    private StatDTO inquiries;
    private StatDTO sales;
}