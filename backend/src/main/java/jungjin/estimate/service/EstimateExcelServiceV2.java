package jungjin.estimate.service;

import jakarta.persistence.EntityNotFoundException;
import jungjin.common.exception.NotFoundException;
import jungjin.config.UploadConfig;
import jungjin.estimate.domain.Calculate;
import jungjin.estimate.domain.Structure;
import jungjin.estimate.domain.StructureDetail;
import jungjin.estimate.repository.EstimateRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EstimateExcelServiceV2 {
    private final UploadConfig uploadConfig;
    private final EstimateRepository estimateRepository;

    public File generateExcelAndReturnFile(Long id) throws IOException {
        createExcel(id);
        File file = new File(uploadConfig.getUploadDir() + "/estimate/estimate" + id + ".xlsx");
        if (!file.exists()) throw new NotFoundException("Excel not found");
        return file;
    }

    public void createExcel(Long structureId) throws IOException {
        Structure structure = estimateRepository.findById(structureId)
                .orElseThrow(() -> new EntityNotFoundException("structure not found"));
        StructureDetail detail = structure.getStructureDetail();
        List<Calculate> calculations = structure.getCalculateList();

        try (FileInputStream fis = new FileInputStream(uploadConfig.getUploadDir() + "/estimate/sample/sample.xlsx");
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowindex = 4;

            // Font and Style Setup
            XSSFFont smallFont = workbook.createFont();
            smallFont.setFontHeightInPoints((short) 9);

            XSSFFont normalFont = workbook.createFont();
            normalFont.setFontHeightInPoints((short) 10);

            XSSFDataFormat df = workbook.createDataFormat();

            XSSFCellStyle cellStyle = createStyle(workbook, smallFont, df, false);
            XSSFCellStyle totalCellStyle = createStyle(workbook, smallFont, df, true);
            XSSFCellStyle lastNoteStyle = createLastNoteStyle(workbook, normalFont);

            // Header Row
            XSSFRow headerRow = sheet.getRow(0);
            headerRow.getCell(0).setCellValue("[" + detail.getStructure().getCityName().getName() + "]" + detail.getStructure().getPlaceName());
            headerRow.getCell(11).setCellValue(detail.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

            // Body Rows
            List<Calculate> doors = new ArrayList<>();
            long piTotal = 0, ppTotal = 0, pTotal = 0, diTotal = 0, dncTotal = 0, dTotal = 0;

            for (Calculate calc : calculations) {
                int excelRow = rowindex + 1;
                if ("D".equals(calc.getType())) {
                    doors.add(calc);
                    continue;
                }
                rowindex = writeCalcRow(sheet, rowindex, calc, cellStyle, excelRow, true);
                piTotal += calc.getTotal();
                pTotal += (long) (calc.getUPrice() + calc.getEPrice()) * calc.getAmount();
            }

            int etcSubtotalRow = writeSubtotal(sheet, rowindex++, totalCellStyle, 5);
            rowindex++;

            // Door Section Title
            writeSectionTitle(sheet, rowindex++, "Ο 창호공사", cellStyle);
            int doorStart = rowindex;
            for (Calculate door : doors) {
                int excelRow = rowindex + 1;
                rowindex = writeCalcRow(sheet, rowindex, door, cellStyle, excelRow, false);
                diTotal += door.getTotal();
                dncTotal += (long) door.getEPrice() * door.getAmount();
                dTotal += (long) (door.getUPrice() + door.getEPrice()) * door.getAmount();
            }

            int doorSubtotalRow = writeSubtotal(sheet, rowindex++, totalCellStyle, doorStart);

            // Grand Total Row
            writeGrandTotal(sheet, rowindex++, totalCellStyle, etcSubtotalRow, doorSubtotalRow);

            // Note Section
            createNoteRow(sheet, rowindex + 2, lastNoteStyle);

            // Save File
            String fileName = "estimate" + structureId + ".xlsx";
            try (FileOutputStream out = new FileOutputStream(new File(uploadConfig.getUploadDir() + "/estimate/" + fileName))) {
                workbook.write(out);
            }
        }
    }

    private XSSFCellStyle createStyle(XSSFWorkbook workbook, XSSFFont font, XSSFDataFormat df, boolean shaded) {
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setDataFormat(df.getFormat("#,##0"));
        if (shaded) {
            style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }
        return style;
    }

    private XSSFCellStyle createLastNoteStyle(XSSFWorkbook workbook, XSSFFont font) {
        XSSFCellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        style.setFont(font);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    private int writeCalcRow(XSSFSheet sheet, int rowIdx, Calculate calc, XSSFCellStyle style, int excelRow, boolean applyEPrice) {
        XSSFRow row = sheet.createRow(rowIdx);
        row.setHeight((short) 360);

        String name = calc.getName();
        int amount = calc.getAmount();
        int uPrice = calc.getUPrice();
        int ePrice = calc.getEPrice();

        boolean isSpecial = name.equals("기타부재료") || name.equals("장비대") || name.equals("운반비") || name.equals("폐기물");

        int col = 0;
        row.createCell(col++).setCellValue(name);
        row.createCell(col++).setCellValue(calc.getStandard());
        row.createCell(col++).setCellValue(calc.getUnit());
        row.createCell(col++).setCellValue(amount);

        // UPrice + Total (기타/장비/운반/폐기물)
        if (isSpecial) {
            row.createCell(4);
            row.createCell(5);
            row.createCell(6);
            row.createCell(7);
            row.createCell(8).setCellValue(uPrice);
            row.createCell(9).setCellFormula("D" + excelRow + "*I" + excelRow);
        } else {
            row.createCell(4).setCellValue(uPrice);
            row.createCell(5).setCellFormula("D" + excelRow + "*E" + excelRow);
            if (ePrice != 0) {
                row.createCell(6).setCellValue(ePrice);
                row.createCell(7).setCellFormula("D" + excelRow + "*G" + excelRow);
            } else {
                row.createCell(6);
                row.createCell(7);
            }
            row.createCell(8);
            row.createCell(9);
        }

        row.createCell(10).setCellValue(uPrice + ePrice);
        row.createCell(11).setCellFormula("D" + excelRow + "*K" + excelRow);
        row.createCell(12);

        // Apply styles
        for (int i = 0; i <= 12; i++) {
            if (row.getCell(i) == null) row.createCell(i);
            row.getCell(i).setCellStyle(style);
        }

        return rowIdx + 1;
    }
    private int writeSubtotal(XSSFSheet sheet, int rowIdx, XSSFCellStyle style, int startRow) {
        XSSFRow row = sheet.createRow(rowIdx);
        row.setHeight((short) 360);

        // Title cell: no style applied
        XSSFCell titleCell = row.createCell(0);
        titleCell.setCellValue("   소   계");
        titleCell.setCellStyle(style);

        for (int i = 1; i <= 12; i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);

            if (i == 5 || i == 7 || i == 9 || i == 11) { // columns F, H, J, L
                char colChar = (char) ('A' + i);
                cell.setCellFormula("SUM(" + colChar + startRow + ":" + colChar + rowIdx + ")");
            }
        }

        return rowIdx;
    }

    private void writeSectionTitle(XSSFSheet sheet, int rowIdx, String title, XSSFCellStyle style) {
        XSSFRow row = sheet.createRow(rowIdx);
        row.createCell(0).setCellValue(title);
        for (int i = 0; i <= 12; i++) {
            XSSFCell cell = row.getCell(i);
            if (cell == null) {
                cell = row.createCell(i);
            }
            cell.setCellStyle(style);
        }
    }

    private void writeGrandTotal(XSSFSheet sheet, int rowIdx, XSSFCellStyle style, int etcRow, int doorRow) {
        XSSFRow row = sheet.createRow(rowIdx);

        XSSFCell titleCell = row.createCell(0);
        titleCell.setCellValue("   합   계");
        titleCell.setCellStyle(style);

        for (int i = 1; i <= 12; i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);

            // Sum only specific columns: F, H, J, L (index 5, 7, 9, 11)
            if (i == 5 || i == 7 || i == 9 || i == 11) {
                char colChar = (char) ('A' + i);
                // No +1 here; etcRow and doorRow already point to the subtotal rows
                cell.setCellFormula(colChar + String.valueOf(etcRow + 1) + "+" + colChar + String.valueOf(doorRow + 1));
            }
        }
    }

    private void createNoteRow(XSSFSheet sheet, int rowIndex, XSSFCellStyle style) {
        XSSFRow row = sheet.createRow(rowIndex);
        XSSFCell cell = row.createCell(0);
        cell.setCellStyle(style);
        cell.setCellValue("  자재 물량에는 시공 시 발생하는 로스 물량이 포함되어 있습니다.\n" +
                "  물량산출은 횡시공 기준으로 산출하였습니다. 종시공일경우 물량차이가 다소 발생하실 수 있습니다.\n" +
                "  판넬의 코일두께 및 단열재 밀도, 난연성능의 구분 등은 직접 입력 하셔야 합니다.\n" +
                "  모든 단가는 직접 입력하셔서 사용하시기 바랍니다.\n" +
                "  판넬발주 또는 기타문의사항이 있으시면 연락주시기 바랍니다.\n\n" +
                "  M2패널 담당자 연락처 010-5835-5030 / 이메일: jeffbang@hanmail.net \n ");
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 6, 0, 12));
    }
}
