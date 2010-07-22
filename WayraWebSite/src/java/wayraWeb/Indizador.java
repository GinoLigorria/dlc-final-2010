package wayraWeb;


import Gestores.GestorDirectorio;
import java.io.Console;
import java.io.File;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Indizador.java
 *
 * Created on 22-jul-2010, 2:20:42
 */

/**
 *
 * @author Nico
 */
public class Indizador extends javax.swing.JApplet {

    /** Initializes the applet Indizador */
    public void init() {
        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    initComponents();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /** This method is called from within the init() method to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jtRuta = new javax.swing.JTextField();
        btnExaminar = new javax.swing.JButton();
        btnIndexar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaAcciones = new javax.swing.JTextArea();

        jLabel1.setFont(new java.awt.Font("Copperplate Gothic Light", 1, 12));
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("WayraSearch Indexer");

        jtRuta.setEditable(false);

        btnExaminar.setIcon(new javax.swing.ImageIcon("G:\\DOCUMENTOS DE NICO\\escritorio xp\\crystal_project\\22x22\\actions\\viewmag.png")); // NOI18N
        btnExaminar.setText("Examinar");
        btnExaminar.setToolTipText("Seleccione el archivo que desea indexar o reindexar.");
        btnExaminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarArchivo(evt);
            }
        });

        btnIndexar.setIcon(new javax.swing.ImageIcon("G:\\DOCUMENTOS DE NICO\\escritorio xp\\crystal_project\\22x22\\apps\\cache.png")); // NOI18N
        btnIndexar.setText("Indexar");
        btnIndexar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                indexarArchivo(evt);
            }
        });

        jtaAcciones.setColumns(20);
        jtaAcciones.setEditable(false);
        jtaAcciones.setRows(5);
        jScrollPane1.setViewportView(jtaAcciones);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(75, 75, 75))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(109, 109, 109)
                                .addComponent(btnIndexar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnExaminar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnExaminar)
                    .addComponent(jtRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnIndexar, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buscarArchivo(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarArchivo
        JFileChooser j=new JFileChooser();
        int n=j.showOpenDialog(jtRuta);
        if(n == JFileChooser.APPROVE_OPTION) {
           JOptionPane.showMessageDialog(null, "Debe selecciona un archivo para continuar.", "Error al seleccionar archivo.", JOptionPane.INFORMATION_MESSAGE);
    }

    }//GEN-LAST:event_buscarArchivo

    private void indexarArchivo(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_indexarArchivo
       File DirectorioInicial = new File(jtRuta.getText());//"D:\\Facu\\2009\\DLC\\DLC-Final-2010\\test");

        if (DirectorioInicial.isDirectory())
        {
         Date dateobj = new Date();
         long startTime = dateobj.getTime();
         System.out.println("Indización iniciada: " + startTime);

         GestorDirectorio.indizar(DirectorioInicial);

         Date dateobj2 = new Date();
         long endTime = dateobj2.getTime();
         jtaAcciones.append("Inidzacion finalizada: " + endTime);
         jtaAcciones.append("Duracion: " + ((endTime-startTime)/1000) );

        }
        else
        {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un directorio.", "Error", JOptionPane.INFORMATION_MESSAGE);
        }

    }//GEN-LAST:event_indexarArchivo

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExaminar;
    private javax.swing.JButton btnIndexar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jtRuta;
    private javax.swing.JTextArea jtaAcciones;
    // End of variables declaration//GEN-END:variables

}
