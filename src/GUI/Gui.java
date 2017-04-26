 package GUI;
 
 import java.awt.Container;
 import java.awt.Dimension;
 import java.awt.EventQueue;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.beans.PropertyVetoException;
 import java.sql.Connection;
 import javax.swing.JDesktopPane;
 import javax.swing.JFrame;
 import javax.swing.JList;
 import javax.swing.JMenu;
 import javax.swing.JMenuBar;
 import javax.swing.JMenuItem;
 import javax.swing.JPanel;
 import javax.swing.JPasswordField;
 import javax.swing.JTextField;
 import javax.swing.UIManager;
 //import quick.dbtable.DBTable;
 
 public class Gui
   extends JFrame
 {
   private JTextField fieldUsuario;
   private JPasswordField fieldContra;
   private JTextField textConsulta;
   private JPanel panelSesion;
   private JPanel panelConsulta;
   private JDesktopPane jDesktopPane1;
   private administracion ventanaAdministracion;
   private cajero ventanaCajero;
   private prestamo ventanaPrestamo;
   private DBTable tabla;
   private Connection cnx;
   JList<String> listaTablas;
   JList<String> listaColumnas;
   
   public static void main(String[] args)
   {
     EventQueue.invokeLater(new Runnable() {
       public void run() {
         try {
           Gui window = new Gui();
           window.setVisible(true);
         } catch (Exception e) {
           e.printStackTrace();
         }
       }
     });
   }
   
 
 
 
   public Gui()
   {
     initialize();
     
     this.ventanaPrestamo = new prestamo();
     this.ventanaPrestamo.setSize(800, 600);
     this.ventanaPrestamo.setLocation(0, 0);
     this.ventanaPrestamo.setVisible(false);
     
     this.ventanaAdministracion = new administracion();
     this.ventanaAdministracion.setSize(800, 600);
     this.ventanaAdministracion.setLocation(0, 0);
     this.ventanaAdministracion.setVisible(false);
     
     this.ventanaCajero = new cajero();
     this.ventanaCajero.setSize(800, 600);
     this.ventanaCajero.setLocation(0, 0);
     this.ventanaCajero.setVisible(false);
     
     this.jDesktopPane1.add(this.ventanaCajero);
     this.jDesktopPane1.add(this.ventanaAdministracion);
     this.jDesktopPane1.add(this.ventanaPrestamo);
   }
   
   public void Gui2() {
     initialize();
   }
   
 
 
   private void initialize()
   {
     try
     {
       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
     
     this.jDesktopPane1 = new JDesktopPane();
     getContentPane().add(this.jDesktopPane1, "Center");
     this.jDesktopPane1.setPreferredSize(new Dimension(800, 600));
     
     JMenuBar menuBar = new JMenuBar();
     setJMenuBar(menuBar);
     
     JMenu mnAcciones = new JMenu("Acciones");
     menuBar.add(mnAcciones);
     
     JMenuItem mntmAdministracin = new JMenuItem("Administración");
     mntmAdministracin.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent evt) {
         Gui.this.mnAdministracionActionPerformed(evt);
       }
       
     });
     mnAcciones.add(mntmAdministracin);
     
     JMenuItem mntmCajeroAutomatico = new JMenuItem("Cajero automático");
     mntmCajeroAutomatico.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent evt) {
         Gui.this.mnCajeroAutomaticoPerformed(evt);
       }
     });
     mnAcciones.add(mntmCajeroAutomatico);
     
 
     JMenuItem mntmPrestamos = new JMenuItem("Prestamos");
     mntmPrestamos.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent evt) {
         Gui.this.mnPrestamosPerformed(evt);
       }
     });
     mnAcciones.add(mntmPrestamos);
     
     setTitle("Proyecto 2");
     setDefaultCloseOperation(3);
     
     setSize(1000, 600);
   }
   
 
 
 
 
 
 
 
 
   private void mnAdministracionActionPerformed(ActionEvent evt)
   {
     try
     {
       this.ventanaAdministracion.setMaximum(true);
     }
     catch (PropertyVetoException localPropertyVetoException) {}
     this.ventanaAdministracion.reiniciarAdministracion();
     this.ventanaAdministracion.setVisible(true);
     this.ventanaCajero.setVisible(false);
     this.ventanaPrestamo.setVisible(false);
   }
   
   private void mnCajeroAutomaticoPerformed(ActionEvent evt)
   {
     try
     {
       this.ventanaCajero.setMaximum(true);
     }
     catch (PropertyVetoException localPropertyVetoException) {}
     this.ventanaCajero.reiniciarCajero();
     this.ventanaCajero.setVisible(true);
     this.ventanaPrestamo.setVisible(false);
     this.ventanaAdministracion.setVisible(false);
   }
   
   private void mnPrestamosPerformed(ActionEvent evt)
   {
     try
     {
       this.ventanaPrestamo.setMaximum(true);
     }
     catch (PropertyVetoException localPropertyVetoException) {}
     this.ventanaPrestamo.reiniciarPrestamo();
     this.ventanaPrestamo.setVisible(true);
     this.ventanaAdministracion.setVisible(false);
     this.ventanaCajero.setVisible(false);
   }
 }


