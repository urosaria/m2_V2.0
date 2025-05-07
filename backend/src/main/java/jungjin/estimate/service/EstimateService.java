package jungjin.estimate.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jungjin.estimate.mapper.EstimateMapper;
import org.springframework.transaction.annotation.Transactional;
import jungjin.HandlebarsHelper;
import jungjin.config.UploadConfig;
import jungjin.estimate.domain.Calculate;
import jungjin.estimate.domain.Structure;
import jungjin.estimate.domain.StructureDetail;
import jungjin.estimate.repository.EstimateCalculateRepository;
import jungjin.estimate.repository.EstimateDetailRepository;
import jungjin.estimate.repository.EstimateRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EstimateService {
    private final EstimateRepository estimateRepository;
    private final EstimateDetailRepository estimateDetailRepository;
    private final EstimateCalculateRepository estimateCalculateRepository;
    private final UploadConfig uploadConfig;
    private final EstimateMapper estimateMapper;

    @PersistenceContext
    private EntityManager entityManager;




    public Page<Structure> listEstimateAll(int page, int size) {
        PageRequest request = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createDate"));
        return estimateRepository.findAll((Pageable)request);
    }

    public Page<Structure> listEstimateStatus(String searchCondition, String searchText, String status, int page, int size) {
        PageRequest request = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.DESC, "createDate"));
        Page<Structure> structures = null;
        if (!searchCondition.equals("") && !status.equals("")) {
            if (searchCondition.equals("AT") || searchCondition.equals("BT") || searchCondition.equals("BE") || searchCondition.equals("BB") || searchCondition.equals("AB") || searchCondition.equals("AE")) {
                structures = estimateRepository.findByStructureTypeAndStatus(searchCondition, status, (Pageable)request);
            } else if (searchCondition.equals("placeName")) {
                structures = estimateRepository.findByPlaceNameContainingAndStatus(searchText, status, (Pageable)request);
            } else if (searchCondition.equals("name")) {
                structures = estimateRepository.findByUserNameContainingAndStatus(searchText, status, (Pageable)request);
            } else if (searchCondition.equals("phone")) {
                structures = estimateRepository.findByUserPhoneContainingAndStatus(searchText, status, (Pageable)request);
            } else if (searchCondition.equals("mail")) {
                structures = estimateRepository.findByUserEmailContainingAndStatus(searchText, status, (Pageable)request);
            }
        } else if (!searchCondition.equals("")) {
            if (searchCondition.equals("AT") || searchCondition.equals("BT") || searchCondition.equals("BE") || searchCondition.equals("BB") || searchCondition.equals("AB") || searchCondition.equals("AE")) {
                structures = estimateRepository.findByStructureType(searchCondition, (Pageable)request);
            } else if (searchCondition.equals("placeName")) {
                structures = estimateRepository.findByPlaceNameContaining(searchText, (Pageable)request);
            } else if (searchCondition.equals("name")) {
                structures = estimateRepository.findByUserNameContaining(searchText, (Pageable)request);
            } else if (searchCondition.equals("phone")) {
                structures = estimateRepository.findByUserPhoneContaining(searchText, (Pageable)request);
            } else if (searchCondition.equals("mail")) {
                structures = estimateRepository.findByUserEmailContaining(searchText, (Pageable)request);
            }
        } else if (!status.equals("")) {
            structures = estimateRepository.findByStatus(status, (Pageable)request);
        }
        return structures;
    }

    @Transactional
    public Page<Structure> listEstimate(int page, int size, Long userNum) {
        PageRequest request = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createDate"));
        Page<Structure> structures = estimateRepository.findByUserNumAndStatusNot(userNum, "D", (Pageable)request);
        
        // Initialize user data to prevent lazy loading issues
        structures.getContent().forEach(structure -> {
            if (structure.getUser() != null) {
                structure.getUser().getName(); // Force initialization
            }
        });
        
        return structures;
    }

    public Structure saveEstimate(Structure insertEstimate) {
        insertEstimate.setCreateDate(LocalDateTime.now());
        return (Structure)estimateRepository.save(insertEstimate);
    }

    public Structure showEstimate(Long id) {
        return estimateRepository.findById(id).orElse(null);
    }

    public Structure showEstimateCopy(Long id) {
        Structure structure = estimateRepository.findById(id).orElse(null);
        if (structure == null) {
            throw new EntityNotFoundException("Structure with id " + id + " not found.");
        }

        entityManager.detach(structure); // detach from persistence context
        return structure;
    }

    public Calculate saveCal(Calculate calculate) {
        return (Calculate)estimateCalculateRepository.save(calculate);
    }

    public void saveCalCopy(Calculate calculate) {
        estimateCalculateRepository.save(calculate);
    }

    public void deleteCal(Long structureId) {
        estimateCalculateRepository.deleteByStructureId(structureId);
    }

    public void updateStructureStatus(Long id, String status) {
        if (status.equals("Q")) {
            LocalDateTime statusDate = LocalDateTime.now();
            estimateRepository.updateStructureStatusQ(id, status, statusDate);
        } else {
            estimateRepository.updateStructureStatus(id, status);
        }
    }

    public List<Calculate> listCal(Long structureId) {
        List<Calculate> listCal = estimateCalculateRepository.findByStructureIdOrderBySortAsc(structureId);
        return listCal;
    }

    public List<Calculate> findByStructureIdAndType(Long structureId) {
        return estimateCalculateRepository.findByStructureIdAndType(structureId, "D");
    }

    public void excel(String path, Long structure_id) throws IOException {
        StructureDetail structureDetail = estimateDetailRepository.findByStructureId(structure_id);
        List<Calculate> listCal = estimateCalculateRepository.findByStructureIdOrderBySortAsc(structure_id);
        FileInputStream fis = new FileInputStream(uploadConfig + "/estimate/sample/sample.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFFormulaEvaluator xSSFFormulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
        int columnindex = 0;
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFFont xSSFFont1 = workbook.createFont();
        xSSFFont1.setFontHeightInPoints((short)9);
        XSSFFont xSSFFont2 = workbook.createFont();
        xSSFFont2.setFontHeightInPoints((short)10);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        XSSFDataFormat df = workbook.createDataFormat();
        cellStyle.setDataFormat(df.getFormat("#,##0"));
        cellStyle.setFont((Font)xSSFFont1);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFCellStyle totalCellStyle = workbook.createCellStyle();
        totalCellStyle.setBorderBottom(BorderStyle.THIN);
        totalCellStyle.setBorderLeft(BorderStyle.THIN);
        totalCellStyle.setBorderTop(BorderStyle.THIN);
        totalCellStyle.setBorderRight(BorderStyle.THIN);
        totalCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        totalCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        totalCellStyle.setDataFormat(df.getFormat("#,##0"));
        totalCellStyle.setFont((Font)xSSFFont1);
        totalCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFCellStyle lastStyle = workbook.createCellStyle();
        lastStyle.setWrapText(true);
        lastStyle.setFont((Font)xSSFFont2);
        lastStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFRow xSSFRow = sheet.getRow(0);
        Cell cell = xSSFRow.getCell(0);
        cell.setCellValue("[" + HandlebarsHelper.cityNameKr(structureDetail.getStructure().getCityName()) + "]" + structureDetail.getStructure().getPlaceName());
        cell = xSSFRow.getCell(11);
        CharSequence createDate = HandlebarsHelper.formatDate(structureDetail.getCreateDate(), "yyyy-MM-dd hh:mm");
        cell.setCellValue(createDate.toString());
        xSSFRow = sheet.getRow(4);
        int rowindex = 4;
        int size = listCal.size();
        long piTotal = 0L;
        long pncTotal = 0L;
        long ppTotal = 0L;
        long pTotal = 0L;
        long diTotal = 0L;
        long dncTotal = 0L;
        long dpTotal = 0L;
        long dTotal = 0L;
        int excelRow = 0;
        int etcExcelLast = 0;
        int excelDoorRow = 0;
        List<Calculate> dList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String type = ((Calculate)listCal.get(i)).getType();
            excelRow = rowindex + 1;
            if (type.equals("D")) {
                dList.add(listCal.get(i));
            } else {
                xSSFRow = sheet.createRow(rowindex);
                xSSFRow.setHeight((short)360);
                String name = ((Calculate)listCal.get(i)).getName();
                String unit = ((Calculate)listCal.get(i)).getUnit();
                long total = ((Calculate)listCal.get(i)).getTotal();
                long ttotal = 0L;
                String standard = ((Calculate)listCal.get(i)).getStandard();
                int amount = ((Calculate)listCal.get(i)).getAmount();
                int ePrice = ((Calculate)listCal.get(i)).getEPrice();
                int price = ((Calculate)listCal.get(i)).getUPrice();
                cell = xSSFRow.createCell(0);
                cell.setCellStyle((CellStyle)cellStyle);
                cell.setCellValue(name);
                cell = xSSFRow.createCell(1);
                cell.setCellStyle((CellStyle)cellStyle);
                cell.setCellValue(standard);
                cell = xSSFRow.createCell(2);
                cell.setCellStyle((CellStyle)cellStyle);
                cell.setCellValue(unit);
                cell = xSSFRow.createCell(3);
                cell.setCellStyle((CellStyle)cellStyle);
                cell.setCellValue(amount);
                cell = xSSFRow.createCell(4);
                cell.setCellStyle((CellStyle)cellStyle);
                cell = xSSFRow.createCell(5);
                cell.setCellStyle((CellStyle)cellStyle);
                cell = xSSFRow.createCell(6);
                cell.setCellStyle((CellStyle)cellStyle);
                cell = xSSFRow.createCell(7);
                cell.setCellStyle((CellStyle)cellStyle);
                cell = xSSFRow.createCell(8);
                cell.setCellStyle((CellStyle)cellStyle);
                cell = xSSFRow.createCell(9);
                cell.setCellStyle((CellStyle)cellStyle);
                cell = xSSFRow.createCell(10);
                cell.setCellStyle((CellStyle)cellStyle);
                cell = xSSFRow.createCell(11);
                cell.setCellStyle((CellStyle)cellStyle);
                cell = xSSFRow.createCell(12);
                cell.setCellStyle((CellStyle)cellStyle);
                cell = xSSFRow.createCell(4);
                cell.setCellStyle((CellStyle)cellStyle);
                cell = xSSFRow.createCell(5);
                cell.setCellStyle((CellStyle)cellStyle);
                cell.setCellFormula("D" + excelRow + "*E" + excelRow);
                cell = xSSFRow.createCell(6);
                cell.setCellStyle((CellStyle)cellStyle);
                cell = xSSFRow.createCell(7);
                cell.setCellStyle((CellStyle)cellStyle);
                cell.setCellFormula("D" + excelRow + "*G" + excelRow);
                cell = xSSFRow.createCell(8);
                cell.setCellStyle((CellStyle)cellStyle);
                cell = xSSFRow.createCell(9);
                cell.setCellStyle((CellStyle)cellStyle);
                cell.setCellFormula("D" + excelRow + "*I" + excelRow);
                cell = xSSFRow.createCell(10);
                cell.setCellStyle((CellStyle)cellStyle);
                cell.setCellFormula("SUM(E" + excelRow + "+G" + excelRow + "+I" + excelRow + ")");
                cell = xSSFRow.createCell(11);
                cell.setCellStyle((CellStyle)cellStyle);
                cell.setCellFormula("SUM(F" + excelRow + "+H" + excelRow + "+J" + excelRow + ")");
                cell = xSSFRow.createCell(12);
                cell.setCellStyle((CellStyle)cellStyle);
                rowindex++;
            }
        }
        xSSFRow = sheet.createRow(rowindex);
        xSSFRow.setHeight((short)360);
        cell = xSSFRow.createCell(0);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell.setCellValue("   소   계");
                cell = xSSFRow.createCell(1);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(2);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(3);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(4);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(5);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell.setCellFormula("SUM(F5:F" + rowindex + ")");
        cell = xSSFRow.createCell(6);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(7);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell.setCellFormula("SUM(H5:H" + rowindex + ")");
        cell = xSSFRow.createCell(8);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(9);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell.setCellFormula("SUM(J5:J" + rowindex + ")");
        cell = xSSFRow.createCell(10);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(11);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell.setCellFormula("SUM(L5:L" + rowindex + ")");
        cell = xSSFRow.createCell(12);
        cell.setCellStyle((CellStyle)totalCellStyle);
        etcExcelLast = rowindex + 1;
        rowindex++;
        rowindex++;
        xSSFRow = sheet.createRow(rowindex);
        cell = xSSFRow.createCell(0);
        xSSFRow.setHeight((short)360);
        cell.setCellStyle((CellStyle)cellStyle);
        cell.setCellValue("Ο 창호도어 공사");
                cell = xSSFRow.createCell(1);
        cell.setCellStyle((CellStyle)cellStyle);
        cell = xSSFRow.createCell(2);
        cell.setCellStyle((CellStyle)cellStyle);
        cell = xSSFRow.createCell(3);
        cell.setCellStyle((CellStyle)cellStyle);
        cell = xSSFRow.createCell(4);
        cell.setCellStyle((CellStyle)cellStyle);
        cell = xSSFRow.createCell(5);
        cell.setCellStyle((CellStyle)cellStyle);
        cell = xSSFRow.createCell(6);
        cell.setCellStyle((CellStyle)cellStyle);
        cell = xSSFRow.createCell(7);
        cell.setCellStyle((CellStyle)cellStyle);
        cell = xSSFRow.createCell(8);
        cell.setCellStyle((CellStyle)cellStyle);
        cell = xSSFRow.createCell(9);
        cell.setCellStyle((CellStyle)cellStyle);
        cell = xSSFRow.createCell(10);
        cell.setCellStyle((CellStyle)cellStyle);
        cell = xSSFRow.createCell(11);
        cell.setCellStyle((CellStyle)cellStyle);
        cell = xSSFRow.createCell(12);
        cell.setCellStyle((CellStyle)cellStyle);
        rowindex++;
        int doorExcelStart = 0;
        int doorExcelLast = 0;
        for (int j = 0; j < dList.size(); j++) {
            xSSFRow = sheet.createRow(rowindex);
            xSSFRow.setHeight((short)360);
            excelDoorRow = rowindex + 1;
            if (j == 0)
                doorExcelStart = rowindex + 1;
            String name = ((Calculate)dList.get(j)).getName();
            String unit = ((Calculate)dList.get(j)).getUnit();
            long total = ((Calculate)dList.get(j)).getTotal();
            long ttotal = 0L;
            String standard = ((Calculate)dList.get(j)).getStandard();
            int amount = ((Calculate)dList.get(j)).getAmount();
            int ePrice = ((Calculate)dList.get(j)).getEPrice();
            int price = ((Calculate)dList.get(j)).getUPrice();
            cell = xSSFRow.createCell(0);
            cell.setCellStyle((CellStyle)cellStyle);
            cell.setCellValue(name);
            cell = xSSFRow.createCell(1);
            cell.setCellStyle((CellStyle)cellStyle);
            cell.setCellValue(standard);
            cell = xSSFRow.createCell(2);
            cell.setCellStyle((CellStyle)cellStyle);
            cell.setCellValue(unit);
            cell = xSSFRow.createCell(3);
            cell.setCellStyle((CellStyle)cellStyle);
            cell.setCellValue(amount);
            cell = xSSFRow.createCell(4);
            cell.setCellStyle((CellStyle)cellStyle);
            cell.setCellValue(0.0D);
            cell = xSSFRow.createCell(5);
            cell.setCellStyle((CellStyle)cellStyle);
            cell.setCellFormula("D" + excelDoorRow + "*E" + excelDoorRow);
            cell = xSSFRow.createCell(6);
            cell.setCellStyle((CellStyle)cellStyle);
            cell.setCellValue(0.0D);
            cell = xSSFRow.createCell(7);
            cell.setCellStyle((CellStyle)cellStyle);
            cell.setCellFormula("D" + excelDoorRow + "*G" + excelDoorRow);
            cell = xSSFRow.createCell(8);
            cell.setCellStyle((CellStyle)cellStyle);
            cell.setCellValue(0.0D);
            cell = xSSFRow.createCell(9);
            cell.setCellStyle((CellStyle)cellStyle);
            cell.setCellFormula("D" + excelDoorRow + "*I" + excelDoorRow);
            cell = xSSFRow.createCell(10);
            cell.setCellStyle((CellStyle)cellStyle);
            cell.setCellFormula("SUM(E" + excelDoorRow + "+G" + excelDoorRow + "+I" + excelDoorRow + ")");
            cell = xSSFRow.createCell(11);
            cell.setCellStyle((CellStyle)cellStyle);
            cell.setCellFormula("SUM(F" + excelDoorRow + "+H" + excelDoorRow + "+J" + excelDoorRow + ")");
            cell = xSSFRow.createCell(12);
            cell.setCellStyle((CellStyle)cellStyle);
            rowindex++;
        }
        xSSFRow = sheet.createRow(rowindex);
        doorExcelLast = rowindex + 1;
        cell = xSSFRow.createCell(0);
        xSSFRow.setHeight((short)360);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell.setCellValue("   소   계");
                cell = xSSFRow.createCell(1);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(2);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(3);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(4);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(5);
        cell.setCellStyle((CellStyle)totalCellStyle);
        if (doorExcelStart != 0)
            cell.setCellFormula("SUM(F" + doorExcelStart + ":F" + rowindex + ")");
        cell = xSSFRow.createCell(6);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(7);
        cell.setCellStyle((CellStyle)totalCellStyle);
        if (doorExcelStart != 0)
            cell.setCellFormula("SUM(H" + doorExcelStart + ":H" + rowindex + ")");
        cell = xSSFRow.createCell(8);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(9);
        cell.setCellStyle((CellStyle)totalCellStyle);
        if (doorExcelStart != 0)
            cell.setCellFormula("SUM(J" + doorExcelStart + ":J" + rowindex + ")");
        cell = xSSFRow.createCell(10);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(11);
        cell.setCellStyle((CellStyle)totalCellStyle);
        if (doorExcelStart != 0)
            cell.setCellFormula("SUM(L" + doorExcelStart + ":L" + rowindex + ")");
        cell = xSSFRow.createCell(12);
        cell.setCellStyle((CellStyle)totalCellStyle);
        rowindex++;
        xSSFRow = sheet.createRow(rowindex);
        xSSFRow.setHeight((short)360);
        cell = xSSFRow.createCell(0);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell.setCellValue("   합   계");
                cell = xSSFRow.createCell(1);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(2);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(3);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(4);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(5);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell.setCellValue((piTotal + diTotal));
        cell.setCellFormula("F" + etcExcelLast + "+F" + doorExcelLast);
        cell = xSSFRow.createCell(6);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(7);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell.setCellFormula("H" + etcExcelLast + "+H" + doorExcelLast);
        cell = xSSFRow.createCell(8);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(9);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell.setCellFormula("J" + etcExcelLast + "+J" + doorExcelLast);
        cell = xSSFRow.createCell(10);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(11);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell.setCellFormula("L" + etcExcelLast + "+L" + doorExcelLast);
        cell = xSSFRow.createCell(12);
        cell.setCellStyle((CellStyle)totalCellStyle);
        rowindex++;
        rowindex++;
        xSSFRow = sheet.createRow(rowindex);
        cell = xSSFRow.createCell(0);
        cell.setCellStyle((CellStyle)lastStyle);
        cell.setCellValue("  자재 물량에는 시공 시 발생하는 로스 물량이 포함되어 있습니다. \n 물량산출은 횡시공 기준으로 산출하였습니다. 종시공일경우 물량차이가 다소 발생하실 수 있습니다. \n  판넬의 코일두께 및 단열재 밀도, 난연성능의 구분 등은 직접 입력 하셔야 합니다. \n  모든 단가는 직접 입력하셔서 사용하시기 바랍니다. \n  판넬발주 또는 기타문의사항이 있으시면 연락주시기 바랍니다. \n \n M2패널 담당자 연락처 010-5835-5030 / 이메일: jeffbang@hanmail.net \n ");
        sheet.addMergedRegion(new CellRangeAddress(rowindex, rowindex + 6, 0, 12));
        try {
            Long id = Long.valueOf(structureDetail.getStructure().getId());
            if (id != null) {
                String fileName = "estimate" + String.valueOf(id) + ".xlsx";
                FileOutputStream fileOutputStream = new FileOutputStream(new File(uploadConfig+ "/estimate/" + fileName));
                workbook.write(fileOutputStream);
                fileOutputStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void excel_backup(String path, Long structure_id) throws IOException {
        StructureDetail structureDetail = estimateDetailRepository.findByStructureId(structure_id);
        List<Calculate> listCal = estimateCalculateRepository.findByStructureIdOrderBySortAsc(structure_id);
        FileInputStream fis = new FileInputStream(uploadConfig + "/estimate/sample/sample.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFFormulaEvaluator xSSFFormulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
        int columnindex = 0;
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        XSSFDataFormat df = workbook.createDataFormat();
        cellStyle.setDataFormat(df.getFormat("#,##0"));
        XSSFCellStyle totalCellStyle = workbook.createCellStyle();
        totalCellStyle.setBorderBottom(BorderStyle.THIN);
        totalCellStyle.setBorderLeft(BorderStyle.THIN);
        totalCellStyle.setBorderTop(BorderStyle.THIN);
        totalCellStyle.setBorderRight(BorderStyle.THIN);
        totalCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        totalCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        totalCellStyle.setDataFormat(df.getFormat("#,##0"));
        XSSFRow xSSFRow = sheet.getRow(0);
        Cell cell = xSSFRow.getCell(0);
        cell.setCellValue("[" + HandlebarsHelper.cityNameKr(structureDetail.getStructure().getCityName()) + "]" + structureDetail.getStructure().getPlaceName());
        cell = xSSFRow.getCell(11);
        CharSequence createDate = HandlebarsHelper.formatDate(structureDetail.getCreateDate(), "yyyy-MM-dd hh:mm");
        cell.setCellValue(createDate.toString());
        xSSFRow = sheet.getRow(4);
        int rowindex = 4;
        int size = listCal.size();
        long piTotal = 0L;
        long pncTotal = 0L;
        long ppTotal = 0L;
        long pTotal = 0L;
        long diTotal = 0L;
        long dncTotal = 0L;
        long dpTotal = 0L;
        long dTotal = 0L;
        int excelRow = 0;
        int etcExcelLast = 0;
        int excelDoorRow = 0;
        List<Calculate> dList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String type = ((Calculate)listCal.get(i)).getType();
            excelRow = rowindex + 1;
            if (type.equals("D")) {
                dList.add(listCal.get(i));
            } else {
                xSSFRow = sheet.createRow(rowindex);
                String name = ((Calculate)listCal.get(i)).getName();
                String unit = ((Calculate)listCal.get(i)).getUnit();
                long total = ((Calculate)listCal.get(i)).getTotal();
                long ttotal = 0L;
                String standard = ((Calculate)listCal.get(i)).getStandard();
                int amount = ((Calculate)listCal.get(i)).getAmount();
                int ePrice = ((Calculate)listCal.get(i)).getEPrice();
                int price = ((Calculate)listCal.get(i)).getUPrice();
                cell = xSSFRow.createCell(0);
                cell.setCellStyle((CellStyle)cellStyle);
                cell.setCellValue(name);
                cell = xSSFRow.createCell(1);
                cell.setCellStyle((CellStyle)cellStyle);
                cell.setCellValue(standard);
                cell = xSSFRow.createCell(2);
                cell.setCellStyle((CellStyle)cellStyle);
                cell.setCellValue(unit);
                cell = xSSFRow.createCell(3);
                cell.setCellStyle((CellStyle)cellStyle);
                cell.setCellValue(amount);
                if (name.equals("기타부재료") || name.equals("장비대") || name.equals("운반비") || name.equals("폐기물")) {
                    cell = xSSFRow.createCell(4);
                    cell.setCellStyle((CellStyle)cellStyle);
                    cell = xSSFRow.createCell(5);
                    cell.setCellStyle((CellStyle)cellStyle);
                    cell = xSSFRow.createCell(8);
                    cell.setCellStyle((CellStyle)cellStyle);
                    cell.setCellValue(price);
                    cell = xSSFRow.createCell(9);
                    cell.setCellStyle((CellStyle)cellStyle);
                    cell.setCellFormula("D" + excelRow + "*I" + excelRow);
                    ppTotal += total;
                } else {
                    cell = xSSFRow.createCell(4);
                    cell.setCellStyle((CellStyle)cellStyle);
                    cell.setCellValue(price);
                    cell = xSSFRow.createCell(5);
                    cell.setCellStyle((CellStyle)cellStyle);
                    cell.setCellFormula("D" + excelRow + "*E" + excelRow);
                    piTotal += total;
                    cell = xSSFRow.createCell(8);
                    cell.setCellStyle((CellStyle)cellStyle);
                    cell = xSSFRow.createCell(9);
                    cell.setCellStyle((CellStyle)cellStyle);
                }
                cell = xSSFRow.createCell(6);
                cell.setCellStyle((CellStyle)cellStyle);
                if (ePrice != 0)
                    cell.setCellValue(ePrice);
                cell = xSSFRow.createCell(7);
                cell.setCellStyle((CellStyle)cellStyle);
                if (ePrice != 0) {
                    cell.setCellValue((ePrice * amount));
                    cell.setCellFormula("D" + excelRow + "*G" + excelRow);
                }
                cell = xSSFRow.createCell(10);
                cell.setCellStyle((CellStyle)cellStyle);
                cell.setCellValue((price + ePrice));
                cell = xSSFRow.createCell(11);
                cell.setCellStyle((CellStyle)cellStyle);
                ttotal = (price + ePrice) * amount;
                cell.setCellFormula("D" + excelRow + "*K" + excelRow);
                pTotal += ttotal;
                cell = xSSFRow.createCell(12);
                cell.setCellStyle((CellStyle)cellStyle);
                rowindex++;
            }
        }
        xSSFRow = sheet.createRow(rowindex);
        cell = xSSFRow.createCell(0);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell.setCellValue("   소   계");
                cell = xSSFRow.createCell(1);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(2);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(3);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(4);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(5);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell.setCellFormula("SUM(F5:F" + rowindex + ")");
        cell = xSSFRow.createCell(6);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(7);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell.setCellFormula("SUM(H5:H" + rowindex + ")");
        cell = xSSFRow.createCell(8);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(9);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell.setCellFormula("SUM(J5:J" + rowindex + ")");
        cell = xSSFRow.createCell(10);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(11);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell.setCellFormula("SUM(L5:L" + rowindex + ")");
        cell = xSSFRow.createCell(12);
        cell.setCellStyle((CellStyle)totalCellStyle);
        etcExcelLast = rowindex + 1;
        rowindex++;
        rowindex++;
        xSSFRow = sheet.createRow(rowindex);
        cell = xSSFRow.createCell(0);
        cell.setCellStyle((CellStyle)cellStyle);
        cell.setCellValue("Ο 창호공사");
                cell = xSSFRow.createCell(1);
        cell.setCellStyle((CellStyle)cellStyle);
        cell = xSSFRow.createCell(2);
        cell.setCellStyle((CellStyle)cellStyle);
        cell = xSSFRow.createCell(3);
        cell.setCellStyle((CellStyle)cellStyle);
        cell = xSSFRow.createCell(4);
        cell.setCellStyle((CellStyle)cellStyle);
        cell = xSSFRow.createCell(5);
        cell.setCellStyle((CellStyle)cellStyle);
        cell = xSSFRow.createCell(6);
        cell.setCellStyle((CellStyle)cellStyle);
        cell = xSSFRow.createCell(7);
        cell.setCellStyle((CellStyle)cellStyle);
        cell = xSSFRow.createCell(8);
        cell.setCellStyle((CellStyle)cellStyle);
        cell = xSSFRow.createCell(9);
        cell.setCellStyle((CellStyle)cellStyle);
        cell = xSSFRow.createCell(10);
        cell.setCellStyle((CellStyle)cellStyle);
        cell = xSSFRow.createCell(11);
        cell.setCellStyle((CellStyle)cellStyle);
        cell = xSSFRow.createCell(12);
        cell.setCellStyle((CellStyle)cellStyle);
        rowindex++;
        int doorExcelStart = 0;
        int doorExcelLast = 0;
        for (int j = 0; j < dList.size(); j++) {
            xSSFRow = sheet.createRow(rowindex);
            excelDoorRow = rowindex + 1;
            if (j == 0)
                doorExcelStart = rowindex + 1;
            String name = ((Calculate)dList.get(j)).getName();
            String unit = ((Calculate)dList.get(j)).getUnit();
            long total = ((Calculate)dList.get(j)).getTotal();
            long ttotal = 0L;
            String standard = ((Calculate)dList.get(j)).getStandard();
            int amount = ((Calculate)dList.get(j)).getAmount();
            int ePrice = ((Calculate)dList.get(j)).getEPrice();
            int price = ((Calculate)dList.get(j)).getUPrice();
            cell = xSSFRow.createCell(0);
            cell.setCellStyle((CellStyle)cellStyle);
            cell.setCellValue(name);
            cell = xSSFRow.createCell(1);
            cell.setCellStyle((CellStyle)cellStyle);
            cell.setCellValue(standard);
            cell = xSSFRow.createCell(2);
            cell.setCellStyle((CellStyle)cellStyle);
            cell.setCellValue(unit);
            cell = xSSFRow.createCell(3);
            cell.setCellStyle((CellStyle)cellStyle);
            cell.setCellValue(amount);
            cell = xSSFRow.createCell(4);
            cell.setCellStyle((CellStyle)cellStyle);
            cell.setCellValue(price);
            cell = xSSFRow.createCell(5);
            cell.setCellStyle((CellStyle)cellStyle);
            cell.setCellFormula("D" + excelDoorRow + "*E" + excelDoorRow);
            diTotal += total;
            cell = xSSFRow.createCell(6);
            cell.setCellStyle((CellStyle)cellStyle);
            if (ePrice != 0)
                cell.setCellValue(ePrice);
            cell = xSSFRow.createCell(7);
            cell.setCellStyle((CellStyle)cellStyle);
            if (ePrice != 0) {
                cell.setCellFormula("D" + excelDoorRow + "*G" + excelDoorRow);
                dncTotal += ePrice * amount;
            }
            cell = xSSFRow.createCell(8);
            cell.setCellStyle((CellStyle)cellStyle);
            cell = xSSFRow.createCell(9);
            cell.setCellStyle((CellStyle)cellStyle);
            cell = xSSFRow.createCell(10);
            cell.setCellStyle((CellStyle)cellStyle);
            cell.setCellValue((price + ePrice));
            cell = xSSFRow.createCell(11);
            cell.setCellStyle((CellStyle)cellStyle);
            ttotal = (price + ePrice) * amount;
            cell.setCellFormula("D" + excelDoorRow + "*K" + excelDoorRow);
            dTotal += ttotal;
            cell = xSSFRow.createCell(12);
            cell.setCellStyle((CellStyle)cellStyle);
            rowindex++;
        }
        xSSFRow = sheet.createRow(rowindex);
        doorExcelLast = rowindex + 1;
        cell = xSSFRow.createCell(0);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell.setCellValue("   소   계");
                cell = xSSFRow.createCell(1);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(2);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(3);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(4);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(5);
        cell.setCellStyle((CellStyle)totalCellStyle);
        if (doorExcelStart != 0)
            cell.setCellFormula("SUM(F" + doorExcelStart + ":F" + rowindex + ")");
        cell = xSSFRow.createCell(6);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(7);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(8);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(9);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(10);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(11);
        cell.setCellStyle((CellStyle)totalCellStyle);
        if (doorExcelStart != 0)
            cell.setCellFormula("SUM(L" + doorExcelStart + ":L" + rowindex + ")");
        cell = xSSFRow.createCell(12);
        cell.setCellStyle((CellStyle)totalCellStyle);
        rowindex++;
        xSSFRow = sheet.createRow(rowindex);
        cell = xSSFRow.createCell(0);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell.setCellValue("   합   계");
                cell = xSSFRow.createCell(1);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(2);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(3);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(4);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(5);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell.setCellValue((piTotal + diTotal));
        cell.setCellFormula("F" + etcExcelLast + "+F" + doorExcelLast);
        cell = xSSFRow.createCell(6);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(7);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell.setCellValue((pncTotal + dncTotal));
        cell.setCellFormula("H" + etcExcelLast + "+H" + doorExcelLast);
        cell = xSSFRow.createCell(8);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(9);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell.setCellFormula("J" + etcExcelLast + "+J" + doorExcelLast);
        cell = xSSFRow.createCell(10);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(11);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell.setCellFormula("L" + etcExcelLast + "+L" + doorExcelLast);
        cell = xSSFRow.createCell(12);
        cell.setCellStyle((CellStyle)totalCellStyle);
        try {
            Long id = Long.valueOf(structureDetail.getStructure().getId());
            if (id != null) {
                String fileName = "estimate" + String.valueOf(id) + ".xlsx";
                FileOutputStream fileOutputStream = new FileOutputStream(new File(uploadConfig + "/estimate/" + fileName));
                workbook.write(fileOutputStream);
                fileOutputStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void excel_old(String path, Long structure_id) throws IOException {
        StructureDetail structureDetail = estimateDetailRepository.findByStructureId(structure_id);
        List<Calculate> listCal = estimateCalculateRepository.findByStructureIdOrderBySortAsc(structure_id);
        FileInputStream fis = new FileInputStream(uploadConfig + "/estimate/sample/sample.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        int columnindex = 0;
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        XSSFDataFormat df = workbook.createDataFormat();
        cellStyle.setDataFormat(df.getFormat("#,##0"));
        XSSFCellStyle totalCellStyle = workbook.createCellStyle();
        totalCellStyle.setBorderBottom(BorderStyle.THIN);
        totalCellStyle.setBorderLeft(BorderStyle.THIN);
        totalCellStyle.setBorderTop(BorderStyle.THIN);
        totalCellStyle.setBorderRight(BorderStyle.THIN);
        totalCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        totalCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        totalCellStyle.setDataFormat(df.getFormat("#,##0"));
        XSSFRow xSSFRow = sheet.getRow(0);
        Cell cell = xSSFRow.getCell(0);
        cell.setCellValue("[" + HandlebarsHelper.cityNameKr(structureDetail.getStructure().getCityName()) + "]" + structureDetail.getStructure().getPlaceName());
        cell = xSSFRow.getCell(11);
        CharSequence createDate = HandlebarsHelper.formatDate(structureDetail.getCreateDate(), "yyyy-MM-dd hh:mm");
        cell.setCellValue(createDate.toString());
        xSSFRow = sheet.getRow(4);
        int rowindex = 4;
        int size = listCal.size();
        long piTotal = 0L;
        long pncTotal = 0L;
        long ppTotal = 0L;
        long pTotal = 0L;
        long diTotal = 0L;
        long dncTotal = 0L;
        long dpTotal = 0L;
        long dTotal = 0L;
        List<Calculate> dList = new ArrayList<>();
        int i;
        for (i = 0; i < size; i++) {
            String type = ((Calculate)listCal.get(i)).getType();
            if (type.equals("D")) {
                dList.add(listCal.get(i));
            } else {
                xSSFRow = sheet.createRow(rowindex);
                String name = ((Calculate)listCal.get(i)).getName();
                String unit = ((Calculate)listCal.get(i)).getUnit();
                long total = ((Calculate)listCal.get(i)).getTotal();
                long ttotal = 0L;
                String standard = ((Calculate)listCal.get(i)).getStandard();
                int amount = ((Calculate)listCal.get(i)).getAmount();
                int ePrice = ((Calculate)listCal.get(i)).getEPrice();
                int price = ((Calculate)listCal.get(i)).getUPrice();
                cell = xSSFRow.createCell(0);
                cell.setCellStyle((CellStyle)cellStyle);
                cell.setCellValue(name);
                cell = xSSFRow.createCell(1);
                cell.setCellStyle((CellStyle)cellStyle);
                cell.setCellValue(standard);
                cell = xSSFRow.createCell(2);
                cell.setCellStyle((CellStyle)cellStyle);
                cell.setCellValue(unit);
                cell = xSSFRow.createCell(3);
                cell.setCellStyle((CellStyle)cellStyle);
                cell.setCellValue(amount);
                if (name.equals("기타부재료") || name.equals("장비대") || name.equals("운반비") || name.equals("폐기물")) {
                    cell = xSSFRow.createCell(4);
                    cell.setCellStyle((CellStyle)cellStyle);
                    cell = xSSFRow.createCell(5);
                    cell.setCellStyle((CellStyle)cellStyle);
                    cell = xSSFRow.createCell(8);
                    cell.setCellStyle((CellStyle)cellStyle);
                    cell.setCellValue(price);
                    cell = xSSFRow.createCell(9);
                    cell.setCellStyle((CellStyle)cellStyle);
                    cell.setCellValue(total);
                    ppTotal += total;
                } else {
                    cell = xSSFRow.createCell(4);
                    cell.setCellStyle((CellStyle)cellStyle);
                    cell.setCellValue(price);
                    cell = xSSFRow.createCell(5);
                    cell.setCellStyle((CellStyle)cellStyle);
                    cell.setCellValue(total);
                    piTotal += total;
                    cell = xSSFRow.createCell(8);
                    cell.setCellStyle((CellStyle)cellStyle);
                    cell = xSSFRow.createCell(9);
                    cell.setCellStyle((CellStyle)cellStyle);
                }
                cell = xSSFRow.createCell(6);
                cell.setCellStyle((CellStyle)cellStyle);
                if (ePrice != 0)
                    cell.setCellValue(ePrice);
                cell = xSSFRow.createCell(7);
                cell.setCellStyle((CellStyle)cellStyle);
                if (ePrice != 0) {
                    cell.setCellValue((ePrice * amount));
                    pncTotal += ePrice * amount;
                }
                cell = xSSFRow.createCell(10);
                cell.setCellStyle((CellStyle)cellStyle);
                cell.setCellValue((price + ePrice));
                cell = xSSFRow.createCell(11);
                cell.setCellStyle((CellStyle)cellStyle);
                ttotal = (price + ePrice) * amount;
                cell.setCellValue(ttotal);
                pTotal += ttotal;
                cell = xSSFRow.createCell(12);
                cell.setCellStyle((CellStyle)cellStyle);
                rowindex++;
            }
        }
        xSSFRow = sheet.createRow(rowindex);
        cell = xSSFRow.createCell(0);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell.setCellValue("   소   계");
                cell = xSSFRow.createCell(1);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(2);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(3);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(4);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(5);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell.setCellValue(piTotal);
        cell = xSSFRow.createCell(6);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(7);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell.setCellValue(pncTotal);
        cell = xSSFRow.createCell(8);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(9);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell.setCellValue(ppTotal);
        cell = xSSFRow.createCell(10);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(11);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell.setCellValue(pTotal);
        cell = xSSFRow.createCell(12);
        cell.setCellStyle((CellStyle)totalCellStyle);
        rowindex++;
        rowindex++;
        xSSFRow = sheet.createRow(rowindex);
        cell = xSSFRow.createCell(0);
        cell.setCellStyle((CellStyle)cellStyle);
        cell.setCellValue("Ο 창호공사");
                cell = xSSFRow.createCell(1);
        cell.setCellStyle((CellStyle)cellStyle);
        cell = xSSFRow.createCell(2);
        cell.setCellStyle((CellStyle)cellStyle);
        cell = xSSFRow.createCell(3);
        cell.setCellStyle((CellStyle)cellStyle);
        cell = xSSFRow.createCell(4);
        cell.setCellStyle((CellStyle)cellStyle);
        cell = xSSFRow.createCell(5);
        cell.setCellStyle((CellStyle)cellStyle);
        cell = xSSFRow.createCell(6);
        cell.setCellStyle((CellStyle)cellStyle);
        cell = xSSFRow.createCell(7);
        cell.setCellStyle((CellStyle)cellStyle);
        cell = xSSFRow.createCell(8);
        cell.setCellStyle((CellStyle)cellStyle);
        cell = xSSFRow.createCell(9);
        cell.setCellStyle((CellStyle)cellStyle);
        cell = xSSFRow.createCell(10);
        cell.setCellStyle((CellStyle)cellStyle);
        cell = xSSFRow.createCell(11);
        cell.setCellStyle((CellStyle)cellStyle);
        cell = xSSFRow.createCell(12);
        cell.setCellStyle((CellStyle)cellStyle);
        rowindex++;
        for (i = 0; i < dList.size(); i++) {
            xSSFRow = sheet.createRow(rowindex);
            String name = ((Calculate)dList.get(i)).getName();
            String unit = ((Calculate)dList.get(i)).getUnit();
            long total = ((Calculate)dList.get(i)).getTotal();
            long ttotal = 0L;
            String standard = ((Calculate)dList.get(i)).getStandard();
            int amount = ((Calculate)dList.get(i)).getAmount();
            int ePrice = ((Calculate)dList.get(i)).getEPrice();
            int price = ((Calculate)dList.get(i)).getUPrice();
            cell = xSSFRow.createCell(0);
            cell.setCellStyle((CellStyle)cellStyle);
            cell.setCellValue(name);
            cell = xSSFRow.createCell(1);
            cell.setCellStyle((CellStyle)cellStyle);
            cell.setCellValue(standard);
            cell = xSSFRow.createCell(2);
            cell.setCellStyle((CellStyle)cellStyle);
            cell.setCellValue(unit);
            cell = xSSFRow.createCell(3);
            cell.setCellStyle((CellStyle)cellStyle);
            cell.setCellValue(amount);
            cell = xSSFRow.createCell(4);
            cell.setCellStyle((CellStyle)cellStyle);
            cell.setCellValue(price);
            cell = xSSFRow.createCell(5);
            cell.setCellStyle((CellStyle)cellStyle);
            cell.setCellValue(total);
            diTotal += total;
            cell = xSSFRow.createCell(6);
            cell.setCellStyle((CellStyle)cellStyle);
            if (ePrice != 0)
                cell.setCellValue(ePrice);
            cell = xSSFRow.createCell(7);
            cell.setCellStyle((CellStyle)cellStyle);
            if (ePrice != 0) {
                cell.setCellValue((ePrice * amount));
                dncTotal += ePrice * amount;
            }
            cell = xSSFRow.createCell(8);
            cell.setCellStyle((CellStyle)cellStyle);
            cell = xSSFRow.createCell(9);
            cell.setCellStyle((CellStyle)cellStyle);
            cell = xSSFRow.createCell(10);
            cell.setCellStyle((CellStyle)cellStyle);
            cell.setCellValue((price + ePrice));
            cell = xSSFRow.createCell(11);
            cell.setCellStyle((CellStyle)cellStyle);
            ttotal = (price + ePrice) * amount;
            cell.setCellValue(ttotal);
            dTotal += ttotal;
            cell = xSSFRow.createCell(12);
            cell.setCellStyle((CellStyle)cellStyle);
            rowindex++;
        }
        xSSFRow = sheet.createRow(rowindex);
        cell = xSSFRow.createCell(0);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell.setCellValue("   소   계");
                cell = xSSFRow.createCell(1);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(2);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(3);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(4);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(5);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell.setCellValue(diTotal);
        cell = xSSFRow.createCell(6);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(7);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(8);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(9);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(10);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(11);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell.setCellValue(dTotal);
        cell = xSSFRow.createCell(12);
        cell.setCellStyle((CellStyle)totalCellStyle);
        rowindex++;
        xSSFRow = sheet.createRow(rowindex);
        cell = xSSFRow.createCell(0);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell.setCellValue("   합   계");
                cell = xSSFRow.createCell(1);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(2);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(3);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(4);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(5);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell.setCellValue((piTotal + diTotal));
        cell = xSSFRow.createCell(6);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(7);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell.setCellValue((pncTotal + dncTotal));
        cell = xSSFRow.createCell(8);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(9);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(10);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell = xSSFRow.createCell(11);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell.setCellValue((pTotal + dTotal));
        cell = xSSFRow.createCell(12);
        cell.setCellStyle((CellStyle)totalCellStyle);
        try {
            Long id = Long.valueOf(structureDetail.getStructure().getId());
            if (id != null) {
                String fileName = "estimate" + String.valueOf(id) + ".xlsx";
                FileOutputStream fileOutputStream = new FileOutputStream(new File(uploadConfig + "/estimate/" + fileName));
                workbook.write(fileOutputStream);
                fileOutputStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void excelCopy(Long oldId, Long newId) {
        File orgFile = new File(uploadConfig + "/estimate/estimate" + oldId + ".xlsx");
        try {
            FileInputStream inputStream = new FileInputStream(orgFile);
            FileOutputStream outputStream = new FileOutputStream(uploadConfig + "/estimate/estimate" + newId + ".xlsx");
            FileChannel fcin = inputStream.getChannel();
            FileChannel fcout = outputStream.getChannel();
            long size = fcin.size();
            fcin.transferTo(0L, size, fcout);
            fcout.close();
            fcin.close();
            outputStream.close();
            inputStream.close();
        } catch (Exception exception) {}
    }
}
