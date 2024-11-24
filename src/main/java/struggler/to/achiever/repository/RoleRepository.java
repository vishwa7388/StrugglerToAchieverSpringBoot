package struggler.to.achiever.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import struggler.to.achiever.model.RoleEntity;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity,Long> {

   RoleEntity findByName(String name);
}
