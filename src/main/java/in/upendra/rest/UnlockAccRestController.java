package in.upendra.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.upendra.bindings.UnlockForm;
import in.upendra.constants.AppConstants;
import in.upendra.properties.AppProps;
import in.upendra.service.UserService;

@RestController
public class UnlockAccRestController {
	
	private UserService userService;
	
	private AppProps appProps;
	
	public UnlockAccRestController(UserService userService, AppProps appProps) {
		this.userService = userService;
		this.appProps = appProps;
	} 
	
	@PostMapping("/unlockaccount")
	public String accUnlock(@RequestBody UnlockForm unlockForm) {
		boolean unlockAccount = userService.unlockAccount(unlockForm);
		if(unlockAccount) {
			return appProps.getMessages().get(AppConstants.UNLOCK_SUCCESS);
		}
		return appProps.getMessages().get(AppConstants.UNLOCK_FAIL);
	}

}
