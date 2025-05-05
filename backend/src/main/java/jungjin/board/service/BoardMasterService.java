package jungjin.board.service;

import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import jungjin.board.domain.BoardMaster;
import jungjin.board.repository.BoardMasterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardMasterService {
    private final BoardMasterRepository boardMasterRepository;

    public List<BoardMaster> listBoardMaster() {
        return boardMasterRepository.findAll();
    }

    public void saveBoardMaster(BoardMaster boardMaster) {
        boardMaster.setStatus("S");
        boardMasterRepository.save(boardMaster);
    }

    public BoardMaster showBoardMaster(long id) {
        return boardMasterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BoardMaster with id " + id + " not found."));
    }

    @Transactional
    public void updateBoardMaster(long id, BoardMaster updateBoardMaster) {
        BoardMaster boardMaster = boardMasterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BoardMaster with id " + id + " not found."));

        boardMaster.update(updateBoardMaster);  // assumes a custom update method
        boardMasterRepository.save(boardMaster);
    }

    @Transactional
    public BoardMaster deleteBoardMaster(long id) {
        BoardMaster boardMaster = boardMasterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BoardMaster with id " + id + " not found."));

        boardMaster.setStatus("D");
        return boardMasterRepository.save(boardMaster);
    }
}
