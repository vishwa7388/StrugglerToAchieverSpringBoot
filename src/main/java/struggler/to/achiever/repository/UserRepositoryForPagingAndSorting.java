package struggler.to.achiever.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import struggler.to.achiever.model.UserEntity;

@Repository
public interface UserRepositoryForPagingAndSorting extends PagingAndSortingRepository<UserEntity,Long> {
}
