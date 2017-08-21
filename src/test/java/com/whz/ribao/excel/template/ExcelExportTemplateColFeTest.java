package com.whz.ribao.excel.template;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.afterturn.easypoi.util.PoiCssUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Before;
import org.junit.Test;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.util.PoiMergeCellUtil;

public class ExcelExportTemplateColFeTest {

	Map<String, Object> value = new HashMap<String, Object>();

	@Test
	public void path(){
		System.out.println(new File("static/doc/ExcelExportTemplateColFeTest_one1.xlsx").getAbsolutePath());
	}

	@Test
	public void one() throws Exception {
		TemplateExportParams params = new TemplateExportParams(
		    "/static/doc/for_Col1.xlsx", true);
		params.setColForEach(true);
		Workbook book = ExcelExportUtil.exportExcel(params, value);

		//my
        Sheet sheetAt = book.getSheetAt(0);
        sheetAt.addMergedRegion(new CellRangeAddress(0,0,0,1));

        CellStyle style = book.createCellStyle();
        //背景色
        style.setFillForegroundColor(HSSFColor.AQUA.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //居中
        style.setAlignment(HorizontalAlignment.CENTER);
        //加粗
        Font font = book.createFont();
        font.setBold(true);
        style.setFont(font);
        //边框
        style.setBorderBottom(BorderStyle.THIN); //下边框
        style.setBorderLeft(BorderStyle.THIN);//左边框
        style.setBorderTop(BorderStyle.THIN);//上边框
        style.setBorderRight(BorderStyle.THIN);//右边框

        sheetAt.getRow(0).getCell(0).setCellStyle(style);

		PoiMergeCellUtil.mergeCells(book.getSheetAt(0), 0, 0,1);
		FileOutputStream fos = new FileOutputStream("d:/doc/ExcelExportTemplateColFeTest_one1.xlsx");
		book.write(fos);
		fos.close();

	}

	@Before
	public void testBefore() {
		Map<String, Object> map = new HashMap<String, Object>();

		List<Map<String, Object>> valList = new ArrayList<Map<String, Object>>();
		map = new HashMap<String, Object>();
		map.put("one", "2017-01-01");
		map.put("two", "");
		valList.add(map);
		map = new HashMap<String, Object>();
		map.put("one", "王慧佐");
		map.put("two", "100%");
		valList.add(map);
		map = new HashMap<String, Object>();
		map.put("one", "王慧佐");
		map.put("two", "80%");
		valList.add(map);

        map = new HashMap<String, Object>();
        map.put("one", "");
        map.put("two", "");
        valList.add(map);
        map = new HashMap<String, Object>();
        map.put("one", "张驰");
        map.put("two", "50%");
        valList.add(map);
        map = new HashMap<String, Object>();
        map.put("one", "张驰");
        map.put("two", "100%");
        valList.add(map);


		value.put("valList", valList);
	}

}
