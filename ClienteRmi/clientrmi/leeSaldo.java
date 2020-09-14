import java.rmi.Remote;
import java.rmi.RemoteException;
    
public interface leeSaldo extends Remote {

    String leerSaldo(String cuenta) throws RemoteException;

}
