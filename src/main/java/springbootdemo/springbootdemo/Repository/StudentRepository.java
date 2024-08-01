package springbootdemo.springbootdemo.Repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import springbootdemo.springbootdemo.Entity.StudentEntity;

import java.util.List;


@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long>{


//    @Modifying
//    @Query("DELETE FROM sample.student s WHERE s.id IN :ids")
//    void deleteStudentsByIds(List<Long> ids);

    @Modifying
    @Query("DELETE FROM StudentEntity s WHERE s.id IN :ids")
    void deleteAllByIdIn(List<Long> ids);
}
