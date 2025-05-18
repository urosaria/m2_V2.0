package jungjin.board.service;

import java.util.List;

import jungjin.board.dto.BoardMasterRequestDTO;
import jungjin.board.dto.BoardMasterResponseDTO;
import jungjin.common.exception.NotFoundException;
import jungjin.board.domain.BoardMaster;
import jungjin.board.repository.BoardMasterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BoardMasterService {
    private final BoardMasterRepository boardMasterRepository;

    public List<BoardMasterResponseDTO> listBoardMasters() {
        return boardMasterRepository.findAll().stream()
                .map(BoardMasterResponseDTO::fromEntity)
                .toList();
    }

    public BoardMasterResponseDTO getBoardMasterById(Long id) {
        return boardMasterRepository.findById(id)
                .map(BoardMasterResponseDTO::fromEntity)
                .orElseThrow(() -> new NotFoundException("BoardMaster not found with id: " + id));
    }

    public BoardMaster getBoardMasterEntity(long id) {
        return boardMasterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("BoardMaster not found with id: " + id));
    }

    public BoardMasterResponseDTO createBoardMaster(BoardMasterRequestDTO dto) {
        BoardMaster entity = BoardMaster.builder()
                .name(dto.getName())
                .replyYn(dto.getReplyYn())
                .status(dto.getStatus() != null ? dto.getStatus() : "S")
                .skinName(dto.getSkinName())
                .build();

        return BoardMasterResponseDTO.fromEntity(boardMasterRepository.save(entity));
    }

    public BoardMasterResponseDTO updateBoardMaster(Long id, BoardMasterRequestDTO dto) {
        BoardMaster boardMaster = boardMasterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("BoardMaster not found with id: " + id));

        boardMaster.setName(dto.getName());
        boardMaster.setReplyYn(dto.getReplyYn());
        boardMaster.setSkinName(dto.getSkinName());

        return BoardMasterResponseDTO.fromEntity(boardMasterRepository.save(boardMaster));
    }

    public void deleteBoardMaster(Long id) {
        BoardMaster boardMaster = boardMasterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("BoardMaster not found with id: " + id));
        boardMaster.setStatus("D");
        boardMasterRepository.save(boardMaster);
    }
}
