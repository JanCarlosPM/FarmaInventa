package Dao;

import java.io.*;
import javax.swing.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ModeloExcelVentas {

    Workbook wb;

    public String Exportar(File archivo, JTable tablaProductos) {
        String respuesta = "No se realizo con exito la exportacion";
        int numFila = tablaProductos.getRowCount(), numColumna = tablaProductos.getColumnCount();
        if (archivo.getName().endsWith("xls")) {
            wb = new HSSFWorkbook();
        } else {
            wb = new XSSFWorkbook();
        }
        Sheet hoja = wb.createSheet("Pruebita");
        try {
            for (int i = -1; i < numFila; i++) {
                Row fila = hoja.createRow(i + 1);
                for (int j = 0; j < numColumna; j++) {
                    Cell celda = fila.createCell(j);
                    if (i == -1) {
                        celda.setCellValue(String.valueOf(tablaProductos.getColumnName(j)));
                    } else {
                        celda.setCellValue(String.valueOf(tablaProductos.getValueAt(i, j)));
                    }
                    wb.write(new FileOutputStream(archivo));
                }
            }
            respuesta = "Exportacion exitosa";

        } catch (Exception e) {
        }
        return respuesta;
    }

}
