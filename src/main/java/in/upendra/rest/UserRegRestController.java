package in.upendra.rest;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.upendra.bindings.UserForm;
import in.upendra.constants.AppConstants;
import in.upendra.service.UserService;

@RestController
public class UserRegRestController {
	
	private UserService userService;
	
	public UserRegRestController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/countries")
	public Map<Integer, String> countries() {
		return userService.getCountries();
	}
	
	@GetMapping("/states/{countryId}")
	public Map<Integer, String> states(@PathVariable Integer countryId){
		return userService.getStates(countryId);
	}
	
	@GetMapping("/cities/{stateId}")
	public Map<Integer, String> cities(@PathVariable Integer stateId){
		return userService.getCities(stateId);
	}
	
	@GetMapping("/emailcheck/{email}")
	public String emailCheck(@PathVariable String email) {
		boolean uniqueEmail = userService.uniqueEmail(email);
		if(uniqueEmail) {
			return AppConstants.UNIQUE;
		}
		return AppConstants.DUPLICATE;
	}
	
	@PostMapping("/save")
	public String saveUser(@RequestBody UserForm userForm) {
		boolean saveUser = userService.saveUser(userForm);
		if(saveUser) {
			return AppConstants.SUCCESS;
		}
		return AppConstants.FAIL;
	}

}
