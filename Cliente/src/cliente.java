
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class cliente {
	public static void main(String args[]) throws InterruptedException {

		Socket s = null;
		try {

			int serverPort = 5003;
			s = new Socket("localhost", serverPort);
			DataInputStream in = new DataInputStream(s.getInputStream());
			DataOutputStream out = new DataOutputStream(s.getOutputStream());

			Scanner sn = new Scanner(System.in);
			int opcion = 0;
			int salir = 0;
			String sa;
			String result;
			String satr;
			String resultr;
			Boolean comp;
			float cuenta;
			float saldo;
			String ok;
			String nuevosaldo;

			do {
				//solicitamos el numero de cuenta con el que vamos a trabajar y le pedimos al usuario que sellecione una opcion del menu
				System.out.println("Introduzca su numero de cuenta: \n");
				Scanner sc = new Scanner(System.in);
				String numcuenta = sc.nextLine();
				//le mandamos el numero de cuenta al servidor seleccionando la opcion que queremos en este caso la opcion de leer una cuenta y este nos devolvera el saldo de la cuenta
				//comprobamos tambien si la cuenta es correcta para poder continuar 
				out.writeUTF(numcuenta);
				out.writeUTF("1"); 
				result = in.readUTF();
				sa = in.readUTF();
				if ("ok".equals(result)) {
				
					System.out.println("Seleccione una opción: \n");
					System.out.println("1.- Consultar saldo \n");
					System.out.println("2.- Sacar dinero \n");
					System.out.println("3.- Ingresar dinero \n");
					System.out.println("4.- Transferencia de cuentas \n");
					System.out.println("0.- Salir \n");
					opcion = Integer.parseInt(sn.nextLine());
	
					switch (opcion) {
	
					case 1:
						//en caso de seleccionarse la opcion 1 y tras haber recibido el saldo de la cuenta, le mostramos el saldo al cliente por pantalla
							saldo = Float.parseFloat(sa);
							System.out.println("Su saldo es: \n" + saldo);
						break;
	
					case 2:
						//comprobamos si la cuenta esta bloqueada, en caso de que no, la bloqueamos y operamos en caso de que si no se podra realizar ninguna operacion
						    out.writeUTF(numcuenta);
					        out.writeUTF("5");
					        comp = in.readBoolean();
					        if(comp == true) {
							//solicitamos al servidor que bloque la cuenta en la que se va a operar
								out.writeUTF(numcuenta);
							    out.writeUTF("3");
								saldo = Float.parseFloat(sa);
								//solicitamos al usuario la cantidad de dinero a extraer de la cuenta y tras hacer el calculo mostramos el nuevo saldo al usuario
								System.out.println("Introduzca la cantidad a extraer: \n");
								float extraer = sn.nextFloat();
								cuenta = saldo - extraer;
								nuevosaldo = Float.toString(cuenta);
								System.out.println("Su nuevo saldo es de: \n" + cuenta);
		
								//en caso de quedar un saldo positivo solicitamos al servidor que desbloque la cuenta y realizamos la escritura del nuevo saldo
								if (cuenta >= 0) {
									out.writeUTF(numcuenta);
									out.writeUTF("2");
									out.writeUTF(nuevosaldo);
									out.writeUTF(numcuenta);
									out.writeUTF("4");
									
									//comprobamos que la escritura se ha realizado con exito o no
									ok = in.readUTF();
									if ("ok".equals(ok)) {
										System.out.println("Se ha realizado con exito \n");
									} else {
										System.out.println("ERROR \n");
									}
		
									//en caso de que el nuevo saldo de la cuenta sea negativo, preguntamos al usuario si desea continuar con la transaccion
									//en caso de que si quiera continuar, repetimos el proceso de desbloqueo escritura y confirmacion, y en caso negativo 
								} else {
									System.out.println(
											"El saldo de la cuenta pasara a estar en negativo ¿desea continuar? 1-SI 2-NO \n");
									int confirmacion = sn.nextInt();
									if (confirmacion == 1) {
										out.writeUTF(numcuenta);
										out.writeUTF("2");
										out.writeUTF(nuevosaldo);
										out.writeUTF(numcuenta);
										out.writeUTF("4");
											
										ok = in.readUTF();
										if ("ok".equals(ok)) {
											System.out.println("Se ha realizado con exito: \n");
										} else {
											System.out.println("ERROR \n");
										}
									} else {
										System.out.println("Transacción finalizada \n");
									}
								}
					        }else {
					        	System.out.println("Esta cuenta esta bloqueada \n");
					        }
						break;
	
					case 3:
							//comprobamos si la cuenta esta bloqueada, en caso de que no, la bloqueamos y operamos en caso de que si no se podra realizar ninguna operacion
						    out.writeUTF(numcuenta);
					        out.writeUTF("5");
					        comp = in.readBoolean();
					        if(comp == true) {
							//solicitamos al servidor que bloque la cuenta en la que se va a operar
							   out.writeUTF(numcuenta);
							    out.writeUTF("3");
								saldo = Float.parseFloat(sa);
								//solicitamos al usuario la cantidad de dinero a ingresar de la cuenta y tras hacer el calculo mostramos el nuevo saldo al usuario
								System.out.println("Introduzca la cantidad a ingresar: \n");
								float ingresar = sn.nextFloat();
								cuenta = saldo + ingresar;
								System.out.println("Su nuevo saldo es de: \n" + cuenta);
		
								//solicitamos al servidor que desbloque la cuenta y realizamos la escritura del nuevo saldo. Finalmente confirmamos si se ha realizado correctamente la operacion
								nuevosaldo = Float.toString(cuenta);
								out.writeUTF(numcuenta);
								out.writeUTF("4");
								out.writeUTF(numcuenta);
								out.writeUTF("2");
								out.writeUTF(nuevosaldo);
								
								ok = in.readUTF();
								if ("ok".equals(ok)) {
									System.out.println("Se ha realizado con exito: \n");
								} else {
									System.out.println("ERROR \n");
								}
					        }else {
					        	System.out.println("Esta cuenta esta bloqueada \n");
					        }
						break;
						
					case 4: 
							//comprobamos si la cuenta esta bloqueada, en caso de que no, la bloqueamos y operamos en caso de que si no se podra realizar ninguna operacion
						    out.writeUTF(numcuenta);
					        out.writeUTF("5");
					        comp = in.readBoolean();
					        if(comp == true) {
							//solicitamos al usuario la cuenta a la que desea realizar la transferencia y pedimos la info de la cuenta al servidor para comprobar que esta sea correcta antes de continuar
								saldo = Float.parseFloat(sa);
								System.out.println("Introduzca la cuenta a la que realizar la transferencia: \n");
								String cuentatr = sn.nextLine();
								out.writeUTF(cuentatr);
								out.writeUTF("1");
								resultr = in.readUTF();
							    satr = in.readUTF();
								out.writeUTF(numcuenta);
								out.writeUTF("3");
								if ("ok".equals(result)) {
									//solicitamos al usuario la cantidad de dinero a extraer de la cuenta y tras hacer el calculo mostramos el nuevo saldo al usuario
									System.out.println("Introduzca la cantidad a transferir: \n");
									float transfer = sn.nextFloat();
									cuenta = saldo - transfer;
									System.out.println("Su nuevo saldo es de: \n" + cuenta);
									nuevosaldo = Float.toString(cuenta);
		
									if (cuenta >= 0) {
										//en caso de que el nuevo saldo de la cuenta sea positivo, solicitamos al servidor la escritura del nuevo saldo 
										out.writeUTF(numcuenta);
										out.writeUTF("2"); 
										out.writeUTF(nuevosaldo);
										out.writeUTF(numcuenta);
										out.writeUTF("4");
										
										//sumamos la cantidad a transferir a la cuenta deseada y de nuevo solicitamos al servidor la escritura del nuevo saldo
										out.writeUTF(cuentatr);
										out.writeUTF("3");
										float saldotr = Float.parseFloat(satr);
										float cuenta2 = saldotr + transfer;
										String nuevosaldotr = Float.toString(cuenta2);
	
										out.writeUTF(cuentatr);
										out.writeUTF("2");
										out.writeUTF(nuevosaldotr);
										out.writeUTF(cuentatr);
										out.writeUTF("4");
	
										ok = in.readUTF();
										if ("ok".equals(ok)) {
											System.out.println("Se ha realizado con exito: \n");
										} else {
											System.out.println("ERROR \n");
										}
										//en caso de que el nuevo saldo de la cuenta sea negativo, cancelamos la operacion
									} else {
										System.out.println("ERROR - El saldo de la cuenta pasara a estar en negativo  \n");
									} 
								} else {
									System.out.println("La cuenta introducida es incorrecta o no existe \n");
									}
					        }else {
					        	System.out.println("Esta cuenta esta bloqueada \n");
					        }
						break;
						
					case 0:
						out.writeUTF("6");
						break;
					}
					
				} else {
				System.out.println("La cuenta introducida es incorrecta o no existe \n");
				}
				
			} while (opcion != 0);

		} catch (UnknownHostException e) {
			System.out.println("Socket:" + e.getMessage());
		} catch (EOFException e) {
			System.out.println("EOF:" + e.getMessage());
		} catch (IOException e) {
			System.out.println("readline: " + e.getMessage());
		} finally {
			if (s != null)
				try {
					s.close();
				} catch (IOException e) {
					System.out.println("close:" + e.getMessage());
				}
		}
	}
}