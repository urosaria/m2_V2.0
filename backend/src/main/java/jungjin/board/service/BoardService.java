package jungjin.board.service;

import jungjin.board.domain.BoardMaster;
import jungjin.board.dto.BoardRequestDTO;
import jungjin.board.dto.BoardResponseDTO;
import jungjin.board.mapper.BoardMapper;
import jungjin.common.exception.BusinessException;
import jungjin.common.exception.NotFoundException;
import jungjin.board.domain.Board;
import jungjin.board.domain.BoardFile;
import jungjin.board.repository.BoardFileRepository;
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

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

	private final BoardRepository boardRepository;
	private final BoardFileRepository boardFileRepository;
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

	public BoardResponseDTO updateBoard(Long boardMasterId, Long boardId, BoardRequestDTO request, List<MultipartFile> files, String deleteFiles) {
		Board board = boardRepository.findById(boardId)
				.orElseThrow(() -> new NotFoundException("Board not found with id: " + boardId));

		if (!boardMasterId.equals(board.getBoardMaster().getId())) {
			throw new BusinessException("Board does not belong to board master: " + boardMasterId);
		}

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

	public BoardFile fileDetailService(Long id) {
		return this.boardFileRepository.findById(id)
			.orElseThrow(() -> new NotFoundException("BoardFile not found with id: " + id));
	}

	public void updateBoardReadCount(Long id) {
		boardRepository.updateBoardReadCount(id);
	}
}
