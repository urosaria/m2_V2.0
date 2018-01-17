package jungjin.board.service;

import jungjin.board.domain.BoardMaster;
import jungjin.board.repository.BoardMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardMasterService {

    @Autowired
    BoardMasterRepository boardMasterRepository;

    public List<BoardMaster> listBoardMaster(){
        return boardMasterRepository.findAll();
    }

    public void saveBoardMaster(BoardMaster boardMaster) {
        boardMaster.setStatus("S");
        boardMasterRepository.save(boardMaster);
    }
    public BoardMaster showBoardMaster(int id){
        return boardMasterRepository.findOne(id);
    }

    @Transactional
    public void updateBoardMaster(int id, BoardMaster updateBoareMaster){
        BoardMaster boardMaster = boardMasterRepository.findOne(id);
        boardMaster.update(updateBoareMaster);
        boardMasterRepository.save(boardMaster);
    }

    @Transactional
    public BoardMaster deleteBoardMaster(int id){
        BoardMaster boardMaster = boardMasterRepository.findOne(id);
        boardMaster.setStatus("D");
        boardMaster = boardMasterRepository.save(boardMaster);
        return boardMaster;
    }
}
