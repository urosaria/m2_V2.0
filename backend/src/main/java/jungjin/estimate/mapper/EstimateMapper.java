package jungjin.estimate.mapper;

import jungjin.estimate.domain.*;
import jungjin.estimate.dto.CalculateDTO;
import jungjin.estimate.dto.ComponentRequestDTO;
import jungjin.estimate.dto.EstimateRequestDTO;
import jungjin.estimate.dto.EstimateResponseDTO;
import jungjin.user.domain.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EstimateMapper {

    public Structure toStructure(EstimateRequestDTO dto, User user) {
        Structure structure = new Structure();
        structure.setUser(user);
        structure.setTitle(dto.getTitle())
                .setCityName(dto.getCityName())
                .setPlaceName(dto.getPlaceName())
                .setStructureType(Enum.valueOf(StructureTypeCode.class, dto.getStructureType()))
                .setWidth(dto.getWidth())
                .setLength(dto.getLength())
                .setHeight(dto.getHeight())
                .setTrussHeight(dto.getTrussHeight())
                .setEavesLength(dto.getEavesLength())
                .setRearTrussHeight(dto.getRearTrussHeight())
                .setInsideWidth(dto.getInsideWidth())
                .setInsideLength(dto.getInsideLength())
                .setRooftopSideHeight(dto.getRooftopSideHeight())
                .setRooftopWidth(dto.getRooftopWidth())
                .setRooftopHeight(dto.getRooftopHeight())
                .setRooftopLength(dto.getRooftopLength())
                .setCreateDate(LocalDateTime.now())
                .setStatus("S");

        //set detail info
        StructureDetail detail = toStructureDetail(dto);
        detail.setStructure(structure);
        structure.setStructureDetail(detail);

        return structure;
    }

    public StructureDetail toStructureDetail(EstimateRequestDTO dto) {
        StructureDetail detail = new StructureDetail();
        detail.setInsideWallYn(dto.getInsideWallYn())
                .setCeilingYn(dto.getCeilingYn())
                .setWindowYn(dto.getWindowYn())
                .setDoorYn(dto.getDoorYn())
                .setCanopyYn(dto.getCanopyYn())
                .setDownpipeYn(dto.getDownpipeYn())

                .setInsideWallType(toEnumSafe(InsulationTypeCode.class, dto.getInsideWallType()))
                .setInsideWallPaper(toEnumSafe(InsulationSubTypeCode.class, dto.getInsideWallPaper()))
                .setInsideWallThick(dto.getInsideWallThick())

                .setOutsideWallType(toEnumSafe(InsulationTypeCode.class, dto.getOutsideWallType()))
                .setOutsideWallPaper(toEnumSafe(InsulationSubTypeCode.class, dto.getOutsideWallPaper()))
                .setOutsideWallThick(dto.getOutsideWallThick())

                .setRoofType(toEnumSafe(InsulationTypeCode.class, dto.getRoofType()))
                .setRoofPaper(toEnumSafe(InsulationSubTypeCode.class, dto.getRoofPaper()))
                .setRoofThick(dto.getRoofThick())

                .setCeilingType(toEnumSafe(InsulationTypeCode.class, dto.getCeilingType()))
                .setCeilingPaper(toEnumSafe(InsulationSubTypeCode.class, dto.getCeilingPaper()));

        // Convert and assign subcomponents
        detail.setCanopyList(dto.getCanopyList().stream().map(d -> toCanopy(d, detail)).toList());
        detail.setCeilingList(dto.getCeilingList().stream().map(d -> toCeiling(d, detail)).toList());
        detail.setDoorList(dto.getDoorList().stream().map(d -> toDoor(d, detail)).toList());
        detail.setDownpipeList(dto.getDownpipeList().stream().map(d -> toDownpipe(d, detail)).toList());
        detail.setInsideWallList(dto.getInsideWallList().stream().map(d -> toInsideWall(d, detail)).toList());
        detail.setWindowList(dto.getWindowList().stream().map(d -> toWindow(d, detail)).toList());

        return detail;
    }

    private String toStringSafe(Object obj) {
        return obj != null ? obj.toString() : null;
    }

    private Canopy toCanopy(ComponentRequestDTO dto, StructureDetail parent) {
        return new Canopy()
                .setLength(dto.getLength())
                .setAmount(dto.getAmount())
                .setStructureDetail(parent);
    }

    private Ceiling toCeiling(ComponentRequestDTO dto, StructureDetail parent) {
        return new Ceiling()
                .setLength(dto.getLength())
                .setHeight(dto.getHeight())
                .setAmount(dto.getAmount())
                .setStructureDetail(parent);
    }

    private Door toDoor(ComponentRequestDTO dto, StructureDetail parent) {
        return new Door()
                .setWidth(dto.getWidth())
                .setHeight(dto.getHeight())
                .setAmount(dto.getAmount())
                .setType(dto.getType())
                .setSubType(dto.getSubType())
                .setStructureDetail(parent);
    }

    private Downpipe toDownpipe(ComponentRequestDTO dto, StructureDetail parent) {
        return new Downpipe()
                .setWidth(dto.getWidth())
                .setHeight(dto.getHeight())
                .setAmount(dto.getAmount())
                .setStructureDetail(parent);
    }

    private InsideWall toInsideWall(ComponentRequestDTO dto, StructureDetail parent) {
        return new InsideWall()
                .setLength(dto.getLength()) // width = length here based on getWidth() override
                .setHeight(dto.getHeight())
                .setAmount(dto.getAmount())
                .setStructureDetail(parent);
    }

    private Window toWindow(ComponentRequestDTO dto, StructureDetail parent) {
        return new Window()
                .setWidth(dto.getWidth())
                .setHeight(dto.getHeight())
                .setAmount(dto.getAmount())
                .setType(dto.getType())
                .setStructureDetail(parent);
    }

    public Structure updateEntity(Structure entity, EstimateRequestDTO dto) {
        entity.setTitle(dto.getTitle())
                .setCityName(dto.getCityName())
                .setPlaceName(dto.getPlaceName())
                .setStructureType(StructureTypeCode.valueOf(dto.getStructureType()))
                .setWidth(dto.getWidth())
                .setLength(dto.getLength())
                .setHeight(dto.getHeight())
                .setTrussHeight(dto.getTrussHeight())
                .setEavesLength(dto.getEavesLength())
                .setRearTrussHeight(dto.getRearTrussHeight())
                .setInsideWidth(dto.getInsideWidth())
                .setInsideLength(dto.getInsideLength())
                .setRooftopSideHeight(dto.getRooftopSideHeight())
                .setRooftopWidth(dto.getRooftopWidth())
                .setRooftopHeight(dto.getRooftopHeight())
                .setRooftopLength(dto.getRooftopLength());

        StructureDetail detail = entity.getStructureDetail();

        detail.setInsideWallYn(dto.getInsideWallYn())
                .setCeilingYn(dto.getCeilingYn())
                .setWindowYn(dto.getWindowYn())
                .setDoorYn(dto.getDoorYn())
                .setCanopyYn(dto.getCanopyYn())
                .setDownpipeYn(dto.getDownpipeYn())

                .setInsideWallType(toEnumSafe(InsulationTypeCode.class, dto.getInsideWallType()))
                .setInsideWallPaper(toEnumSafe(InsulationSubTypeCode.class, dto.getInsideWallPaper()))
                .setInsideWallThick(dto.getInsideWallThick())

                .setOutsideWallType(toEnumSafe(InsulationTypeCode.class, dto.getOutsideWallType()))
                .setOutsideWallPaper(toEnumSafe(InsulationSubTypeCode.class, dto.getOutsideWallPaper()))
                .setOutsideWallThick(dto.getOutsideWallThick())

                .setRoofType(toEnumSafe(InsulationTypeCode.class, dto.getRoofType()))
                .setRoofPaper(toEnumSafe(InsulationSubTypeCode.class, dto.getRoofPaper()))
                .setRoofThick(dto.getRoofThick())

                .setCeilingType(toEnumSafe(InsulationTypeCode.class, dto.getCeilingType()))
                .setCeilingPaper(toEnumSafe(InsulationSubTypeCode.class, dto.getCeilingPaper()));

        // Replace subcomponents
        detail.getCanopyList().clear();
        detail.getCanopyList().addAll(dto.getCanopyList().stream().map(d -> toCanopy(d, detail)).toList());

        detail.getCeilingList().clear();
        detail.getCeilingList().addAll(dto.getCeilingList().stream().map(d -> toCeiling(d, detail)).toList());

        detail.getDoorList().clear();
        detail.getDoorList().addAll(dto.getDoorList().stream().map(d -> toDoor(d, detail)).toList());

        detail.getDownpipeList().clear();
        detail.getDownpipeList().addAll(dto.getDownpipeList().stream().map(d -> toDownpipe(d, detail)).toList());

        detail.getInsideWallList().clear();
        detail.getInsideWallList().addAll(dto.getInsideWallList().stream().map(d -> toInsideWall(d, detail)).toList());

        detail.getWindowList().clear();
        detail.getWindowList().addAll(dto.getWindowList().stream().map(d -> toWindow(d, detail)).toList());

        return entity;
    }

    public EstimateResponseDTO toResponseDTO(Structure structure) {
        StructureDetail detail = structure.getStructureDetail();
        EstimateResponseDTO dto = new EstimateResponseDTO();

        // Top-level fields
        dto.setId(structure.getId());
        dto.setTitle(structure.getTitle());
        dto.setCityName(CityCode.fromCode(structure.getCityName().getCode()));
        dto.setPlaceName(structure.getPlaceName());
        dto.setStructureType(StructureTypeCode.valueOf(structure.getStructureType().name()));
        dto.setWidth(structure.getWidth());
        dto.setLength(structure.getLength());
        dto.setHeight(structure.getHeight());
        dto.setTrussHeight(structure.getTrussHeight());
        dto.setEavesLength(structure.getEavesLength());
        dto.setRearTrussHeight(structure.getRearTrussHeight());
        dto.setInsideWidth(structure.getInsideWidth());
        dto.setInsideLength(structure.getInsideLength());
        dto.setRooftopSideHeight(structure.getRooftopSideHeight());
        dto.setRooftopWidth(structure.getRooftopWidth());
        dto.setRooftopHeight(structure.getRooftopHeight());
        dto.setRooftopLength(structure.getRooftopLength());
        dto.setCreatedAt(structure.getCreateDate());
        dto.setUpdatedAt(structure.getUpdateDate());

        // Detail (nullable)
        if (detail != null) {
            dto.setInsideWallYn(detail.getInsideWallYn());
            dto.setCeilingYn(detail.getCeilingYn());
            dto.setWindowYn(detail.getWindowYn());
            dto.setDoorYn(detail.getDoorYn());
            dto.setCanopyYn(detail.getCanopyYn());
            dto.setDownpipeYn(detail.getDownpipeYn());


            dto.setInsideWallType(toEnumSafe(detail.getInsideWallType()));
            dto.setInsideWallPaper(toEnumSafe(detail.getInsideWallPaper()));
            dto.setInsideWallThick(detail.getInsideWallThick());

            dto.setOutsideWallType(toEnumSafe(detail.getOutsideWallType()));
            dto.setOutsideWallPaper(toEnumSafe(detail.getOutsideWallPaper()));
            dto.setOutsideWallThick(detail.getOutsideWallThick());

            dto.setRoofType(toEnumSafe(detail.getRoofType()));
            dto.setRoofPaper(toEnumSafe(detail.getRoofPaper()));
            dto.setRoofThick(detail.getRoofThick());

            dto.setCeilingType(toEnumSafe(detail.getCeilingType()));
            dto.setCeilingPaper(toEnumSafe(detail.getCeilingPaper()));

            // Subcomponents
            dto.setCanopyList(detail.getCanopyList().stream().map(this::toComponentDTO).toList());
            dto.setCeilingList(detail.getCeilingList().stream().map(this::toComponentDTO).toList());
            dto.setDoorList(detail.getDoorList().stream().map(this::toComponentDTO).toList());
            dto.setDownpipeList(detail.getDownpipeList().stream().map(this::toComponentDTO).toList());
            dto.setInsideWallList(detail.getInsideWallList().stream().map(this::toComponentDTO).toList());
            dto.setWindowList(detail.getWindowList().stream().map(this::toComponentDTO).toList());
        }

        // Calculate items (optional)
        if (structure.getCalculateList() != null) {
            dto.setCalculateList(
                    structure.getCalculateList().stream()
                            .map(this::toCalculateDTO)
                            .toList()
            );
        }

        return dto;
    }

    public <T extends Enum<T>> T toEnumSafe(Class<T> enumClass, String value) {
        if (value == null || value.isBlank()) return null;
        try {
            return Enum.valueOf(enumClass, value);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    public <T extends Enum<T>> T toEnumSafe(T enumValue) {
        return enumValue;
    }

    private CalculateDTO toCalculateDTO(Calculate cal) {
        CalculateDTO dto = new CalculateDTO();
        dto.setName(cal.getName());
        dto.setStandard(cal.getStandard());
        dto.setUnit(cal.getUnit());
        dto.setAmount(cal.getAmount());
        dto.setUPrice(cal.getUPrice());
        dto.setType(cal.getType());
        dto.setTotal(cal.getTotal());
        dto.setSort(cal.getSort());
        return dto;
    }

    private ComponentRequestDTO toComponentDTO(Canopy entity) {
        return new ComponentRequestDTO(
                entity.getId(),
                entity.getLength(),
                entity.getAmount(),
                0,
                0,
                null,
                null,
                null
        );
    }

    private ComponentRequestDTO toComponentDTO(Ceiling entity) {
        return new ComponentRequestDTO(
                entity.getId(),
                entity.getLength(),
                entity.getAmount(),
                0,
                entity.getHeight(),
                null,
                null,
                null
        );
    }

    private ComponentRequestDTO toComponentDTO(Door entity) {
        return new ComponentRequestDTO(
                entity.getId(),
                0,
                entity.getAmount(),
                entity.getWidth(),
                entity.getHeight(),
                entity.getType(),
                entity.getSubType(),
                entity.getSelectWh()
        );
    }

    private ComponentRequestDTO toComponentDTO(Downpipe entity) {
        return new ComponentRequestDTO(
                entity.getId(),
                0,
                entity.getAmount(),
                entity.getWidth(),
                entity.getHeight(),
                null,
                null,
                null
        );
    }

    private ComponentRequestDTO toComponentDTO(InsideWall entity) {
        return new ComponentRequestDTO(
                entity.getId(),
                entity.getLength(),
                entity.getAmount(),
                0,
                entity.getHeight(),
                null,
                null,
                null
        );
    }

    private ComponentRequestDTO toComponentDTO(Window entity) {
        return new ComponentRequestDTO(
                entity.getId(),
                0,
                entity.getAmount(),
                entity.getWidth(),
                entity.getHeight(),
                entity.getType(),
                null,
                null
        );
    }


}