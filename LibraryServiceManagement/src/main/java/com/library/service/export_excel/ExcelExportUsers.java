package com.library.service.export_excel;

import com.library.entity.Role;
import com.library.entity.User;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ExcelExportUsers {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<User> userList;

    public ExcelExportUsers(List<User> userList) {
        this.userList = userList;
        workbook = new XSSFWorkbook();
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        }else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        }else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void createHeaderRow() {
        sheet = workbook.createSheet("User Information");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(20);
        font.setColor(IndexedColors.RED.getIndex());
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillBackgroundColor(IndexedColors.DARK_GREEN.getIndex());

        createCell(row,0,"User Information", style );
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,12));
        sheet.addMergedRegion(new CellRangeAddress(1,1,8,12));
        font.setFontHeightInPoints((short) 10);

        //Style Header
        row = sheet.createRow(1);
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row,0, "ID", style);
        createCell(row,1, "Name", style);
        createCell(row,2, "Username", style);
        createCell(row,3, "Password", style);
        createCell(row,4, "Email", style);
        createCell(row,5, "Avatar", style);
        createCell(row,6, "Address", style);
        createCell(row,7, "Status", style);
        createCell(row,8, "Roles", style);
    }

    private void writeUserData() {
        int rowCount=2;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for(User user: userList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, user.getId(), style);
            createCell(row, columnCount++, user.getName(), style);
            createCell(row, columnCount++, user.getUsername(), style);
            createCell(row, columnCount++, "null", style);
            createCell(row, columnCount++, user.getEmail(), style);
            createCell(row, columnCount++, user.getAvatar(), style);
            createCell(row, columnCount++, user.getAddress(), style);

            if(user.getStatus() == User.AccountStatus.ACTIVE) {
                createCell(row, columnCount++, "ACTIVE", style);
            } else if (user.getStatus() == User.AccountStatus.CLOSED) {
                createCell(row, columnCount++, "CLOSED", style);
            } else if (user.getStatus() == User.AccountStatus.CANCELED) {
                createCell(row, columnCount++, "CANCELED", style);
            } else if (user.getStatus() == User.AccountStatus.BLACKLISTED) {
                createCell(row, columnCount++, "BLACKLISTED", style);
            } else if (user.getStatus() == User.AccountStatus.NONE) {
                createCell(row, columnCount++, "NONE", style);
            } else {
                createCell(row, columnCount++, null, style);
            }

            for(Role role: user.getRoles()) {
                createCell(row, columnCount++, role.getName(), style);
            }
        }
    }

    public void exportUserDataToExcel(HttpServletResponse response) throws IOException {
        createHeaderRow();
        writeUserData();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

}
