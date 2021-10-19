package in.upendra.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import in.upendra.service.UserService;

@RestController
public class ForgotPswdRestController {
	
	private UserService userService;
	
	public ForgotPswdRestController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/forgotpswd/{email}")
	public String forgotPassword(@PathVariable String email) {
		String forgotPswd = userService.forgotPswd(email);
		return forgotPswd;
	}

}
