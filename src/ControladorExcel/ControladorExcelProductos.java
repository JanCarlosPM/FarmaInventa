package ControladorExcel;

import Dao.ModeloExcel;
import Interfaces.FrmReportesProductos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ControladorExcelProductos implements ActionListener {
    
    ModeloExcel modeloE = new ModeloExcel();
    FrmReportesProductos vistaE = new FrmReportesProductos();
    JFileChooser selecArchivo = new JFileChooser();
    File archivo;
    int contAccion;
    
     public ControladorExcelProductos(FrmReportesProductos vistaE, ModeloExcel modeloE) {
        this.vistaE = vistaE;
        this.modeloE = modeloE;
        this.vistaE.btnExportarExcel.addActionListener(this);
    }

    public void AgregarFiltro() {
        selecArchivo.setFileFilter(new FileNameExtensionFilter("Excel (*.xls)", "xls"));
        selecArchivo.setFileFilter(new FileNameExtensionFilter("Excel (*.xlsx)", "xlsx"));
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        contAccion++;
        if (contAccion == 1) {
            AgregarFiltro();
        }

        if (e.getSource() == vistaE.btnExportarExcel) {
            if (selecArchivo.showDialog(null, "Exportar") == JFileChooser.APPROVE_OPTION) {
                archivo = selecArchivo.getSelectedFile();
                if (archivo.getName().endsWith("xls") || archivo.getName().endsWith("xlsx")) {
                    JOptionPane.showMessageDialog(null, modeloE.Exportar(archivo, vistaE.tablaProductos));
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un formato valido", "Farmacia", 2);
                }
            }
        }
    }
    
}
