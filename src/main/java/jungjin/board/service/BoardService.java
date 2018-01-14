package jungjin.board.service;

import jungjin.board.domain.Board;
import jungjin.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

	@Autowired
	BoardRepository boardRepository;
	
	public List<Board> listBoard(){
		return boardRepository.findAll();
	}


}
