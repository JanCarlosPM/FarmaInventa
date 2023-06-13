package ControladorExcel;

import Dao.ModeloExcel;
import Interfaces.FrmExcel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class ControladorExcel implements ActionListener {

    ModeloExcel modeloE = new ModeloExcel();
    FrmExcel vistaE = new FrmExcel();
    JFileChooser selecArchivo = new JFileChooser();
    File archivo;
    int contAccion;

    public ControladorExcel(FrmExcel vistaE, ModeloExcel modeloE) {
        this.vistaE = vistaE;
        this.modeloE = modeloE;
        this.vistaE.btnImportar.addActionListener(this);
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

        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) vistaE.tablaImportar.getModel();

        if (e.getSource() == vistaE.btnImportar) {
            if (selecArchivo.showDialog(null, "Seleccionar archivo") == JFileChooser.APPROVE_OPTION) {
                archivo = selecArchivo.getSelectedFile();
                if (archivo.getName().endsWith("xls") || archivo.getName().endsWith("xlsx")) {
                    vistaE.btnImportar.setEnabled(false);
                    modeloE.Importar(archivo, vistaE.tablaImportar);
                } else {
                    vistaE.btnImportar.setEnabled(true);
                    JOptionPane.showMessageDialog(null, "Seleccione un formato valido", "Farmacia", 2);
                }
            }
        }
    }
}
