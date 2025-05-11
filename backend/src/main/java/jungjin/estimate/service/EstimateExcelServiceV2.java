package jungjin.estimate.service;

import jakarta.persistence.EntityNotFoundException;
import jungjin.HandlebarsHelper;
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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EstimateExcelServiceV2 {
    private final UploadConfig uploadConfig;
    private final EstimateRepository estimateRepository;

    public void excel(Long structure_id) throws IOException {
        Structure structure = estimateRepository.findById(structure_id)
                .orElseThrow(() -> new EntityNotFoundException("structure not found"));
        StructureDetail structureDetail = structure.getStructureDetail();
        List<Calculate> listCal = structure.getCalculateList();
        FileInputStream fis = new FileInputStream(uploadConfig.getUploadDir() + "/estimate/sample/sample.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        //XSSFFormulaEvaluator xSSFFormulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
        //int columnindex = 0;
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
        cellStyle.setFont(xSSFFont1);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFCellStyle totalCellStyle = workbook.createCellStyle();
        totalCellStyle.setBorderBottom(BorderStyle.THIN);
        totalCellStyle.setBorderLeft(BorderStyle.THIN);
        totalCellStyle.setBorderTop(BorderStyle.THIN);
        totalCellStyle.setBorderRight(BorderStyle.THIN);
        totalCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        totalCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        totalCellStyle.setDataFormat(df.getFormat("#,##0"));
        totalCellStyle.setFont(xSSFFont1);
        totalCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFCellStyle lastStyle = workbook.createCellStyle();
        lastStyle.setWrapText(true);
        lastStyle.setFont(xSSFFont2);
        lastStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFRow xSSFRow = sheet.getRow(0);
        Cell cell = xSSFRow.getCell(0);
        cell.setCellValue("[" + structureDetail.getStructure().getCityName().getName() + "]" + structureDetail.getStructure().getPlaceName());
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
        for (Calculate calculate : listCal) {
            String type = calculate.getType();
            excelRow = rowindex + 1;
            if (type.equals("D")) {
                dList.add(calculate);
            } else {
                xSSFRow = sheet.createRow(rowindex);
                String name = calculate.getName();
                String unit = calculate.getUnit();
                long total = calculate.getTotal();
                long ttotal = 0L;
                String standard = calculate.getStandard();
                int amount = calculate.getAmount();
                int ePrice = calculate.getEPrice();
                int price = calculate.getUPrice();
                cell = xSSFRow.createCell(0);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(name);
                cell = xSSFRow.createCell(1);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(standard);
                cell = xSSFRow.createCell(2);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(unit);
                cell = xSSFRow.createCell(3);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(amount);
                if (name.equals("기타부재료") || name.equals("장비대") || name.equals("운반비") || name.equals("폐기물")) {
                    cell = xSSFRow.createCell(4);
                    cell.setCellStyle( cellStyle);
                    cell = xSSFRow.createCell(5);
                    cell.setCellStyle(cellStyle);
                    cell = xSSFRow.createCell(8);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(price);
                    cell = xSSFRow.createCell(9);
                    cell.setCellStyle(cellStyle);
                    cell.setCellFormula("D" + excelRow + "*I" + excelRow);
                    ppTotal += total;
                } else {
                    cell = xSSFRow.createCell(4);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(price);
                    cell = xSSFRow.createCell(5);
                    cell.setCellStyle(cellStyle);
                    cell.setCellFormula("D" + excelRow + "*E" + excelRow);
                    piTotal += total;
                    cell = xSSFRow.createCell(8);
                    cell.setCellStyle(cellStyle);
                    cell = xSSFRow.createCell(9);
                    cell.setCellStyle(cellStyle);
                }
                cell = xSSFRow.createCell(6);
                cell.setCellStyle(cellStyle);
                if (ePrice != 0)
                    cell.setCellValue(ePrice);
                cell = xSSFRow.createCell(7);
                cell.setCellStyle(cellStyle);
                if (ePrice != 0) {
                    cell.setCellValue((ePrice * amount));
                    cell.setCellFormula("D" + excelRow + "*G" + excelRow);
                }
                cell = xSSFRow.createCell(10);
                cell.setCellStyle(cellStyle);
                cell.setCellValue((price + ePrice));
                cell = xSSFRow.createCell(11);
                cell.setCellStyle(cellStyle);
                ttotal = (long) (price + ePrice) * amount;
                cell.setCellFormula("D" + excelRow + "*K" + excelRow);
                pTotal += ttotal;
                cell = xSSFRow.createCell(12);
                cell.setCellStyle(cellStyle);
                rowindex++;
            }
        }
        xSSFRow = sheet.createRow(rowindex);
        cell = xSSFRow.createCell(0);
        cell.setCellStyle((CellStyle)totalCellStyle);
        cell.setCellValue("   소   계");
        cell = xSSFRow.createCell(1);
        cell.setCellStyle(totalCellStyle);
        cell = xSSFRow.createCell(2);
        cell.setCellStyle(totalCellStyle);
        cell = xSSFRow.createCell(3);
        cell.setCellStyle(totalCellStyle);
        cell = xSSFRow.createCell(4);
        cell.setCellStyle(totalCellStyle);
        cell = xSSFRow.createCell(5);
        cell.setCellStyle(totalCellStyle);
        cell.setCellFormula("SUM(F5:F" + rowindex + ")");
        cell = xSSFRow.createCell(6);
        cell.setCellStyle(totalCellStyle);
        cell = xSSFRow.createCell(7);
        cell.setCellStyle(totalCellStyle);
        cell.setCellFormula("SUM(H5:H" + rowindex + ")");
        cell = xSSFRow.createCell(8);
        cell.setCellStyle(totalCellStyle);
        cell = xSSFRow.createCell(9);
        cell.setCellStyle(totalCellStyle);
        cell.setCellFormula("SUM(J5:J" + rowindex + ")");
        cell = xSSFRow.createCell(10);
        cell.setCellStyle(totalCellStyle);
        cell = xSSFRow.createCell(11);
        cell.setCellStyle(totalCellStyle);
        cell.setCellFormula("SUM(L5:L" + rowindex + ")");
        cell = xSSFRow.createCell(12);
        cell.setCellStyle(totalCellStyle);
        etcExcelLast = rowindex + 1;
        rowindex++;
        rowindex++;
        xSSFRow = sheet.createRow(rowindex);
        cell = xSSFRow.createCell(0);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Ο 창호공사");
        cell = xSSFRow.createCell(1);
        cell.setCellStyle(cellStyle);
        cell = xSSFRow.createCell(2);
        cell.setCellStyle(cellStyle);
        cell = xSSFRow.createCell(3);
        cell.setCellStyle(cellStyle);
        cell = xSSFRow.createCell(4);
        cell.setCellStyle(cellStyle);
        cell = xSSFRow.createCell(5);
        cell.setCellStyle(cellStyle);
        cell = xSSFRow.createCell(6);
        cell.setCellStyle(cellStyle);
        cell = xSSFRow.createCell(7);
        cell.setCellStyle(cellStyle);
        cell = xSSFRow.createCell(8);
        cell.setCellStyle(cellStyle);
        cell = xSSFRow.createCell(9);
        cell.setCellStyle(cellStyle);
        cell = xSSFRow.createCell(10);
        cell.setCellStyle(cellStyle);
        cell = xSSFRow.createCell(11);
        cell.setCellStyle(cellStyle);
        cell = xSSFRow.createCell(12);
        cell.setCellStyle(cellStyle);
        rowindex++;
        int doorExcelStart = 0;
        int doorExcelLast = 0;
        for (int j = 0; j < dList.size(); j++) {
            xSSFRow = sheet.createRow(rowindex);
            excelDoorRow = rowindex + 1;
            if (j == 0)
                doorExcelStart = rowindex + 1;
            String name = (dList.get(j)).getName();
            String unit = (dList.get(j)).getUnit();
            long total = (dList.get(j)).getTotal();
            long ttotal = 0L;
            String standard = (dList.get(j)).getStandard();
            int amount = (dList.get(j)).getAmount();
            int ePrice = (dList.get(j)).getEPrice();
            int price = (dList.get(j)).getUPrice();
            cell = xSSFRow.createCell(0);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(name);
            cell = xSSFRow.createCell(1);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(standard);
            cell = xSSFRow.createCell(2);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(unit);
            cell = xSSFRow.createCell(3);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(amount);
            cell = xSSFRow.createCell(4);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(price);
            cell = xSSFRow.createCell(5);
            cell.setCellStyle(cellStyle);
            cell.setCellFormula("D" + excelDoorRow + "*E" + excelDoorRow);
            diTotal += total;
            cell = xSSFRow.createCell(6);
            cell.setCellStyle(cellStyle);
            if (ePrice != 0)
                cell.setCellValue(ePrice);
            cell = xSSFRow.createCell(7);
            cell.setCellStyle(cellStyle);
            if (ePrice != 0) {
                cell.setCellFormula("D" + excelDoorRow + "*G" + excelDoorRow);
                dncTotal += (long) ePrice * amount;
            }
            cell = xSSFRow.createCell(8);
            cell.setCellStyle(cellStyle);
            cell = xSSFRow.createCell(9);
            cell.setCellStyle(cellStyle);
            cell = xSSFRow.createCell(10);
            cell.setCellStyle(cellStyle);
            cell.setCellValue((price + ePrice));
            cell = xSSFRow.createCell(11);
            cell.setCellStyle(cellStyle);
            ttotal = (long) (price + ePrice) * amount;
            cell.setCellFormula("D" + excelDoorRow + "*K" + excelDoorRow);
            dTotal += ttotal;
            cell = xSSFRow.createCell(12);
            cell.setCellStyle(cellStyle);
            rowindex++;
        }
        xSSFRow = sheet.createRow(rowindex);
        doorExcelLast = rowindex + 1;
        cell = xSSFRow.createCell(0);
        cell.setCellStyle(totalCellStyle);
        cell.setCellValue("   소   계");
        cell = xSSFRow.createCell(1);
        cell.setCellStyle(totalCellStyle);
        cell = xSSFRow.createCell(2);
        cell.setCellStyle(totalCellStyle);
        cell = xSSFRow.createCell(3);
        cell.setCellStyle(totalCellStyle);
        cell = xSSFRow.createCell(4);
        cell.setCellStyle(totalCellStyle);
        cell = xSSFRow.createCell(5);
        cell.setCellStyle(totalCellStyle);
        if (doorExcelStart != 0)
            cell.setCellFormula("SUM(F" + doorExcelStart + ":F" + rowindex + ")");
        cell = xSSFRow.createCell(6);
        cell.setCellStyle(totalCellStyle);
        cell = xSSFRow.createCell(7);
        cell.setCellStyle(totalCellStyle);
        cell = xSSFRow.createCell(8);
        cell.setCellStyle(totalCellStyle);
        cell = xSSFRow.createCell(9);
        cell.setCellStyle(totalCellStyle);
        cell = xSSFRow.createCell(10);
        cell.setCellStyle(totalCellStyle);
        cell = xSSFRow.createCell(11);
        cell.setCellStyle(totalCellStyle);
        if (doorExcelStart != 0)
            cell.setCellFormula("SUM(L" + doorExcelStart + ":L" + rowindex + ")");
        cell = xSSFRow.createCell(12);
        cell.setCellStyle(totalCellStyle);
        rowindex++;
        xSSFRow = sheet.createRow(rowindex);
        cell = xSSFRow.createCell(0);
        cell.setCellStyle(totalCellStyle);
        cell.setCellValue("   합   계");
        cell = xSSFRow.createCell(1);
        cell.setCellStyle(totalCellStyle);
        cell = xSSFRow.createCell(2);
        cell.setCellStyle(totalCellStyle);
        cell = xSSFRow.createCell(3);
        cell.setCellStyle(totalCellStyle);
        cell = xSSFRow.createCell(4);
        cell.setCellStyle(totalCellStyle);
        cell = xSSFRow.createCell(5);
        cell.setCellStyle(totalCellStyle);
        cell.setCellValue((piTotal + diTotal));
        cell.setCellFormula("F" + etcExcelLast + "+F" + doorExcelLast);
        cell = xSSFRow.createCell(6);
        cell.setCellStyle(totalCellStyle);
        cell = xSSFRow.createCell(7);
        cell.setCellStyle(totalCellStyle);
        cell.setCellValue((pncTotal + dncTotal));
        cell.setCellFormula("H" + etcExcelLast + "+H" + doorExcelLast);
        cell = xSSFRow.createCell(8);
        cell.setCellStyle(totalCellStyle);
        cell = xSSFRow.createCell(9);
        cell.setCellStyle(totalCellStyle);
        cell.setCellFormula("J" + etcExcelLast + "+J" + doorExcelLast);
        cell = xSSFRow.createCell(10);
        cell.setCellStyle(totalCellStyle);
        cell = xSSFRow.createCell(11);
        cell.setCellStyle(totalCellStyle);
        cell.setCellFormula("L" + etcExcelLast + "+L" + doorExcelLast);
        cell = xSSFRow.createCell(12);
        cell.setCellStyle(totalCellStyle);

        createNoteRow(sheet, rowindex + 2, lastStyle);
        try {
            String fileName = "estimate" + String.valueOf(structure_id) + ".xlsx";
            FileOutputStream fileOutputStream = new FileOutputStream(new File(uploadConfig.getUploadDir() + "/estimate/" + fileName));
            workbook.write(fileOutputStream);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
