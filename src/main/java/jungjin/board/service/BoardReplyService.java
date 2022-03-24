package jungjin.board.service;

import java.util.List;
import jungjin.board.domain.BoardReply;
import jungjin.board.repository.BoardReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardReplyService {
    @Autowired
    BoardReplyRepository boardReplyRepository;

    public List<BoardReply> listBoardReply(Long board_id) {
        return this.boardReplyRepository.findByBoardId(board_id);
    }

    public void saveBoardReply(BoardReply insertBoard) {
        insertBoard.insert(insertBoard);
        this.boardReplyRepository.save(insertBoard);
    }

    public BoardReply showBoardReply(Long id) {
        return (BoardReply)this.boardReplyRepository.findOne(id);
    }

    @Transactional
    public void updateBoardReply(Long id, BoardReply updateBoard) {
        BoardReply board = (BoardReply)this.boardReplyRepository.findOne(id);
        board.update(updateBoard);
        this.boardReplyRepository.save(board);
    }

    @Transactional
    public BoardReply deleteBoardReply(Long id) {
        BoardReply boardReply = (BoardReply)this.boardReplyRepository.findOne(id);
        boardReply.setStatus("D");
        boardReply = (BoardReply)this.boardReplyRepository.save(boardReply);
        return boardReply;
    }
}
