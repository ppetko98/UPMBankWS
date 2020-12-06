/**
 * UPMBankWSSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
package es.upm.fi.sos.upmbank;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.transport.http.HTTPConstants;

import es.upm.fi.sos.upmbank.UPMAuthClient.UPMAuthenticationAuthorizationWSSkeletonStub;
import es.upm.fi.sos.upmbank.UPMAuthClient.UPMAuthenticationAuthorizationWSSkeletonStub.AddUser;
import es.upm.fi.sos.upmbank.UPMAuthClient.UPMAuthenticationAuthorizationWSSkeletonStub.ChangePassword;
import es.upm.fi.sos.upmbank.UPMAuthClient.UPMAuthenticationAuthorizationWSSkeletonStub.ChangePasswordBackEnd;
import es.upm.fi.sos.upmbank.UPMAuthClient.UPMAuthenticationAuthorizationWSSkeletonStub.ChangePasswordResponseE;
import es.upm.fi.sos.upmbank.UPMAuthClient.UPMAuthenticationAuthorizationWSSkeletonStub.Login;
import es.upm.fi.sos.upmbank.UPMAuthClient.UPMAuthenticationAuthorizationWSSkeletonStub.LoginBackEnd;
import es.upm.fi.sos.upmbank.UPMAuthClient.UPMAuthenticationAuthorizationWSSkeletonStub.RemoveUser;
import es.upm.fi.sos.upmbank.UPMAuthClient.UPMAuthenticationAuthorizationWSSkeletonStub.RemoveUserE;
import es.upm.fi.sos.upmbank.UPMAuthClient.UPMAuthenticationAuthorizationWSSkeletonStub.RemoveUserResponseE;
import es.upm.fi.sos.upmbank.UPMAuthClient.UPMAuthenticationAuthorizationWSSkeletonStub.UserBackEnd;
import es.upm.fi.sos.upmbank.xsd.AddMovementResponse;
import es.upm.fi.sos.upmbank.xsd.BankAccount;
import es.upm.fi.sos.upmbank.xsd.BankAccountResponse;
import es.upm.fi.sos.upmbank.xsd.Movement;
import es.upm.fi.sos.upmbank.xsd.MovementList;
import es.upm.fi.sos.upmbank.xsd.Response;
import es.upm.fi.sos.upmbank.xsd.User;

/**
 * UPMBankWSSkeleton java skeleton for the axisService
 */
public class UPMBankWSSkeleton {

	// Class variables
	private final static String superuserName = "superuser",
			superuserPwd = "superuser";
	private static int sessions = 0;
	private static User superuser = new User();
	private UPMAuthenticationAuthorizationWSSkeletonStub upmauth;

	// private static LinkedList<User> usersList = new
	// LinkedList<User>();//Linked List that contains all created users
	private static Map<String, User> usersMap = new HashMap<String, User>();
	//private static Map<String, LinkedList<BankAccountP>> accountsMap = new HashMap<String, LinkedList<BankAccountP>>(); //Maps username with bank account list
	private static Map<String, Map<String, BankAccountP>> accountsMap = new HashMap<String, Map<String,BankAccountP>>(); //Maps username with a list (map) of iban and bank account
	private static Map<String, ArrayList<MovementP>> movementsMap = new HashMap<String, ArrayList<MovementP>>();//Maps bankAcc-iban with movements on that acc
	private static Map<String, Integer> activeSessions = new HashMap<String, Integer>();//Maps username with number of sessions

//	private Response response = new Response();
	private String user = "", pwd = "";

	public UPMBankWSSkeleton() throws AxisFault {
		if (sessions == 0) {
			superuser.setName(superuserName);
			superuser.setPwd(superuserPwd);
			usersMap.put(superuserName, superuser);
		}
		upmauth = new UPMAuthenticationAuthorizationWSSkeletonStub();
		upmauth._getServiceClient().engageModule("addressing");
		upmauth._getServiceClient().getOptions().setManageSession(true);
		
		int timeOutInMilliSeconds = 1000*60*10;
		Options options = upmauth._getServiceClient().getOptions();
		options.setTimeOutInMilliSeconds(timeOutInMilliSeconds);
		options.setProperty(HTTPConstants.SO_TIMEOUT, timeOutInMilliSeconds);
		options.setProperty(HTTPConstants.CONNECTION_TIMEOUT, timeOutInMilliSeconds);
		
		System.out
				.println("Actualmente hay " + sessions + " sesiones activas.");
/*
		System.out.println("Lista de usuarios registrados: \n");
		for (String username : usersMap.keySet()) {
			System.out.println("\t\t" + username);
		}*/
		printInfo();
	}
	
	

	/**
	 * Auto generated method signature
	 * 
	 * @param addBankAcc
	 * @return addBankAccResponse
	 */

	public es.upm.fi.sos.upmbank.AddBankAccResponse addBankAcc(
			es.upm.fi.sos.upmbank.AddBankAcc addBankAcc) {

		double quantity = addBankAcc.getArgs0().getQuantity();

		AddBankAccResponse abar = new AddBankAccResponse();
		BankAccountResponse resp = new BankAccountResponse();

		if (isLogged(user)) {
			String iban = "";
			do {
				iban = generateIBAN();
			}while (existsIBAN(iban));//generates random iban assuring it is not already in use
			BankAccountP bankAcc = new BankAccountP(quantity);
			bankAcc.setIBAN(iban);
			
			if (accountsMap.containsKey(user)) {
				accountsMap.get(user).put(iban, bankAcc);
				resp.setResult(true);
				resp.setIBAN(iban);
				System.out.println("Cuenta bancaria añadida al usuario - "+user);
				System.out.println("Lista de cuentas abiertas del cliente:\n"+printAccList(user));
			} else {
				Map<String, BankAccountP> auxMap = new HashMap<String, BankAccountP>();
				auxMap.put(iban,bankAcc);
				accountsMap.put(user,auxMap);
				
				resp.setResult(true);
				resp.setIBAN(iban);
				System.out.println("Cuenta bancaria añadida al usuario - "+user);
				System.out.println("Abierta nueva lista de cuentas para el cliente");
			}
			
		} else {
			resp.setResult(false);
			System.out
					.println("Add Bank Account incorrecto - Usuario no loggeado");
		}
		abar.set_return(resp);
		printInfo();
		return abar;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param closeBankAcc
	 * @return closeBankAccResponse
	 */

	public es.upm.fi.sos.upmbank.CloseBankAccResponse closeBankAcc(
			es.upm.fi.sos.upmbank.CloseBankAcc closeBankAcc) {
		
		String iban = closeBankAcc.getArgs0().getIBAN();
		
		CloseBankAccResponse cbar = new CloseBankAccResponse();
		Response clresp = new Response();
		
		if (isLogged(user)) {
			BankAccountP ba = getAcc(user, iban);
			if (ba!=null) {
				double saldo = ba.getBalance();
				if (saldo==0.0) {
					
					accountsMap.get(user).remove(ba.getIBAN());
					
					if (existsIBAN(iban)) {
						System.out.println("Close Bank Account incorrecto - Cuenta no eliminada");
						clresp.setResponse(false);
					}else{
						System.out.println("Close Bank Account correcto");
						clresp.setResponse(true);
					}
					
				}else{
					System.out.println("Close Bank Account incorrecto - Cuenta con saldo");
					clresp.setResponse(false);
				}
			}else{
				System.out.println("Close Bank Account incorreto - Cuenta no existente");
				clresp.setResponse(false);
			}
		}else{
			System.out.println("Close Bank Account incorrecto - usuario no loggeado");
			clresp.setResponse(false);
		}
		
		cbar.set_return(clresp );
		printInfo();
		return cbar;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param logout
	 * @return
	 */

	public void logout(es.upm.fi.sos.upmbank.Logout logout) {

		if (!user.equals("") && activeSessions.get(user)>0) {
			int updatedSessions = incrementSession(user, false);
			
			if (updatedSessions == 0) {
				activeSessions.remove(user);
				user = "";
				pwd = "";
				System.out.println("Logout - Usuario sin ninguna sesión abierta");
			} else {
				System.out.println("Logout - Usuario: "+user+" - tiene "+updatedSessions+" sesiones abiertas");
			}
		}else{
			System.out.println("Logout - Ningún usuario loggeado");
		}
		printInfo();
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param removeUser
	 * @return removeUserResponse
	 * @throws RemoteException
	 */

	public es.upm.fi.sos.upmbank.RemoveUserResponse removeUser(
			es.upm.fi.sos.upmbank.RemoveUser removeUser) throws RemoteException {

		RemoveUserResponse ruresp = new RemoveUserResponse();
		Response respuesta = new Response();
		String username = removeUser.getArgs0().getUsername();

		System.out.println("Username a eliminar: "+username);
		System.out.println(accountsMap.get(username)==null);
		
		if (user.equals(superuserName) && activeSessions.get(user) > 0) {
			System.out.println("Superuser logged");
			if ( accountsMap.get(username)==null || accountsMap.get(username).isEmpty()) {
				System.out.println("user se debe poder eliminar");
				
//				RemoveUserE removingUser = new RemoveUserE();
//				Username userRemoved = new Username();
//				userRemoved.setUsername(username);
//				removeUser.setArgs0(userRemoved);
				//RemoveUserResponseE rmResponse = upmauth
				//		.removeUser(removingUser);
				
				RemoveUserE removeUser0 = new RemoveUserE();
				RemoveUser param = new RemoveUser();
				removeUser0.setRemoveUser(param);
				param.setName(username);
				RemoveUserResponseE rmResponse = upmauth.removeUser(removeUser0);
				
				if (rmResponse.get_return().getResult()) {
					Set<String> accSet = accountsMap.get(username).keySet();
					for (String iban : accSet) {
						movementsMap.remove(iban);
					}
					accountsMap.remove(username);
					usersMap.remove(username);
					activeSessions.remove(username);
					System.out.println("Remove User correcto");
					respuesta.setResponse(true);
				} else {
					System.out
							.println("Remove User incorrecto - Servicio UPM Authentication");
					respuesta.setResponse(false);
				}
			} else {
				System.out
						.println("Remove User incorrecto - Cliente con cuentas abiertas");
				respuesta.setResponse(false);
			}
		} else {
			System.out.println("Remove User incorrecto - login no válido");
			respuesta.setResponse(false);
		}
		ruresp.set_return(respuesta);
		printInfo();
		return ruresp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param addWithdrawal
	 * @return addWithdrawalResponse
	 */

	public es.upm.fi.sos.upmbank.AddWithdrawalResponse addWithdrawal(
			es.upm.fi.sos.upmbank.AddWithdrawal addWithdrawal) {
		
		String iban = addWithdrawal.getArgs0().getIBAN();
		double quantity = addWithdrawal.getArgs0().getQuantity();
		
		AddWithdrawalResponse awr = new AddWithdrawalResponse();
		AddMovementResponse resp = new AddMovementResponse();

		if (isLogged(user)) {
			if (existsIBAN(iban)) {
				
				if (quantity<=accountsMap.get(user).get(iban).getBalance()) {
					
				
				double balance = accountsMap.get(user).get(iban).getBalance() - quantity;
				accountsMap.get(user).get(iban).setBalance(balance);
				resp.setBalance(balance);
				resp.setResult(true);
				System.out.println("Retirada correcta - Cuenta: "+iban+"\t\t\t\t\t\tSaldo: "+balance);
				//add movement
				MovementP mov = new MovementP();
				mov.setIBAN(iban);
				mov.setQuantity(-quantity);
				mov.setMovDate(new Date());
				if (movementsMap.containsKey(iban)) {
					ArrayList<MovementP> movList = movementsMap.get(iban);
					movList.add(mov);
					movementsMap.put(iban, movList);
					System.out.println("Retirada correcta - Lista de movimientos actualizada");
				}else{
					ArrayList<MovementP> movList = new ArrayList<MovementP>();
					movList.add(mov);
					movementsMap.put(iban, movList);
					System.out.println("Retirada correcta - Lista de movimientos creada");
				}
				}else{
					System.out.println("Retirada incorrecta - Cuenta con saldo insuficiente");
					resp.setResult(false);
				}
				
			}else{											
				System.out.println("Retirada incorrecta - Cuenta bancaria no existente");
				resp.setResult(false);
			}
		}else{
			System.out.println("Retirada incorrecta - Usuario no loggeado");
			resp.setResult(false);
		}
		
		awr.set_return(resp);
		return awr;
		
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param addUser
	 * @return addUserResponse
	 * @throws RemoteException
	 */

	public es.upm.fi.sos.upmbank.AddUserResponse addUser(
			es.upm.fi.sos.upmbank.AddUser addUser) throws RemoteException {

		String username = addUser.getArgs0().getUsername();
		String password = "";
		
		AddUserResponse aur = new AddUserResponse();
		
		es.upm.fi.sos.upmbank.xsd.AddUserResponse resp = new es.upm.fi.sos.upmbank.xsd.AddUserResponse();
		
		if (!user.equals(superuserName)) {
			resp.setResponse(false);
			System.out.println("Error al añadir usuario - Superuser no logged");
		}else if(usersMap.containsKey(username)){
			resp.setResponse(false);
			System.out.println("Error al añadir usuario - Usuario ya registrado");
		}
		else if (!username.equals("")){

			AddUser addUser2 = new AddUser();
			UserBackEnd ube = new UserBackEnd();
			ube.setName(username);
			addUser2.setUser(ube);
			
			es.upm.fi.sos.upmbank.UPMAuthClient.UPMAuthenticationAuthorizationWSSkeletonStub.AddUserResponse responseUPM = upmauth.addUser(addUser2);
			
			if (responseUPM.get_return().getResult()) {
				password = responseUPM.get_return().getPassword();
				User addedUser = new User();
				addedUser.setName(username);
				addedUser.setPwd(password);
				usersMap.put(username, addedUser);
				resp.setPwd(password);
				resp.setResponse(true);
				System.out.println("Usuario añadido:\tusername = " + username
						+ "\n\t\t\t\tpassword = " + password);
				accountsMap.put(username, new HashMap<String, UPMBankWSSkeleton.BankAccountP>());
				
			} else {
				System.out.println("Error al añadir usuario - UPM Auth");
				resp.setResponse(false);
			}
		}else{
			System.out.println("Nombre de usuario no válido");
			resp.setResponse(false);
		}
		aur.set_return(resp);
		printInfo();
		return aur;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param addIncome
	 * @return addIncomeResponse
	 */

	public es.upm.fi.sos.upmbank.AddIncomeResponse addIncome(
			es.upm.fi.sos.upmbank.AddIncome addIncome) {
		
		String iban = addIncome.getArgs0().getIBAN();
		double quantity = addIncome.getArgs0().getQuantity();
		
		AddIncomeResponse aire = new AddIncomeResponse();
		AddMovementResponse resp = new AddMovementResponse();
		
		if (isLogged(user)) {
//			BankAccountP bp = getAcc(user, iban);
			if (existsIBAN(iban)) {
				double balance = quantity + accountsMap.get(user).get(iban).getBalance();
				accountsMap.get(user).get(iban).setBalance(balance);
				resp.setBalance(balance);
				resp.setResult(true);
				System.out.println("Ingreso añadido - Cuenta: "+iban+"\t\t\t\t\t\tSaldo: "+balance);
				//add movement
				MovementP mov = new MovementP();
				mov.setIBAN(iban);
				mov.setQuantity(quantity);
				mov.setMovDate(new Date());
				if (movementsMap.containsKey(iban)) {
					ArrayList<MovementP> movList = movementsMap.get(iban);
					movList.add(mov);
					movementsMap.put(iban, movList);
					System.out.println("Ingreso añadido - Lista de movimientos actualizada");
				}else{
					ArrayList<MovementP> movList = new ArrayList<UPMBankWSSkeleton.MovementP>();
					movList.add(mov);
					movementsMap.put(iban, movList);
					System.out.println("Ingreso añadido - Lista de movimientos creada");
				}
				
			}else{											
				System.out.println("Error al añadir ingreso - Cuenta bancara no existente");
				resp.setResult(false);
			}
		}else{
			System.out.println("Error al añadir ingreso - Usuario no loggeado");
			resp.setResult(false);
		}
		
		aire.set_return(resp);
		return aire;
		
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param login
	 * @return loginResponse
	 * @throws RemoteException
	 */

	public es.upm.fi.sos.upmbank.LoginResponse login(
			es.upm.fi.sos.upmbank.Login login) throws RemoteException {

		LoginResponse lr = new LoginResponse();
		Response resp = new Response();

		String log_name = login.getArgs0().getName();
		String log_pwd = login.getArgs0().getPwd();

		if (!usersMap.containsKey(log_name)) {
			System.out.println("login incorrecto - usuario no registrado");// Usuario
																			// no
																			// registrado
			resp.setResponse(false);
		} else {
			if (log_name.equals(superuserName) && log_pwd.equals(superuserPwd)) {// Login
																					// superusuario
				user = log_name;
				pwd = log_pwd;
				resp.setResponse(true);
				System.out.println("login correcto - superuser: "
						+ incrementSession(user, true)
						+ " sesiones actualmente activas");
			} else if (!user.equals("") && !log_name.equals(user)) {// Un
																	// usuario
																	// U2 no
																	// puede
																	// hacer
																	// login si
																	// está
																	// autenticado
																	// desde un
																	// U1
				resp.setResponse(false);
				System.out.println("login incorrecto - usuario no coincidente");
			} else {
				if (log_name.equals(user)) {// Un usuario ya autenticado intenta
											// hacer login
					resp.setResponse(true);
					System.out.println("login correcto - " + log_name + ": "
							+ incrementSession(user, true)
							+ " sesiones actualmente activas");
				} else if (user.equals("")
						&& log_pwd.equals(usersMap.get(log_name).getPwd())) {

					LoginBackEnd loginbackend = new LoginBackEnd();
					loginbackend.setName(log_name);
					loginbackend.setPassword(log_pwd);
					Login login1 = new Login();
					login1.setLogin(loginbackend);
					es.upm.fi.sos.upmbank.UPMAuthClient.UPMAuthenticationAuthorizationWSSkeletonStub.LoginResponse loginResponse = upmauth
							.login(login1);
					if (loginResponse.get_return().getResult()) {
						user = log_name;
						pwd = log_pwd;
						resp.setResponse(true);
						System.out.println("login correcto - " + log_name
								+ ": " + incrementSession(user, true)
								+ " sesiones actualmente activas");

					} else {
						System.out
								.println("login incorrecto - UPM Authentication");
						resp.setResponse(false);
					}

				} else {
					System.out.println("login incorrecto - password errónea");
					resp.setResponse(false);
				}
			}
		}
		lr.set_return(resp);
		printInfo();
		return lr;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param getMyMovements
	 * @return getMyMovementsResponse
	 */

	public es.upm.fi.sos.upmbank.GetMyMovementsResponse getMyMovements(
			es.upm.fi.sos.upmbank.GetMyMovements getMyMovements) {
		
		GetMyMovementsResponse gmmr = new GetMyMovementsResponse();
		MovementList resp = new MovementList();
		
		MovementList finalMList = new MovementList();
//		finalMList.setMovementQuantities();
		System.out.println("Recuperar movimientos");
		if (isLogged(user)) {
			Set<String> allBankAcc = accountsMap.get(user).keySet();
			ArrayList<MovementP> allTransactions = new ArrayList<UPMBankWSSkeleton.MovementP>();
			for (String iban : allBankAcc) {
				 allTransactions.addAll(movementsMap.get(iban));
			}
			System.out.println("recuperado todas las transacciones");
			Collections.sort(allTransactions, dateComparator);
			double[] array = new double[10];
			
			int i = 0;
			for (MovementP item : allTransactions) {
				array[i] = item.getQuantity(); 
				i++;
			}
			System.out.println("lista generada");
			finalMList.setMovementQuantities(array);
			//gmmr.set_return(finalMList );
			resp.setMovementQuantities(array);
			resp.setResult(true);
			System.out.println("Recuperar Movimientos correcto");
			System.out.println(array.toString());
		}else{
			resp.setResult(false);
			System.out.println("Recuperar Movimientos erróneo - usuario no loggeado");
		}
		gmmr.set_return(resp);
		return gmmr;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param changePassword
	 * @return changePasswordResponse
	 * @throws RemoteException
	 */

	public es.upm.fi.sos.upmbank.ChangePasswordResponse changePassword(
			es.upm.fi.sos.upmbank.ChangePassword changePassword)
			throws RemoteException {

		ChangePasswordResponse chpwre = new ChangePasswordResponse();
		Response chpwResponse = new Response();

		String oldPwd = changePassword.getArgs0().getOldpwd();
		String newPwd = changePassword.getArgs0().getNewpwd();

		if (!user.equals("") && pwd.equals(oldPwd)
				&& activeSessions.get(user) > 0) {

			ChangePassword changePassword8 = new ChangePassword();
			ChangePasswordBackEnd param = new ChangePasswordBackEnd();
			param.setName(user);
			param.setOldpwd(oldPwd);
			param.setNewpwd(newPwd);
			changePassword8.setChangePassword(param);
			ChangePasswordResponseE changePasswordResponseE = upmauth
					.changePassword(changePassword8);
			if (changePasswordResponseE.get_return().getResult()) {
				chpwResponse.setResponse(true);
				System.out.println("... Actualizando datos en memoria ...");
				pwd = newPwd;
				User localUser = new User();
				localUser.setName(user);
				localUser.setPwd(pwd);
				usersMap.put(user, localUser);

				System.out.println("Change Password correcto");
			} else {
				chpwResponse.setResponse(false);
				System.out
						.println("Change Password incorrecto - UPM Authentication");
			}

		} else {
			System.out
					.println("Change Password incorrecto - usuario mal identificado");
		}
		chpwre.set_return(chpwResponse);
		return chpwre;
	}

	// Private method that increments or decrements the current session count
	// for a given user
	// Reuturns the current active sessions for that user
	private int incrementSession(String user, boolean incrementOrDecrement) {
		int currentSessions = 0;

		if (activeSessions.containsKey(user) && incrementOrDecrement) {
			currentSessions = activeSessions.get(user) + 1;
			activeSessions.put(user, currentSessions);
			sessions++;
		} else if (activeSessions.containsKey(user) && !incrementOrDecrement) {
			currentSessions = activeSessions.get(user) - 1;
			activeSessions.put(user, currentSessions);
			sessions--;
		} else if (!activeSessions.containsKey(user) && incrementOrDecrement) {
			activeSessions.put(user, 1);
			sessions++;
			currentSessions = 1;
		} else {
			
		}

		return currentSessions;
	}

	// Private method that return a boolean whether the user has logged in in
	// this session
	private boolean isLogged(String userCheck) {
		boolean value = false;
		if (user.equals(userCheck) && activeSessions.get(userCheck) > 0) {
			value = true;
		}
		return value;
	}

	private String generateIBAN() {

		Random rand = new Random();
		String card = "ES";
		for (int i = 0; i < 14; i++) {
			int n = rand.nextInt(10) + 0;
			card += Integer.toString(n);
		}
		String iban = "";
		for (int i = 0; i < 16; i++) {
			if (i!=0 && i % 4 == 0)
				iban+="-";
			iban+=card.charAt(i);
		}
		return iban;
		
	}
	
	private boolean existsIBAN(String iban){
		boolean exists = false;
		
		for (Map<String, BankAccountP> iterable_element : accountsMap.values() ) {
			if (iterable_element.containsKey(iban)) {
				exists = true;
			}
		}
		
		return exists;
	}
	
	private String printAccList(String user){
		
//		LinkedList<BankAccountP> accList = accountsMap.get(user);
		Map<String, BankAccountP> accList = accountsMap.get(user);
		String bal = "{";
		for (BankAccountP bap : accList.values()) {
			bal.concat("   "+bap.getIBAN()+":"+bap.getBalance());
		}
		bal.concat("    }");
		return "Account List for "+user+ "=" + bal;
		
	}
	
	private BankAccountP getAcc(String username, String iban){
		BankAccountP returned = null;
		for (BankAccountP bankAccount : accountsMap.get(username).values() ) {
			if (iban.equals(bankAccount.getIBAN())) {
				return bankAccount;
			}
		}
		return returned;
	}
	
	private static Comparator<MovementP> dateComparator = new Comparator<MovementP>() {
		
		@Override
		public int compare(MovementP o1, MovementP o2) {
			if(o1.getMovDate().after(o2.getMovDate()))
				return -1;
			else if (o1.getMovDate().before(o2.getMovDate()))
				return 1;
			else
				return 0;
		}
	};
	
	private void printInfo(){
		System.out.println("\nLista de usuarios registrados:");
		String line = "\t";
		for (String username : usersMap.keySet()) {
			line+=username+" | ";
		}
		System.out.println(line);
		System.out.println("\nLista de cuentas activas:");
		line="\t";
		for ( String session : activeSessions.keySet()) {
			line+=session+" | ";
		}
		System.out.println(line);
		System.out.println("\nLista de cuentas bancarias:");
		line="\t";
		for ( Map<String, BankAccountP> mapa : accountsMap.values()) {
			for (String iban : mapa.keySet()) {
				line+=iban+" | ";
			}
		}
		System.out.println(line);
	}
	
	//Private class that extends BankAccount and contains the balance value
	private class BankAccountP extends BankAccount{
		
		private double balance;

		public double getBalance() {
			return balance;
		}

		public void setBalance(double balance) {
			this.balance = balance;
		}

		public BankAccountP(double balance) {
			super();
			this.balance = balance;
		}

		public BankAccountP() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public boolean equals(Object obj) {
			return ((BankAccountP) obj).getIBAN().equals(this.getIBAN());
		}
		
	}

	private class MovementP	extends Movement{
		
		private Date movDate;

		public Date getMovDate() {
			return movDate;
		}

		public void setMovDate(Date movDate) {
			this.movDate = movDate;
		}
		
		@Override
		public boolean equals(Object obj) {
			return ((MovementP) obj).getMovDate().equals(this.getMovDate());
		}
		
	}
	
}
