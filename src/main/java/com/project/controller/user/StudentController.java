package com.project.controller.user;

import com.project.payload.request.business.ChooseLessonProgramWithId;
import com.project.payload.request.user.StudentRequest;
import com.project.payload.request.user.StudentRequestWithoutPassword;
import com.project.payload.response.ResponseMessage;
import com.project.payload.response.user.StudentResponse;
import com.project.service.user.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService ;

    @PostMapping("/save") // http://localhost:8080/students/save + POST + JSON
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseMessage<StudentResponse>> saveStudent(
            @RequestBody @Valid StudentRequest studentRequest){
        return ResponseEntity.ok(studentService.saveStudent(studentRequest));
    }

    //!!! ogrencinin kendisini update etme islemi
    @PatchMapping("/update") // http://localhost:8080/students/update + PATCH + JSON
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public ResponseEntity<String> updateStudent(@RequestBody @Valid
                                                  StudentRequestWithoutPassword studentRequestWithoutPassword,
                                                HttpServletRequest request){
        return studentService.updateStudent(studentRequestWithoutPassword, request);
    }

    @PutMapping("/update/{userId}")// http://localhost:8080/students/update/1 + PUT + JSON
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseMessage<StudentResponse> updateStudentForManagers(@PathVariable Long userId,
                                                                     @RequestBody @Valid StudentRequest studentRequest ){
        return studentService.updateStudentForManagers(userId,studentRequest);
    }

    //Ogrenci kendine LessonProgram ekliyor
    @PostMapping("/addLessonProgramToStudent")// http://localhost:8080/students/addLessonProgramToStudent + POST + JSON
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public ResponseMessage<StudentResponse> addLessonProgram(HttpServletRequest request,
                                                             @RequestBody @Valid ChooseLessonProgramWithId chooseLessonProgramWithId){
        String userName = (String) request.getAttribute("username");
        return studentService.addLessonProgramToStudent(userName, chooseLessonProgramWithId);
    }

    @GetMapping("/changeStatus") // http://localhost:8080/students/changeStatus?id=1&status=true + GET
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseMessage changeStatusOfStudent(@RequestParam Long id, @RequestParam boolean status){
        return studentService.changeStatusOfStudent(id,status);
    }
}
