
import java.rmi.Remote;
import java.rmi.RemoteException;
    
public interface finServicio extends Remote {

    String finalizarServicio(String cuenta) throws RemoteException;

}

