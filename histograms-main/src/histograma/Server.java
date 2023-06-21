package histograma;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
    void registerClient() throws RemoteException;

    void uploadImage(byte[] image, String imageName) throws RemoteException;

    byte[] getImageOne() throws RemoteException;

    byte[] getImageTwo() throws RemoteException;

    String getImageNameOne() throws RemoteException;

    String getImageNameTwo() throws RemoteException;
    
    void setTime(String time, String tipo) throws RemoteException;
   

}
