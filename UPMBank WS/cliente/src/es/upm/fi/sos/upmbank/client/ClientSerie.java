package es.upm.fi.sos.upmbank.client;

import java.rmi.RemoteException;

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

public class ClientSerie {

	public static void main(String[] args) throws RemoteException, InterruptedException {

		UPMBankWSStub stub = new UPMBankWSStub();
		stub._getServiceClient().getOptions().setManageSession(true);
		stub._getServiceClient().engageModule("addressing");
		
		String username = "", password = "", username2 = "", password2 = "";

		System.out.println("Prueba con cliente no registrado en el sistema");
		username = "petko1";
		Login login_sudo = new Login();
		User superuser = new User();
		superuser.setName("superuser");
		superuser.setPwd("superuser");
		login_sudo.setArgs0(superuser);
		LoginResponse lrs = stub.login(login_sudo);

		Logout logout4 = new Logout();
		stub.logout(logout4);
		stub.logout(logout4);
		// stub.logout(logout4);

		Login login1 = new Login();

		User usuario = new User();
		usuario.setName(username);
		usuario.setPwd("passwordDaIgual");
		login1.setArgs0(usuario);
		LoginResponse lr = stub.login(login1);
		if (lr.get_return().getResponse()) {
			System.out.println("Login correcto WTF");
		} else {
			System.out.println("Login incorrecto");
		}

		System.out.println("Prueba con superuser");
		username = "superuser";
		password = "superuser";

		usuario.setName(username);
		usuario.setPwd(password);
		login1.setArgs0(usuario);
		lr = stub.login(login1);
		if (lr.get_return().getResponse()) {
			System.out.println("Login de superuser correcto");
			System.out.println("Prueba de añadir usuario...");

			username2 = "petko31";

			AddUser addUser9 = new AddUser();
			Username param = new Username();
			param.setUsername(username2);
			addUser9.setArgs0(param);
			AddUserResponseE adduserresponse = stub.addUser(addUser9);
			if (adduserresponse.get_return().getResponse()) {
				System.out.println("Añadir usuario correcto");
				password2 = adduserresponse.get_return().getPwd();
				System.out.println("Contraseña generada para el usuario "
						+ username2 + ": " + password2);
				System.out
						.println("\nPruba de hacer login con un usuario adicional");

				Login login2 = new Login();
				User user2 = new User();
				user2.setName(username2);
				user2.setPwd(password2);
				login2.setArgs0(user2);
				LoginResponse lr2 = stub.login(login2);

				if (!lr2.get_return().getResponse()) {
					System.out.println("Login no realizado");
					System.out.println("Prueba logout de superuser");

					Logout logout1 = new Logout();
					stub.logout(logout1);

					System.out.println("Vuelta a probar login");
					LoginResponse lr3 = stub.login(login2);
					if (lr3.get_return().getResponse()) {
						System.out.println("Login correcto");
						System.out.println("Prueba de cambio de contraseña");

						ChangePassword changePassword1 = new ChangePassword();
						PasswordPair pp1 = new PasswordPair();
						pp1.setOldpwd(password2);
						password2 = "petko1234";// petko76288
												// pp1.setNewpwd(password2);
						changePassword1.setArgs0(pp1);
						ChangePasswordResponse chr1 = stub
								.changePassword(changePassword1);
						if (chr1.get_return().getResponse()) {
							System.out.println("Cambio de contraseña correcto");

							
							System.out.println("Prueba crear cuenta bancaria");
							AddBankAcc addBankAcc0 = new AddBankAcc();
							Deposit deposit = new Deposit();
							deposit.setQuantity(100);
							addBankAcc0.setArgs0(deposit);
							
							String iban = "";
							AddBankAccResponse abaresp = stub.addBankAcc(addBankAcc0);
							if (abaresp.get_return().getResult()) {
								System.out.println("Cuenta bancaria añadida correctamente:"+abaresp.get_return().getIBAN());
							
							iban = abaresp.get_return().getIBAN();
							}
							
							System.out.println("Realizar Retirada");
							AddWithdrawal addWithdrawal1 = new AddWithdrawal();
							Movement movement1 = new Movement();
							movement1.setIBAN(iban);
							movement1.setQuantity(10);
							addWithdrawal1.setArgs0(movement1);
							AddWithdrawalResponse awresp = stub.addWithdrawal(addWithdrawal1);
							if (awresp.get_return().getResult()) {
								System.out.println("Retirada Correcta, saldo restante: "+awresp.get_return().getBalance());
							}else{
								System.out.println("Retirada incorrecta??");
							}
							
							Thread.sleep(1000);
							
							System.out.println("Realizar Retirada");
							AddWithdrawal addWithdrawal2 = new AddWithdrawal();
							Movement movement2 = new Movement();
							movement2.setIBAN(iban);
							movement2.setQuantity(50);
							addWithdrawal2.setArgs0(movement2);
							AddWithdrawalResponse awresp2 = stub.addWithdrawal(addWithdrawal2);
							if (awresp2.get_return().getResult()) {
								System.out.println("Retirada Correcta, saldo restante: "+awresp2.get_return().getBalance());
							}else{
								System.out.println("Retirada incorrecta??");
							}
							
							Thread.sleep(1000);
							
							System.out.println("Realizar Retirada");
							AddWithdrawal addWithdrawal3 = new AddWithdrawal();
							Movement movement3 = new Movement();
							movement3.setIBAN(iban);
							movement3.setQuantity(40);
							addWithdrawal3.setArgs0(movement3);
							AddWithdrawalResponse awresp3 = stub.addWithdrawal(addWithdrawal3);
							if (awresp3.get_return().getResult()) {
								System.out.println("Retirada Correcta, saldo restante: "+awresp3.get_return().getBalance());
							}else{
								System.out.println("Retirada incorrecta??");
							}
							
							Thread.sleep(1000);
							
							AddIncome addIncome1 = new AddIncome();
							Movement movement5 = new Movement();
							movement5.setQuantity(20);
							movement5.setIBAN(iban);
							addIncome1.setArgs0(movement5);
							AddIncomeResponse airesp = stub.addIncome(addIncome1);
							if (airesp.get_return().getResult()) {
								System.out.println("Ingreso añadido correctamente - saldo cuenta: "+airesp.get_return().getBalance());
							}else{
								System.out.println("!!!!!!!!!!!Ingreso erróneo");
							}
							
							Thread.sleep(1000);
							
							System.out.println("Realizar Retirada");
							AddWithdrawal addWithdrawal4 = new AddWithdrawal();
							Movement movement4 = new Movement();
							movement4.setIBAN(iban);
							movement4.setQuantity(20);
							addWithdrawal4.setArgs0(movement4);
							AddWithdrawalResponse awresp4 = stub.addWithdrawal(addWithdrawal4);
							if (awresp4.get_return().getResult()) {
								System.out.println("!!!!Retirada Correcta, saldo restante: "+awresp4.get_return().getBalance());
							}else{
								System.out.println("Retirada incorrecta");
							}
							
							
							System.out.println("Obtener lista de movimientos");
							GetMyMovements getMyMovements1 = new GetMyMovements();
							GetMyMovementsResponse gmmResp = stub.getMyMovements(getMyMovements1);
							if(gmmResp.get_return().getResult()){
								System.out.println("Lista obtenida correctamente");
								double[] arr = gmmResp.get_return().getMovementQuantities();
								for (int i = 0; i < arr.length; i++) {
									System.out.println(arr[i]);
								}
							}
							
							System.out.println("Prueba eliminar cuenta bancaria");
							CloseBankAcc closeBankAcc2 = new CloseBankAcc();
							BankAccount bankacc = new BankAccount();
							bankacc.setIBAN(iban);
							closeBankAcc2.setArgs0(bankacc);
							CloseBankAccResponse cbaresp = stub.closeBankAcc(closeBankAcc2);
							if (cbaresp.get_return().getResponse()) {
								System.out.println("Cuenta eliminada");
							}else{
								System.out.println("!!!!!!!Cuenta no eliminada");
							}
							
							logout1 = new Logout();
							stub.logout(logout1);

							System.out
									.println("Se ha tenido que hacer logout, se intenta iniciar sesion de nuevo con superuser");
							Login login3 = new Login();
							username = "superuser";
							password = "superuser";
							User superusuario = new User();
							superusuario.setName(username);
							superusuario.setPwd(password);
							login3.setArgs0(superusuario);
							lr = stub.login(login3);

							if (lr.get_return().getResponse()) {
								System.out
										.println("login de superuser correcto");
								System.out
										.println("Prueba de eliminar usuario...");

								RemoveUser removeUser1 = new RemoveUser();
								Username username1 = new Username();
								username1.setUsername(username2);
								removeUser1.setArgs0(username1);
								RemoveUserResponse ruresponse = stub
										.removeUser(removeUser1);
								if (ruresponse.get_return().getResponse()) {
									System.out
											.println("Usuario eliminado correctamente");

									
//									login_sudo = new Login();
//									superuser = new User();
//									superuser.setName("superuser");
//									superuser.setPwd("superuser");
//									login_sudo.setArgs0(superuser);
//									lrs = stub.login(login_sudo);

									logout4 = new Logout();
									stub.logout(logout4);
									System.out.println("Logout de superusuario");
									
									Logout logout5 = new Logout();
									stub.logout(logout5 );
//									Login login4 = new Login();
//									User user3 = new User();
//									
//									login4.setArgs0(user3 );
//									stub.login(login4 );
									
									System.exit(0);
								} else {
									System.out
											.println("!!!!!!!!!! Remove user incorrecto");
									System.exit(1);
								}
							} else {
								System.out.println("!!!!!!!! Login incorrecto");
								System.exit(1);
							}

						} else {
							System.out
									.println("!!!!!!!Cambio de contraseña incorrecto");
							System.exit(1);
						}
					} else {
						System.out.println("!!!!!!!! Login incorrecto");
						System.exit(1);
					}

				} else {
					System.out.println("!!!!!!!!!!Login realizado");
					System.exit(1);
				}

			} else {
				System.out.println("!!!!!!!!!!Añadir usuario erróneo");
				System.exit(1);
			}
		} else {
			System.out.println("!!!!!!!!!!!!!Login de superuser incorrecto");
			System.exit(1);
		}

	}
}
