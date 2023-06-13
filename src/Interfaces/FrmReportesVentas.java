package Interfaces;

import ControladorExcel.ControladorExcelVentas;
import Dao.Conexion;
import Dao.ModeloExcelVentas;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrmReportesVentas extends javax.swing.JFrame {

    /**
     * Creates new form FrmReportesVentas
     */
    public FrmReportesVentas() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.dispose();
        setTitle("Reportes de ventas");
        mostrar("Ventas");
        setIconImage(getIconImage());
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 27, 27));
        
        DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        txtFecha_actual.setText(dtf3.format(LocalDateTime.now()));
    }

    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Iconos/brain-128.png"));
        return retValue;
    }
    
    public void generarPDF() {
        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) tablaVentas.getModel();

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "No existen productos en la tabla", "Farmacia", 2);
        } else {

            try {

                Document documento = new Document();
                String destino = "Reporte de Ventas " + txtFecha_actual.getText() + ".pdf";
                PdfWriter.getInstance(documento, new FileOutputStream(destino));

                //LOGO//
                com.itextpdf.text.Image header = com.itextpdf.text.Image.getInstance("src/img/Logo.png");
                header.scaleToFit(70, 70);
                header.setAlignment(Chunk.ALIGN_RIGHT);

                documento.open();
                documento.add(header);

                //FARMACIA//
                Paragraph a = new Paragraph("Farmacia");
                a.setAlignment(1);
                documento.add(a);

                //MANAGUA, NICARAGUA//
                Paragraph b = new Paragraph("Managua, Nicaragua");
                b.setAlignment(1);
                documento.add(b);
                
                //REPORTE DE PRODUCTOS//
                Paragraph c = new Paragraph("Reporte de Ventas");
                c.setAlignment(1);
                documento.add(c);
                
                documento.add(new Paragraph("\n"));

                //TABLA PRODUCTOS - CANTIDAD - PRECIO//
                PdfPTable tabla = new PdfPTable(6);
                tabla.setWidthPercentage(100);
                PdfPCell factura = new PdfPCell(new Phrase("Factura"));
                factura.setBackgroundColor(BaseColor.WHITE);
                PdfPCell pago_total = new PdfPCell(new Phrase("Pago Total"));
                pago_total.setBackgroundColor(BaseColor.WHITE);
                PdfPCell recibido = new PdfPCell(new Phrase("Recibido"));
                recibido.setBackgroundColor(BaseColor.WHITE);
                PdfPCell cambio = new PdfPCell(new Phrase("Cambio"));
                cambio.setBackgroundColor(BaseColor.WHITE);
                PdfPCell fecha_venta = new PdfPCell(new Phrase("Fecha de Venta"));
                fecha_venta.setBackgroundColor(BaseColor.WHITE);
                PdfPCell hora_venta = new PdfPCell(new Phrase("Hora de Venta"));
                hora_venta.setBackgroundColor(BaseColor.WHITE);
                
                tabla.addCell(factura);
                tabla.addCell(pago_total);
                tabla.addCell(recibido);
                tabla.addCell(cambio);
                tabla.addCell(fecha_venta);
                tabla.addCell(hora_venta);

                for (int i = 0; i < tablaVentas.getRowCount(); i++) {
                    tabla.addCell(tablaVentas.getValueAt(i, 0).toString());
                    tabla.addCell("C$ " + tablaVentas.getValueAt(i, 4).toString());
                    tabla.addCell("C$ " + tablaVentas.getValueAt(i, 5).toString());
                    tabla.addCell("C$ " + tablaVentas.getValueAt(i, 6).toString());
                    tabla.addCell(tablaVentas.getValueAt(i, 7).toString());
                    tabla.addCell(tablaVentas.getValueAt(i, 8).toString());
                }

                documento.add(tabla);

                documento.close();
                JOptionPane.showMessageDialog(null, "Reporte de Ventas creada con éxito", "Farmacia", 2);
                try {
                    File path = new File("Reporte de Ventas " + txtFecha_actual.getText() + ".pdf");
                    Desktop.getDesktop().open(path);
                } catch (Exception e) {

                }

            } catch (FileNotFoundException | DocumentException ex) {

            } catch (IOException e) {

            }
        }
    }
    
    public void mostrar(String tabla) {
        String sql = "SELECT num_factura, cantidad_productos, sub_total, iva, pago_total, pago_cliente, cambio, fecha_venta, hora_venta FROM " + tabla;
        Statement st;
        Conexion conn = new Conexion();
        Connection conexion = conn.getConexion();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("factura");
        model.addColumn("total productos");
        model.addColumn("sub total");
        model.addColumn("iva");
        model.addColumn("pago total");
        model.addColumn("recibido");
        model.addColumn("cambio");
        model.addColumn("fecha venta");
        model.addColumn("hora venta");
        tablaVentas.setModel(model);

        String[] datos = new String[9];
        try {
            st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                datos[6] = rs.getString(7);
                datos[7] = rs.getString(8);
                datos[8] = rs.getString(9);
                model.addRow(datos);
            }
            conexion.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaVentas = new javax.swing.JTable();
        btnRegresar = new javax.swing.JButton();
        btnExportarExcel = new javax.swing.JButton();
        btnExportarPDF = new javax.swing.JButton();
        txtFecha_actual = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(16, 188, 227));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 40)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Farmacia");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/brain-128.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(jLabel2)))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel1)
                .addGap(38, 38, 38)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(256, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 290, 500));

        jPanel2.setBackground(new java.awt.Color(30, 36, 37));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 40)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Reporte de Ventas");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(315, 17, -1, -1));

        tablaVentas = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tablaVentas.setBackground(new java.awt.Color(30, 36, 37));
        tablaVentas.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        tablaVentas.setForeground(new java.awt.Color(255, 255, 255));
        tablaVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "factura", "total productos", "sub total", "iva", "pago total", "recibido", "cambio", "fecha venta", "hora venta"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaVentas.setEnabled(false);
        tablaVentas.setFocusable(false);
        tablaVentas.getTableHeader().setResizingAllowed(false);
        tablaVentas.getTableHeader().setReorderingAllowed(false);
        tablaVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaVentasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaVentas);
        if (tablaVentas.getColumnModel().getColumnCount() > 0) {
            tablaVentas.getColumnModel().getColumn(0).setResizable(false);
            tablaVentas.getColumnModel().getColumn(1).setResizable(false);
            tablaVentas.getColumnModel().getColumn(2).setResizable(false);
            tablaVentas.getColumnModel().getColumn(3).setResizable(false);
            tablaVentas.getColumnModel().getColumn(4).setResizable(false);
            tablaVentas.getColumnModel().getColumn(5).setResizable(false);
            tablaVentas.getColumnModel().getColumn(6).setResizable(false);
            tablaVentas.getColumnModel().getColumn(7).setResizable(false);
            tablaVentas.getColumnModel().getColumn(8).setResizable(false);
        }

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 99, 1015, 315));

        btnRegresar.setFont(new java.awt.Font("Segoe UI", 3, 20)); // NOI18N
        btnRegresar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/IconRegresar.png"))); // NOI18N
        btnRegresar.setText("Regresar");
        btnRegresar.setContentAreaFilled(false);
        btnRegresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRegresar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });
        jPanel2.add(btnRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(359, 432, -1, -1));

        btnExportarExcel.setFont(new java.awt.Font("Segoe UI", 3, 20)); // NOI18N
        btnExportarExcel.setForeground(new java.awt.Color(255, 255, 255));
        btnExportarExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/IconExcel.png"))); // NOI18N
        btnExportarExcel.setText("EXCEL");
        btnExportarExcel.setContentAreaFilled(false);
        btnExportarExcel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExportarExcel.setHideActionText(true);
        btnExportarExcel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnExportarExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarExcelActionPerformed(evt);
            }
        });
        jPanel2.add(btnExportarExcel, new org.netbeans.lib.awtextra.AbsoluteConstraints(495, 432, 130, -1));

        btnExportarPDF.setFont(new java.awt.Font("Segoe UI", 3, 20)); // NOI18N
        btnExportarPDF.setForeground(new java.awt.Color(255, 255, 255));
        btnExportarPDF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/IconPdf.png"))); // NOI18N
        btnExportarPDF.setText("PDF");
        btnExportarPDF.setContentAreaFilled(false);
        btnExportarPDF.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExportarPDF.setHideActionText(true);
        btnExportarPDF.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnExportarPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarPDFActionPerformed(evt);
            }
        });
        jPanel2.add(btnExportarPDF, new org.netbeans.lib.awtextra.AbsoluteConstraints(631, 432, 140, 40));

        txtFecha_actual.setFont(new java.awt.Font("Segoe UI", 3, 15)); // NOI18N
        txtFecha_actual.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(txtFecha_actual, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 10, 130, 30));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 0, 1050, 500));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        FrmReportesPyV a = new FrmReportesPyV();
        a.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed
    
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_formKeyPressed
    int xx, xy;
    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        xx = evt.getX();
        xy = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();

        this.setLocation(x - xx, y - xy);
    }//GEN-LAST:event_formMouseDragged

    private void btnExportarExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarExcelActionPerformed
        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) tablaVentas.getModel();

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "No existen ventas en la tabla", "Farmacia", 2);
        } else {
            ModeloExcelVentas modeloE = new ModeloExcelVentas();
            ControladorExcelVentas e = new ControladorExcelVentas(this, modeloE);
        }
    }//GEN-LAST:event_btnExportarExcelActionPerformed

    private void btnExportarPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarPDFActionPerformed
        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) tablaVentas.getModel();

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "No existen ventas en la tabla", "Farmacia", 2);
        } else {
            generarPDF();
        }

    }//GEN-LAST:event_btnExportarPDFActionPerformed

    private void tablaVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaVentasMouseClicked
       
    }//GEN-LAST:event_tablaVentasMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmReportesVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmReportesVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmReportesVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmReportesVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmReportesVentas().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnExportarExcel;
    private javax.swing.JButton btnExportarPDF;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable tablaVentas;
    private javax.swing.JLabel txtFecha_actual;
    // End of variables declaration//GEN-END:variables
}
