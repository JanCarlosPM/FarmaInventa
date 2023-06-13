package ControladorExcel;

import Dao.ModeloExcelVentas;
import Interfaces.FrmReportesVentas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ControladorExcelVentas implements ActionListener {

    ModeloExcelVentas modeloE = new ModeloExcelVentas();
    FrmReportesVentas vistaE = new FrmReportesVentas();
    JFileChooser selecArchivo = new JFileChooser();
    File archivo;
    int contAccion;

    public ControladorExcelVentas(FrmReportesVentas vistaE, ModeloExcelVentas modeloE) {
        this.vistaE = vistaE;
        this.modeloE = modeloE;
        this.vistaE.btnExportarExcel.addActionListener(this);
        vistaE.setVisible(true);
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
                    JOptionPane.showMessageDialog(null, modeloE.Exportar(archivo, vistaE.tablaVentas));
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un formato valido", "Farmacia", 2);
                }
            }
        }
    }

}
