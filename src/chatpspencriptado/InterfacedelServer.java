/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatpspencriptado;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 * @author dabalrodriguez
 */
public class InterfacedelServer extends javax.swing.JFrame {

    private int puerto = 9090;
    private SSLServerSocket ssv;
    private SSLSocket ss;
    private PrintWriter pw;
    private BufferedReader br;
    private SSLContext sc;

    /**
     * Creates new form IServer
     */
    public InterfacedelServer(SSLContext sc) {
        try { //Igual que en el Cliente
            this.sc = sc;
            SSLServerSocketFactory ssf = sc.getServerSocketFactory();
            ssv = (SSLServerSocket) ssf.createServerSocket(puerto);
            
            //flujo de entrada y salida de texto
            setTitle("Server");
            setVisible(true);
            readMessage();
        } catch (IOException ex) {
            System.out.println(ex);
        }
        initComponents();
    }

    public void readMessage() { //Método para leer los mensajes
        Thread read = new Thread() {
            @Override
            public void run() {
                String mensaje;
                try {
                    ss = (SSLSocket) ssv.accept();
                    System.out.println("El Socket ha sido aceptado");
                    //Utilizo el Buffer y Print para la llegada y salida de mensajes (Streams)
                    pw = new PrintWriter(ss.getOutputStream(), true);
                    br = new BufferedReader(new InputStreamReader(ss.getInputStream()));
                  
                    //Bucle para la lectura de lineas
                    while ((mensaje = br.readLine()) != null) {
                        System.out.println("Leyendo...");
                        TextAreaS.append("\n"+ "\n"+mensaje);
                    }
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
        };
        read.start();
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
        ButtonS = new javax.swing.JButton();
        TextFieldS = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TextAreaS = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ButtonS.setText("Enviar");
        ButtonS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSActionPerformed(evt);
            }
        });

        TextFieldS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextFieldSActionPerformed(evt);
            }
        });

        TextAreaS.setColumns(20);
        TextAreaS.setRows(5);
        jScrollPane1.setViewportView(TextAreaS);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addComponent(TextFieldS))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ButtonS)
                .addGap(19, 19, 19))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonS)
                    .addComponent(TextFieldS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSActionPerformed
        //En este metodo evalua el texto y si detecta la palabra magica sale del programa.
        String texto = TextFieldS.getText();
        if(texto.equalsIgnoreCase("salir")){
            System.exit(0);
        }else{
            //Añado texto
            TextAreaS.append("El servidor dice:" + texto);
            pw.println("El servidor dice: " + texto);
            pw.flush();
           //Ponemos el textfiled a 0
            TextFieldS.setText("");
        }   
        
    }

    private void TextFieldSActionPerformed(java.awt.event.ActionEvent evt) {
        
    }
    private javax.swing.JButton ButtonS;
    private javax.swing.JTextArea TextAreaS;
    private javax.swing.JTextField TextFieldS;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    
}