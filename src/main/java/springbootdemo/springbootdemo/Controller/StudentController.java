package springbootdemo.springbootdemo.Controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springbootdemo.springbootdemo.Entity.StudentEntity;
import springbootdemo.springbootdemo.Repository.student.StudentRepository;
import springbootdemo.springbootdemo.Service.StudentService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class StudentController {

    private final StudentService studentService;
    private final StudentRepository studentRepository;

    @PostMapping("student")
    public String saveStudent(@RequestBody StudentEntity studentEntity){
        studentService.saveStudent(studentEntity);
        return  "Student created successfully !";
    }

    @PostMapping("/students")
    public String saveAllStudents(@RequestBody List<StudentEntity> studentEntities){
        studentRepository.saveAll(studentEntities);
        return "All students saved successfully!";
    }

    @GetMapping("students")
    public List<StudentEntity> findAllStudents(){
         return studentService.findAllStudents();
    }

    @GetMapping("student/{id}")
    public Optional<StudentEntity> findStudentById(@PathVariable Long id){
            return studentService.findStudent(id);
    }

    @PutMapping("updateStudent/{id}")
    public String updateStudent(@RequestBody StudentEntity studentEntity, @PathVariable Long id) {
        if (studentService.isStudentPresent(id)) {
            studentService.updateStudent(studentEntity, id);
            return "Student updated successfully !";
        } else {
            return "Student with id " + id + " not found!";
        }
    }

    @DeleteMapping("deleteStudent")
    public String deleteStudent(@RequestParam Long id){
        if (studentService.isStudentPresent(id)){
            studentService.deleteStudentById(id);
            return "Student successfully deleted!";
        }else {
            return "Student with id "+id+" not found!";
        }
    }

    @DeleteMapping("deleteStudents")
    public String deleteStudents(@RequestBody List<Long> ids){
        boolean b = false;
        for (Long l : ids) {
            if (studentService.isStudentPresent(l)) {
                studentService.deleteStudentById(l);
                b = true;
            } else {
                b = false;
            }
        }

         if (b){
               return "Mentioned Student are successfully deleted.";
           }else {
               return "Student with Mentioned not found!";
           }
    }

    //Not implemented
    @DeleteMapping("deleteAllStudents")
    public String deleteAllStudents(@RequestBody List<Long> ids){
        studentService.deleteAllStudentsById(ids);
        return "All students are deleted successfully!";
    }

}
