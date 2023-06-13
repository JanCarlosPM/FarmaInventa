package Dao;

import java.io.*;
import java.util.Iterator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ModeloExcel {

    Workbook wb;

    public String Importar(File archivo, JTable tablaImportar) {
        String respuesta = "No se pudo realizar la importacion";
        DefaultTableModel modeloT = new DefaultTableModel();
        tablaImportar.setModel(modeloT);

        try {
            wb = WorkbookFactory.create(new FileInputStream(archivo));
            Sheet hoja = wb.getSheetAt(0);
            Iterator filaIterator = hoja.rowIterator();
            int indiceFila = -1;
            while (filaIterator.hasNext()) {
                indiceFila++;
                Row fila = (Row) filaIterator.next();
                Iterator columnaIterator = fila.cellIterator();
                Object[] listaColumna = new Object[6];
                int indiceColumna = -1;
                while (columnaIterator.hasNext()) {
                    indiceColumna++;
                    Cell celda = (Cell) columnaIterator.next();
                    if (indiceFila == 0) {
                        modeloT.addColumn(celda.getStringCellValue());
                    } else {
                        switch(celda.getCellType()){
                            case Cell.CELL_TYPE_NUMERIC:
                                listaColumna[indiceColumna] = (int) Math.round(celda.getNumericCellValue());
                                break;
                            case Cell.CELL_TYPE_STRING:
                                listaColumna[indiceColumna] = celda.getStringCellValue();
                                break;
                            case Cell.CELL_TYPE_BOOLEAN:
                                listaColumna[indiceColumna] = celda.getBooleanCellValue();
                                break;
                            default:
                                listaColumna[indiceColumna] = celda.getDateCellValue();
                        }
                    }
                }
                if(indiceFila!=0)modeloT.addRow(listaColumna);
            }
                respuesta = "Importacion Exitosa";
        } catch (Exception e) {
        }

        return respuesta;
    }
    
    public String Exportar(File archivo, JTable tablaProductos){
        String respuesta = "No se realizo con exito la exportacion";
        int numFila = tablaProductos.getRowCount(), numColumna = tablaProductos.getColumnCount();
        if (archivo.getName().endsWith("xls")){
            wb = new HSSFWorkbook();
        } else {
            wb = new XSSFWorkbook();
        }
        Sheet hoja = wb.createSheet("Pruebita");
        try {
            for (int i = -1; i < numFila; i++){
                Row fila = hoja.createRow(i+1);
                for (int j = 0; j < numColumna; j++) {
                    Cell celda = fila.createCell(j);
                    if (i==-1){
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
