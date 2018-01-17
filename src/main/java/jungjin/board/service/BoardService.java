package jungjin.board.service;

import jungjin.board.domain.Board;
import jungjin.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BoardService {

	@Autowired
	BoardRepository boardRepository;
	
	public List<Board> listBoard(int board_master_id){
		return boardRepository.findByBoardMasterId(board_master_id);
	}

	public void saveBoard(Board insertBoard) {
		insertBoard.insert(insertBoard);
		boardRepository.save(insertBoard);
	}

	public Board showBoard(Long id){
		return boardRepository.findOne(id);
	}

	@Transactional
	public void updateBoard(Long id, Board updateBoard){
		Board board = boardRepository.findOne(id);
		board.update(updateBoard);
		boardRepository.save(board);
	}

	@Transactional
	public Board deleteBoard(Long id){
		Board board = boardRepository.findOne(id);
		board.setStatus("D");
		board = boardRepository.save(board);
		return board;
	}
}
