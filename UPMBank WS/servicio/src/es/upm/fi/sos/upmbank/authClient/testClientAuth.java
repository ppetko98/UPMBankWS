package es.upm.fi.sos.upmbank.authClient;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import es.upm.fi.sos.upmbank.UPMAuthClient.UPMAuthenticationAuthorizationWSSkeletonStub;
import es.upm.fi.sos.upmbank.UPMAuthClient.UPMAuthenticationAuthorizationWSSkeletonStub.AddUser;
import es.upm.fi.sos.upmbank.UPMAuthClient.UPMAuthenticationAuthorizationWSSkeletonStub.AddUserResponse;
import es.upm.fi.sos.upmbank.UPMAuthClient.UPMAuthenticationAuthorizationWSSkeletonStub.ChangePassword;
import es.upm.fi.sos.upmbank.UPMAuthClient.UPMAuthenticationAuthorizationWSSkeletonStub.ChangePasswordBackEnd;
import es.upm.fi.sos.upmbank.UPMAuthClient.UPMAuthenticationAuthorizationWSSkeletonStub.ChangePasswordResponse;
import es.upm.fi.sos.upmbank.UPMAuthClient.UPMAuthenticationAuthorizationWSSkeletonStub.ChangePasswordResponseE;
import es.upm.fi.sos.upmbank.UPMAuthClient.UPMAuthenticationAuthorizationWSSkeletonStub.ExistUser;
import es.upm.fi.sos.upmbank.UPMAuthClient.UPMAuthenticationAuthorizationWSSkeletonStub.ExistUserResponseE;
import es.upm.fi.sos.upmbank.UPMAuthClient.UPMAuthenticationAuthorizationWSSkeletonStub.Login;
import es.upm.fi.sos.upmbank.UPMAuthClient.UPMAuthenticationAuthorizationWSSkeletonStub.LoginBackEnd;
import es.upm.fi.sos.upmbank.UPMAuthClient.UPMAuthenticationAuthorizationWSSkeletonStub.LoginResponse;
import es.upm.fi.sos.upmbank.UPMAuthClient.UPMAuthenticationAuthorizationWSSkeletonStub.LoginResponseBackEnd;
import es.upm.fi.sos.upmbank.UPMAuthClient.UPMAuthenticationAuthorizationWSSkeletonStub.RemoveUser;
import es.upm.fi.sos.upmbank.UPMAuthClient.UPMAuthenticationAuthorizationWSSkeletonStub.RemoveUserE;
import es.upm.fi.sos.upmbank.UPMAuthClient.UPMAuthenticationAuthorizationWSSkeletonStub.RemoveUserResponseE;
import es.upm.fi.sos.upmbank.UPMAuthClient.UPMAuthenticationAuthorizationWSSkeletonStub.UserBackEnd;
import es.upm.fi.sos.upmbank.UPMAuthClient.UPMAuthenticationAuthorizationWSSkeletonStub.Username;

public class testClientAuth {

	public static void main(String[] args) throws RemoteException {
		// TODO Auto-generated method stub
		
		try {
			UPMAuthenticationAuthorizationWSSkeletonStub upmauth = new UPMAuthenticationAuthorizationWSSkeletonStub();
			upmauth._getServiceClient().engageModule("addressing");
			upmauth._getServiceClient().getOptions().setManageSession(true);
			
			/*
			String username="petko2";
			String password="petkopetko";
			
			System.out.println("crear usuario");
			AddUser addUser1 = new AddUser();
			UserBackEnd userbackend = new UserBackEnd();
			userbackend.setName(username);
			addUser1.setUser(userbackend);
			AddUserResponse responseAddUser1 = upmauth.addUser(addUser1);
			if (responseAddUser1.get_return().getResult()){
				System.out.println("Respuesta correcta");
				password = responseAddUser1.get_return().getPassword();
				System.out.println("Contraseña generada para el usario:"+responseAddUser1.get_return().getPassword());
			}else{
				System.out.println("Respuesta negativa");
			}	//petko28390
				*/
			
			/*
			String username="petko21";
			ExistUser existUser4 = new ExistUser();
			Username exists_param = new Username();
			exists_param.setName(username);
			existUser4.setUsername(exists_param);
			ExistUserResponseE exusresponse = upmauth.existUser(existUser4);
			if (exusresponse.get_return().getResult()){
				System.out.println("usuario existe");
				//System.out.println("Contraseña generada para el usario:"+responseAddUser1.get_return().getPassword());
			}else{
				System.out.println("usuario no existe");
			}
			
			System.out.println("probar login");
			LoginBackEnd loginbackend = new LoginBackEnd();
			loginbackend.setName(username);
			loginbackend.setPassword(password);
			Login login1 = new Login();
			login1.setLogin(loginbackend);
			LoginResponse loginResponse = upmauth.login(login1);
			if (loginResponse.get_return().getResult()){
				System.out.println("login correcto");
				//System.out.println("Contraseña generada para el usario:"+responseAddUser1.get_return().getPassword());
			}else{
				System.out.println("login incorrecto");
			}
			
			
			ChangePassword changePassword8 = new ChangePassword();
			ChangePasswordBackEnd param = new ChangePasswordBackEnd();
			param.setName(username);
			param.setOldpwd(password);
			password = "petko25555";
			param.setNewpwd(password);
			
			changePassword8.setChangePassword(param );
			ChangePasswordResponseE chpwresponse = upmauth.changePassword(changePassword8 );
			if (chpwresponse.get_return().getResult()){
				System.out.println("cambio de constraseña correcto");
				//System.out.println("Contraseña generada para el usario:"+responseAddUser1.get_return().getPassword());
			}else{
				System.out.println("cambio de contraseña incorrecto");
			}
			
			
			RemoveUserE removeUser0 = new RemoveUserE();
			RemoveUser param_rm = new RemoveUser();
			param_rm.setName(username);
			param_rm.setPassword(password);
			removeUser0.setRemoveUser(param_rm );
			RemoveUserResponseE rmresponse = upmauth.removeUser(removeUser0 );
			if (rmresponse.get_return().getResult()){
				System.out.println("usuario borrado correctamente");
				//System.out.println("Contraseña generada para el usario:"+responseAddUser1.get_return().getPassword());
			}else{
				System.out.println("usuario no borrado corectamente");
			}
			*/
			
			
		
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
