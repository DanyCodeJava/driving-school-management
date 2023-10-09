package danycode.dsm.service;

import danycode.dsm.dto.*;

public interface StudentService {

   // void saveStudent(StudentDto studentDto);

    StudentDto getStudentDetails(Long id) throws StudentNotFoundException;

    PageDto<StudentDto> listByPage(int pageNumber, UserSortField sortingField, SortDirection sortingDir, String keyword);


    StudentDto saveStudent(StudentDto studentDto, PhotoFileDto studentPhoto);
}
