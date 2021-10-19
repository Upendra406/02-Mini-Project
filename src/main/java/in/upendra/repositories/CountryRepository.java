package in.upendra.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.upendra.entities.CountryMasterEntity;

@Repository
public interface CountryRepository extends JpaRepository<CountryMasterEntity, Serializable> {

}
