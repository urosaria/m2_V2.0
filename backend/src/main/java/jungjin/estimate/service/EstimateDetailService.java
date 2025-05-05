package jungjin.estimate.service;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jungjin.estimate.domain.Canopy;
import jungjin.estimate.domain.Ceiling;
import jungjin.estimate.domain.Door;
import jungjin.estimate.domain.InsideWall;
import jungjin.estimate.domain.StructureDetail;
import jungjin.estimate.domain.Window;
import jungjin.estimate.repository.EstimateCanopyRepository;
import jungjin.estimate.repository.EstimateCeilingRepository;
import jungjin.estimate.repository.EstimateDetailRepository;
import jungjin.estimate.repository.EstimateDoorRepository;
import jungjin.estimate.repository.EstimateInsideWallRepository;
import jungjin.estimate.repository.EstimateWindowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sk.nociar.jpacloner.JpaCloner;

@Service
@RequiredArgsConstructor
public class EstimateDetailService {

    EstimateDetailRepository estimateDetailRepository;
    EstimateCanopyRepository estimateCanopyRepository;
    EstimateCeilingRepository estimateCeilingRepository;
    EstimateDoorRepository estimateDoorRepository;
    EstimateInsideWallRepository estimateInsideWallRepository;
    EstimateWindowRepository estimateWindowRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public StructureDetail saveEstimateDetail(StructureDetail insertEstimateDetail) {
        insertEstimateDetail.insert(insertEstimateDetail);
        return (StructureDetail)this.estimateDetailRepository.save(insertEstimateDetail);
    }

    public StructureDetail showEstimateDetail(Long structure_id) {
        return this.estimateDetailRepository.findByStructureId(structure_id);
    }

    public StructureDetail showEstimateDetailCopy(Long structure_id) {
        StructureDetail structureDetail = this.estimateDetailRepository.findByStructureId(structure_id);
        this.entityManager.detach(structureDetail);
        return structureDetail;
    }

    public StructureDetail showEstimateDetailId(Long id) {
        return this.estimateDetailRepository.findById(id).orElse(null);
    }

    public StructureDetail saveEstimateDetailEtc(StructureDetail insertEstimateDetail) {
        int canopyCount = insertEstimateDetail.getCanopyList().size();
        int ceilingCount = insertEstimateDetail.getCeilingList().size();
        int doorCount = insertEstimateDetail.getDoorList().size();
        int insideWallCount = insertEstimateDetail.getInsideWallList().size();
        int windowCont = insertEstimateDetail.getWindowList().size();
        this.estimateCanopyRepository.deleteByStructureDetailId(insertEstimateDetail.getId());
        if (insertEstimateDetail.getCanopyYn().equals("Y"))
            for (int i = 0; i < canopyCount; i++) {
                Canopy canopy = insertEstimateDetail.getCanopyList().get(i);
                System.out.println("canopy.getLength():" + canopy.getLength());
                if (canopy.getLength() != 0) {
                    canopy.setStructureDetail(insertEstimateDetail);
                    this.estimateCanopyRepository.save(canopy);
                }
            }
        this.estimateCeilingRepository.deleteByStructureDetailId(insertEstimateDetail.getId());
        if (insertEstimateDetail.getCeilingYn().equals("Y"))
            for (int i = 0; i < ceilingCount; i++) {
                Ceiling ceiling = insertEstimateDetail.getCeilingList().get(i);
                if (ceiling.getLength() != 0) {
                    ceiling.setStructureDetail(insertEstimateDetail);
                    this.estimateCeilingRepository.save(ceiling);
                }
            }
        this.estimateDoorRepository.deleteByStructureDetailId(insertEstimateDetail.getId());
        if (insertEstimateDetail.getDoorYn().equals("Y"))
            for (int i = 0; i < doorCount; i++) {
                Door door = insertEstimateDetail.getDoorList().get(i);
                if (door.getSubType() != null && !door.getSubType().equals("")) {
                    if (!door.getSubType().equals("H")) {
                        String selectWh = door.getSelectWh();
                        System.out.println("selectWh::" + selectWh);
                        String[] realValue = selectWh.split("\\,");
                        door.setWidth(Integer.parseInt(realValue[0]));
                        door.setHeight(Integer.parseInt(realValue[1]));
                    }
                    door.setStructureDetail(insertEstimateDetail);
                    this.estimateDoorRepository.save(door);
                }
            }
        this.estimateInsideWallRepository.deleteByStructureDetailId(insertEstimateDetail.getId());
        if (insertEstimateDetail.getInsideWallYn().equals("Y"))
            for (int i = 0; i < insideWallCount; i++) {
                InsideWall insideWall = insertEstimateDetail.getInsideWallList().get(i);
                if (insideWall.getLength() != 0) {
                    insideWall.setStructureDetail(insertEstimateDetail);
                    this.estimateInsideWallRepository.save(insideWall);
                }
            }
        this.estimateWindowRepository.deleteByStructureDetailId(insertEstimateDetail.getId());
        if (insertEstimateDetail.getWindowYn().equals("Y"))
            for (int i = 0; i < windowCont; i++) {
                Window window = insertEstimateDetail.getWindowList().get(i);
                if (window.getWidth() != 0) {
                    window.setStructureDetail(insertEstimateDetail);
                    this.estimateWindowRepository.save(window);
                }
            }
        return estimateDetailRepository.findById(insertEstimateDetail.getId())
                .orElseThrow(() -> new EntityNotFoundException("StructureDetail not found"));
    }

    public void deleteInsideWall(Long id) {
        this.estimateInsideWallRepository.deleteById(id);
    }

    public void deleteCeiling(Long id) {
        this.estimateCeilingRepository.deleteById(id);
    }

    public void deleteWindow(Long id) {
        this.estimateWindowRepository.deleteById(id);
    }

    public void deleteDoor(Long id) {
        this.estimateDoorRepository.deleteById(id);
    }

    public void deleteCanopy(Long id) {
        this.estimateCanopyRepository.deleteById(id);
    }

    public void copyEstimateDetailEtc(Long oldId, StructureDetail detail) {
        List<Canopy> canopyList = this.estimateCanopyRepository.findByStructureDetailId(oldId);
        canopyList = JpaCloner.clone(canopyList, new String[] { "*" });
        for (int i = 0; i < canopyList.size(); i++) {
            Canopy canopy = new Canopy();
            canopy = canopyList.get(i);
            canopy.setId(0L);
            canopy.setStructureDetail(detail);
            this.estimateCanopyRepository.save(canopy);
        }
        List<Ceiling> ceilingList = this.estimateCeilingRepository.findByStructureDetailId(oldId);
        ceilingList = JpaCloner.clone(ceilingList, new String[] { "*" });
        for (int j = 0; j < ceilingList.size(); j++) {
            Ceiling ceiling = ceilingList.get(j);
            ceiling.setId(0L);
            ceiling.setStructureDetail(detail);
            this.estimateCeilingRepository.save(ceiling);
        }
        List<Door> doorList = this.estimateDoorRepository.findByStructureDetailId(oldId);
        doorList = JpaCloner.clone(doorList, new String[] { "*" });
        for (int k = 0; k < doorList.size(); k++) {
            Door door = doorList.get(k);
            door.setId(0L);
            door.setStructureDetail(detail);
            this.estimateDoorRepository.save(door);
        }
        List<InsideWall> insideWallList = this.estimateInsideWallRepository.findByStructureDetailId(oldId);
        insideWallList = JpaCloner.clone(insideWallList, new String[] { "*" });
        for (int m = 0; m < insideWallList.size(); m++) {
            InsideWall insideWall = insideWallList.get(m);
            insideWall.setId(0L);
            insideWall.setStructureDetail(detail);
            this.estimateInsideWallRepository.save(insideWall);
        }
        List<Window> windowList = this.estimateWindowRepository.findByStructureDetailId(oldId);
        windowList = JpaCloner.clone(windowList, new String[] { "*" });
        for (int n = 0; n < windowList.size(); n++) {
            Window window = windowList.get(n);
            window.setId(0L);
            window.setStructureDetail(detail);
            this.estimateWindowRepository.save(window);
        }
    }
}
