package com.library.service.export_excel;

import com.library.entity.Order;
import com.library.entity.OrderItem;
import com.library.entity.Role;
import com.library.entity.User;
import com.library.repository.OrderItemRepository;
import com.library.service.OrderItemService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ExcelExportOrders {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Order> orderList;

    private OrderItemRepository orderItemRepository;

    public ExcelExportOrders(List<Order> orderList) {
        this.orderList = orderList;
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
        sheet = workbook.createSheet("Your Order detail");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(20);
        font.setColor(IndexedColors.RED.getIndex());
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillBackgroundColor(IndexedColors.DARK_GREEN.getIndex());

        createCell(row,0,"Your Order detail", style );
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,12));
        //sheet.addMergedRegion(new CellRangeAddress(1,1,8,12));
        font.setFontHeightInPoints((short) 10);

        //Style Header
        row = sheet.createRow(1);
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row,0, "SKU", style);
        createCell(row,1, "UserName", style);
        createCell(row,2, "Email", style);
        createCell(row,3, "Phone Number", style);
        createCell(row,4, "Address", style);
        createCell(row,5, "Total Deposit", style);
        createCell(row,6, "Total Rent", style);
        createCell(row,7, "Created At", style);
        createCell(row,8, "Updated At", style);
        createCell(row,9, "Status", style);
        createCell(row,10, "Order Type", style);
    }

    private void writeUserData() {
        int rowCount=2;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for(Order order: orderList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, order.getOrderId(), style);
            createCell(row, columnCount++, order.getFullName(), style);
            createCell(row, columnCount++, order.getEmail(), style);
            createCell(row, columnCount++, order.getPhoneNumber(), style);
            createCell(row, columnCount++, order.getAddress(), style);
            createCell(row, columnCount++, order.getTotalDeposit(), style);
            createCell(row, columnCount++, order.getTotalRent(), style);
            createCell(row, columnCount++, order.getCreatedAt().toString(), style);
            createCell(row, columnCount++, order.getUpdatedAt().toString(), style);
            createCell(row, columnCount++, order.getStatus().toString(), style);
            createCell(row, columnCount++, order.getType().toString(), style);
        }
    }

    public void exportOrderDataToExcel(HttpServletResponse response) throws IOException {
        createHeaderRow();
        writeUserData();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

}
