package jungjin.board.service;

import java.util.List;
import jungjin.board.domain.BoardMaster;
import jungjin.board.repository.BoardMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardMasterService {
    @Autowired
    BoardMasterRepository boardMasterRepository;

    public List<BoardMaster> listBoardMaster() {
        return this.boardMasterRepository.findAll();
    }

    public void saveBoardMaster(BoardMaster boardMaster) {
        boardMaster.setStatus("S");
        this.boardMasterRepository.save(boardMaster);
    }

    public BoardMaster showBoardMaster(int id) {
        return (BoardMaster)this.boardMasterRepository.findOne(Integer.valueOf(id));
    }

    @Transactional
    public void updateBoardMaster(int id, BoardMaster updateBoareMaster) {
        BoardMaster boardMaster = (BoardMaster)this.boardMasterRepository.findOne(Integer.valueOf(id));
        boardMaster.update(updateBoareMaster);
        this.boardMasterRepository.save(boardMaster);
    }

    @Transactional
    public BoardMaster deleteBoardMaster(int id) {
        BoardMaster boardMaster = (BoardMaster)this.boardMasterRepository.findOne(Integer.valueOf(id));
        boardMaster.setStatus("D");
        boardMaster = (BoardMaster)this.boardMasterRepository.save(boardMaster);
        return boardMaster;
    }
}
