package in.upendra.bindings;

import lombok.Data;

@Data
public class UnlockForm {

	private String email;
	
	private String tempPswd;
	
	private String newPswd;
	
	private String confirmPswd;
}
