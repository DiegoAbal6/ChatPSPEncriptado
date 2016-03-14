package chatpspencriptado;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * ** @author dabalrodriguez ***
 */
public class SSLCliente {

    private PrintWriter pw;
    private BufferedReader br;
    private int puerto = 9090;

    public SSLCliente() {
        try {
            
            System.out.println("Añadiendo fichero al objeto KeyStore...");
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream("/home/administrador/NetBeansProjects/ChatPSPEncriptado/src/chatpspencriptado/mySrvKeystore"),
                    "chatpsp".toCharArray());
            // Gestionamos la clave de acceso al certificado.
            System.out.println("Comprobando <mykey>...");
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, "chatpsp".toCharArray());
            // Le pasamos el objeto KeyStore con el certificado
            System.out.println("Añadiendo credenciales de fiabilidad al keyStore con TrustManagerFactory...");
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            tmf.init(ks);

            //Contexto SSL 
            System.out.println("Generando el contexto SSL de transmision de datos...");
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

            // Creo el servidor SSL, mediante el objeto del mismo
            SSLSocketFactory sf = sc.getSocketFactory();
            SSLSocket sslSocket = (SSLSocket) sf.createSocket("localhost", puerto);

            pw = new PrintWriter(sslSocket.getOutputStream(), true);
            br = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));

            read();
        } catch (KeyManagementException | UnrecoverableKeyException | KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException ex) {
            System.out.println(ex);
        }
    }

    public void read() {
        Thread read = new Thread() {
            @Override
            public void run() {
                try {
                    String mensaje;
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