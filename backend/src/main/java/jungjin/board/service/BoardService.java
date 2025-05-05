package jungjin.board.service;

import java.util.List;
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

	BoardRepository boardRepository;
	BoardFileRepository boardFileRepository;

	public Page<Board> listBoard(int page, int size, int board_master_id) {
		PageRequest request = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createDate"));
		return this.boardRepository.findByBoardMasterIdAndStatus((Pageable)request, board_master_id, "S");
	}

	public List<Board> listUserBoard(int board_master_id, Long user_num) {
		return this.boardRepository.findByBoardMasterIdAndUserNum(board_master_id, user_num);
	}

	public void saveBoard(Board insertBoard) {
		insertBoard.insert(insertBoard);
		this.boardRepository.save(insertBoard);
	}

	public Board showBoard(Long id) {
		updateBoardReadcount(id);
		return this.boardRepository.findById(id).orElse(null);
	}

	@Transactional
	public void updateBoard(Long id, Board updateBoard) {
		Board board = this.boardRepository.findById(id).orElse(null);
		board.update(updateBoard);
		this.boardRepository.save(board);
	}

	@Transactional
	public Board deleteBoard(Long id) {
		Board board = this.boardRepository.findById(id).orElse(null);
		board.setStatus("D");
		board = (Board)this.boardRepository.save(board);
		return board;
	}

	public BoardFile saveBoardFile(BoardFile boardFile) {
		return (BoardFile)this.boardFileRepository.save(boardFile);
	}

	public void deleteBoardFile(Long[] ids) {
		this.boardFileRepository.deleteBoardFile(ids);
	}

	public BoardFile fileDetailService(Long id) {
		return this.boardFileRepository.findById(id).orElse(null);
	}

	public void updateBoardReadcount(Long id) {
		this.boardRepository.updateBoardReadcount(id);
	}
}
