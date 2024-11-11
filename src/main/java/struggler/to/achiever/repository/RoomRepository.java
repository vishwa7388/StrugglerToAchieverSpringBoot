package struggler.to.achiever.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import struggler.to.achiever.model.RoomEntity;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity,Long> {
    //Note:
    //JPA repository is basically combination of CurdRepository and Pagingandsortingrepository.
}
