package com.sx.common.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelStyleType;
import cn.afterturn.easypoi.word.WordExportUtil;
import com.sx.common.entity.WorkbookEntity;
import com.sx.common.constants.ContentTypeMapping;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jiangshuai
 * <p color = "yellow">
 * 文档导出工具类
 * </p>
 */
public class WorkbookUtils{


    public static void exportTemplate(TemplateExportParams params, Map<String, Object> map, String fileName) throws IOException {
        flush(ExcelExportUtil.exportExcel(params, map), fileName);
    }

    public static void flush(Workbook workbook, String fileName) throws IOException {
        try {
            if (workbook == null) {
                return;
            }
            String fileType = "." + FileTypeUtils.getFileType(fileName);
            HttpServletResponse response = ServletUtils.getResponse();
            assert response != null;
            response.setContentType(ContentTypeMapping.getContentType(fileType));
            response.setCharacterEncoding("utf-8");
            String encodeFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=" + encodeFileName);
            workbook.write(response.getOutputStream());
        } finally {
            IOUtils.closeQuietly(workbook);
        }
    }

    /**
     * 描述：  <b color=yellow>Excel模版模式导出</b>
     *
     * @param templateUrl 模版路径
     * @param name    文件名
     * @param map         参数
     * @return void
     * @time 2023/4/6 18:44
     */
    public static void exportExcelTemplate(String templateUrl, String name, Map<String, Object> map) {
        HttpServletResponse response = ServletUtils.getResponse();
        assert response != null;
        TemplateExportParams params = new TemplateExportParams(templateUrl, true);
        params.setStyle(ExcelStyleType.BORDER.getClazz());
        response.setContentType("application/vnd.ms-excel;charset=utf8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(name, StandardCharsets.UTF_8));
        try(Workbook book = ExcelExportUtil.exportExcel(params, map);
            OutputStream outputStream = response.getOutputStream()) {
            book.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 描述：  <b color=yellow>导出Excel</b>
     * @param exportParams 导出参数（标题、sheet名称、是否创建表头，表格类型）
     * @param name 导出文件名
     * @param list 数据
     * @param pojoClass class
     */
    public static void exportExcel(ExportParams exportParams,String name, List<?> list, Class<?> pojoClass,List<WorkbookEntity> eList) {
        HttpServletResponse response = ServletUtils.getResponse();
        assert response != null;
        response.setContentType("application/vnd.ms-excel;charset=utf8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(name, StandardCharsets.UTF_8));
        try(Workbook book = ExcelExportUtil.exportExcel(exportParams,pojoClass,list);
            OutputStream outputStream = response.getOutputStream()){
            if(eList != null && !eList.isEmpty()){
                Sheet sheet = null;
                Row row = null;
                for (WorkbookEntity e : eList){
                    if(e.getSheetNum()!=null && e.getSheetNum()>=0){
                         sheet = book.getSheetAt(e.sheetNum);
                    }
                    if(sheet!= null && e.getSheetNum()!=null  && e.getCreateRow()>=0){
                         row = sheet.createRow(sheet.getLastRowNum() + e.getCreateRow());
                    }
                    assert row != null;
                    Cell cell = row.createCell(e.createCell);
                    Font font = book.createFont();
                    //字体样式
                    font.setFontName("宋体");
                    //字体大小
                    short size;
                    if( e.fontSize <= 0){
                         size = 11;
                    }else{
                        size = e.fontSize;
                    }
                    font.setFontHeightInPoints(size);
                    CellStyle style = book.createCellStyle();
                    style.setFont(font);
                    //水平居中
                    style.setAlignment(HorizontalAlignment.LEFT);
                    //上下居中
                    style.setVerticalAlignment(VerticalAlignment.CENTER);
                    cell.setCellStyle(style);
                    cell.setCellValue(e.getValue());
                }
            }
            book.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 描述：  <b color=yellow>导出Excel</b>
     * @param exportParams 导出参数（标题、sheet名称、是否创建表头，表格类型）
     * @param name 导出文件名
     * @param list 数据
     * @param pojoClass class
     */
    public static void exportExcel(ExportParams exportParams,String name, List<?> list, Class<?> pojoClass) {
        exportExcel(exportParams, name, list, pojoClass,new ArrayList<>());
    }
    /**
     * 描述：  <b color=yellow>导出Word模版模式</b>
     *
     * @param templateUrl 模版路径
     * @param fileName    文件名
     * @param map         参数
     * @time 2023/4/6 18:44
     */
    public static void exportWordTemplate(String templateUrl, String fileName, Map<String, Object> map) {
        try (XWPFDocument doc = WordExportUtil.exportWord07(templateUrl, map)){
            HttpServletResponse response = ServletUtils.getResponse();
            fileName = URLEncoder.encode(fileName + ".docx", StandardCharsets.UTF_8);
            assert response != null;
            response.setContentType(ContentTypeMapping.getContentType(".docx"));
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            doc.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 描述：  <b color=yellow>导出excel模板</b>
     *
     * @param templateUrl 模版路径
     * @param fileName    文件名
     * @author jiangshuai
     * @time 2023/4/6 18:44
     */
    public static void exportExcelTemplate(String templateUrl, String fileName) {
        HttpServletResponse response = ServletUtils.getResponse();
        TemplateExportParams params = new TemplateExportParams(templateUrl);
        assert response != null;
        response.setContentType("application/octet-stream");
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
        response.setHeader("filename", URLEncoder.encode(fileName, StandardCharsets.UTF_8));
        try (Workbook workbook = ExcelExportUtil.exportExcel(params,new HashMap<>(0));
             OutputStream bass = new BufferedOutputStream(response.getOutputStream())){
             workbook.write(bass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
