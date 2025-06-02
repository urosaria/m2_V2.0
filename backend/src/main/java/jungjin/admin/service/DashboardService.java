package jungjin.admin.service;

import jungjin.admin.dto.DashboardResponseDTO;
import jungjin.board.service.BoardService;
import jungjin.estimate.service.EstimateService;
import jungjin.picture.service.PictureService;
import jungjin.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final EstimateService estimateService;
    private final BoardService boardService;
    private final UserService userService;
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