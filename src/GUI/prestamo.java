 package GUI;
 
 import java.awt.Container;
 import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.io.PrintStream;
 import java.math.BigInteger;
 import java.security.MessageDigest;
 import java.security.NoSuchAlgorithmException;
 import java.sql.Connection;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.sql.Statement;
 import java.text.DateFormat;
 import java.text.SimpleDateFormat;
 import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
 import javax.swing.JInternalFrame;
 import javax.swing.JLabel;
 import javax.swing.JList;
 import javax.swing.JOptionPane;
 import javax.swing.JPanel;
 import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
 import javax.swing.JTextField;
 import quick.dbtable.DBTable;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import javax.swing.JTable;
 
 
 
 
 
 
 
 
 public class prestamo
   extends JInternalFrame
 {
   private JPanel panelSesion;
   private JTextField fieldUsuario;
   private JTextField fieldContra;
   private JPanel panelPrestamo;
   private DBTable tabla;
   private JTextField tipo_doc;
   private JTextField documento;
   private JTextField monto;
   private JPanel panel;
   private JList list;
   private JTextArea textCuota;
   private JTextArea textTasa;
   private Float interes;
   private Float valorCuota;
   private JTextField textField;
   private JTextField textField_1;
   private DBTable tablaCuotas;
   private JPanel panelInfoPrestamos;
   private JPanel panelBotones;
   private JPanel panelImpagos;
   private DBTable tablaMorosos;
   private JPanel panelMorosos;
   
   public prestamo()
   {
     initialize();
   }
   
   private void initialize()
   {
     setBounds(500, 250, 899, 592);
     setDefaultCloseOperation(3);
     getContentPane().setLayout(null);
     
 
     this.panelSesion = new JPanel();
     this.panelSesion.setVisible(true);
     
 
     this.panelPrestamo = new JPanel();
     this.panelPrestamo.setVisible(false);
     
     this.panelMorosos = new JPanel();
     this.panelMorosos.setBounds(10, 76, 863, 448);
     getContentPane().add(this.panelMorosos);
     this.panelMorosos.setVisible(false);
     
     this.tablaMorosos = new DBTable();
     tablaMorosos.getTable().setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
     this.panelMorosos.add(this.tablaMorosos);
     this.tablaMorosos.setEditable(false);
     this.panelImpagos = new JPanel();
     this.panelImpagos.setBounds(10, 76,873, 550);
     getContentPane().add(this.panelImpagos);
     this.panelImpagos.setLayout(null);
     this.panelImpagos.setVisible(false);
     
     
     this.textField = new JTextField();
     this.textField.setBounds(89, 32, 86, 20);
     this.panelImpagos.add(this.textField);
     this.textField.setColumns(10);
     
     this.textField_1 = new JTextField();
     this.textField_1.setBounds(89, 63, 86, 20);
     this.panelImpagos.add(this.textField_1);
     this.textField_1.setColumns(10);
     
     JLabel lblTipoDoc = new JLabel("Tipo doc");
     lblTipoDoc.setHorizontalAlignment(SwingConstants.RIGHT);
     lblTipoDoc.setBounds(19, 35, 64, 14);
     this.panelImpagos.add(lblTipoDoc);
     
     JLabel lblDocumento_1 = new JLabel("Documento");
     lblDocumento_1.setBounds(10, 66, 73, 14);
     this.panelImpagos.add(lblDocumento_1);
     
     JButton btnVerCuotasImpagas = new JButton("Ver Cuotas Impagas");
     btnVerCuotasImpagas.setBounds(22, 95, 156, 23);
     this.panelImpagos.add(btnVerCuotasImpagas);
     btnVerCuotasImpagas.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent arg0) {
         String tipoDoc = prestamo.this.textField.getText();
         String doc = prestamo.this.textField_1.getText();

         textField.setEditable(false);
         textField_1.setEditable(false);
         
         prestamo.this.cargarTablaCuotasImpagas(tipoDoc, doc);
       }
       
     });
     this.panelInfoPrestamos = new JPanel();
     this.panelInfoPrestamos.setBounds(217, 28, 632, 404);
     this.panelImpagos.add(this.panelInfoPrestamos);
     this.panelInfoPrestamos.setLayout(null);
     
     JButton btnNewButton = new JButton("Pagar Seleccionadas");
     btnNewButton.setBounds(254, 345, 153, 23);
     this.panelInfoPrestamos.add(btnNewButton);
     btnNewButton.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent arg0) {
         String tipo = prestamo.this.textField.getText();
         String doc = prestamo.this.textField_1.getText();
         prestamo.this.pagarCuotas(tipo, doc);
       }
       
     });
     this.tablaCuotas = new DBTable();
     tablaCuotas.getTable().setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
     this.tablaCuotas.setBounds(33, 24, 576, 280);
     this.tablaCuotas.setEditable(false);
     this.panelInfoPrestamos.add(this.tablaCuotas);
     this.panelPrestamo.setEnabled(false);
     this.panelPrestamo.setBounds(10, 76, 863, 448);
     getContentPane().add(this.panelPrestamo);
     this.panelPrestamo.setLayout(null);
     
 
 
     this.tabla = new DBTable();
     this.panelPrestamo.add(this.tabla);
     this.tabla.setBounds(782, 299, 79, 100);
     this.tabla.setEditable(false);
     this.tabla.setVisible(false);
     
     this.tipo_doc = new JTextField();
     this.tipo_doc.setBounds(89, 32, 86, 20);
     this.panelPrestamo.add(this.tipo_doc);
     this.tipo_doc.setColumns(10);
     
     this.documento = new JTextField();
     this.documento.setBounds(89, 63, 86, 20);
     this.panelPrestamo.add(this.documento);
     this.documento.setColumns(10);
     
     
     
     JLabel tipo_doc1 = new JLabel("Tipo doc");
     tipo_doc1.setHorizontalAlignment(SwingConstants.RIGHT);
     tipo_doc1.setBounds(19, 35, 64, 14);
     this.panelPrestamo.add(tipo_doc1);
     
     JLabel lblDocumento = new JLabel("Documento");
     lblDocumento.setHorizontalAlignment(SwingConstants.RIGHT);
     lblDocumento.setBounds(10, 66, 73, 14);
     this.panelPrestamo.add(lblDocumento);
     
     JButton btnCheckearPrestamos = new JButton("Chequear");
     btnCheckearPrestamos.setBounds(22, 95, 156, 23);
     this.panelPrestamo.add(btnCheckearPrestamos);
     btnCheckearPrestamos.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent arg0) {
         String tipoDoc = prestamo.this.tipo_doc.getText();
         String doc = prestamo.this.documento.getText();
         
         tipo_doc.setEditable(false);
         documento.setEditable(false);
         //Limpiar campos
    	 monto.setText("");
    	 list.setListData(new String[]{});
    	 textTasa.setText("");
    	 textCuota.setText("");
         
         prestamo.this.checkearPrestamos(tipoDoc, doc);
       }
       
     });
     this.panel = new JPanel();
     panel.setBorder(new BevelBorder(BevelBorder.RAISED, Color.GRAY, null, null, null));
     this.panel.setBounds(206, 11, 647, 415);
     this.panelPrestamo.add(this.panel);
     this.panel.setVisible(false);
     this.panel.setLayout(null);
     
     this.monto = new JTextField();
     this.monto.setBounds(61, 11, 86, 20);
     this.panel.add(this.monto);
     this.monto.setColumns(10);
     
     this.list = new JList();
     this.list.setBounds(10, 64, 149, 209);
     
    
     
     JScrollPane scrollPane = new JScrollPane();
     scrollPane.setViewportView(list);
     scrollPane.setBounds(10, 64, 149, 209);
     panel.add(scrollPane);
     
     JLabel lblMonto = new JLabel("Monto");
     lblMonto.setBounds(17, 14, 46, 14);
     this.panel.add(lblMonto);
     
     this.textTasa = new JTextArea();
     this.textTasa.setBounds(282, 82, 127, 23);
     this.panel.add(this.textTasa);
     
     this.textCuota = new JTextArea();
     this.textCuota.setBounds(282, 167, 127, 23);
     this.panel.add(this.textCuota);
     
     JLabel lblTasaInteres = new JLabel("Tasa interes");
     lblTasaInteres.setHorizontalAlignment(SwingConstants.CENTER);
     lblTasaInteres.setBounds(307, 65, 89, 14);
     this.panel.add(lblTasaInteres);
     
     JLabel lblValorCuota = new JLabel("Valor cuota");
     lblValorCuota.setHorizontalAlignment(SwingConstants.CENTER);
     lblValorCuota.setBounds(307, 153, 89, 13);
     this.panel.add(lblValorCuota);
     
     JButton btnCrearPrestamo = new JButton("Crear prestamo");
     btnCrearPrestamo.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         prestamo.this.solicitarPrestamo();
       }
     });
     btnCrearPrestamo.setBounds(463, 121, 117, 23);
     this.panel.add(btnCrearPrestamo);
     
     JButton btnCalcular = new JButton("Calcular");
     btnCalcular.setBounds(182, 121, 89, 23);
     this.panel.add(btnCalcular);
     
     JLabel lblIcono = new JLabel("Nro Cuotas");
     lblIcono.setBounds(43, 43, 80, 16);
     this.panel.add(lblIcono);
     btnCalcular.addActionListener(new ActionListener()
     {
       public void actionPerformed(ActionEvent arg0) {
         String mntAux = prestamo.this.monto.getText();
         if (prestamo.this.list.isSelectionEmpty()) {
           JOptionPane.showMessageDialog(prestamo.this.panelSesion, "Seleccione cantidad de cuotas");
         }
         else {
           String mesesAux = prestamo.this.list.getSelectedValue().toString();
           
           if (mntAux.isEmpty()) {
             JOptionPane.showMessageDialog(prestamo.this.panelSesion, "Ingrese monto");
           }
           else if (!mesesAux.isEmpty())
           {
 
             int mnt = Integer.parseInt(mntAux);
             int meses = Integer.parseInt(mesesAux);
             prestamo.this.calcular(mnt, meses);
           }
           
         }
         
       }
       
     });
     this.tipo_doc.setBounds(89, 32, 86, 20);
     this.documento.setBounds(89, 63, 86, 20);
     tipo_doc1.setHorizontalAlignment(SwingConstants.RIGHT);
     tipo_doc1.setBounds(19, 35, 64, 14);
     lblDocumento.setHorizontalAlignment(SwingConstants.RIGHT);
     lblDocumento.setBounds(10, 66, 73, 14);
     
     JLabel lblNewLabel = new JLabel("");
     lblNewLabel.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/Imagenes/Wallet-512.png")).getImage().getScaledInstance(103, 123, Image.SCALE_DEFAULT)));
     lblNewLabel.setBounds(309, 70, 107, 136);
     panelSesion.add(lblNewLabel);
     this.panelSesion.setBounds(219, 88, 422, 282);
     getContentPane().add(this.panelSesion);
     this.panelSesion.setLayout(null);
     
 
 
 
     this.fieldUsuario = new JTextField();
     this.fieldUsuario.setBounds(181, 117, 116, 20);
     this.panelSesion.add(this.fieldUsuario);
     this.fieldUsuario.setColumns(10);
     
     this.fieldContra = new JPasswordField();
     this.fieldContra.setBounds(181, 149, 116, 20);
     this.panelSesion.add(this.fieldContra);
     this.fieldContra.setColumns(10);
     
     JButton btIngresar = new JButton("Ingresar");
     btIngresar.setBounds(206, 174, 88, 20);
     this.panelSesion.add(btIngresar);
     btIngresar.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent arg0) {
         String usuario = prestamo.this.fieldUsuario.getText();
         String contra = prestamo.this.fieldContra.getText();
         prestamo.this.conectarBD(usuario, contra);
       }
       
     });
     JLabel lblUsuario = new JLabel("Usuario");
     lblUsuario.setHorizontalAlignment(4);
     lblUsuario.setBounds(91, 120, 81, 14);
     this.panelSesion.add(lblUsuario);
     
     JLabel lblContra = new JLabel("Contraseña");
     lblContra.setHorizontalAlignment(4);
     lblContra.setBounds(100, 151, 71, 14);
     this.panelSesion.add(lblContra);
     
     JLabel lblBienvenidoAlSistema = new JLabel("Préstamos");
     lblBienvenidoAlSistema.setHorizontalAlignment(0);
     lblBienvenidoAlSistema.setBounds(67, 83, 303, 22);
     this.panelSesion.add(lblBienvenidoAlSistema);
     lblBienvenidoAlSistema.setFont(new Font("Tahoma", 0, 18));
     
     this.panelBotones = new JPanel();
     this.panelBotones.setBounds(10, 0, 863, 53);
     getContentPane().add(this.panelBotones);
     this.panelBotones.setLayout(null);
     this.panelBotones.setVisible(false);
     
     JButton button = new JButton("Pago Cuotas");
     button.setBounds(366, 11, 117, 23);
     this.panelBotones.add(button);
     button.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent arg0) {
    	   
    	 //Limpiar campos
    	 textField.setEditable(true);
         textField_1.setEditable(true);
    	 textField.setText("");
    	 textField_1.setText("");
    	 tablaCuotas.removeAllRows();
    	 
         prestamo.this.panelInfoPrestamos.setVisible(false);
         prestamo.this.panelImpagos.setVisible(true);
         prestamo.this.panelPrestamo.setVisible(false);
         prestamo.this.panelMorosos.setVisible(false);
       }
       
 
     });
     JButton button_1 = new JButton("Morosos");
     button_1.setBounds(565, 11, 117, 23);
     this.panelBotones.add(button_1);
     button_1.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent arg0) {
         prestamo.this.panelMorosos.setVisible(true);
         prestamo.this.panelImpagos.setVisible(false);
         prestamo.this.panelPrestamo.setVisible(false);
         prestamo.this.cargarTablaMorosos();
       }
       
     });
     JButton button_2 = new JButton("Solicitar Prestamo");
     button_2.setBounds(145, 11, 143, 23);
     this.panelBotones.add(button_2);
     button_2.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent arg0) {
    	   
    	   tipo_doc.setEditable(true);
           documento.setEditable(true);

    	   
    	 //Limpiar campos
      	 documento.setText("");
      	 tipo_doc.setText("");
      	 
         prestamo.this.panel.setVisible(false);
         prestamo.this.panelImpagos.setVisible(false);
         prestamo.this.panelMorosos.setVisible(false);
         prestamo.this.panelPrestamo.setVisible(true);
       }
     });
   }
   
 
 
 
 
   public void conectarBD(String usuario2, String clave2)
   {
     try
     {
       Class.forName("com.mysql.jdbc.Driver").newInstance();
     } catch (Exception ex) { System.out.println("error new instance");
     }
     String servidor = "localhost:3306";
     String baseDatos = "banco";
     String url = "jdbc:mysql://" + servidor + "/" + baseDatos;
     String driver = "com.mysql.jdbc.Driver";
     String usuario = "empleado";
     String clave = "empleado";
     try
     {
       this.tabla.connectDatabase(driver, url, usuario, clave);
       
 
       String consulta = "SELECT password FROM empleado WHERE legajo = " + usuario2 + ";";
       Statement stmt = this.tabla.getConnection().createStatement();
       stmt.execute(consulta);
       ResultSet rs = stmt.getResultSet();
       rs.next();
       String password = rs.getString(1);
       if (password.equals(getMD5(clave2))) {
         mostrarOpciones();
       }
       else
       {
         JOptionPane.showMessageDialog(this.panelSesion, "Error Usuario/Contraseña");
       }
       
     }
     catch (SQLException ex)
     {
       JOptionPane.showMessageDialog(this.panelSesion, "Error Usuario/Contraseña");
     }
     catch (ClassNotFoundException e)
     {
       e.printStackTrace();
     }
   }
   
   public void mostrarOpciones() {
     this.panelSesion.setVisible(false);
     this.panelBotones.setVisible(true);
   }
   
   public static String getMD5(String input) {
     try {
       MessageDigest md = MessageDigest.getInstance("MD5");
       byte[] messageDigest = md.digest(input.getBytes());
       BigInteger number = new BigInteger(1, messageDigest);
       String hashtext = number.toString(16);
       
       while (hashtext.length() < 32) {
         hashtext = "0" + hashtext;
       }
       return hashtext;
     }
     catch (NoSuchAlgorithmException e) {
       throw new RuntimeException(e);
     }
   }
   
   public void checkearPrestamos(String tipoDoc, String doc) {
     try {
       Statement stm = this.tabla.getConnection().createStatement();
       
 
       String consulta = "Select nro_doc from Cliente where nro_doc=" + doc + " and tipo_doc='" + tipoDoc + "';";
       ResultSet rs = stm.executeQuery(consulta);
       if (!rs.next())
       {
         JOptionPane.showMessageDialog(this.panelSesion, "El dni ingresado no corresponde a un cliente.");
       }
       else {
         consulta = "Select nro_doc from prestamo NATURAL JOIN cliente WHERE nro_doc =" + doc + " and tipo_doc='" + tipoDoc + "';";
         rs = stm.executeQuery(consulta);
         if (!rs.next())
         {
           this.panel.setVisible(true);
           cargarListaMeses();
         }
         else {
           JOptionPane.showMessageDialog(this.panelSesion, "El cliente ingresado ya tiene un prestamo asignado");
           documento.setText("");
           tipo_doc.setText("");
           documento.setEditable(true);
           tipo_doc.setEditable(true);
         }
       }
     } catch (Exception ex) {
       JOptionPane.showMessageDialog(this.panelSesion, "Error tipoDoc/nroDoc.");
     }
   }
   
   public void cargarListaMeses() {
     try {
       Statement stm = this.tabla.getConnection().createStatement();
       String consulta = "Select DISTINCT periodo from tasa_prestamo;";
       ResultSet rs = stm.executeQuery(consulta);
       String[] arr = new String[50];
       
       int i = 0;
       while (rs.next()) {
         arr[i] = rs.getString(1);
         i++;
       }
       
       this.list.setListData(arr);
     }
     catch (SQLException localSQLException) {}
   }
   
   public void calcular(int monto, int meses)
   {
     try {
       Statement stm = this.tabla.getConnection().createStatement();
       String consulta = "Select tasa FROM tasa_prestamo WHERE periodo =" + meses + " AND monto_inf<=" + monto + " and monto_sup>=" + monto;
       ResultSet rs = stm.executeQuery(consulta);
       String tasaAux = null;
       if (!rs.next()) {
         JOptionPane.showMessageDialog(this.panelSesion, "El monto no es correcto para la cantidad de cuotas indicada");
       }
       else {
         tasaAux = rs.getString(1);
         Float tasa = Float.valueOf(Float.parseFloat(tasaAux));
         
         this.interes = Float.valueOf(monto * tasa.floatValue() * meses / 1200.0F);
         this.valorCuota = Float.valueOf((monto + this.interes.floatValue()) / meses);
         
         this.textTasa.setText(tasa.toString());
         this.textCuota.setText(this.valorCuota.toString());
       }
       
     }
     catch (SQLException e)
     {
       JOptionPane.showMessageDialog(this.panelSesion, "hubo un error");
     }
   }
   
 
   public void solicitarPrestamo()
   {
     try
     {
       DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
       
       Date date = new Date();
       String fechaActual = dateFormat.format(date);
       String ff = "";
       for (int i = 0; i < 10; i++) {
         char c = fechaActual.charAt(i);
         if (c != '/') {
           ff = ff + c;
         }
       }
       
       String meses = this.list.getSelectedValue().toString();
       String mont = this.monto.getText();
       String tasaI = this.textTasa.getText();
       
 
       String consulta = "select nro_cliente from Cliente where nro_doc =" + this.documento.getText() + ";";
       Statement stmt = this.tabla.getConnection().createStatement();
       
       boolean a = stmt.execute(consulta);
       ResultSet rs = stmt.getResultSet();
       rs.next();
       String nroCli = rs.getString(1);
       
 
       String sql = "INSERT INTO Prestamo (fecha,cant_meses,monto,tasa_interes,interes,valor_cuota,legajo,nro_cliente) VALUES (" + 
         ff + "," + meses + "," + mont + "," + tasaI + "," + this.interes.toString() + "," + this.valorCuota.toString() + "," + this.fieldUsuario.getText() + "," + nroCli + ");";
       
       stmt.execute(sql);
       
       sql = "select nro_prestamo from prestamo where nro_cliente =" + nroCli;
       stmt.execute(sql);
       rs = stmt.getResultSet();
       rs.next();
       String nroPrestamo = rs.getString(1);
       
 
 /* REEMPLAZADO ESTE CODIGO POR EL USO DEL TRIGGER
 
       sql = "INSERT INTO Pago VALUES (" + nroPrestamo + "," + 1 + ",date_add(" + ff + ",interval 1 month)," + "NULL);";
       stmt.execute(sql);
       
       for (int i = 2; i <= Integer.parseInt(meses); i++) {
         sql = "select fecha_venc from pago where nro_prestamo =" + nroPrestamo + " and nro_pago =" + (i - 1) + ";";
         stmt.execute(sql);
         rs = stmt.getResultSet();
         rs.next();
         String fecha = rs.getString(1);
         
         String f = "";
         for (int j = 0; j < 10; j++) {
           char c = fecha.charAt(j);
           if (c != '-') {
             f = f + c;
           }
         }
         
         sql = "INSERT INTO Pago VALUES (" + nroPrestamo + "," + i + ",date_add(" + f + ",interval 1 month)," + "NULL);";
         stmt.execute(sql);
       }
       
 */
       
       
       
       
       
       JOptionPane.showMessageDialog(this.panelSesion, "Prestamo creado correctamente");
       this.panel.setVisible(false);
       tipo_doc.setEditable(true);
       documento.setEditable(true);
       tipo_doc.setText("");
       documento.setText("");
       monto.setText("");
       list.setListData(new String[]{});
       textTasa.setText("");
       textCuota.setText("");
 
     }
     catch (SQLException ex)
     {
       JOptionPane.showMessageDialog(this, 
         "Se produjo un error al intentar conectarse a la base de datos.\n" + ex.getMessage(), 
         "Error", 
         0);
       System.out.println("SQLException: " + ex.getMessage());
       System.out.println("SQLState: " + ex.getSQLState());
       System.out.println("VendorError: " + ex.getErrorCode());
     }
   }
   
   public void mostrarPrestamo(String tipo, String nro)
   {
     try {
       Statement stm = this.tabla.getConnection().createStatement();
       String consulta = "Select nro_doc from prestamo NATURAL JOIN cliente WHERE nro_doc =" + nro + ";";
       ResultSet rs = stm.executeQuery(consulta);
       
       if (rs.next())
       {
         cargarTablaCuotasImpagas(tipo, nro);
         this.panelInfoPrestamos.setVisible(true);
       }
       else
       {
         JOptionPane.showMessageDialog(this.panelSesion, "El cliente ingresado no tiene un prestamo asignado");
       }
     }
     catch (Exception ex) {
       JOptionPane.showMessageDialog(this.panelSesion, "hubo un error");
     }
   }
   
   public void cargarTablaCuotasImpagas(String tipo, String nro) {
     try { Statement stm = this.tabla.getConnection().createStatement();
       
       String consulta = "Select nro_doc from Cliente where nro_doc=" + nro + " and tipo_doc='" + tipo + "';";
       ResultSet rs = stm.executeQuery(consulta);
       if (!rs.next())
       {
         JOptionPane.showMessageDialog(this.panelSesion, "El dni ingresado no corresponde a un cliente.");
       }
       
       this.tabla.getConnection().createStatement();
       consulta = "select nro_pago, valor_cuota,fecha_venc from pago natural join (select nro_prestamo, valor_cuota from prestamo natural join cliente where nro_doc =" + nro + ")D where fecha_pago is NULL;";
       rs = stm.executeQuery(consulta);
       this.tablaCuotas.refresh(rs);
       panelInfoPrestamos.setVisible(true);
       
     }
     catch (SQLException localSQLException) {}
   }
   
   public void pagarCuotas(String tipo, String doc) {
     DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
     
     Date date = new Date();
     String fechaActual = dateFormat.format(date);
     
     String f = "";
     for (int j = 0; j < 10; j++) {
       char c = fechaActual.charAt(j);
       if (c != '/') {
         f = f + c;
       }
     }
     try
     {
       Statement stm = this.tabla.getConnection().createStatement();
       String consulta = "Select max(nro_prestamo) from prestamo NATURAL JOIN cliente WHERE nro_doc=" + doc;
       ResultSet resu = stm.executeQuery(consulta);
       String nro_prestamo = "";
       if (resu.next()) {
         nro_prestamo = resu.getString(1);
       }
       int[] pagosSeleccionados = this.tablaCuotas.getSelectedRows();
       for (int i = 0; i < pagosSeleccionados.length; i++)
       {
         String nroPago = this.tablaCuotas.getValueAt(pagosSeleccionados[i], 0).toString();
         String modificacion = "update pago set fecha_pago =" + f + " where nro_pago =" + nroPago + " and nro_prestamo=" + nro_prestamo + ";";
         
 
 
         stm.execute(modificacion);
       }
     }
     catch (SQLException ex) {
       JOptionPane.showMessageDialog(this, 
         "Se produjo un error al intentar conectarse a la base de datos.\n" + ex.getMessage(), 
         "Error", 
         0);
       System.out.println("SQLException: " + ex.getMessage());
       System.out.println("SQLState: " + ex.getSQLState());
       System.out.println("VendorError: " + ex.getErrorCode());
     }
     cargarTablaCuotasImpagas(tipo, doc);
   }
   
   public void cargarTablaMorosos()
   {
     DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
     
     Date date = new Date();
     String fechaActual = dateFormat.format(date);
     
     String f = "";
     for (int j = 0; j < 10; j++) {
       char c = fechaActual.charAt(j);
       if (c != '/') {
         f = f + c;
       }
     }
     
 
     String consulta = "select nro_cliente,tipo_doc, nro_doc, nombre, apellido, nro_prestamo, monto, cant_meses, valor_cuota, impagas FROM (cliente natural join prestamo) natural join (select nro_prestamo, count(nro_prestamo) as impagas from pago where fecha_venc < " + 
     
 
       f + 
       " and fecha_pago is NULL group by nro_prestamo having count(nro_prestamo)> 1)D;";
     try
     {
       Statement stm = this.tabla.getConnection().createStatement();
       ResultSet rs = stm.executeQuery(consulta);
       this.tablaMorosos.refresh(rs);
     } catch (SQLException e) {
       System.out.println("ERRORRRASUHRA");
     }
   }
   
   public void reiniciarPrestamo() {
     this.panelBotones.setVisible(false);
     this.panelPrestamo.setVisible(false);
     this.panelMorosos.setVisible(false);
     this.panelImpagos.setVisible(false);
     this.panelSesion.setVisible(true);
     this.fieldUsuario.setText("");
     this.fieldContra.setText("");
   }
 }


