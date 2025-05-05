package jungjin.board.service;

import java.util.List;

import jungjin.board.domain.BoardReply;
import jungjin.board.repository.BoardReplyRepository;
import jungjin.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardReplyService {
    private final BoardReplyRepository boardReplyRepository;

    public List<BoardReply> listBoardReply(Long board_id) {
        return boardReplyRepository.findByBoardId(board_id);
    }

    public void saveBoardReply(BoardReply insertBoard) {
        insertBoard.insert(insertBoard);
        boardReplyRepository.save(insertBoard);
    }

    public BoardReply showBoardReply(Long id) {
        return boardReplyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("BoardReply with id " + id + " not found."));
    }

    @Transactional
    public void updateBoardReply(Long id, BoardReply updateBoard) {
        BoardReply board = boardReplyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("BoardReply with id " + id + " not found."));
        board.update(updateBoard);
        boardReplyRepository.save(board);
    }

    @Transactional
    public BoardReply deleteBoardReply(Long id) {
        BoardReply boardReply = boardReplyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("BoardReply with id " + id + " not found."));
        boardReply.setStatus("D");
        return boardReplyRepository.save(boardReply);
    }
}
