package in.upendra.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.upendra.entities.CityMasterEntity;

@Repository
public interface CityRepository extends JpaRepository<CityMasterEntity, Serializable> {

	List<CityMasterEntity> findByStateId(Integer stateId);

}
