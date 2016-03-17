

package chatpspencriptado;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 *
 * @author dabalrodriguez
 */
public class ChatSSL {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
     try {
            //Fichero keytool
            KeyStore ks = KeyStore.getInstance("JKS");
            
      
            ks.load(new FileInputStream("/Users/Diego/NetBeansProjects/ChatPSPEncriptado/src/chatpspencriptado/mySrvKeystore"), "chatpsp".toCharArray());
            
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, "chatpsp".toCharArray());

            //Fiabilidad del certificado
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(ks);
            //Context
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

            //Socket server con el ssl
            System.out.println("El socket del servidor ha sido creado");
            //Objetos
            IServer is = new IServer(sc);
            ICliente ic = new ICliente(sc);
         
            System.out.println("El socket del cliente está siendo verificado por el servidor");
            
        } catch (IOException | CertificateException | UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException | KeyManagementException ex) {
            System.out.println(ex);
        }
    }
    
}