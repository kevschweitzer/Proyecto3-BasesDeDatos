 package GUI;
 
 import java.awt.Container;
 import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.io.PrintStream;
 import java.sql.Connection;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
 import javax.swing.JInternalFrame;
 import javax.swing.JLabel;
 import javax.swing.JList;
 import javax.swing.JOptionPane;
 import javax.swing.JPanel;
 import javax.swing.JPasswordField;
 import javax.swing.JScrollPane;
 import javax.swing.JTextField;
 import javax.swing.event.ListSelectionEvent;
 import javax.swing.event.ListSelectionListener;
 import quick.dbtable.DBTable;
import javax.swing.JTable;
 
 
  public class administracion extends JInternalFrame{
   private JTextField fieldUsuario;
   private JPasswordField fieldContra;
   private JTextField textConsulta;
   private JPanel panelSesion;
   private JPanel panelConsulta;
   private DBTable tabla;
   private JList<String> listaTablas;
   private JList<String> listaColumnas;
   
   public administracion()
   {
     initialize();
   }
   
   private void initialize()
   {
     setBounds(500, 250, 899, 565);
     setDefaultCloseOperation(3);
     getContentPane().setLayout(null);
     
 
     this.panelConsulta = new JPanel();
     this.panelConsulta.setVisible(false);
     this.panelSesion = new JPanel();
     this.panelSesion.setVisible(true);
     this.panelSesion.setBounds(219, 88, 422, 282);
     getContentPane().add(this.panelSesion);
     this.panelSesion.setLayout(null);
     
 
 
 
     this.fieldUsuario = new JTextField();
     this.fieldUsuario.setBounds(181, 117, 116, 20);
     this.panelSesion.add(this.fieldUsuario);
     this.fieldUsuario.setColumns(10);
     
     this.fieldContra = new JPasswordField();
     this.fieldContra.setBounds(181, 148, 116, 20);
     this.panelSesion.add(this.fieldContra);
     this.fieldContra.setColumns(10);
     
     JLabel lblNewLabel = new JLabel("");
     lblNewLabel.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/Imagenes/boss_man-512.png")).getImage().getScaledInstance(103, 123, Image.SCALE_DEFAULT)));
     lblNewLabel.setBounds(309, 71, 94, 123);
     this.panelSesion.add(lblNewLabel);
     JButton btIngresar = new JButton("Ingresar");
     btIngresar.setBounds(206, 174, 88, 20);
     this.panelSesion.add(btIngresar);
     btIngresar.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent arg0) {
         String usuario = administracion.this.fieldUsuario.getText();
         String contra = administracion.this.fieldContra.getText();
         administracion.this.conectarBD(usuario, contra);
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
     
     JLabel lblBienvenidoAlSistema = new JLabel("Administración");
     lblBienvenidoAlSistema.setHorizontalAlignment(0);
     lblBienvenidoAlSistema.setBounds(67, 83, 303, 22);
     this.panelSesion.add(lblBienvenidoAlSistema);
     lblBienvenidoAlSistema.setFont(new Font("Tahoma", 0, 18));
     this.panelConsulta.setBounds(0, 76, 882, 410);
     getContentPane().add(this.panelConsulta);
     this.panelConsulta.setLayout(null);
     
     this.textConsulta = new JTextField();
     this.textConsulta.setBounds(22, 26, 555, 38);
     this.panelConsulta.add(this.textConsulta);
     this.textConsulta.setColumns(10);
     
     JLabel lblConsultaSql = new JLabel("Comando SQL");
     lblConsultaSql.setBounds(125, 11, 78, 14);
     this.panelConsulta.add(lblConsultaSql);
     
 
 
     this.tabla = new DBTable();
     tabla.getTable().setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
     
 
     this.panelConsulta.add(this.tabla);
     this.tabla.setBounds(22, 75, 690, 324);
     
 
     this.tabla.setEditable(false);
     
 
 
 
     JPanel panelListas = new JPanel();
     panelListas.setBounds(722, 76, 150, 336);
     this.panelConsulta.add(panelListas);
     panelListas.setLayout(null);
     
 
 
 
     this.listaTablas = new JList();
     this.listaTablas.setSelectionMode(0);
     this.listaTablas.setLayoutOrientation(0);
     this.listaTablas.addListSelectionListener(new ListSelectionListener()
     {
 
 
       public void valueChanged(ListSelectionEvent e)
       {
 
         String s = (String)administracion.this.listaTablas.getSelectedValue();
         String cons = "select column_name FROM information_schema.columns WHERE table_name='" + s + "'";
         String[] arr = administracion.this.consultarTablas(cons);
         administracion.this.listaColumnas.setListData(arr);
 
       }
       
 
 
     });
     JScrollPane scroll = new JScrollPane();
     scroll.setBounds(10, 29, 130, 140);
     panelListas.add(scroll);
     scroll.setViewportView(this.listaTablas);
     
 
 
     this.listaColumnas = new JList();
     this.listaColumnas.setSelectionMode(0);
     this.listaColumnas.setLayoutOrientation(0);
     
 
     JScrollPane scroll2 = new JScrollPane();
     scroll2.setBounds(10, 195, 130, 130);
     panelListas.add(scroll2);
     scroll2.setViewportView(this.listaColumnas);
     
 
     JLabel lblAtributos = new JLabel("Atributos");
     lblAtributos.setBounds(48, 180, 46, 14);
     panelListas.add(lblAtributos);
     
     JLabel lblTablas = new JLabel("Tablas");
     lblTablas.setBounds(48, 11, 46, 14);
     panelListas.add(lblTablas);
     
     JButton btnEjecutar = new JButton("Ejecutar");
     btnEjecutar.setBounds(599, 34, 131, 23);
     this.panelConsulta.add(btnEjecutar);
     btnEjecutar.addActionListener(new ActionListener()
     {
 
       public void actionPerformed(ActionEvent arg0)
       {
         administracion.this.ejecutarComando(administracion.this.textConsulta.getText());
       }
       
     });
   }
   
   private void conectarBD(String usuario, String clave)
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
     
     try
     {
       this.tabla.connectDatabase(driver, url, usuario, clave);
       
       mostrarPanelConsultas();
     }
     catch (SQLException ex) {
       JOptionPane.showMessageDialog(this.panelSesion, "Error Usuario/Contraseña");
     }
     catch (ClassNotFoundException e)
     {
       e.printStackTrace();
     }
   }
   
 
 
   private void mostrarPanelConsultas()
   {
     this.panelSesion.setVisible(false);
     this.panelConsulta.setVisible(true);
     cargarListas();
   }
   
 
 
 
   private void cargarListas()
   {
     String[] arr = consultarTablas("show tables;");
     this.listaTablas.setListData(arr);
   }
   
 
 
 
 
 
   public void ejecutarComando(String c)
   {
     try
     {
       Statement stm = this.tabla.getConnection().createStatement();
       stm.execute(c);
       ResultSet rs = stm.getResultSet();
       this.tabla.refresh(rs);
       stm.close();
       this.textConsulta.setText("");
     }
     catch (SQLException ex)
     {
       JOptionPane.showMessageDialog(this.panelConsulta, "Error: Asegurese de que el comando ingresado es valido");
     }
   }
   
 
 
 
 
 
   public String[] consultarTablas(String consulta)
   {
     DBTable tabla2 = new DBTable();
     String[] arr = new String[100];
     try
     {
       Statement stm = this.tabla.getConnection().createStatement();
       stm.execute(consulta);
       ResultSet rs = stm.getResultSet();
       tabla2.refresh(rs);
     } catch (SQLException localSQLException) {}
     int filas = tabla2.getRowCount();
     
     for (int i = 0; i < filas; i++) {
       arr[i] = ((String)tabla2.getValueAt(i, 0));
     }
     
 
     return arr;
   }
   
   public void reiniciarAdministracion()
   {
     this.panelSesion.setVisible(true);
     this.panelConsulta.setVisible(false);
     this.fieldContra.setText("");
     this.fieldUsuario.setText("");
   }
 }


