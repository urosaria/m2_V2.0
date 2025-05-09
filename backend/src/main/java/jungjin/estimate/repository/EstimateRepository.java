package jungjin.estimate.repository;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.transaction.Transactional;
import jungjin.estimate.domain.Structure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EstimateRepository extends JpaRepository<Structure, Long> {
    Page<Structure> findByUserNum(Long userNum, Pageable pageable);

    Page<Structure> findByUserNumAndStatus(Long paramLong, String paramString, Pageable paramPageable);

    Page<Structure> findByUserNumAndStatusNot(Long paramLong, String paramString, Pageable paramPageable);

    Page<Structure> findByStatus(String paramString, Pageable paramPageable);

    Page<Structure> findByStructureTypeAndStatus(String paramString1, String paramString2, Pageable paramPageable);

    Page<Structure> findByPlaceNameContainingAndStatus(String paramString1, String paramString2, Pageable paramPageable);

    Page<Structure> findByUserNameContainingAndStatus(String paramString1, String paramString2, Pageable paramPageable);

    Page<Structure> findByUserPhoneContainingAndStatus(String paramString1, String paramString2, Pageable paramPageable);

    Page<Structure> findByUserEmailContainingAndStatus(String paramString1, String paramString2, Pageable paramPageable);

    Page<Structure> findByStructureType(String paramString, Pageable paramPageable);

    Page<Structure> findByPlaceNameContaining(String paramString, Pageable paramPageable);

    Page<Structure> findByUserNameContaining(String paramString, Pageable paramPageable);

    Page<Structure> findByUserPhoneContaining(String paramString, Pageable paramPageable);

    Page<Structure> findByUserEmailContaining(String paramString, Pageable paramPageable);

    @Transactional
    @Modifying
    @Query("update Structure set status=:status where id = :id")
    void updateStructureStatus(@Param("id") Long paramLong, @Param("status") String paramString);

    @Transactional
    @Modifying
    @Query("update Structure set status=:status, statusDate=:statusDate where id = :id")
    void updateStructureStatusQ(@Param("id") Long paramLong, @Param("status") String paramString, @Param("statusDate") LocalDateTime paramLocalDateTime);
}
