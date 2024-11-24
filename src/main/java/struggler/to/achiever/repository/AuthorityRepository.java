package struggler.to.achiever.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import struggler.to.achiever.model.AuthorityEntity;

@Repository
public interface AuthorityRepository extends CrudRepository<AuthorityEntity,Long>{

   AuthorityEntity findByName(String name);
}