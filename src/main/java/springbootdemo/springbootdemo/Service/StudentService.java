package springbootdemo.springbootdemo.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springbootdemo.springbootdemo.student.StudentEntity;
import springbootdemo.springbootdemo.student.StudentRepository;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public void saveStudent(StudentEntity studentEntity){
        studentRepository.save(studentEntity);
    }

    public void saveAllStudents(List<StudentEntity> studentEntities){
        studentRepository.saveAll(studentEntities);
    }

    public Optional<StudentEntity> findStudent(Long id){
        return studentRepository.findById(id);
    }

    public List<StudentEntity> findAllStudents(){
        return studentRepository.findAll();
    }

    public void updateStudent(StudentEntity studentEntity, Long id){
        Optional<StudentEntity> optionalStudentEntity =
                studentRepository.findById(id);
        if(optionalStudentEntity.isPresent()){
            StudentEntity existingEntity = optionalStudentEntity.get();
            existingEntity.setName(studentEntity.getName());
            existingEntity.setAge(studentEntity.getAge());
            existingEntity.setEmail(studentEntity.getEmail());

            studentRepository.save(existingEntity);
        }
    }

    public void deleteStudentById(Long id){
        studentRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllStudentsById(List<Long> ids){
        studentRepository.deleteAllByIdIn(ids);
    }

    public boolean isStudentPresent(Long id){
        Optional<StudentEntity> student = studentRepository.findById(id);
        return student.isPresent();
    }

}
