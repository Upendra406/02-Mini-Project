package in.upendra.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name = "USER_ACCOUNT")
@Data
public class UserAccountEntity {

	@Id
	@GeneratedValue
	@Column(name = "USER_ID")
	private Integer userId;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "EMAIL", unique = true)
	private String email;

	@Column(name = "MOBILE_NUMBER")
	private Long mobileNumber;

	@Column(name = "DOB")
	private LocalDate dob;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "COUNTRY_ID")
	private Integer countryId;

	@Column(name = "STATE_ID")
	private Integer stateId;

	@Column(name = "CITY_ID")
	private Integer cityId;

	@Column(name = "ACCONT_STATUS")
	private String accStatus;

	@Column(name = "CREATED_DATE", updatable = false)
	@CreationTimestamp
	private LocalDate createdDate;

	@Column(name = "UPDATED_DATE", insertable = false)
	@UpdateTimestamp
	private LocalDate updatedDate;

}
