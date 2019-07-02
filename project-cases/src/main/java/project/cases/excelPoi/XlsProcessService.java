package project.cases.excelPoi;

import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.util.Iterator;

/**
 *  ExcelTest业务类(child)
 */

@Service
public class XlsProcessService {

    public HSSFWorkbook tableToxls(JSONObject data) {
        XlsProcesserContext context = new XlsProcesserContext();  // XlsProcesserContext.java
        HSSFWorkbook wb = new HSSFWorkbook();
        setColorIndex(wb);
        CellStyle titleStyle = createTitleStyle(wb);
        CellStyle thStyle = createThStyle(wb);
        CellStyle tStyle = createTStyle(wb);
        CellStyle percentStyle = wb.createCellStyle();
        percentStyle.cloneStyleFrom(tStyle);
        percentStyle.setDataFormat((short) 0xa);
        context.setWb(wb);
        context.setTableStyle(thStyle);
        context.setTitleStyle(titleStyle);
        context.settStyle(tStyle);
        context.setPercentStyle(percentStyle);
        Sheet sheet = context.getWb().createSheet();
        context.setBoardSheet(sheet);
        context.setC1(0);
        context.setC2(data.getJSONArray("data").getJSONArray(0).size() - 1);
        context.setR1(0);
        context.setR2(0);
        context.setData(data);
        new TableXlsProcesser().drawContent(context);  // TableXlsProcesser.java
        setAutoWidth(sheet);
        return wb;
    }


    private void setAutoWidth(Sheet dataSheet) {
        int max = 0;
        Iterator<Row> i = dataSheet.rowIterator();
        while (i.hasNext()) {
            if (i.next().getLastCellNum() > max) {
                max = i.next().getLastCellNum();
            }
        }
        for (int colNum = 0; colNum < max; colNum++) {
            dataSheet.autoSizeColumn(colNum, true);
        }
    }


    private CellStyle createTitleStyle(HSSFWorkbook wb) {
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 16);
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setFontName("微软雅黑");
        CellStyle titleStyle = wb.createCellStyle();
        titleStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        //titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setFont(font);
        //titleStyle.setAlignment(HorizontalAlignment.CENTER);
        //titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return titleStyle;
    }

    private CellStyle createThStyle(HSSFWorkbook wb) {
        CellStyle thStyle = wb.createCellStyle();
        thStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        //  thStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // thStyle.setBorderBottom(BorderStyle.THIN);
        thStyle.setBottomBorderColor(IndexedColors.BLUE_GREY.getIndex());
        // thStyle.setBorderLeft(BorderStyle.THIN);

        thStyle.setLeftBorderColor(IndexedColors.BLUE_GREY.getIndex());
        // thStyle.setBorderRight(BorderStyle.THIN);
        thStyle.setRightBorderColor(IndexedColors.BLUE_GREY.getIndex());
        // thStyle.setBorderTop(BorderStyle.THIN);
        thStyle.setTopBorderColor(IndexedColors.BLUE_GREY.getIndex());
        // thStyle.setAlignment(HorizontalAlignment.CENTER);
        //thStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //thStyle.setShrinkToFit(true);
        Font font = wb.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        thStyle.setFont(font);
        return thStyle;
    }

    private CellStyle createTStyle(HSSFWorkbook wb) {
        CellStyle tStyle = wb.createCellStyle();
        //  tStyle.setBorderBottom(BorderStyle.THIN);
        tStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        //  tStyle.setBorderLeft(BorderStyle.THIN);
        tStyle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        // tStyle.setBorderRight(BorderStyle.THIN);
        tStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        // tStyle.setBorderTop(BorderStyle.THIN);
        tStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        //tStyle.setAlignment(HorizontalAlignment.CENTER);
        // tStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // tStyle.setShrinkToFit(true);
        return tStyle;
    }

    private void setColorIndex(HSSFWorkbook wb) {
        HSSFPalette customPalette = wb.getCustomPalette();
        customPalette.setColorAtIndex(IndexedColors.BLUE.index, (byte) 26, (byte) 127, (byte) 205);
        customPalette.setColorAtIndex(IndexedColors.BLUE_GREY.index, (byte) 56, (byte) 119, (byte) 166);
        customPalette.setColorAtIndex(IndexedColors.GREY_25_PERCENT.index, (byte) 235, (byte) 235, (byte) 235);
    }
}
