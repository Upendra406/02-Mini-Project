package in.upendra.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import in.upendra.bindings.LoginForm;
import in.upendra.bindings.UnlockForm;
import in.upendra.bindings.UserForm;

@Service
public interface UserService {

	public String loginCheck(LoginForm loginForm);

	public Map<Integer, String> getCountries();

	public Map<Integer, String> getStates(Integer countryId);

	public Map<Integer, String> getCities(Integer stateId);

	public boolean uniqueEmail(String email);

	public boolean saveUser(UserForm userForm);

	public boolean unlockAccount(UnlockForm unlockForm);

	public String forgotPswd(String email);

}
