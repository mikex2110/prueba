import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.net.MalformedURLException;
import java.rmi.Naming;

public class clientermi {

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        //establecemos las interfaces que van a intervenir en la tarea del paso de mensajes
        //Registry registry = LocateRegistry.getRegistry();
        leeSaldo leeRemote = (leeSaldo) Naming.lookup("rmi://localhost/leeSaldo");
        escribeSaldo escRemote = (escribeSaldo) Naming.lookup("rmi://localhost/escribeSaldo");
        finServicio finRemote = (finServicio) Naming.lookup("rmi://localhost/finServicio");
        //declaramos la variables que vamos a utilizar en general en varias partes del codigo
        Scanner sn = new Scanner(System.in);
        Scanner sos = new Scanner(System.in);
        Scanner sl = new Scanner(System.in);
            int opcion=0;
            int salir=0;
            String sa, satr, result, ok, vale, nuevosaldo, nuevosaldotr;
            float operacion=0, operaciontr=0;
            float saldo=0, saldotr=0;
            //realizamos un menu con las diferentes  opciones que va a poder llevar a cabo el usuario
            do{
            	
            	System.out.println("Introduzca su numero de cuenta: \n");
                //guardamos en numcuenta la cuenta con la vamos a operar en la primera opcion
                String numcuenta = sn.nextLine(); 
                sa = leeRemote.leerSaldo(numcuenta);   
                //pasamos a la funcion leeSaldo del servidor el contenido de numcuentay recibimos el saldo de dicha cuenta
                //o una b en caso de la cuenta este mal introducida o no exista
                if (!"b".equals(sa)){
            	
                System.out.println("Seleccione una opción: \n");
                System.out.println("1.- Consultar saldo \n");
                System.out.println("2.- Sacar dinero \n");
                System.out.println("3.- Ingresar dinero \n");
                System.out.println("4.- Transferencia de cuentas \n");
                System.out.println("0.- Salir \n");
                opcion = Integer.parseInt(sn.nextLine());
  
  
                switch (opcion) {
                    
                    //la primera opcion es para consultar el saldo de la cuenta que el usuario nos proporcione
                        case 1:                               
                            //en caso de estar correcto, mostraremos por pantalla el saldo que nos haya proporcionado el servidor
                            saldo = Float.parseFloat(sa);
                            System.out.println("Su saldo es: \n" +saldo);
                            break;
                            
                            //esta opcion servira para realizar una extraccion de saldo de la cuenta deseada
                            case 2:   
                            	
                                //con el saldo de la cuenta y el saldo a retirar introducido por el usuario realizamos los calculos del saldo 
                                //que quedará en la cuenta y le mandaremos dicho dato a la funcion escribeSaldo del servidor                              
                                saldo = Float.parseFloat(sa);
                                System.out.println("Introduzca la cantidad a extraer: \n");
                                float extraer = sos.nextFloat(); 
                                operacion = saldo-extraer;
                                //convertimos a string la operacion que hemos realizado para devolverla al sevidor
                                nuevosaldo = Float.toString(operacion);
                                //en caso de que el saldo quede negativo le preguntaremos al usuario si desea confirmar la operacion si es asi mando el saldo 

                                //invocaremos la funcion del servidor escribeSaldo pasandole como mensajes el nuevo saldo de la cuenta y el propio numero de la cuenta
                                if (operacion>=0){
                                    vale = escRemote.escribirSaldo(nuevosaldo, numcuenta);
                                    //el servidor nos manda una confirmacion de que el proceso se ha realizado con exito o no
                                    if (!"b".equals(vale)){
                                        System.out.println("Se ha realizado con exito la operacion su nuevo saldo es de: \n" +operacion);
                                    }else{
                                        System.out.println("ERROR: \n");
                                    }
                                        
                                }else{
                                    //mismo proceso pero con la comprobacion del saldo negativo
                                    System.out.println("El saldo de la cuenta pasara a estar en negativo ¿desea continuar? 1-SI 2-NO \n");
                                    int confirmacion = sos.nextInt(); 
                                    if (confirmacion==1){
                                    	vale = escRemote.escribirSaldo(nuevosaldo, numcuenta);
                                        //el servidor nos manda una confirmacion de que el proceso se ha realizado con exito o no
                                        if (!"b".equals(vale)){
                                            System.out.println("Se ha realizado con exito la operacion su nuevo saldo es de: \n" +operacion);
                                        }else{
                                            System.out.println("ERROR: \n");
                                        }
                                    }else{
                                        System.out.println("Operacion cancelada \n");
                                    }
                                }       
                            break;
                        
                        case 3:
                                saldo = Float.parseFloat(sa);
                                System.out.println("Introduzca la cantidad a ingresar: \n");
                                float ingresar = sos.nextFloat(); 
                                operacion = saldo+ingresar;
                                nuevosaldo = Float.toString(operacion);
                                
                                vale = escRemote.escribirSaldo(nuevosaldo, numcuenta);
                                if (!"b".equals(vale)){
                                    	System.out.println("Se ha realizado con exito la operacion su nuevo saldo es de: \n" +operacion);
                                    }else{
                                        System.out.println("ERROR: \n");
                                    }
                                
                        break;
                        
                        case 4:                             	
                            //solicitamos al usuario la cuenta en la que añadir el dinero y hacemos las comprobaciones 
                            System.out.println("Introduzca la cuenta a la que realizar la transferencia: \n");
                            String cuentatr = sl.nextLine();
                            satr = leeRemote.leerSaldo(cuentatr);
                            if (!"b".equals(satr)){
                            	
                        	    //solicitaremos al usuario la cantidad que quiere transferir y que se restara del saldo de su cuenta
                                saldo = Float.parseFloat(sa);
                                System.out.println("Introduzca la cantidad a transferir: \n");
                                float transfer = sos.nextFloat(); 
                                operacion = saldo-transfer;
                                nuevosaldo = Float.toString(operacion);
                                //restamos a la cuenta a cantidad a transferir, en caso de quedar la cuenta en negativo dara error en caso de ser positivo le 
                                //te mando el nuevo saldo a poner en la cuenta indicada
                                if (operacion>=0){
                                        vale = escRemote.escribirSaldo(nuevosaldo, numcuenta);
                                        if (!"b".equals(vale)){
                                    	System.out.println("Se ha realizado con exito la operacion su nuevo saldo es de: \n" +operacion);
                                    }else{
                                        System.out.println("ERROR: \n");
                                    }
                                    


                                        saldotr = Float.parseFloat(satr);
                                        operaciontr = saldotr+transfer;
                                        nuevosaldotr = Float.toString(operaciontr);
                                        vale = escRemote.escribirSaldo(nuevosaldotr, cuentatr);
                                        if (!"b".equals(vale)){
                                        	System.out.println("Se ha realizado con exito la operacion su nuevo saldo es de: \n" +operaciontr);
                                        }else{
                                            System.out.println("ERROR: \n");
                                        }
                                        
                                }else{
                                    System.out.println("ERROR - El saldo de la cuenta pasara a estar en negativo  \n");
                                } 
                             
	                         }else{
	                        	 //en caso de  que la cuenta no exista o este mal escrita se mostrara un mensaje de error
	                             System.out.println("La cuenta introducida es incorrecta o no existe \n");  
	                            } 
                        break;
                        
                        case 0:
                            //esta opcion es en el caso que el usuario desee cerrar el programa y se mandara al servidor dicha seleccion
                            String finalisasion = "fin";
                            finRemote.finalizarServicio(finalisasion);
                            System.out.println("Gracias por usar nuestros servicios.");
                        break;
                }
                
                }else{
                    //en caso de  que la cuenta no exista o este mal escrita se mostrara un mensaje de error
                    System.out.println("La cuenta introducida es incorrecta o no existe \n");  
                }
        }while (opcion !=0);
    }
}
