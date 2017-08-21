package com.whz.ribao.utils.poi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.whz.ribao.entity.BaseBao;
import com.whz.ribao.utils.DateUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by m1897 on 2017/8/14.
 */
public class PoiRibaoTemplateExportUtil {

    private static final Map<String, Object> smallHead = new HashedMap();

    /** 默认间隔3行 */
    private static final int BLANK_SPACE_NUM = 3;

    /** 一共5列 */
    private static final int COL_NUM = 5;

    /** 第几行开始循环 */
    private static final int START_ROW = 3;

    static {
        smallHead.put("a","开发内容");
        smallHead.put("b","负责人");
        smallHead.put("c","预计完成比例");
        smallHead.put("d","结果 100%");
        smallHead.put("e","备注");
    }


    /**
     *
     * @param baseBaos key:date value:rows
     * @return
     */
    public static Workbook exportExcel(Map<String, List<BaseBao>> baseBaos) throws IOException {
        //代表一行
        Map<String, Object> map;
        //代表所有列
        List<Map<String, Object>> valList = new ArrayList<>();

        List<Integer> headIndexList = new ArrayList<>();
        List<Integer> smallHeadIndexList = new ArrayList<>();
        List<Integer> contextIndexList = new ArrayList<>();

        List<Map.Entry<String, List<BaseBao>>> sortBaseBaos = baseBaos.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).collect(Collectors.toList());

        for (Map.Entry<String,List<BaseBao>> baseBao: sortBaseBaos){
            String head = baseBao.getKey();
            //大标题
            map = new HashedMap();
            map.put("a",head);
            valList.add(map);
            headIndexList.add(valList.size() - 1);
            //小标题
            map = new HashedMap();
            map.putAll(smallHead);
            valList.add(map);
            smallHeadIndexList.add(valList.size() - 1);
            //content
            baseBao.getValue().stream()
                    .sorted(Comparator.comparing(BaseBao::getName))
                    .map(PoiRibaoTemplateExportUtil::toLine).forEach(r -> {
                valList.add(r);
                contextIndexList.add(valList.size() - 1);
            });
            //空格
            for (int i = 0; i < BLANK_SPACE_NUM; i++) {
                valList.add(new HashedMap());
            }
        }

        //开始导出
        Map<String, Object> value = new HashMap<String, Object>();
        value.put("valList", valList);
        //姓名：智慧课室 后台组 王慧佐
        value.put("name", "姓名：智慧课室 后台组 王慧佐");
        //时间：2017-8-10
        value.put("date", "时间："+DateUtil.getNowDate());

        TemplateExportParams params = new TemplateExportParams(
                "d:\\ribao.xlsx", true);
//                "/usr/common/baseBao/doc/excel/template/ribao.xlsx", true);
        params.setColForEach(true);
        Workbook book = ExcelExportUtil.exportExcel(params, value);

        styleHead(book, headIndexList);
        styleSmallHead(book, smallHeadIndexList);
        styleContext(book, contextIndexList);

        return book;
    }


    private static Map<String,Object> toLine(BaseBao baseBao){
        Map<String, Object> newMap = new HashedMap();
        newMap.put("a",baseBao.getModule()+"-"+baseBao.getDescription());
        newMap.put("b",baseBao.getName());
        newMap.put("c", baseBao.getPlan()==null?"":baseBao.getPlan()+"%");
        newMap.put("d",baseBao.getPractical()==null?"":baseBao.getPractical()+"%");
        newMap.put("e",baseBao.getNote());
        return newMap;
    }

    private static void styleHead(Workbook book, List<Integer> indexList){
        style(book, indexList, true, true, true, true, true, true);
    }
    private static void styleSmallHead(Workbook book, List<Integer> indexList){
        style(book, indexList, false, true, false, true, true, true);
    }
    private static void styleContext(Workbook book, List<Integer> indexList){
        style(book, indexList, false, false, false, false, true, true);
    }

    private static void style(Workbook book, List<Integer> rows,
                              boolean merge,
                              boolean foreground,
                              boolean alignmentCenter,
                              boolean fontBold,
                              boolean border,
                              boolean autoWarp){
        //my
        Sheet sheetAt = book.getSheetAt(0);

        for (Integer i : rows){
            i = i + START_ROW;
            //合并
            if (merge)
                sheetAt.addMergedRegion(new CellRangeAddress(i,i,0,COL_NUM-1));

            CellStyle style = book.createCellStyle();
            //背景色
            if (foreground){
                style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            }
            //居中
            if (alignmentCenter)
                style.setAlignment(HorizontalAlignment.CENTER);
            //都垂直居中
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            //加粗
            if (fontBold){
                Font font = book.createFont();
                font.setBold(true);
                style.setFont(font);
            }
            //边框
            if(border){
                style.setBorderBottom(BorderStyle.THIN); //下边框
                style.setBorderLeft(BorderStyle.THIN);//左边框
                style.setBorderTop(BorderStyle.THIN);//上边框
                style.setBorderRight(BorderStyle.THIN);//右边框
            }
            //自动换行
            if (autoWarp)
                style.setWrapText(true);

            for (int j = 0; j < COL_NUM; j++) {
                sheetAt.getRow(i).getCell(j).setCellStyle(style);
            }
        }

    }

}
