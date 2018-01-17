package jungjin.board.service;

import jungjin.board.domain.Board;
import jungjin.board.domain.BoardReply;
import jungjin.board.repository.BoardReplyRepository;
import jungjin.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardReplyService {

    @Autowired
    BoardReplyRepository boardReplyRepository;

    public List<BoardReply> listBoardReply(Long board_id){
        return boardReplyRepository.findByBoardId(board_id);
    }

    public void saveBoardReply(BoardReply insertBoard) {
        insertBoard.insert(insertBoard);
        boardReplyRepository.save(insertBoard);
    }

    public BoardReply showBoardReply(Long id){
        return boardReplyRepository.findOne(id);
    }

    @Transactional
    public void updateBoardReply(Long id, BoardReply updateBoard){
        BoardReply board = boardReplyRepository.findOne(id);
        board.update(updateBoard);
        boardReplyRepository.save(board);
    }

    @Transactional
    public BoardReply deleteBoardReply(Long id){
        BoardReply boardReply = boardReplyRepository.findOne(id);
        boardReply.setStatus("D");
        boardReply = boardReplyRepository.save(boardReply);
        return boardReply;
    }
}
