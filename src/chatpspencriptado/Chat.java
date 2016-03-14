package chatpspencriptado;

/**
 *
 * @author dabalrodriguez
 */
public class Chat {
    
    public static void main(String[] args) {
        SSLServer ssls = new SSLServer();
        SSLCliente sslc = new SSLCliente();
        System.out.println("Socket cliente aceptado en el servidor...");
	ssls.sendMessage("Hola!");
        ssls.sendMessage("Hola!");
        
    }
}