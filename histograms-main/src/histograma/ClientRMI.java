package histograma;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientRMI {

    public static void main(String[] args) {
        try {
            // Obtener referencia del registro con la ip del servidor
            // Registry registry = LocateRegistry.getRegistry("192.168.0.12", 8080);
           // Registry registry = LocateRegistry.getRegistry("192.168.137.127", 8181);
            Registry registry = LocateRegistry.getRegistry("192.168.1.148", 8181);
            //Registry registry = LocateRegistry.getRegistry("localhost", 8080);
            Server server = (Server) registry.lookup("server");
            FormularioHistograma formulario = new FormularioHistograma(server);
            formulario.setVisible(true);
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }
}
