package in.upendra.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.upendra.entities.UserAccountEntity;

@Repository
public interface UserRepository extends JpaRepository<UserAccountEntity, Serializable> {

	public UserAccountEntity findByEmailAndPassword(String email, String password);
 
	public UserAccountEntity findByEmail(String email);




}
