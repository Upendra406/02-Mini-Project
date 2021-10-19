package in.upendra.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.upendra.bindings.LoginForm;
import in.upendra.service.UserService;

@RestController
public class LoginRestController {
	
	private UserService userService;
	
	public LoginRestController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/login")
	public String login(@RequestBody LoginForm loginForm) {
		String loginCheck = userService.loginCheck(loginForm);
		return loginCheck;	
	}

}
