package Interfaces;

import Dao.Conexion;
import Dao.DProductosImplementacion;
import Dao.DVentasImplementacion;
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
import com.itextpdf.text.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelos.Productos;
import modelos.Ventas;

public class FrmVentas extends javax.swing.JFrame {

    /**
     * Creates new form FrmVentas
     */
    public FrmVentas() {
        initComponents();
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 27, 27));
        this.setLocationRelativeTo(null);
        this.dispose();
        setTitle("Ventas");
        mostrar("Productos");
        setIconImage(getIconImage());

        DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        txtFecha_venta.setText(dtf3.format(LocalDateTime.now()));

        DateTimeFormatter dtf5 = DateTimeFormatter.ofPattern("HH:mm");
        txtHora_venta.setText(dtf5.format(LocalDateTime.now()));
    }

    @Override
    public java.awt.Image getIconImage() {
        java.awt.Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Iconos/brain-128.png"));
        return retValue;
    }

    public void generarPDF() {

        if (txtPane.getText().equals("") || txtPago_total.getText().equals("") || txtPago_cliente.getText().equals("") || txtCambio.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Tabla y/o Campos vacios", "Farmacia", 2);
        } else {
            try {

                Document documento = new Document();
                String destino = "C:\\Users\\janca\\Desktop\\" + txtSerie.getText() + ".pdf";
                PdfWriter.getInstance(documento, new FileOutputStream(destino));

                //LOGO//
                Image header = Image.getInstance("src/img/Logo.png");
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

                //RUC//
                Paragraph c = new Paragraph("RUC " + "J0310000001693");
                c.setAlignment(1);
                documento.add(c);

                //NUMERO DE SERIE//
                Paragraph d = new Paragraph("No. " + txtSerie.getText());
                d.setAlignment(1);
                documento.add(d);

                documento.add(new Paragraph("\n"));

                //TABLA PRODUCTOS - CANTIDAD - PRECIO//
                PdfPTable tabla = new PdfPTable(3);
                tabla.setWidthPercentage(100);
                PdfPCell producto = new PdfPCell(new Phrase("Productos"));
                producto.setBackgroundColor(BaseColor.WHITE);
                PdfPCell cantidad = new PdfPCell(new Phrase("Cantidad"));
                cantidad.setBackgroundColor(BaseColor.WHITE);
                PdfPCell precio = new PdfPCell(new Phrase("Precio Unitario"));
                precio.setBackgroundColor(BaseColor.WHITE);

                tabla.addCell(producto);
                tabla.addCell(cantidad);
                tabla.addCell(precio);

                for (int i = 0; i < tablaDetalles.getRowCount(); i++) {
                    tabla.addCell(tablaDetalles.getValueAt(i, 0).toString());
                    tabla.addCell(tablaDetalles.getValueAt(i, 2).toString());
                    tabla.addCell("C$ " + tablaDetalles.getValueAt(i, 1).toString());
                }

                documento.add(tabla);
                documento.add(Chunk.NEWLINE);

                //TABLA SUBTOTAL//
                PdfPTable tabla1 = new PdfPTable(2);
                tabla.setWidthPercentage(100);
                PdfPCell subtotal = new PdfPCell(new Phrase("SubTotal"));
                subtotal.setBackgroundColor(BaseColor.WHITE);
                tabla1.addCell("SubTotal");
                tabla1.addCell("C$ " + txtSub_total.getText());
                documento.add(tabla1);

                //TABLA TOTAL/7
                PdfPTable tabla2 = new PdfPTable(2);
                tabla.setWidthPercentage(100);
                PdfPCell total = new PdfPCell(new Phrase("Total"));
                total.setBackgroundColor(BaseColor.WHITE);
                tabla2.addCell("Total");
                tabla2.addCell("C$ " + txtPago_total.getText());
                documento.add(tabla2);

                //TABLA RECIBIDO//
                PdfPTable tabla3 = new PdfPTable(2);
                tabla.setWidthPercentage(100);
                PdfPCell recibido = new PdfPCell(new Phrase("Recibido"));
                recibido.setBackgroundColor(BaseColor.WHITE);
                tabla3.addCell("Recibido");
                double pc = Double.parseDouble(txtPago_cliente.getText());
                tabla3.addCell("C$ " + pc);
                documento.add(tabla3);

                //TABLA CAMBIO//
                PdfPTable tabla4 = new PdfPTable(2);
                tabla.setWidthPercentage(100);
                PdfPCell cambio = new PdfPCell(new Phrase("Cambio"));
                cambio.setBackgroundColor(BaseColor.WHITE);
                tabla4.addCell("Cambio");
                tabla4.addCell("C$ " + txtCambio.getText());
                documento.add(tabla4);

                documento.add(Chunk.NEWLINE);

                //TEXTO DE IVA//
                Paragraph j = new Paragraph("Precios incluyen impuestos de venta");
                j.setAlignment(1);
                documento.add(j);
                Paragraph k = new Paragraph("Impuesto pagado C$ " + txtIva.getText());
                k.setAlignment(1);
                documento.add(k);
                Paragraph l = new Paragraph("# ARTS. Vendidos     " + txtCantidad_total.getText());
                l.setAlignment(1);
                documento.add(l);

                documento.add(Chunk.NEWLINE);

                //FECHA Y HORA DE LA VENTA//
                Paragraph fechayhora = new Paragraph(txtFecha_venta.getText() + "     " + txtHora_venta.getText());
                fechayhora.setAlignment(1);
                documento.add(fechayhora);

                documento.add(Chunk.NEWLINE);

                //AGRADECIMIENTO DE LA COMPRA//
                Paragraph f = new Paragraph("GRACIAS POR SU COMPRA");
                f.setAlignment(1);
                documento.add(f);

                documento.close();
                JOptionPane.showMessageDialog(null, "Factura creada con éxito", "Farmacia", 2);
                
                try {
                    File path = new File("C:\\Users\\janca\\Desktop\\" + txtSerie.getText() + ".pdf");
                    Desktop.getDesktop().open(path);
                } catch (Exception e) {

                }
                
            } catch (FileNotFoundException | DocumentException ex) {

            } catch (IOException e) {

            }
            
            mostrar("Productos");
            DefaultTableModel model = new DefaultTableModel();
            model = (DefaultTableModel) tablaDetalles.getModel();
            model.setRowCount(0);
            txtSub_total.setText("");
            txtIva.setText("");
            txtCambio.setText("");
            txtPago_total.setText("");
            txtPago_cliente.setText("");
            txtProducto.setText("");
            txtPrecio.setText("");
            txtCantidad.setText("");
            txtCantidad_total.setText("");
            txtPane.setText("");
            txtSerie.setText("");
            txtStock.setText("");
            btnListar_Producto.setEnabled(true);
            btnEliminar.setEnabled(true);
            btnGuardar_venta.setEnabled(true);
            btnRegresar.setEnabled(true);
            tablaDetalles.setEnabled(true);
            tablaProductos.setEnabled(true);
            txtCantidad.setEditable(true);
            txtBuscar.setEditable(true);

        }
    }

    public void drwobill() {
        txtPane.setText("                     Farmacia La Farmacia \n");
        txtPane.setText(txtPane.getText() + "                             Managua \n");
        txtPane.setText(txtPane.getText() + "                            Nicaragua \n");
        txtPane.setText(txtPane.getText() + "\t        No. " + txtSerie.getText() + "             \n");
        txtPane.setText(txtPane.getText() + "---------------------------------------------------\n");
        txtPane.setText(txtPane.getText() + "Precio Unitario          Cantidad\t      Producto\n");
        txtPane.setText(txtPane.getText() + "---------------------------------------------------\n");

        for (int i = 0; i < tablaDetalles.getRowCount(); i++) {
            String name = tablaDetalles.getValueAt(i, 0).toString();
            String cantandprice = tablaDetalles.getValueAt(i, 1).toString() + "\t" + tablaDetalles.getValueAt(i, 2).toString();
            txtPane.setText(txtPane.getText() + cantandprice + "                   " + name + "\n");
        }

        txtPane.setText(txtPane.getText() + "---------------------------------------------------\n");
        txtPane.setText(txtPane.getText() + "SubTotal : " + txtSub_total.getText() + "\n");
        txtPane.setText(txtPane.getText() + "Total : " + txtPago_total.getText() + "\n");
        double pc = Double.parseDouble(txtPago_cliente.getText());
        txtPane.setText(txtPane.getText() + "Recibido : " + pc + "\n");
        txtPane.setText(txtPane.getText() + "Cambio : " + txtCambio.getText() + "\n");
        txtPane.setText(txtPane.getText() + "\n");
        txtPane.setText(txtPane.getText() + "          Precios incluyen impuestos de venta\n");
        txtPane.setText(txtPane.getText() + "\t     Impuesto pagado C$ " + txtIva.getText() + "\n");
        txtPane.setText(txtPane.getText() + "\t     # ARTS. Vendidos " + txtCantidad_total.getText() + "\n");
        txtPane.setText(txtPane.getText() + "---------------------------------------------------\n");
        txtPane.setText(txtPane.getText() + txtFecha_venta.getText() + "\t\t          " + txtHora_venta.getText() + "\n");
        txtPane.setText(txtPane.getText() + "---------------------------------------------------\n");
        txtPane.setText(txtPane.getText() + "               GRACIAS POR SU COMPRA               \n");
        txtPane.setText(txtPane.getText() + "---------------------------------------------------\n");
    }

    public String NroSerieVentas() {
        String serie = "";
        String sql = "SELECT max(num_factura) FROM Ventas";
        Statement st;
        Conexion conn = new Conexion();
        Connection conexion = conn.getConexion();
        try {
            st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                serie = rs.getString(1);
            }
            conexion.close();
        } catch (Exception e) {
        }

        return serie;
    }

    void generarSerie() {
        String serie = NroSerieVentas();
        if (serie == null) {
            txtSerie.setText("0000001");
        } else {
            int increment = Integer.parseInt(serie);
            increment = increment + 1;
            txtSerie.setText("000000" + increment);
        }
    }

    public void mostrar(String tabla) {
        String sql = "SELECT producto, precio, stock FROM " + tabla;
        Statement st;
        Conexion conn = new Conexion();
        Connection conexion = conn.getConexion();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Producto");
        model.addColumn("Precio");
        model.addColumn("Stock");
        tablaProductos.setModel(model);
        String[] datos = new String[3];
        try {
            st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
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

        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtPane = new javax.swing.JTextPane();
        jLabel4 = new javax.swing.JLabel();
        btnPrint = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaDetalles = new javax.swing.JTable();
        txtProducto = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JLabel();
        btnListar_Producto = new javax.swing.JButton();
        btnNueva_venta = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtPago_cliente = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtSub_total = new javax.swing.JLabel();
        txtIva = new javax.swing.JLabel();
        txtPago_total = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtCantidad_total = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JButton();
        btnGuardar_venta = new javax.swing.JButton();
        btnRegresar = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        txtFecha_venta = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtHora_venta = new javax.swing.JLabel();
        txtSerie = new javax.swing.JLabel();
        txtCambio = new javax.swing.JLabel();
        txtStock = new javax.swing.JLabel();

        jButton2.setText("Eliminar Producto");

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
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(16, 188, 227));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 40)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Farmacia");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/brain-128.png"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 270, 670));

        jPanel2.setBackground(new java.awt.Color(30, 36, 37));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 40)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Registro de Ventas");

        txtPane.setBackground(new java.awt.Color(255, 255, 255));
        txtPane.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtPane.setForeground(new java.awt.Color(0, 0, 0));
        txtPane.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(txtPane);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 3, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Factura");

        btnPrint.setFont(new java.awt.Font("Segoe UI", 3, 13)); // NOI18N
        btnPrint.setForeground(new java.awt.Color(255, 255, 255));
        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/IconPrint.png"))); // NOI18N
        btnPrint.setText("Imprimir");
        btnPrint.setContentAreaFilled(false);
        btnPrint.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

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
                "Producto", "Precio", "Stock"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaProductos.setFocusable(false);
        tablaProductos.getTableHeader().setResizingAllowed(false);
        tablaProductos.getTableHeader().setReorderingAllowed(false);
        tablaProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaProductosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tablaProductosMouseEntered(evt);
            }
        });
        jScrollPane2.setViewportView(tablaProductos);
        if (tablaProductos.getColumnModel().getColumnCount() > 0) {
            tablaProductos.getColumnModel().getColumn(0).setResizable(false);
            tablaProductos.getColumnModel().getColumn(1).setResizable(false);
            tablaProductos.getColumnModel().getColumn(2).setResizable(false);
        }

        jLabel5.setFont(new java.awt.Font("Segoe UI", 3, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/IconBuscar.png"))); // NOI18N
        jLabel5.setText("Buscar");

        txtBuscar.setBackground(new java.awt.Color(30, 36, 37));
        txtBuscar.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtBuscar.setForeground(new java.awt.Color(255, 255, 255));
        txtBuscar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 3, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Detalles de la Venta");

        tablaDetalles = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tablaDetalles.setBackground(new java.awt.Color(30, 36, 37));
        tablaDetalles.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        tablaDetalles.setForeground(new java.awt.Color(255, 255, 255));
        tablaDetalles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Producto", "Precio", "Cantidad", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaDetalles.setFocusable(false);
        tablaDetalles.getTableHeader().setResizingAllowed(false);
        tablaDetalles.getTableHeader().setReorderingAllowed(false);
        tablaDetalles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDetallesMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablaDetalles);
        if (tablaDetalles.getColumnModel().getColumnCount() > 0) {
            tablaDetalles.getColumnModel().getColumn(0).setResizable(false);
            tablaDetalles.getColumnModel().getColumn(1).setResizable(false);
            tablaDetalles.getColumnModel().getColumn(2).setResizable(false);
            tablaDetalles.getColumnModel().getColumn(3).setResizable(false);
        }

        txtProducto.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtProducto.setForeground(new java.awt.Color(255, 255, 255));
        txtProducto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtProducto.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        txtPrecio.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtPrecio.setForeground(new java.awt.Color(255, 255, 255));
        txtPrecio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtPrecio.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        btnListar_Producto.setFont(new java.awt.Font("Segoe UI", 3, 13)); // NOI18N
        btnListar_Producto.setForeground(new java.awt.Color(255, 255, 255));
        btnListar_Producto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/IconListarProducto.png"))); // NOI18N
        btnListar_Producto.setText("Listar Producto");
        btnListar_Producto.setContentAreaFilled(false);
        btnListar_Producto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnListar_Producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListar_ProductoActionPerformed(evt);
            }
        });

        btnNueva_venta.setFont(new java.awt.Font("Segoe UI", 3, 13)); // NOI18N
        btnNueva_venta.setForeground(new java.awt.Color(255, 255, 255));
        btnNueva_venta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/IconNuevaVenta.png"))); // NOI18N
        btnNueva_venta.setText("Nueva Venta");
        btnNueva_venta.setContentAreaFilled(false);
        btnNueva_venta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNueva_venta.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNueva_venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNueva_ventaActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Cantidad");

        txtCantidad.setBackground(new java.awt.Color(30, 36, 37));
        txtCantidad.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtCantidad.setForeground(new java.awt.Color(255, 255, 255));
        txtCantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCantidad.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        txtCantidad.setDisabledTextColor(new java.awt.Color(33, 45, 62));
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadKeyPressed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("SubTotal");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("I.V.A");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Total");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Pago cliente");

        txtPago_cliente.setBackground(new java.awt.Color(30, 36, 37));
        txtPago_cliente.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtPago_cliente.setForeground(new java.awt.Color(255, 255, 255));
        txtPago_cliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPago_cliente.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        txtPago_cliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPago_clienteMouseClicked(evt);
            }
        });
        txtPago_cliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPago_clienteKeyPressed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Cambio");

        txtSub_total.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtSub_total.setForeground(new java.awt.Color(255, 255, 255));
        txtSub_total.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtSub_total.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        txtIva.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtIva.setForeground(new java.awt.Color(255, 255, 255));
        txtIva.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtIva.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        txtPago_total.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtPago_total.setForeground(new java.awt.Color(255, 255, 255));
        txtPago_total.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtPago_total.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("N°Productos");

        txtCantidad_total.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtCantidad_total.setForeground(new java.awt.Color(255, 255, 255));
        txtCantidad_total.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtCantidad_total.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        btnEliminar.setFont(new java.awt.Font("Segoe UI", 3, 13)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/IconEliminar.png"))); // NOI18N
        btnEliminar.setText("Eliminar Producto");
        btnEliminar.setContentAreaFilled(false);
        btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnGuardar_venta.setFont(new java.awt.Font("Segoe UI", 3, 13)); // NOI18N
        btnGuardar_venta.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar_venta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/IconGuardar.png"))); // NOI18N
        btnGuardar_venta.setText("Generar Venta");
        btnGuardar_venta.setContentAreaFilled(false);
        btnGuardar_venta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar_venta.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnGuardar_venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardar_ventaActionPerformed(evt);
            }
        });

        btnRegresar.setFont(new java.awt.Font("Segoe UI", 3, 13)); // NOI18N
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

        jLabel15.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Fecha");

        txtFecha_venta.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtFecha_venta.setForeground(new java.awt.Color(255, 255, 255));
        txtFecha_venta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtFecha_venta.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Hora");

        txtHora_venta.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtHora_venta.setForeground(new java.awt.Color(255, 255, 255));
        txtHora_venta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtHora_venta.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        txtSerie.setBackground(new java.awt.Color(30, 36, 37));
        txtSerie.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtSerie.setForeground(new java.awt.Color(255, 255, 255));
        txtSerie.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtSerie.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        txtCambio.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtCambio.setForeground(new java.awt.Color(255, 255, 255));
        txtCambio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtCambio.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        txtStock.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtStock.setForeground(new java.awt.Color(255, 255, 255));
        txtStock.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtStock.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(347, 347, 347)
                        .addComponent(jLabel3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(720, 720, 720)
                        .addComponent(jLabel4)
                        .addGap(39, 39, 39)
                        .addComponent(txtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(6, 6, 6)
                                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(74, 74, 74)
                                .addComponent(jLabel6))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel11))
                                    .addComponent(txtPago_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabel13)
                                .addGap(46, 46, 46)
                                .addComponent(jLabel10)
                                .addGap(93, 93, 93)
                                .addComponent(jLabel8))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(txtCantidad_total, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(txtSub_total, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(txtIva, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnListar_Producto)
                                    .addComponent(btnNueva_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(37, 37, 37)
                                        .addComponent(jLabel7))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(7, 7, 7)
                                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(btnEliminar))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(btnGuardar_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(147, 147, 147)
                                .addComponent(btnPrint))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(jLabel9)
                        .addGap(85, 85, 85)
                        .addComponent(jLabel12)
                        .addGap(95, 95, 95)
                        .addComponent(jLabel15)
                        .addGap(82, 82, 82)
                        .addComponent(jLabel14))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(txtPago_total, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(txtCambio, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(txtFecha_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(txtHora_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel3)
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(txtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(12, 12, 12)
                        .addComponent(jLabel6)
                        .addGap(6, 6, 6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(6, 6, 6)
                                .addComponent(txtPago_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel10)
                            .addComponent(jLabel8))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCantidad_total, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSub_total, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIva, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(btnListar_Producto)
                        .addGap(6, 6, 6)
                        .addComponent(btnNueva_venta)
                        .addGap(8, 8, 8)
                        .addComponent(jLabel7)
                        .addGap(5, 5, 5)
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73)
                        .addComponent(btnEliminar)
                        .addGap(6, 6, 6)
                        .addComponent(btnGuardar_venta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(btnPrint)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel12)
                    .addComponent(jLabel15)
                    .addComponent(jLabel14))
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPago_total, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCambio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFecha_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHora_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 0, 1040, 670));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        String[] titulos = {"Producto", "Precio", "Stock"};
        String[] registros = new String[200];
        String sql = "SELECT * FROM Productos WHERE producto LIKE '%" + txtBuscar.getText() + "%' "
                + "OR precio LIKE '%" + txtBuscar.getText() + "%' " + "OR stock LIKE '%" + txtBuscar.getText() + "%'";
        DefaultTableModel model = new DefaultTableModel();
        model = new DefaultTableModel(null, titulos);
        Conexion conn = new Conexion();
        Connection conexion = conn.getConexion();
        Statement st;
        try {
            st = (Statement) conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                registros[0] = rs.getString("producto");
                registros[1] = rs.getString("precio");
                registros[2] = rs.getString("stock");
                model.addRow(registros);
            }
            tablaProductos.setModel(model);
            conexion.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_txtBuscarKeyPressed

    private void tablaProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaProductosMouseClicked
        int rec = this.tablaProductos.getSelectedRow();
        this.txtProducto.setText(tablaProductos.getValueAt(rec, 0).toString());
        this.txtPrecio.setText(tablaProductos.getValueAt(rec, 1).toString());
        this.txtStock.setText(tablaProductos.getValueAt(rec, 2).toString());
        btnEliminar.setEnabled(false);
        btnListar_Producto.setEnabled(true);
        txtCantidad.setEnabled(true);
    }//GEN-LAST:event_tablaProductosMouseClicked

    private void btnListar_ProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListar_ProductoActionPerformed
        DProductosImplementacion productos_dao = new DProductosImplementacion();
        Productos productos = new Productos();

        if (txtProducto.getText().equals("") || txtPrecio.getText().equals("") || txtCantidad.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Por favor rellene todos los campos", "Farmacia", 2);
        } else {

            int vstock = Integer.parseInt(txtStock.getText());
            int vcantidad = Integer.parseInt(txtCantidad.getText());

            if (vstock == 0) {
                JOptionPane.showMessageDialog(null, "No hay stock de este producto, contacta con el administrador", "Farmacia", 2);
            } else {

                if (vcantidad == 0) {

                    JOptionPane.showMessageDialog(null, "La cantidad es menor que el stock, intenta de nuevo", "Farmacia", 2);

                } else {

                    if (vcantidad > vstock) {
                        JOptionPane.showMessageDialog(null, "La cantidad es mayor que el stock, intenta de nuevo", "Farmacia", 2);
                    } else {
                        int stock = Integer.parseInt(txtStock.getText());
                        int canti = Integer.parseInt(txtCantidad.getText());

                        int stocknew = stock - canti;
                        String pro = txtProducto.getText();
                        String sn = String.valueOf(stocknew);

                        productos.setProducto(pro);
                        productos.setStock(sn);

                        productos_dao.actualizarStock(productos);
                        mostrar("Productos");

                        String nombre = (txtProducto.getText());
                        double precio = Double.parseDouble(txtPrecio.getText());
                        int cantidad = Integer.parseInt(txtCantidad.getText());
                        double total = precio * cantidad;

                        double sum = 0;
                        int cantotal = 0;

                        DefaultTableModel model = new DefaultTableModel();
                        model = (DefaultTableModel) tablaDetalles.getModel();

                        model.addRow(new Object[]{
                            nombre,
                            precio,
                            cantidad,
                            total,});

                        for (int i = 0; i < tablaDetalles.getRowCount(); i++) {
                            double value = Double.parseDouble(tablaDetalles.getValueAt(i, 3).toString());
                            sum += value;
                            cantotal = cantotal + Integer.parseInt(tablaDetalles.getValueAt(i, 2).toString());
                        }

                        double tt = sum * 1.15;
                        double ivaf = tt - sum;

                        DecimalFormat format = new DecimalFormat("00.0");
                        String t = format.format(tt);
                        String ivaff = format.format(ivaf);
                        double ivafff = Double.parseDouble(ivaff);
                        double ttt = Double.parseDouble(t);

                        txtSub_total.setText(Double.toString(sum));
                        txtPago_total.setText(Double.toString(ttt));
                        txtIva.setText(Double.toString(ivafff));
                        txtCantidad_total.setText(Integer.toString(cantotal));

                        txtProducto.setText("");
                        txtPrecio.setText("");
                        txtCantidad.setText("");
                        txtStock.setText("");
                        btnRegresar.setEnabled(false);
                        btnNueva_venta.setEnabled(false);
                    }
                }
            }
        }
    }//GEN-LAST:event_btnListar_ProductoActionPerformed

    private void btnNueva_ventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNueva_ventaActionPerformed
        mostrar("Productos");
        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) tablaDetalles.getModel();
        model.setRowCount(0);
        txtSub_total.setText("");
        txtIva.setText("");
        txtCambio.setText("");
        txtPago_total.setText("");
        txtPago_cliente.setText("");
        txtProducto.setText("");
        txtPrecio.setText("");
        txtCantidad.setText("");
        txtCantidad_total.setText("");
        txtPane.setText("");
        txtSerie.setText("");
        txtStock.setText("");
        btnListar_Producto.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnGuardar_venta.setEnabled(true);
        btnRegresar.setEnabled(true);
        tablaDetalles.setEnabled(true);
        tablaProductos.setEnabled(true);
        txtCantidad.setEditable(true);
        txtBuscar.setEditable(true);
    }//GEN-LAST:event_btnNueva_ventaActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        DProductosImplementacion productos_dao = new DProductosImplementacion();
        Productos productos = new Productos();

        if (txtProducto.getText().equals("") || txtPrecio.getText().equals("") || txtCantidad.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un producto de la tabla y/o rellenar el campo cantidad", "Farmacia", 2);
        } else {
            String producto = txtProducto.getText();

            productos.setProducto(producto);
            productos_dao.buscarStock(productos);

            txtStock.setText(productos.getStock());

            if (tablaDetalles.getSelectedRow() >= 0) {
                DefaultTableModel tm = (DefaultTableModel) tablaDetalles.getModel();
                int rpta = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar el producto", "Farmacia", 2);
                if (rpta == JOptionPane.YES_OPTION) {

                    int stockB = Integer.parseInt(txtStock.getText());
                    int cantiB = Integer.parseInt(txtCantidad.getText());

                    int stocknew = stockB + cantiB;
                    String pro = txtProducto.getText();
                    String sn = String.valueOf(stocknew);

                    productos.setProducto(pro);
                    productos.setStock(sn);

                    productos_dao.actualizarStockTwo(productos);
                    tm.removeRow(tablaDetalles.getSelectedRow());
                    JOptionPane.showMessageDialog(null, "Producto eliminado con Éxito", "Farmacia", 2);

                    txtProducto.setText("");
                    txtPrecio.setText("");
                    txtStock.setText("");
                    txtCantidad.setText("");
                    mostrar("Productos");

                    double sum = 0;
                    int cantotal = 0;

                    for (int i = 0; i < tablaDetalles.getRowCount(); i++) {
                        double value = Double.parseDouble(tablaDetalles.getValueAt(i, 3).toString());
                        sum += value;
                        cantotal = cantotal + Integer.parseInt(tablaDetalles.getValueAt(i, 2).toString());
                    }

                    double tt = sum * 1.15;
                    double ivaf = tt - sum;

                    DecimalFormat format = new DecimalFormat("00.0");
                    String t = format.format(tt);
                    String ivaff = format.format(ivaf);
                    double ivafff = Double.parseDouble(ivaff);
                    double ttt = Double.parseDouble(t);

                    txtSub_total.setText(Double.toString(sum));
                    txtPago_total.setText(Double.toString(ttt));
                    txtIva.setText(Double.toString(ivafff));
                    txtCantidad_total.setText(Integer.toString(cantotal));
                    
                    if (txtPago_total.getText().equals("0.0") || txtCantidad_total.getText().equals("0")
                            || txtSub_total.getText().equals("0.0") || txtIva.getText().equals("0.0")){
                        txtPago_total.setText("");
                        txtCantidad_total.setText("");
                        txtSub_total.setText("");
                        txtIva.setText("");
                        
                        if (txtPago_total.getText().contains("")){
                            btnRegresar.setEnabled(true);
                            btnNueva_venta.setEnabled(true);
                        } else {
                            btnRegresar.setEnabled(false);
                            btnNueva_venta.setEnabled(false);
                        }
                    } else {
                        
                    }
                    
                    txtProducto.setText("");
                    txtPrecio.setText("");
                    txtStock.setText("");
                    txtCantidad.setText("");
                    txtCantidad.setEnabled(true);
                    btnListar_Producto.setEnabled(true);
                   
                    
                } else {
                    txtProducto.setText("");
                    txtPrecio.setText("");
                    txtStock.setText("");
                    txtCantidad.setText("");
                    txtCantidad.setEnabled(true);
                    btnListar_Producto.setEnabled(true);
                }

            }

        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnGuardar_ventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardar_ventaActionPerformed
        DVentasImplementacion ventas_dao = new DVentasImplementacion();
        Ventas ventas = new Ventas();

        if (txtPago_cliente.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Rellene el campo del pago y/o la tabla con productos", "Farmacia", 2);
        } else {
            double pc = Double.parseDouble(txtPago_cliente.getText());
            double pt = Double.parseDouble(txtPago_total.getText());

            DecimalFormat format = new DecimalFormat("000.0");
            String pcc = format.format(pc);
            String ptt = format.format(pt);

            double pccc = Double.parseDouble(pcc);
            double pttt = Double.parseDouble(ptt);

            if (pc < pt) {
                JOptionPane.showMessageDialog(null, "El pago es menor que el total", "Farmacia", 2);
            } else {
                generarSerie();

                double c = pccc - pttt;

                String cc = format.format(c);
                double ccc = Double.parseDouble(cc);

                String npt = String.valueOf(pttt);
                String npc = String.valueOf(pccc);

                txtCambio.setText(Double.toString(ccc));
                drwobill();

                String factura = txtSerie.getText();
                String cantT = txtCantidad_total.getText();
                String subT = txtSub_total.getText();
                String ivaT = txtIva.getText();
                String cambioT = txtCambio.getText();
                String fecha = txtFecha_venta.getText();
                String hora = txtHora_venta.getText();

                ventas.setNum_factura(factura);
                ventas.setCantidad_productos(cantT);
                ventas.setSub_total(subT);
                ventas.setIva(ivaT);
                ventas.setPago_total(npt);
                ventas.setPago_cliente(npc);
                ventas.setCambio(cambioT);
                ventas.setFecha_venta(fecha);
                ventas.setHora_venta(hora);

                ventas_dao.registrarVenta(ventas);

                JOptionPane.showMessageDialog(null, "Venta Guardada con Éxito", "Farmacia", 2);
                btnListar_Producto.setEnabled(false);
                btnEliminar.setEnabled(false);
                btnGuardar_venta.setEnabled(false);
                btnRegresar.setEnabled(true);
                btnNueva_venta.setEnabled(true);
                tablaDetalles.setEnabled(false);
                tablaProductos.setEnabled(false);
                txtBuscar.setEditable(false);
            }
        }
    }//GEN-LAST:event_btnGuardar_ventaActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        generarPDF();
    }//GEN-LAST:event_btnPrintActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        if (txtPane.getText().equals("")) {
            FrmPrincipal a = new FrmPrincipal();
            a.setVisible(true);
            this.setVisible(false);
            this.dispose();
        } else {
            int rpta = JOptionPane.showConfirmDialog(this, "Existe una factura previa, ¿Deseas imprimirla?", "Farmacia", 2);
            if (rpta == JOptionPane.YES_OPTION) {
                generarPDF();
                FrmPrincipal a = new FrmPrincipal();
                a.setVisible(true);
                this.setVisible(false);
                this.dispose();
            } else {
                FrmPrincipal a = new FrmPrincipal();
                a.setVisible(true);
                this.setVisible(false);
                this.dispose();
            }
        }
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

    private void txtCantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyPressed
        char car = evt.getKeyChar();

        if ((car < '0' || car > '9') && (car != (char) KeyEvent.VK_BACK_SPACE)) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Solo se admiten números", "Farmacia", 2);
            txtCantidad.setText("");
        } else if ((car < '0' || car > '9') && (car != '.')
                && (car != (char) KeyEvent.VK_BACK_SPACE)) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Solo se admiten números", "Farmacia", 2);
            txtCantidad.setText("");
        }
    }//GEN-LAST:event_txtCantidadKeyPressed

    private void txtPago_clienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPago_clienteKeyPressed
        char car = evt.getKeyChar();
        
        if ((car < '0' || car > '9') && txtPago_cliente.getText().contains(".")
                && (car != (char) KeyEvent.VK_BACK_SPACE)) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Solo se admiten números", "Farmacia", 2);
            txtPago_cliente.setText("");
        } else if ((car < '0' || car > '9') && (car != '.')
                && (car != (char) KeyEvent.VK_BACK_SPACE)) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Solo se admiten números", "Farmacia", 2);
            txtPago_cliente.setText("");
        }
    }//GEN-LAST:event_txtPago_clienteKeyPressed

    private void tablaDetallesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDetallesMouseClicked
        int rec = this.tablaDetalles.getSelectedRow();
        this.txtProducto.setText(tablaDetalles.getValueAt(rec, 0).toString());
        this.txtPrecio.setText(tablaDetalles.getValueAt(rec, 1).toString());
        this.txtCantidad.setText(tablaDetalles.getValueAt(rec, 2).toString());
        txtCantidad.setEnabled(false);
        btnListar_Producto.setEnabled(false);
        btnEliminar.setEnabled(true);
    }//GEN-LAST:event_tablaDetallesMouseClicked

    private void tablaProductosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaProductosMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaProductosMouseEntered

    private void txtPago_clienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPago_clienteMouseClicked
        
    }//GEN-LAST:event_txtPago_clienteMouseClicked

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
            java.util.logging.Logger.getLogger(FrmVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmVentas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar_venta;
    private javax.swing.JButton btnListar_Producto;
    private javax.swing.JButton btnNueva_venta;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tablaDetalles;
    private javax.swing.JTable tablaProductos;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JLabel txtCambio;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JLabel txtCantidad_total;
    private javax.swing.JLabel txtFecha_venta;
    private javax.swing.JLabel txtHora_venta;
    private javax.swing.JLabel txtIva;
    private javax.swing.JTextField txtPago_cliente;
    private javax.swing.JLabel txtPago_total;
    private javax.swing.JTextPane txtPane;
    private javax.swing.JLabel txtPrecio;
    private javax.swing.JLabel txtProducto;
    private javax.swing.JLabel txtSerie;
    private javax.swing.JLabel txtStock;
    private javax.swing.JLabel txtSub_total;
    // End of variables declaration//GEN-END:variables

}
