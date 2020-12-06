package es.upm.fi.sos.upmbank.client;

import java.rmi.RemoteException;
import java.util.Random;
import java.util.Scanner;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.transport.http.HTTPConstants;

import es.upm.fi.sos.upmbank.client.UPMBankWSStub.AddBankAcc;
import es.upm.fi.sos.upmbank.client.UPMBankWSStub.AddBankAccResponse;
import es.upm.fi.sos.upmbank.client.UPMBankWSStub.AddIncome;
import es.upm.fi.sos.upmbank.client.UPMBankWSStub.AddIncomeResponse;
import es.upm.fi.sos.upmbank.client.UPMBankWSStub.AddUser;
import es.upm.fi.sos.upmbank.client.UPMBankWSStub.AddUserResponseE;
import es.upm.fi.sos.upmbank.client.UPMBankWSStub.AddWithdrawal;
import es.upm.fi.sos.upmbank.client.UPMBankWSStub.AddWithdrawalResponse;
import es.upm.fi.sos.upmbank.client.UPMBankWSStub.BankAccount;
import es.upm.fi.sos.upmbank.client.UPMBankWSStub.ChangePassword;
import es.upm.fi.sos.upmbank.client.UPMBankWSStub.ChangePasswordResponse;
import es.upm.fi.sos.upmbank.client.UPMBankWSStub.CloseBankAcc;
import es.upm.fi.sos.upmbank.client.UPMBankWSStub.CloseBankAccResponse;
import es.upm.fi.sos.upmbank.client.UPMBankWSStub.Deposit;
import es.upm.fi.sos.upmbank.client.UPMBankWSStub.GetMyMovements;
import es.upm.fi.sos.upmbank.client.UPMBankWSStub.GetMyMovementsResponse;
import es.upm.fi.sos.upmbank.client.UPMBankWSStub.Login;
import es.upm.fi.sos.upmbank.client.UPMBankWSStub.LoginResponse;
import es.upm.fi.sos.upmbank.client.UPMBankWSStub.Logout;
import es.upm.fi.sos.upmbank.client.UPMBankWSStub.Movement;
import es.upm.fi.sos.upmbank.client.UPMBankWSStub.PasswordPair;
import es.upm.fi.sos.upmbank.client.UPMBankWSStub.RemoveUser;
import es.upm.fi.sos.upmbank.client.UPMBankWSStub.RemoveUserResponse;
import es.upm.fi.sos.upmbank.client.UPMBankWSStub.User;
import es.upm.fi.sos.upmbank.client.UPMBankWSStub.Username;

public class UPMBankClient {

	public static void main(String[] args) throws AxisFault {

		boolean end = false;

		UPMBankWSStub stub;

		stub = new UPMBankWSStub();

		stub._getServiceClient().getOptions().setManageSession(true);
		stub._getServiceClient().engageModule("addressing");
		
		int timeOutInMilliSeconds = 1000*60*10;
		Options options = stub._getServiceClient().getOptions();
		options.setTimeOutInMilliSeconds(timeOutInMilliSeconds);
		options.setProperty(HTTPConstants.SO_TIMEOUT, timeOutInMilliSeconds);
		options.setProperty(HTTPConstants.CONNECTION_TIMEOUT, timeOutInMilliSeconds);

		
		Scanner sc = new Scanner(System.in);

		String user_usuario = "", user_contraseña = "";
		User user = new User();

		while (!end) {
			try {
				System.out.println("         Operaciones           ");
				System.out.println("1. Login Usuario");
				System.out.println("2. Logout");
				System.out.println("3. Añadir nuevo Usuario");
				System.out.println("4. Eliminar Usuario");
				System.out.println("5. Cambiar Contraseña");
				System.out.println("6. Añadir Cuenta Bancaria");
				System.out.println("7. Cerrar Cuenta Bancaria");
				System.out.println("8. Retirada");
				System.out.println("9. Ingreso");
				System.out.println("10. Obtener movimientos");
				System.out.println("0. SALIR DEL CLIENTE");
				System.out.println("----- ----------- -----");
				System.out.println("User: "+user_usuario);
				System.out
						.println("Introducir numero de operación a realizar: ");

				int op = 0;
				try {
					op = sc.nextInt();
				} catch (java.util.InputMismatchException e) {
				}

				switch (op) {
				case 1:
					System.out.println("1. Login Usuario");
					System.out.println("\tNombre de usuario: ");
					sc = new Scanner(System.in);
					String user_log = sc.nextLine();
					System.out.println("\tContraseña: ");
					sc = new Scanner(System.in);
					String user_contraseña_log = sc.nextLine();
					user.setName(user_log);
					user.setPwd(user_contraseña_log);
					Login login1 = new Login();
					login1.setArgs0(user);
					if (stub.login(login1).get_return().getResponse()) {
						user_usuario = user_log;
						user_contraseña = user_contraseña_log;
					}
					System.out.println("Respuesta: "
							+ stub.login(login1).get_return().getResponse());

					break;
				case 2:
					System.out.println("2. Logout");
					Logout logout1 = new Logout();
					stub.logout(logout1);
					user_usuario = "";
					user_contraseña = "";
					System.out.println("Usuario desconectado");
					break;
				case 3:
					System.out.println("3. [sudo] Añadir nuevo Usuario");
					System.out.println("\tIntroducir nombre de usuario: ");
					sc = new Scanner(System.in);
					String user_nuevo = sc.nextLine();

					AddUser addUser9 = new AddUser();
					Username param = new Username();
					param.setUsername(user_nuevo);
					addUser9.setArgs0(param);
					AddUserResponseE response = stub.addUser(addUser9);
					response.get_return().getResponse();
					System.out.println("Respuesta: "
							+ response.get_return().getResponse());
					if (response.get_return().getResponse()) {
						System.out.println("\tContraseña generada para el usuario "
							+ user_nuevo + ": "
							+ response.get_return().getPwd());
					}
					
					break;
				case 4:
					System.out.println("4. [sudo] Eliminar Usuario");
					System.out.println("\tIntroducir nombre de usuario: ");
					sc = new Scanner(System.in);
					String user_eliminado = sc.nextLine();

					RemoveUser removeUser5 = new RemoveUser();
					Username param1 = new Username();
					param1.setUsername(user_eliminado);
					removeUser5.setArgs0(param1);
					System.out.println("Respuesta: "
							+ stub.removeUser(removeUser5).get_return()
									.getResponse());
					break;
				case 5:
					System.out.println("5. Cambiar Contraseña");
					System.out
							.println("Introducir antigua contraseña para el usuario:\n");
					sc = new Scanner(System.in);
					String old_pwd = sc.nextLine();
					System.out.println("Introducir contraseña nueva:\n");
					sc = new Scanner(System.in);
					String new_pwd = sc.nextLine();
					
					ChangePassword changePassword17 = new ChangePassword();
					PasswordPair param2 = new PasswordPair();
					param2.setOldpwd(old_pwd);
					param2.setNewpwd(new_pwd);
					changePassword17.setArgs0(param2);
					if (stub.changePassword(changePassword17)
							.get_return().getResponse()) {
						user_contraseña = new_pwd;
					}
					System.out.println("Respuesta: "
							+ stub.changePassword(changePassword17)
									.get_return().getResponse());

					break;
				case 6:
					System.out.println("6. Añadir Cuenta Bancaria");

					AddBankAcc addBankAcc0 = new AddBankAcc();
					Deposit deposit = new Deposit();
					
					System.out.println("Introducir cantidad inicial: ");
					sc = new Scanner(System.in);
					double quantity= sc.nextDouble();
					deposit.setQuantity(quantity);
					addBankAcc0.setArgs0(deposit);
					
					AddBankAccResponse responseAddBankAcc = stub
							.addBankAcc(addBankAcc0);
					System.out.println("Respuesta: "
							+ responseAddBankAcc.get_return().getResult());
					if (responseAddBankAcc.get_return().getResult()) {
						System.out.println("\tIBAN cuenta: "
							+ responseAddBankAcc.get_return().getIBAN());
					}
					break;
				case 7:
					System.out.println("7. Cerrar Cuenta Bancaria");
					System.out.println("Introducir IBAN de cuenta:");
					sc = new Scanner(System.in);
					String iban = sc.nextLine();
					CloseBankAcc closeBankAcc1 = new CloseBankAcc();
					BankAccount banacc1 = new BankAccount();
					banacc1.setIBAN(iban);
					closeBankAcc1.setArgs0(banacc1);
					CloseBankAccResponse cbaresp = stub.closeBankAcc(closeBankAcc1 );
					System.out.println("Respuesta: "+cbaresp.get_return().getResponse());
					break;
				case 8:
					System.out.println("8. Retirada");
					System.out.println("Introducir IBAN de cuenta para realizar retirada:");
					sc = new Scanner(System.in);
					String iban2 = sc.nextLine();
					System.out.println("Introducir cantidad a retirar:");
					sc = new Scanner(System.in);
					double quantity2 = sc.nextDouble();
					AddWithdrawal addWithdrawal7 = new AddWithdrawal();
					Movement movparam = new Movement();
					movparam.setIBAN(iban2);
					movparam.setQuantity(quantity2);
					addWithdrawal7.setArgs0(movparam );
					AddWithdrawalResponse awresp = stub.addWithdrawal(addWithdrawal7 );
					System.out.println("Respuesta: "+awresp.get_return().getResult());
					if (awresp.get_return().getResult()) {
						System.out.println("Saldo de la cuenta = "+awresp.get_return().getBalance());
					}
					break;
				case 9:
					System.out.println("9. Ingreso");
					System.out.println("Introducir IBAN de cuenta para realizar ingreso:");
					sc = new Scanner(System.in);
					String iban3 = sc.nextLine();
					System.out.println("Introducir cantidad a ingresar:");
					sc = new Scanner(System.in);
					double quantity3 = sc.nextDouble();
					
					AddIncome addIncome11 = new AddIncome();
					Movement movparam2 = new Movement();
					movparam2.setIBAN(iban3);
					movparam2.setQuantity(quantity3);
					addIncome11.setArgs0(movparam2 );
					AddIncomeResponse airesp = stub.addIncome(addIncome11 );
					System.out.println("Respuesta: "+airesp.get_return().getResult());
					if (airesp.get_return().getResult()) {
						System.out.println("Saldo de la cuenta = "+airesp.get_return().getBalance());
					}
					break;
				case 10:
					System.out.println("10. Obtener movimientos");
					
					GetMyMovements getMyMovements15 = new GetMyMovements();
					GetMyMovementsResponse gmmresp = stub.getMyMovements(getMyMovements15);
					System.out.println("Respuesta: "+gmmresp.get_return().getResult());
					if (gmmresp.get_return().getResult()) {
						double[] arr = gmmresp.get_return().getMovementQuantities();
						for (int i = 0; i < arr.length; i++) {
							System.out.println(arr[i]);
						}
					}
					break;
				case 0:
					System.out.println("0. SALIR DEL CLIENTE");

					System.out.println("Logout automático");
					Logout logout2 = new Logout();
					stub.logout(logout2);
					user_usuario = "";
					user_contraseña = "";
					System.out.println("Usuario desconectado");

					end = true;
					break;
				default:
					System.out.println("ERROR: Operación no reconocida");
				}

				System.out.println("\n·Pulsa INTRO tecla para continuar");
				sc = new Scanner(System.in);
				sc.nextLine();
			}

			catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		sc.close();
		System.out.println("Cliente cerrado");

	}
}
