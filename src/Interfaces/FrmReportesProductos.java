package Interfaces;

import ControladorExcel.ControladorExcelProductos;
import Dao.Conexion;
import Dao.ModeloExcel;
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

public class FrmReportesProductos extends javax.swing.JFrame {
    
    /**
     * Creates new form FrmRegistro_Ventas
     */
    public FrmReportesProductos() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.dispose();
        setTitle("Reportes de Productos");
        mostrar("Productos");
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 27, 27));

        DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        txtFecha_actual.setText(dtf3.format(LocalDateTime.now()));
    }

    public void generarPDF() {
        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) tablaProductos.getModel();
        
        
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "No existen productos en la tabla", "Farmacia", 2);
        } else {

            try {

                Document documento = new Document();
                String destino = "Reporte de Productos " + txtFecha_actual.getText() + ".pdf";
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
                Paragraph c = new Paragraph("Reporte de Productos");
                c.setAlignment(1);
                documento.add(c);
                
                documento.add(new Paragraph("\n"));

                //TABLA PRODUCTOS - CANTIDAD - PRECIO//
                PdfPTable tabla = new PdfPTable(6);
                tabla.setWidthPercentage(100);
                PdfPCell producto = new PdfPCell(new Phrase("Productos"));
                producto.setBackgroundColor(BaseColor.WHITE);
                PdfPCell descripcion = new PdfPCell(new Phrase("Descripción"));
                descripcion.setBackgroundColor(BaseColor.WHITE);
                PdfPCell precio = new PdfPCell(new Phrase("Precio Unitario"));
                precio.setBackgroundColor(BaseColor.WHITE);
                PdfPCell stock = new PdfPCell(new Phrase("Stock"));
                stock.setBackgroundColor(BaseColor.WHITE);
                PdfPCell fecha_vencimiento = new PdfPCell(new Phrase("Vencimiento"));
                fecha_vencimiento.setBackgroundColor(BaseColor.WHITE);
                PdfPCell fecha_registro = new PdfPCell(new Phrase("Registro"));
                fecha_registro.setBackgroundColor(BaseColor.WHITE);

                tabla.addCell(producto);
                tabla.addCell(descripcion);
                tabla.addCell(precio);
                tabla.addCell(stock);
                tabla.addCell(fecha_vencimiento);
                tabla.addCell(fecha_registro);

                for (int i = 0; i < tablaProductos.getRowCount(); i++) {
                    tabla.addCell(tablaProductos.getValueAt(i, 0).toString());
                    tabla.addCell(tablaProductos.getValueAt(i, 1).toString());
                    tabla.addCell("C$ " + tablaProductos.getValueAt(i, 2).toString());
                    tabla.addCell(tablaProductos.getValueAt(i, 3).toString());
                    tabla.addCell(tablaProductos.getValueAt(i, 4).toString());
                    tabla.addCell(tablaProductos.getValueAt(i, 5).toString());
                }

                documento.add(tabla);

                documento.close();
                JOptionPane.showMessageDialog(null, "Reporte de Productos creada con éxito", "Farmacia", 2);
                try {
                    File path = new File("Reporte de Productos " + txtFecha_actual.getText() + ".pdf");
                    Desktop.getDesktop().open(path);
                } catch (Exception e) {

                }

            } catch (FileNotFoundException | DocumentException ex) {

            } catch (IOException e) {

            }
        }
    }
    
    public void mostrar(String tabla) {
        String sql = "SELECT producto, descripcion, precio, stock, fecha_vencimiento, fecha_registro FROM " + tabla;
        Statement st;
        Conexion conn = new Conexion();
        Connection conexion = conn.getConexion();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("producto");
        model.addColumn("descripcion");
        model.addColumn("precio");
        model.addColumn("stock");
        model.addColumn("fecha de vencimiento");
        model.addColumn("fecha de registro");
        tablaProductos.setModel(model);

        String[] datos = new String[6];
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
                model.addRow(datos);
            }
            conexion.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Iconos/brain-128.png"));
        return retValue;
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
        tablaProductos = new javax.swing.JTable();
        btnRegresar = new javax.swing.JButton();
        btnExportarExcel = new javax.swing.JButton();
        btnExportarPDF = new javax.swing.JButton();
        txtFecha_actual = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
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
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(16, 188, 227));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 40)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Farmacia");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/brain-128.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(jLabel2)))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel1)
                .addGap(42, 42, 42)
                .addComponent(jLabel2)
                .addContainerGap(241, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 290, -1));

        jPanel2.setBackground(new java.awt.Color(30, 36, 37));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 40)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Productos Agregados");

        tablaProductos = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tablaProductos.setBackground(new java.awt.Color(30, 36, 37));
        tablaProductos.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        tablaProductos.setForeground(new java.awt.Color(255, 255, 255));
        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "producto", "descripcion", "precio", "stock", "fecha de vencimineto", "fecha de registro"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tablaProductos.setEnabled(false);
        tablaProductos.setFocusable(false);
        tablaProductos.setRowSelectionAllowed(false);
        tablaProductos.getTableHeader().setResizingAllowed(false);
        tablaProductos.getTableHeader().setReorderingAllowed(false);
        tablaProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaProductosMouseClicked(evt);
            }
        });
        tablaProductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaProductosKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablaProductos);
        if (tablaProductos.getColumnModel().getColumnCount() > 0) {
            tablaProductos.getColumnModel().getColumn(0).setResizable(false);
            tablaProductos.getColumnModel().getColumn(1).setResizable(false);
            tablaProductos.getColumnModel().getColumn(2).setResizable(false);
            tablaProductos.getColumnModel().getColumn(3).setResizable(false);
            tablaProductos.getColumnModel().getColumn(4).setResizable(false);
            tablaProductos.getColumnModel().getColumn(5).setResizable(false);
        }

        btnRegresar.setFont(new java.awt.Font("Segoe UI", 3, 20)); // NOI18N
        btnRegresar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/IconRegresar.png"))); // NOI18N
        btnRegresar.setText("Regresar");
        btnRegresar.setContentAreaFilled(false);
        btnRegresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        btnExportarExcel.setFont(new java.awt.Font("Segoe UI", 3, 20)); // NOI18N
        btnExportarExcel.setForeground(new java.awt.Color(255, 255, 255));
        btnExportarExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/IconExcel.png"))); // NOI18N
        btnExportarExcel.setText("Excel");
        btnExportarExcel.setContentAreaFilled(false);
        btnExportarExcel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExportarExcel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnExportarExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarExcelActionPerformed(evt);
            }
        });

        btnExportarPDF.setFont(new java.awt.Font("Segoe UI", 3, 20)); // NOI18N
        btnExportarPDF.setForeground(new java.awt.Color(255, 255, 255));
        btnExportarPDF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/IconPdf.png"))); // NOI18N
        btnExportarPDF.setText("PDF");
        btnExportarPDF.setContentAreaFilled(false);
        btnExportarPDF.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExportarPDF.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnExportarPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarPDFActionPerformed(evt);
            }
        });

        txtFecha_actual.setFont(new java.awt.Font("Segoe UI", 3, 15)); // NOI18N
        txtFecha_actual.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(401, 401, 401)
                        .addComponent(btnRegresar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExportarExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExportarPDF))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(350, 350, 350)
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtFecha_actual, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(50, 50, 50)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1020, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(20, 20, 20))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtFecha_actual, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(btnRegresar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExportarExcel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExportarPDF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(256, 0, 1090, 500));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        FrmReportesPyV n = new FrmReportesPyV();
        n.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed
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

    private void tablaProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaProductosMouseClicked

    }//GEN-LAST:event_tablaProductosMouseClicked

    private void tablaProductosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaProductosKeyPressed

    }//GEN-LAST:event_tablaProductosKeyPressed

    private void btnExportarExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarExcelActionPerformed
        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) tablaProductos.getModel();

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "No existen productos en la tabla", "Farmacia", 2);
        } else {
            ModeloExcel modeloE = new ModeloExcel();
            ControladorExcelProductos e = new ControladorExcelProductos(this, modeloE);
        }
    }//GEN-LAST:event_btnExportarExcelActionPerformed

    private void btnExportarPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarPDFActionPerformed
        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) tablaProductos.getModel();

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "No existen productos en la tabla", "Farmacia", 2);
        } else {
            generarPDF();
        }

    }//GEN-LAST:event_btnExportarPDFActionPerformed

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
            java.util.logging.Logger.getLogger(FrmReportesProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmReportesProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmReportesProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmReportesProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmReportesProductos().setVisible(true);
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
    public javax.swing.JTable tablaProductos;
    private javax.swing.JLabel txtFecha_actual;
    // End of variables declaration//GEN-END:variables
}
