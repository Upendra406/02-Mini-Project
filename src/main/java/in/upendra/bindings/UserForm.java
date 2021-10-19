package in.upendra.bindings;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserForm {

	private String firstName;

	private String lastName;

	private String email;
	
	private String password;
	
	private String accStatus;

	private Long mobileNumber;

	private LocalDate dob;

	private String gender;

	private Integer countryId;

	private Integer stateId;

	private Integer cityId;

}
