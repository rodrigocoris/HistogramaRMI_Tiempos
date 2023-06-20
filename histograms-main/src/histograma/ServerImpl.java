package histograma;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements Server {
    byte[] imageOne = null;
    String imageNameOne = null;
    byte[] imageTwo = null;
    String imageNameTwo = null;
    String time;
    
    int idClient = 1;

    protected ServerImpl() throws RemoteException {
        super();
    }


    @Override
    public void registerClient() throws RemoteException {
        System.out.println("Client " + idClient + " registered!");
        idClient++;
    }
    
    @Override
    public void setTime(String time, String tipo) throws RemoteException{
        System.out.println(idClient + "tiempo" + tipo + " : "+ time);
        this.time = time;
    }
    
   

    @Override
    public void uploadImage(byte[] image, String imageName) throws RemoteException {
        System.out.println("\nUploading image " + imageName + "...");
        System.out.println("Image size: " + image.length + " bytes");
        System.out.println("Image name: " + imageName);
        if (imageOne == null) {
            imageOne = image;
            imageNameOne = imageName;
            System.out.println("Image " + imageName + " uploaded!");
        } else if (imageTwo == null) {
            imageTwo = image;
            imageNameTwo = imageName;
            System.out.println("Image " + imageName + " uploaded!");
        } else {
            System.out.println("Images are full!");
        }
    }

    @Override
    public byte[] getImageOne() throws RemoteException {
        return imageOne;
    }

    @Override
    public byte[] getImageTwo() throws RemoteException {
        return imageTwo;
    }

    @Override
    public String getImageNameOne() throws RemoteException {
        return imageNameOne;
    }

    @Override
    public String getImageNameTwo() throws RemoteException {
        return imageNameTwo;
    }

}
