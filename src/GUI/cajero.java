 package GUI;
 
 import java.awt.Container;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
 import java.security.MessageDigest;
 import java.security.NoSuchAlgorithmException;
 import java.sql.Connection;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.sql.Statement;
 import java.text.ParseException;
 import java.text.SimpleDateFormat;
 import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
 import javax.swing.JFormattedTextField;
 import javax.swing.JLabel;
 import javax.swing.JOptionPane;
 import javax.swing.JPanel;
 import javax.swing.JPasswordField;
 import javax.swing.JTextField;
 import javax.swing.text.MaskFormatter;
 import quick.dbtable.Column;
 import quick.dbtable.DBTable;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
 
 public class cajero extends javax.swing.JInternalFrame
 {
   private static final long serialVersionUID = 1L;
   private JTextField fieldTarjeta;
   private JPasswordField fieldPIN;
   private JPanel panelSesion;
   private JPanel panelConsultas;
   private DBTable tabla;
   private JLabel lblNombre;
   private String nroATM = "100"; //Por enunciado
   
   public cajero()
   {
	 setBounds(500, 250, 899, 565);
     getContentPane().setLayout(null);
     
     this.panelConsultas = new JPanel();
     this.panelConsultas.setVisible(false);
     
     this.panelSesion = new JPanel();
     this.panelSesion.setBounds(219, 88, 422, 282);
     this.panelSesion.setLayout(null);
     getContentPane().add(this.panelSesion);
     
     JLabel lblNroTarjeta = new JLabel("Nro tarjeta");
     lblNroTarjeta.setHorizontalAlignment(4);
     lblNroTarjeta.setBounds(91, 120, 81, 14);
     this.panelSesion.add(lblNroTarjeta);
     
     JLabel lblPin = new JLabel("PIN");
     lblPin.setHorizontalAlignment(4);
     lblPin.setBounds(100, 151, 71, 14);
     this.panelSesion.add(lblPin);
     
     this.fieldTarjeta = new JTextField();
     this.fieldTarjeta.setBounds(181, 117, 116, 20);
     this.panelSesion.add(this.fieldTarjeta);
     this.fieldTarjeta.setColumns(10);
     
     this.fieldPIN = new JPasswordField();
     this.fieldPIN.setBounds(181, 148, 116, 20);
     this.panelSesion.add(this.fieldPIN);
     
 
     JButton btIngresar = new JButton("Ingresar");
     btIngresar.setBounds(206, 174, 88, 20);
     this.panelSesion.add(btIngresar);
     
     
     
     
     JLabel lblBienvenidoAlSistema = new JLabel("ATM");
     lblBienvenidoAlSistema.setHorizontalAlignment(0);
     lblBienvenidoAlSistema.setBounds(67, 83, 303, 22);
     this.panelSesion.add(lblBienvenidoAlSistema);
     lblBienvenidoAlSistema.setFont(new Font("Tahoma", 0, 18));
     
     JLabel lblNewLabel = new JLabel("");
     lblNewLabel.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/Imagenes/atm-512.png")).getImage().getScaledInstance(103, 123, Image.SCALE_DEFAULT)));
     lblNewLabel.setBounds(309, 95, 107, 130);
     panelSesion.add(lblNewLabel);
     btIngresar.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent arg0) {
         String tarjeta = cajero.this.fieldTarjeta.getText();
         String pin = cajero.this.fieldPIN.getText();
         cajero.this.conectarBD(tarjeta, pin);
       }
     });
     this.panelConsultas.setLayout(null);
     this.panelConsultas.setBounds(0, 0, 882, 410);
     getContentPane().add(this.panelConsultas);
     
     JButton btnconsultarSaldo = new JButton("Consultar Saldo");
     btnconsultarSaldo.setSize(143, 23);
     btnconsultarSaldo.setLocation(21, 154);
     btnconsultarSaldo.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent evt) {
         cajero.this.btnConsultarSaldoActionPerformed(evt);
       }
     });
     this.panelConsultas.add(btnconsultarSaldo);
     
 
 
     JButton btnUltimosMovimientos = new JButton("Ultimos movimientos");
     btnUltimosMovimientos.setBounds(6, 189, 177, 23);
     btnUltimosMovimientos.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent evt) {
         cajero.this.btnUltimosMovimientosActionPerformed(evt);
       }
     });
     this.panelConsultas.add(btnUltimosMovimientos);
     
     JButton btnMovimientosPorPeriodo = new JButton("Movimientos por periodo");
     btnMovimientosPorPeriodo.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent evt) {
         cajero.this.btnMovimientosPorPeriodoActionPerformed(evt);
       }
     });
     btnMovimientosPorPeriodo.setBounds(0, 224, 183, 23);
     this.panelConsultas.add(btnMovimientosPorPeriodo);
     
 
 
     this.tabla = new DBTable();
     tabla.getTable().setCellSelectionEnabled(true);
     tabla.getTable().setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
     
         this.panelConsultas.add(this.tabla);
         this.tabla.setBounds(190, 85, 692, 346);
         
 
         this.tabla.setEditable(false);
         
         JButton btnExtraccion = new JButton("Extraccion");
         btnExtraccion.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
         		realizarExtraccion();
         	}
         });
         btnExtraccion.setBounds(21, 259, 143, 23);
         panelConsultas.add(btnExtraccion);
         
         JButton btnTransferencia = new JButton("Transferencia");
         btnTransferencia.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
         		realizarTransferencia();
         	}
         });
         btnTransferencia.setBounds(21, 294, 143, 23);
         panelConsultas.add(btnTransferencia);
         
         lblNombre = new JLabel("");
         lblNombre.setFont(new Font("Helvetica", Font.PLAIN, 22));
         lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
         lblNombre.setBounds(168, 19, 543, 29);
         panelConsultas.add(lblNombre);
   }
   
   private void getNombreApellido() {
	   try{
		   String rta ="";
		   String consulta = "select distinct nombre, apellido  from trans_cajas_ahorro NATURAL JOIN  Tarjeta  where nro_tarjeta ="+fieldTarjeta.getText()+";";
	       Statement stmt = this.tabla.getConnection().createStatement();
	       stmt.execute(consulta);
	       ResultSet rs = stmt.getResultSet();
	       rs.next();
	       rta = "Bienvenido "+rs.getString(1) + " " + rs.getString(2);
	       lblNombre.setText(rta);
	       
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

private void conectarBD(String tarjeta, String pin)
   {
     try
     {
       Class.forName("com.mysql.jdbc.Driver").newInstance();
     } catch (Exception ex) { System.out.println("error new instance"); }
     String driver = "com.mysql.jdbc.Driver";
     String servidor = "localhost:3306";
     String baseDatos = "banco";
     String usuario = "atm";
     String clave = "atm";
     String uriConexion = "jdbc:mysql://" + servidor + "/" + baseDatos;
     
     try
     {
       tabla.removeAllRows();
       this.tabla.connectDatabase(driver, uriConexion, usuario, clave);
       String consulta = "SELECT pin FROM Tarjeta WHERE Tarjeta.nro_tarjeta = " + this.fieldTarjeta.getText() + ";";
       Statement stmt = this.tabla.getConnection().createStatement();
       
       boolean a = stmt.execute(consulta);
       ResultSet rs = stmt.getResultSet();
       rs.next();
       String pinT = rs.getString(1);
       if (pinT.equals(getMD5(pin))) {
         mostrarPanelConsultas();
         getNombreApellido();
       }
       else {
         JOptionPane.showMessageDialog(this.panelSesion, "Error Tarjeta/Pin");
       }
       
     }
     catch (SQLException ex)
     {
    	JOptionPane.showMessageDialog(this.panelSesion, "Ingrese Tarjeta/Pin");
     }
     catch (ClassNotFoundException e)
     {
       e.printStackTrace();
     }
   }
   
   private void mostrarPanelConsultas()
   {
     this.panelSesion.setVisible(false);
     
     this.panelConsultas.setVisible(true);
   }
   
   private void btnConsultarSaldoActionPerformed(ActionEvent evt)
   {
     String consulta = "SELECT DISTINCT saldo FROM Tarjeta t, trans_cajas_ahorro tc WHERE t.nro_ca = tc.origen and t.nro_tarjeta = " + this.fieldTarjeta.getText() + ";";
     mostrarTabla(consulta);
   }
   
   private void btnUltimosMovimientosActionPerformed(ActionEvent evt)
   {
     String consulta = "select destino, caja, Tipo, if(Tipo='deposito', monto, (-1)*monto) as monto, fecha, hora  from trans_cajas_ahorro tc natural join (select nro_ca as origen from Tarjeta t where t.nro_tarjeta =" + this.fieldTarjeta.getText() + ")D ORDER BY fecha DESC, hora DESC LIMIT 15;";
     mostrarTabla(consulta);
   }
   
   private void btnMovimientosPorPeriodoActionPerformed(ActionEvent evt) {
     String mensaje = "";
     
     try {
    	 JPanel panelIngreso = new JPanel();
    	 panelIngreso.add(new JLabel("Desde"));
    	 JTextField fieldFechaInicio = new JFormattedTextField(new MaskFormatter("####'/##'/##"));
	     fieldFechaInicio.setColumns(10);
	     panelIngreso.add(fieldFechaInicio);
		 
	     panelIngreso.add(Box.createHorizontalStrut(15)); // a spacer
	   	 panelIngreso.add(new JLabel("Hasta"));
	   	 JTextField fieldFechaFin = new JFormattedTextField(new MaskFormatter("####'/##'/##"));
	     fieldFechaFin.setColumns(10);
	     panelIngreso.add(fieldFechaFin);

	     int result = JOptionPane.showConfirmDialog(null, panelIngreso, "Movimientos por periodo", JOptionPane.OK_CANCEL_OPTION);
	     if (result == JOptionPane.OK_OPTION) {     
		     if ((fieldFechaInicio.getText().isEmpty()) || (fieldFechaFin.getText().isEmpty()))
		     {
		       mensaje = "Debe ingresar un valor en el/los campo/s de fecha.";
		     }
		     else if ((!validar(fieldFechaInicio.getText().trim())) || (!validar(fieldFechaFin.getText().trim())))
		     {
		       mensaje = "En el campo 'Fecha' debe ingresar un valor con el formato aaaa/mm/dd.";
		     }
		     else if (!compararFechas(fieldFechaInicio.getText(), fieldFechaFin.getText())) {
		       mensaje = "Fecha inicio debe ser anterior a fecha fin.";
		     }
		     if (mensaje.equals("")) {
		         String FI = "";String FF = "";
		         for (int i = 0; i < 10; i++) {
		           char c = fieldFechaInicio.getText().charAt(i);
		           char cc = fieldFechaFin.getText().charAt(i);
		           if (c != '/') {
		             FI = FI + c;
		             FF = FF + cc;
		           }
		         }
		         String consulta = "select destino, caja, Tipo, if(Tipo='deposito', monto, (-1)*monto) as monto, fecha, hora from trans_cajas_ahorro tc natural join (select nro_ca as origen from Tarjeta t where t.nro_tarjeta =" + this.fieldTarjeta.getText() + ")D WHERE fecha>=" + FI + " and fecha<=" + FF + " ORDER BY fecha DESC, hora DESC LIMIT 15;";
		         mostrarTabla(consulta);
		     }
		     else {
		       JOptionPane.showMessageDialog(this.panelConsultas, mensaje);
		       btnMovimientosPorPeriodoActionPerformed(null);
		     }
	     }
     }
     catch(Exception e){
    	 System.out.println("Hubo un error.");
     }
   }
     
       
       
     
   
   public boolean compararFechas(String f1, String f2) {
     boolean ret = false;
     try {
       SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
       Date date1 = formato.parse(f1);
       Date date2 = formato.parse(f2);
       
       ret = date2.after(date1);
     }
     catch (ParseException e) {
       e.printStackTrace();
     }
     return ret;
   }
   
 
   public static boolean validar(String p_fecha)
   {
     if (p_fecha != null)
     {
       try
       {
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
         sdf.setLenient(false);
         sdf.parse(p_fecha);
         return true;
       }
       catch (ParseException localParseException) {}
     }
     return false;
   }
   
 
   private void mostrarTabla(String consulta)
   {
     try
     {
       this.tabla.setSelectSql(consulta.trim());
       
 
       this.tabla.createColumnModelFromQuery();
       for (int i = 0; i < this.tabla.getColumnCount(); i++)
       {
         if (this.tabla.getColumn(i).getType() == 92)
         {
           this.tabla.getColumn(i).setType(1);
         }
         
         if (this.tabla.getColumn(i).getType() == 91)
         {
           this.tabla.getColumn(i).setDateFormat("dd/MM/YYYY");
         }
       }
       
       this.tabla.refresh();
 
 
     }
     catch (SQLException ex)
     {
 
 
       JOptionPane.showMessageDialog(this.panelConsultas, "Hubo un error");
     }
   }
   
   public static String getMD5(String input)
   {
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
   
   public void realizarExtraccion(){
	   try{
		   String monto = JOptionPane.showInputDialog(panelConsultas, "Ingrese monto a extraer","Extacción",JOptionPane.PLAIN_MESSAGE);
		   if(monto != null){
			   if(monto.length()>0){
				   String consulta = "SELECT nro_ca FROM Tarjeta WHERE nro_tarjeta ="+fieldTarjeta.getText()+";";
			       Statement stmt = this.tabla.getConnection().createStatement();
			       stmt.execute(consulta);
			       ResultSet rs = stmt.getResultSet();
			       rs.next();
			       String origen = rs.getString(1);
			       consulta = "call extraer("+origen+","+monto+","+fieldTarjeta.getText()+","+nroATM+");";
			       stmt.execute(consulta);
			       rs = stmt.getResultSet();
			       rs.next();
			       JOptionPane.showMessageDialog(panelConsultas, rs.getString(1));
			       btnConsultarSaldoActionPerformed(null);
			   }
			   else{
				   JOptionPane.showMessageDialog(panelConsultas, "Debe ingresar monto","Error",JOptionPane.ERROR_MESSAGE);
				   realizarExtraccion();
			   }
		   }		   
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
   
   public void realizarTransferencia(){
	   try{
		   JTextField cajaDestino = new JTextField(5);
		   JTextField monto = new JTextField(5);
	
		   JPanel panelIngreso = new JPanel();
		   panelIngreso.add(new JLabel("Destino:"));
		   panelIngreso.add(cajaDestino);
		   panelIngreso.add(Box.createHorizontalStrut(15)); 
		   panelIngreso.add(new JLabel("Monto"));
		   panelIngreso.add(monto);
	
		   int result = JOptionPane.showConfirmDialog(null, panelIngreso, 
	               "Transferencia", JOptionPane.OK_CANCEL_OPTION);
		   if (result == JOptionPane.OK_OPTION) {
			   
			   if(!cajaDestino.getText().equals("")){
				   if(!monto.getText().equals("")){
				   	   String consulta = "SELECT nro_ca FROM Tarjeta WHERE nro_tarjeta ="+fieldTarjeta.getText()+";";
				       Statement stmt = this.tabla.getConnection().createStatement();
				       stmt.execute(consulta);
				       ResultSet rs = stmt.getResultSet();
				       rs.next();
				       String origen = rs.getString(1);
				       consulta = "call transferir("+origen+","+cajaDestino.getText()+","+monto.getText()+","+fieldTarjeta.getText()+","+nroATM+");";
				       stmt.execute(consulta);
				       rs = stmt.getResultSet();
				       rs.next();
				       JOptionPane.showMessageDialog(panelConsultas, rs.getString(1));
				   }
				   else{
					   JOptionPane.showMessageDialog(panelConsultas, "Debe ingresar monto","Error",JOptionPane.ERROR_MESSAGE);
					   realizarTransferencia();	   
				   }
			   }
			   else{
				   JOptionPane.showMessageDialog(panelConsultas, "Debe ingresar caja destino","Error",JOptionPane.ERROR_MESSAGE);
				   realizarTransferencia();	   
			   }
		   }
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
   
   public void reiniciarCajero() {
     this.panelConsultas.setVisible(false);
     this.panelSesion.setVisible(true);
     this.fieldPIN.setText("");
     this.fieldTarjeta.setText("");
   }
 }


