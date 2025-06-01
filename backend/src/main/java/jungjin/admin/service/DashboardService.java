package jungjin.admin.service;

import jungjin.admin.dto.DashboardResponseDTO;
import jungjin.board.service.BoardService;
import jungjin.estimate.service.EstimateServiceV2;
import jungjin.picture.service.PictureService;
import jungjin.user.service.UserV2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final EstimateServiceV2 estimateService;
    private final BoardService boardService;
    private final UserV2Service userService;
    private final PictureService pictureService;

    public DashboardResponseDTO getDashboardStats() {
        return DashboardResponseDTO.builder()
                .estimates(estimateService.getStats())
                .inquiries(boardService.getStats(2L))
                .users(userService.getStats())
                .pictures(pictureService.getStats())
                .sales(null)
                .build();
    }
}