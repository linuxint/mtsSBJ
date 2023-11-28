package com.devkbil.mtssbj.common;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MakeExcel {

    public MakeExcel() {
    }

    public String get_Filename() {
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddmmmm");
        return ft.format(new Date());
    }

    public String get_Filename(String pre) {
        return pre + get_Filename();
    }

    /**
     * Big data excel download using disk storage
     * @param response
     * @param beans
     * @param filename
     */
    public void download(HttpServletResponse response, Map<String, Object> beans, String filename) {

        OutputStream tempFile = null;
        //FileOutputStream fos = null;
        SXSSFWorkbook workbook = null;      // 워크북
        SXSSFRow row = null;                // 행
        SXSSFCell cell = null;              // 셀
        // CellStyle styleMoneyFormat = null;  // 샐 스타일
        int index = 0;                      // 셀 헤더 카운트
        int rowIndex = 1;                   // 행 카운트
        int columnIndex = 0;                // 열 카운트
        List<?> dataList = null;
        String[] cellHeader = null;         // 엑셀 헤더 정보 구성
        String sheetName = null;

        try {

            cellHeader = (String[])beans.get(ExcelConstant.HEADER_KEY_NAME);
            dataList = (List<?>)beans.get(ExcelConstant.DATA_KEY_NAME);
            sheetName = beans.get(ExcelConstant.SHEET_KEY_NAME).toString();

            // 워크북 생성
            workbook = new SXSSFWorkbook();
            workbook.setCompressTempFiles(true);

            // 워크시트 생성
            SXSSFSheet sheet = workbook.createSheet(sheetName);
            sheet.setRandomAccessWindowSize(ExcelConstant.MAX_ROW_COUNT); // 메모리 행 100개로 제한, 초과 시 Disk로 flush
            sheet.setColumnWidth(2, 300); //셀 칼럼 크기 설정
            row = sheet.createRow(0); // 행 생성
            // styleMoneyFormat = workbook.createCellStyle(); // 셀 스타일 생성
            CreationHelper ch = workbook.getCreationHelper();
            // styleMoneyFormat.setDataFormat(ch.createDataFormat().getFormat("#,##0"));

            responseExcel(response, filename);
            tempFile = response.getOutputStream();

            // 헤더 적용
            for (String head : cellHeader) {
                cell = row.createCell(index++);
                cell.setCellValue(head);
            }

            Map<String, Object> map = new HashMap<String, Object>();
            ObjectMapper objectMapper = new ObjectMapper();

            for (Object data : dataList) {

                row = sheet.createRow(rowIndex);
                map = objectMapper.convertValue(data, Map.class);

                cell = row.createCell(columnIndex);
                cell.setCellValue(rowIndex++); //번호

                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    cell = row.createCell(++columnIndex);
                    try {
                        cell.setCellValue(entry.getValue().toString()); //테스트
                    } catch (Exception e) {
                        cell.setCellValue("");
                    }
                }

                columnIndex = 0;
            }

            //workbook.write(fos);
            workbook.write(tempFile);

            //workbook.write(response.getOutputStream()); // resource leak

        } catch (Exception e) {
            //if(fos != null) try { fos.close(); } catch(Exception ignore) {}
        } finally {
            try {
                workbook.close();
                //if(fos != null) try { fos.close(); } catch(Exception ignore) {}
                tempFile.close();
                response.getOutputStream().flush();
                response.getOutputStream().close();
                workbook.dispose();
            } catch (IOException e) {
                log.info(e.getMessage());
            }

        }
    }

    /**
     * Download excel format
     */
    public void download(HttpServletRequest request, HttpServletResponse response, Map<String, Object> beans,
            String filename, String templateFile) {
        String tempPath = request.getSession().getServletContext().getRealPath("/WEB-INF/templete");

        try {
            InputStream is = new BufferedInputStream(new FileInputStream(tempPath + "/" + templateFile));

            Context context = new Context();
            context.putVar("listview", beans.get("listview"));

            responseExcel(response, filename);
            OutputStream os = response.getOutputStream();

            JxlsHelper helper = JxlsHelper.getInstance();
            helper.setProcessFormulas(false);
            helper.processTemplate(is, os, context);

        } catch (IOException ex) {
            log.error("MakeExcel");
        }
    }

    /**
     * response Excel name
     * @param response
     * @param filename
     * @return
     * @throws UnsupportedEncodingException
     */
    private ServletResponse responseExcel(HttpServletResponse response, String filename) throws UnsupportedEncodingException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", URLEncoder.encode(filename, String.valueOf(StandardCharsets.UTF_8)) + ".xlsx"));

        return response;
    }
}
