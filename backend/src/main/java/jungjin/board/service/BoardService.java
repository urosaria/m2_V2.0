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

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

	private final BoardRepository boardRepository;
	private final BoardFileRepository boardFileRepository;
	private final BoardMapper boardMapper;

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

	public BoardResponseDTO saveBoard(BoardRequestDTO boardRequest, BoardMaster boardMaster, User user) {
		Board board = boardMapper.toEntity(boardRequest, boardMaster, user);
		Board saved = boardRepository.save(board);
		return boardMapper.toDto(saved);
	}

	public BoardResponseDTO updateBoard(Long boardMasterId, Long boardId, BoardRequestDTO request) {
		Board board = boardRepository.findById(boardId)
				.orElseThrow(() -> new NotFoundException("Board not found with id: " + boardId));

		if (!boardMasterId.equals(board.getBoardMaster().getId())) {
			throw new BusinessException("Board does not belong to board master: " + boardMasterId);
		}

		board.setTitle(request.getTitle());
		board.setContents(request.getContents());

		Board saved = boardRepository.save(board);
		return boardMapper.toDto(saved);
	}

	public void deleteBoard(Long boardId) {
		Board board = boardRepository.findById(boardId)
				.orElseThrow(() -> new NotFoundException("Board not found with id: " + boardId));
		boardRepository.delete(board);
	}

//	public List<Board> listUserBoard(int board_master_id, Long user_num) {
//		return boardRepository.findByBoardMasterIdAndUserNum(board_master_id, user_num);
//	}
//
//	@Transactional
//	public Board deleteBoard(Long id) {
//		Board board = boardRepository.findById(id)
//			.orElseThrow(() -> new NotFoundException("Board not found with id: " + id));
//		board.setStatus("D");
//		return boardRepository.save(board);
//	}
//
//	public BoardFile saveBoardFile(BoardFile boardFile) {
//		return (BoardFile)this.boardFileRepository.save(boardFile);
//	}
//
//	public void deleteBoardFile(Long[] ids) {
//		this.boardFileRepository.deleteBoardFile(ids);
//	}

	public BoardFile fileDetailService(Long id) {
		return this.boardFileRepository.findById(id)
			.orElseThrow(() -> new NotFoundException("BoardFile not found with id: " + id));
	}

	public void updateBoardReadCount(Long id) {
		boardRepository.updateBoardReadCount(id);
	}
}
