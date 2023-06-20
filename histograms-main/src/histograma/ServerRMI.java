package histograma;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerRMI {

    public static void main(String[] args) throws RemoteException {
        // Seleccionar ip del servidor
        // System.setProperty("java.rmi.server.hostname", "192.168.0.12");
         //ip de mi casa para hacer pruebas: 192.168.1.148
         //System.setProperty("java.rmi.server.hostname", "192.168.137.127");
         System.setProperty("java.rmi.server.hostname", "192.168.1.148");

        Registry registry = LocateRegistry.createRegistry(8181);
        registry.rebind("server", new ServerImpl());
        System.out.println("Server esta listo");
    }
}
