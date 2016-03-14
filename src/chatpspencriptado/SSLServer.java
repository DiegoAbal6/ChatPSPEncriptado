package chatpspencriptado;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * ** @author dabalrodriguez ***
 */
public class SSLServer {

    private int puerto = 9090;
    private SSLServerSocket ssv;
    private SSLSocket ss;
    private PrintWriter pw;
    private BufferedReader br;

    public SSLServer() {
        try {
            //Le paso el fichero generado con la clave de keytool
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream("/home/administrador/NetBeansProjects/ChatPSPEncriptado/src/chatpspencriptado/mySrvKeystore"), "chatpspencriptado".toCharArray());
            
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, "chatpsp".toCharArray());

            //Certifica el certificado de encriptado
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(ks);

            //Creo el ssl contexto
            System.out.println("Generando SSL");
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

           
            System.out.println("Socket de servidor creado");
            SSLServerSocketFactory ssf = sc.getServerSocketFactory();
            ssv = (SSLServerSocket) ssf.createServerSocket(puerto);
            SSLSocketFactory sslf = sc.getSocketFactory();
            ss = (SSLSocket) sslf.createSocket("localhost", puerto);
            //entrada y salida de texto.
            readMessage();
        } catch (IOException | CertificateException | UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException | KeyManagementException ex) {
            System.out.println(ex);
        }
    }

    public void sendMessage(String mensaje) {
        pw.println("Servidor dice:" + mensaje);
        pw.flush();
        System.out.println("Mensaje del servidor, enviado");
    }
    
   

        //Lectura de mensajes
    public void readMessage() {
        Thread read = new Thread() {

            @Override
            public void run() {
                String mensaje;
                try {
                    ss = (SSLSocket) ssv.accept();
                    System.out.println("El socket ha sido aceptado");

                    pw = new PrintWriter(ss.getOutputStream(), true);
                    br = new BufferedReader(new InputStreamReader(ss.getInputStream()));
                    System.out.println("Input y outputs añadidos del socket cliente");

                    while ((mensaje = br.readLine()) != null) {
                        System.out.println("Leyendo...");
                        System.out.println(mensaje);
                    }
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
        };
        read.start();
    }
}