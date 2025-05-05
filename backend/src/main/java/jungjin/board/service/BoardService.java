package jungjin.board.service;

import java.util.List;
import jungjin.common.exception.NotFoundException;
import jungjin.board.domain.Board;
import jungjin.board.domain.BoardFile;
import jungjin.board.repository.BoardFileRepository;
import jungjin.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;
	private final BoardFileRepository boardFileRepository;

	public Page<Board> listBoard(int page, int size, long boardMasterId) {
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createDate"));
		return boardRepository.findByBoardMasterIdAndStatus(pageable, boardMasterId, "S");
	}

	public List<Board> listUserBoard(int board_master_id, Long user_num) {
		return boardRepository.findByBoardMasterIdAndUserNum(board_master_id, user_num);
	}

	public void saveBoard(Board insertBoard) {
		insertBoard.insert(insertBoard);
		boardRepository.save(insertBoard);
	}

	public Board showBoard(Long id) {
		updateBoardReadcount(id);
		return boardRepository.findById(id)
			.orElseThrow(() -> new NotFoundException("Board not found with id: " + id));
	}

	@Transactional
	public void updateBoard(Long id, Board updateBoard) {
		Board board = boardRepository.findById(id)
			.orElseThrow(() -> new NotFoundException("Board not found with id: " + id));
		board.update(updateBoard);
		boardRepository.save(board);
	}

	@Transactional
	public Board deleteBoard(Long id) {
		Board board = boardRepository.findById(id)
			.orElseThrow(() -> new NotFoundException("Board not found with id: " + id));
		board.setStatus("D");
		return boardRepository.save(board);
	}

	public BoardFile saveBoardFile(BoardFile boardFile) {
		return (BoardFile)this.boardFileRepository.save(boardFile);
	}

	public void deleteBoardFile(Long[] ids) {
		this.boardFileRepository.deleteBoardFile(ids);
	}

	public BoardFile fileDetailService(Long id) {
		return this.boardFileRepository.findById(id)
			.orElseThrow(() -> new NotFoundException("BoardFile not found with id: " + id));
	}

	public void updateBoardReadcount(Long id) {
		this.boardRepository.updateBoardReadcount(id);
	}
}
