package jungjin.board.service;

import java.util.List;

import jakarta.persistence.EntityNotFoundException;
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
        return this.boardReplyRepository.findById(id).orElse(null);
    }

    @Transactional
    public void updateBoardReply(Long id, BoardReply updateBoard) {
        BoardReply board = this.boardReplyRepository.findById(id).orElse(null);
        if (board != null) {
            board.update(updateBoard);
            this.boardReplyRepository.save(board);
        }
    }

    @Transactional
    public BoardReply deleteBoardReply(Long id) {
        BoardReply boardReply = this.boardReplyRepository.findById(id).orElse(null);
        if (boardReply == null) {
            throw new EntityNotFoundException("BoardReply with id " + id + " not found.");
        }

        boardReply.setStatus("D");
        return this.boardReplyRepository.save(boardReply);
    }
}
