package struggler.to.achiever.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import struggler.to.achiever.model.UserLoginEntity;

@Repository
public interface UserLoginRepository extends JpaRepository<UserLoginEntity,Long> {
}
