package cr.co.ctpcit.citsacbackend.logic.mappers.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.exams.ExamEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.StudentEntity;
import cr.co.ctpcit.citsacbackend.data.enums.ExamType;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.StudentDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.StudentExamsDto;
import cr.co.ctpcit.citsacbackend.logic.mappers.exams.ExamMapper;

import java.util.ArrayList;
import java.util.List;


/**
 * Mapper for {@link StudentEntity} and {@link StudentDto} This class is used to convert
 * {@link StudentEntity} to {@link StudentDto}
 */
public class StudentMapper {

  public static StudentDto convertToDto(StudentEntity studentEntity) {
    return StudentDto.builder()
            .id(studentEntity.getId())
            .person(PersonMapper.convertToDto(studentEntity.getStudentPerson()))
            .parents(ParentMapper.convertToDtoList(studentEntity.getParents()))
            .previousSchool(studentEntity.getPreviousSchool())
            .birthDate(studentEntity.getBirthDate())
            .hasAccommodations(studentEntity.getHasAccommodations())
            .previousGrades(studentEntity.getPreviousGrades())
            .build();
  }

  public static List<StudentDto> convertToDtoList(List<StudentEntity> studentEntities) {
    if (studentEntities == null) {
      return null;
    }
    return studentEntities.stream().map(StudentMapper::convertToDto).toList();
  }

  public static StudentEntity convertToEntity(StudentDto inscription) {
    return StudentEntity.builder().id(inscription.id()).birthDate(inscription.birthDate())
        .previousSchool(inscription.previousSchool())
        .hasAccommodations(inscription.hasAccommodations()).previousGrades(inscription.previousGrades()).parents(new ArrayList<>()).build();
  }

  public static StudentExamsDto studentToStudentExamsDto(StudentEntity student,
      List<ExamEntity> exams, ExamType examType) {
    //Map the exams to the correct type
    if (examType == ExamType.ACA) {
      return StudentExamsDto.builder().id(student.getId())
          .person(PersonMapper.convertToDto(student.getStudentPerson()))
          .academicExams(ExamMapper.academicExamsToAcademicExamDetailsDto(exams))
          .daiExams(new ArrayList<>()).build();
    } else {
      return StudentExamsDto.builder().id(student.getId())
          .person(PersonMapper.convertToDto(student.getStudentPerson()))
          .academicExams(new ArrayList<>()).daiExams(ExamMapper.daiExamsToDaiExamDetailsDto(exams))
          .build();
    }
  }
}
