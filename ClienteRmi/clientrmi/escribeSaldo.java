import java.rmi.Remote;
import java.rmi.RemoteException;

    
public interface escribeSaldo extends Remote {

    String escribirSaldo(String nuevoSaldo, String cuenta) throws RemoteException;

}
