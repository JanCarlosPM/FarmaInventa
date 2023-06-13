package Interfaces;

import Dao.DPersonalImplementacion;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JOptionPane;
import modelos.Usuario;

public class FrmLogin extends javax.swing.JFrame {
    
    /**
     * Creates new form FrmLogin
     */
    public FrmLogin() {
        initComponents();
        this.setLocationRelativeTo(null);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 27, 27));
        this.dispose();
        setTitle("Login");
    }

    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Iconos/brain-128.png"));
        return retValue;
    }

    private void limpiar() {
        txtUsuario.setText(null);
        txtContraseña.setText(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        txtContraseña = new javax.swing.JPasswordField();
        btnIngresar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnSalir = new javax.swing.JButton();
        btnRegistrarse = new javax.swing.JButton();
        OCULTAR = new javax.swing.JLabel();
        VER = new javax.swing.JLabel();
        txtRecuperarPass = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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

        jPanel2.setBackground(new java.awt.Color(0, 242, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 60)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Farmacia");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/brain-128.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(104, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(164, 164, 164))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(91, 91, 91))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(jLabel2)
                .addContainerGap(184, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 460, -1));

        jPanel4.setBackground(new java.awt.Color(30, 36, 37));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 40)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Iniciar Sesión");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(99, 66, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 3, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Usuario");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(53, 171, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 3, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Contraseña");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(53, 276, -1, -1));

        txtUsuario.setBackground(new java.awt.Color(30, 36, 37));
        txtUsuario.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtUsuario.setForeground(new java.awt.Color(255, 255, 255));
        txtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUsuarioKeyPressed(evt);
            }
        });
        jPanel4.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(112, 208, 200, 32));

        txtContraseña.setBackground(new java.awt.Color(30, 36, 37));
        txtContraseña.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtContraseña.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.add(txtContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(117, 321, 195, -1));

        btnIngresar.setBackground(new java.awt.Color(73, 181, 172));
        btnIngresar.setFont(new java.awt.Font("Segoe UI", 3, 20)); // NOI18N
        btnIngresar.setForeground(new java.awt.Color(255, 255, 255));
        btnIngresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/icons8_login_30px.png"))); // NOI18N
        btnIngresar.setText("Ingresar");
        btnIngresar.setContentAreaFilled(false);
        btnIngresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIngresar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnIngresar.setIconTextGap(5);
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });
        jPanel4.add(btnIngresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(53, 387, 140, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/user.png"))); // NOI18N
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(74, 208, -1, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/password.png"))); // NOI18N
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(79, 321, -1, -1));

        btnSalir.setBackground(new java.awt.Color(73, 181, 172));
        btnSalir.setFont(new java.awt.Font("Segoe UI", 3, 20)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/icons8_power_off_button_24px.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.setContentAreaFilled(false);
        btnSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalir.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSalir.setIconTextGap(5);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel4.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 443, 100, 38));

        btnRegistrarse.setBackground(new java.awt.Color(73, 181, 172));
        btnRegistrarse.setFont(new java.awt.Font("Segoe UI", 3, 20)); // NOI18N
        btnRegistrarse.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/icons8_login_rounded_up_24px.png"))); // NOI18N
        btnRegistrarse.setText("Registrarse");
        btnRegistrarse.setContentAreaFilled(false);
        btnRegistrarse.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRegistrarse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarseActionPerformed(evt);
            }
        });
        jPanel4.add(btnRegistrarse, new org.netbeans.lib.awtextra.AbsoluteConstraints(271, 388, -1, 37));

        OCULTAR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/IconInvisible.png"))); // NOI18N
        OCULTAR.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                OCULTARMouseClicked(evt);
            }
        });
        jPanel4.add(OCULTAR, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 320, 40, 30));

        VER.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/IconVisible.png"))); // NOI18N
        VER.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                VERMouseClicked(evt);
            }
        });
        jPanel4.add(VER, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 320, 40, 30));

        txtRecuperarPass.setBackground(new java.awt.Color(73, 181, 172));
        txtRecuperarPass.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        txtRecuperarPass.setForeground(new java.awt.Color(255, 255, 255));
        txtRecuperarPass.setText("¿Olvidó su Contraseña?");
        txtRecuperarPass.setContentAreaFilled(false);
        txtRecuperarPass.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtRecuperarPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRecuperarPassActionPerformed(evt);
            }
        });
        jPanel4.add(txtRecuperarPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 350, -1, -1));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(456, 0, 470, 500));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed
  
    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
        DPersonalImplementacion usuario_dao = new DPersonalImplementacion();
        Usuario usuario = new Usuario();

        if (txtUsuario.getText().equals("") || txtContraseña.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Por Favor Rellene todos los Campos", "Farmacia", 2);
        } else {
            
            String user = txtUsuario.getText();
            String pass = txtContraseña.getText();
            usuario.setUsuario(user);
            usuario.setContraseña(pass);
            usuario_dao.buscarUser(usuario);

            txtUsuario.setText(usuario.getUsuario());
            txtContraseña.setText(usuario.getContraseña());

            limpiar();

            this.setVisible(false);
            this.dispose();
        }
    }//GEN-LAST:event_btnIngresarActionPerformed

    private void btnRegistrarseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarseActionPerformed
        FrmRegistrarse e = new FrmRegistrarse();
        e.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnRegistrarseActionPerformed
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

    private void OCULTARMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OCULTARMouseClicked
        OCULTAR.setVisible(false);
        VER.setVisible(true);
        txtContraseña.setEchoChar((char) 0);
    }//GEN-LAST:event_OCULTARMouseClicked

    private void VERMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VERMouseClicked
        OCULTAR.setVisible(true);
        VER.setVisible(false);
        txtContraseña.setEchoChar('*');
    }//GEN-LAST:event_VERMouseClicked

    private void txtRecuperarPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRecuperarPassActionPerformed
        FrmRecuperarPass p = new FrmRecuperarPass();
        p.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_txtRecuperarPassActionPerformed

    private void txtUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyPressed

    }//GEN-LAST:event_txtUsuarioKeyPressed

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
            java.util.logging.Logger.getLogger(FrmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel OCULTAR;
    private javax.swing.JLabel VER;
    private javax.swing.JButton btnIngresar;
    private javax.swing.JButton btnRegistrarse;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPasswordField txtContraseña;
    private javax.swing.JButton txtRecuperarPass;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}