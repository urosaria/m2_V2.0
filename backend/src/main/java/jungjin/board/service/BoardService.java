package jungjin.board.service;

import jungjin.admin.dto.StatDTO;
import jungjin.board.domain.BoardMaster;
import jungjin.board.dto.BoardRequestDTO;
import jungjin.board.dto.BoardResponseDTO;
import jungjin.board.mapper.BoardMapper;
import jungjin.common.exception.NotFoundException;
import jungjin.board.domain.Board;
import jungjin.board.repository.BoardRepository;
import jungjin.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

	private final BoardRepository boardRepository;
	private final BoardMapper boardMapper;
	private final BoardFileService boardFileService;

	public Page<BoardResponseDTO> listBoard(int page, int size, long boardMasterId) {
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createDate"));
		return boardRepository.findByBoardMasterId(pageable, boardMasterId)
				.map(boardMapper::toDto);
	}

	public BoardResponseDTO getBoard(Long id) {
		updateBoardReadCount(id);

		Board board = boardRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Board not found with id: " + id));

		return boardMapper.toDto(board);
	}

	public BoardResponseDTO saveBoard(BoardRequestDTO boardRequest, BoardMaster boardMaster, User user, List<MultipartFile> files) {
		Board board = boardMapper.toEntity(boardRequest, boardMaster, user);
		Board saved = boardRepository.save(board);

		if (files != null && !files.isEmpty()) {
			boardFileService.saveOrUpdateFiles(saved, files, null);
		}
		return boardMapper.toDto(saved);
	}

	public BoardResponseDTO updateBoard(Long boardId, BoardRequestDTO request, List<MultipartFile> files, String deleteFiles) {
		Board board = boardRepository.findById(boardId)
				.orElseThrow(() -> new NotFoundException("Board not found with id: " + boardId));

		board.setTitle(request.getTitle());
		board.setContents(request.getContents());
		Board saved = boardRepository.save(board);

		boardFileService.saveOrUpdateFiles(saved, files, deleteFiles);
		return boardMapper.toDto(saved);
	}

	public void deleteBoard(Long boardId) {
		Board board = boardRepository.findById(boardId)
				.orElseThrow(() -> new NotFoundException("Board not found with id: " + boardId));
		boardRepository.delete(board);
	}

	public void updateBoardReadCount(Long id) {
		boardRepository.updateBoardReadCount(id);
	}

	public StatDTO getStats(Long boardMasterId) {
		long total = boardRepository.countByBoardMaster_Id(boardMasterId);

		LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
		LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

		long todayCount = boardRepository.countByBoardMaster_IdAndCreateDateBetween(
				boardMasterId, startOfDay, endOfDay
		);

		return StatDTO.builder()
				.total(total)
				.today(todayCount)
				.build();
	}
}
