package in.upendra.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import in.upendra.bindings.LoginForm;
import in.upendra.bindings.UnlockForm;
import in.upendra.bindings.UserForm;
import in.upendra.constants.AppConstants;
import in.upendra.entities.CityMasterEntity;
import in.upendra.entities.CountryMasterEntity;
import in.upendra.entities.StateMasterEntity;
import in.upendra.entities.UserAccountEntity;
import in.upendra.properties.AppProps;
import in.upendra.repositories.CityRepository;
import in.upendra.repositories.CountryRepository;
import in.upendra.repositories.StateRepository;
import in.upendra.repositories.UserRepository;
import in.upendra.utils.EmailUtils;

@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;
	
	private CountryRepository countryRepository;
	
	private StateRepository stateRepository;
	
	private CityRepository cityRepository;
	
	private EmailUtils emailUtils;
	
	private AppProps appProps;
	
	public UserServiceImpl(UserRepository userRepository, CountryRepository countryRepository, StateRepository stateRepository, CityRepository cityRepository, EmailUtils emailUtils, AppProps appProps) {
		this.userRepository = userRepository;
		this.countryRepository = countryRepository;
		this.stateRepository = stateRepository;
		this.cityRepository = cityRepository;
		this.emailUtils = emailUtils;
		this.appProps = appProps;
	}
	
	@Override
	public String loginCheck(LoginForm loginForm) {
		UserAccountEntity userAccount = userRepository.findByEmailAndPassword(loginForm.getEmail() , loginForm.getPassword());
		
		if(userAccount != null) {
			String accStatus = userAccount.getAccStatus();
			if(accStatus.equals(AppConstants.LOCKED)){
				return appProps.getMessages().get(AppConstants.ACCOUNT_LOCKED);
				
			} else {
				return AppConstants.SUCCESS;
			}
		} else {
			return appProps.getMessages().get(AppConstants.INVALID_CREDENTIAL);
		}
	}
 
	@Override
	public Map<Integer, String> getCountries() {
		Map<Integer, String> countryMap = new HashMap<>();
		List<CountryMasterEntity> countries = countryRepository.findAll();
		countries.forEach(country ->{
			countryMap.put(country.getCountryId(), country.getCountryName());
		});
		return countryMap;
	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) {
		Map<Integer, String> statesMap = new HashMap<>();
		List<StateMasterEntity> states = stateRepository.findByCountryId(countryId);
		states.forEach(state -> {
			statesMap.put(state.getStateId(), state.getStateName());
		});
		return statesMap;
	}

	@Override
	public Map<Integer, String> getCities(Integer stateId) {
		Map<Integer, String> citiesMap = new HashMap<>();
		List<CityMasterEntity> cities = cityRepository.findByStateId(stateId);
		cities.forEach(city -> {
			citiesMap.put(city.getCityId(), city.getCityName());
		});
		return citiesMap;
	}

	@Override
	public boolean uniqueEmail(String email) {
		UserAccountEntity user = userRepository.findByEmail(email);
		if(user == null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean saveUser(UserForm userForm) {
		userForm.setAccStatus(AppConstants.LOCKED);
		userForm.setPassword(generateTempPswd());
		UserAccountEntity entity = new UserAccountEntity();
		BeanUtils.copyProperties(userForm, entity);
		UserAccountEntity save = userRepository.save(entity);
		if(save.getUserId() != null) {
			String subject = appProps.getMessages().get(AppConstants.REG_EMAIL_SUB);
			String body = getUserRegEmailBody(userForm);
			emailUtils.sendEmail(userForm.getEmail(), subject, body);
			return true;
		}
		return false; 
	}

	@Override
	public boolean unlockAccount(UnlockForm unlockForm) {
        String email = unlockForm.getEmail();
        String tempPswd = unlockForm.getTempPswd();
        UserAccountEntity user = userRepository.findByEmailAndPassword(email, tempPswd);
        if( user != null) {
        	user.setPassword(unlockForm.getNewPswd());
        	user.setAccStatus(AppConstants.UN_LOCKED);
        	userRepository.save(user);
        	return true;
        }
		return false;
	}
	@Override
	public String forgotPswd(String email) {
		UserAccountEntity user = userRepository.findByEmail(email);
		if(user != null) {
			String subject = appProps.getMessages().get(AppConstants.FORGOT_PSWD_EMAIL_SUB);
			String body = getForgotPswdEmailBody(user);	
			emailUtils.sendEmail(user.getEmail(), subject, body);
			return appProps.getMessages().get(AppConstants.FORGOT_PSWD_SUCCESS);
		}
		return appProps.getMessages().get(AppConstants.FORGOT_PSWD_FAIL);
	}

	public String generateTempPswd() {
		String randomText = RandomStringUtils.randomAlphanumeric(6);
		return randomText;	
	}
	
	private String getUserRegEmailBody(UserForm userForm) {
		StringBuffer sb = new StringBuffer();
		String fileName = appProps.getMessages().get(AppConstants.REG_EMAIL_BODY_FILE);
		List<String> list = new ArrayList<>();
		try {
			BufferedReader br = Files.newBufferedReader(Paths.get(fileName));
			list = br.lines().collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		list.forEach(line ->{
			if(line.contains(AppConstants.FNAME)) {
				line = line.replace(AppConstants.FNAME, userForm.getFirstName());
			}
			if(line.contains(AppConstants.LNAME)) {
				line = line.replace(AppConstants.LNAME, userForm.getLastName());
			}
			if(line.contains(AppConstants.TEMP_PSWD)) {
				line = line.replace(AppConstants.TEMP_PSWD, userForm.getPassword());
			}
			if(line.contains(AppConstants.EMAIL)) {
				line = line.replace(AppConstants.EMAIL, userForm.getEmail());
			}
			sb.append(line);
		});
		return sb.toString();	
	}
	
	private String getForgotPswdEmailBody(UserAccountEntity entity) {
		StringBuffer sb = new StringBuffer();
		String fileName = appProps.getMessages().get(AppConstants.FORGOT_PSWD_EMAIL_BODY_FILE);
		List<String> list = new ArrayList<>();
		try {
			BufferedReader br = Files.newBufferedReader(Paths.get(fileName));
			list = br.lines().collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		list.forEach(line ->{
			if(line.contains(AppConstants.FNAME)) {
				line = line.replace(AppConstants.FNAME, entity.getFirstName());
			}
			if(line.contains(AppConstants.LNAME)) {
				line = line.replace(AppConstants.LNAME, entity.getLastName());
			}
			if(line.contains(AppConstants.PASSWORD)) {
				line = line.replace(AppConstants.PASSWORD, entity.getPassword());
			}
			sb.append(line);
		});
		return sb.toString();
	}	
	
}
