package cr.co.ctpcit.citsacbackend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cr.co.ctpcit.citsacbackend.data.entities.exams.AcademicExamEntity;
import cr.co.ctpcit.citsacbackend.data.entities.exams.DaiExamEntity;
import cr.co.ctpcit.citsacbackend.data.entities.exams.EnglishExamEntity;
import cr.co.ctpcit.citsacbackend.data.entities.exams.ExamEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.*;
import cr.co.ctpcit.citsacbackend.data.enums.*;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.*;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.AcademicExamDetailsDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.ExamAcaDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.QuestionAcaDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.QuestionOptionAcaDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.ExamDaiDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.QuestionDaiDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.*;
import cr.co.ctpcit.citsacbackend.logic.dto.questions.QuestionDto;
import cr.co.ctpcit.citsacbackend.logic.dto.questions.QuestionOptionDto;
import cr.co.ctpcit.citsacbackend.logic.mappers.inscriptions.EnrollmentMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestProvider {

  public static PersonEntity provideStudentPerson() {
    PersonEntity person = new PersonEntity();
    person.setId(11L);
    person.setFirstName("Andrés");
    person.setFirstSurname("Rodríguez");
    person.setSecondSurname("Morales");
    person.setIdType(IdType.CC);
    person.setIdNumber("200123654");
    person.setFullSurname("Rodríguez Morales");

    return person;
  }

  public static PersonEntity provideParentPerson() {
    PersonEntity person = new PersonEntity();
    person.setId(1L);
    person.setFirstName("Carlos");
    person.setFirstSurname("Rodríguez");
    person.setSecondSurname("Morales");
    person.setIdType(IdType.CC);
    person.setIdNumber("900321654");
    person.setFullSurname("Rodríguez Morales");

    return person;
  }

  public static AddressEntity provideAddress() {
    AddressEntity address = new AddressEntity();
    address.setId(1L);
    address.setCountry("Costa Rica");
    address.setProvince("San José");
    address.setCity("San José");
    address.setDistrict("Carmen");
    address.setAddressInfo("Avenida Central 100");

    return address;
  }

  public static StudentEntity provideStudent() {
    StudentEntity student = new StudentEntity();
    student.setId(11L);
    student.setStudentPerson(provideStudentPerson());
    student.setBirthDate(LocalDate.parse("2010-03-12"));
    student.setPreviousSchool("Escuela La Sabana");
    student.setHasAccommodations(false);
    student.setPreviousGrades(new BigDecimal("0.00"));

    return student;
  }

  public static ParentEntity provideParent() {
    ParentEntity parent = new ParentEntity();
    parent.setId(1L);
    parent.setParentPerson(provideParentPerson());
    parent.setEmail("carlos.rod@example.com");
    parent.setPhoneNumber("876543210");
    parent.setRelationship(Relationship.F);
    parent.setDaiExam(null);

    return parent;
  }

  public static EnrollmentEntity provideEnrollment() {
    StudentEntity student = TestProvider.provideStudent();
    ParentEntity parent = TestProvider.provideParent();
    parent.addStudent(student);
    parent.addAddress(TestProvider.provideAddress());

    EnrollmentEntity enrollment = new EnrollmentEntity();
    enrollment.setId(1L);
    enrollment.setStatus(ProcessStatus.PENDING);
    enrollment.setEnrollmentDate(Instant.parse("2024-12-15T10:15:30Z"));
    enrollment.setGradeToEnroll(Grades.FIRST);
    enrollment.setKnownThrough(KnownThrough.OT);
    enrollment.setExamDate(LocalDate.parse("2024-12-15"));
    enrollment.setConsentGiven(true);
    enrollment.setStudent(student);

    return enrollment;
  }

  public static List<EnrollmentEntity> provideEnrollmentList() {
    List<EnrollmentEntity> enrollmentList = new ArrayList<>();
    enrollmentList.add(provideEnrollment());
    return enrollmentList;
  }

  public static List<EnrollmentDto> provideEnrollmentDtoList() {
    List<EnrollmentDto> enrollmentDtoList = new ArrayList<>();
    enrollmentDtoList.add(provideEnrollmentDto());
    return enrollmentDtoList;
  }

  public static Page<EnrollmentEntity> provideEnrollmentPage() {
    return new PageImpl<>(provideEnrollmentList());
  }

  public static EnrollmentDto provideEnrollmentDto() {
    ParentDto parent = TestProvider.provideParentDto();
    AddressDto address = TestProvider.provideAddressDto();
    parent.addresses().add(address);

    StudentDto student = TestProvider.provideStudentDto();
    student.parents().add(parent);

    return new EnrollmentDto(null, student, ProcessStatus.PENDING, Grades.FIRST, KnownThrough.OT,
        LocalDate.parse("2024-12-15"), true, false, new ArrayList<>());
  }

  public static DocumentEntity provideDocument() {
    DocumentEntity document = new DocumentEntity();
    document.setId(1L);
    document.setDocumentName("Other Document Sofía");
    document.setDocumentType(DocType.OT);
    document.setDocumentUrl("/home/user/docs/sofia_other.pdf");

    return document;
  }

  public static PersonDto provideParentPersonDto() {
    return new PersonDto(1L, "Carlos", "Rodríguez", "Morales", IdType.CC, "900321654");
  }

  public static ParentDto provideParentDto() {
    return new ParentDto(1L, provideParentPersonDto(), "876543210", "carlos.rod@example.com",
        Relationship.F, new ArrayList<>());
  }

  public static PersonDto provideStudentPersonDto() {
    return new PersonDto(11L, "Andrés", "Rodríguez", "Morales", IdType.CC, "200123654");
  }

  public static StudentDto provideStudentDto() {
    return new StudentDto(11L, provideStudentPersonDto(), LocalDate.parse("2010-03-12"),
        "Escuela La Sabana", false, new ArrayList<>());
  }

  public static AddressDto provideAddressDto() {
    return new AddressDto(1L, "Costa Rica", "San José", "San José", "Carmen",
        "Avenida Central 100");
  }

  public static QuestionDto provideQuestionDto() {
    return new QuestionDto(2L, QuestionType.ACA, "¿Como se calcula el area de un circulo?", null,
        Grades.FIRST, QuestionLevel.EASY, SelectionType.SINGLE, false, getQuestionOptions());
  }

  public static QuestionDto provideDaiQuestionDto() {
    return new QuestionDto(
            1L,
            QuestionType.DAI,
            "¿Cómo te sientes el día de hoy?",
            null,
            Grades.SECOND,
            QuestionLevel.EASY,
            SelectionType.PARAGRAPH,
            false,
            new ArrayList<>()
    );
  }

  private static ArrayList<QuestionOptionDto> getQuestionOptions() {
    ArrayList<QuestionOptionDto> options = new ArrayList<>();
    options.add(new QuestionOptionDto(1L, true, "π * radio^2"));
    options.add(new QuestionOptionDto(2L, false, "2 * radio"));
    options.add(new QuestionOptionDto(3L, false, "π * diámetro"));
    options.add(new QuestionOptionDto(4L, false, "radio * altura"));
    return options;
  }

  public static UpdateWeightsConfigsDto provideUpdateWeightsConfigsDto() {
    return new UpdateWeightsConfigsDto(0.4, 0.4, 0.2);
  }

  public static UpdateContactInfoConfigsDto provideUpdateContactInfoConfigsDto() {
    return new UpdateContactInfoConfigsDto("contactocit@ctpcit.co.cr",
        "notificaciones@ctpcit.co.cr", "88090041", "22370186", "ComplejoEducativoCIT",
        "ComplejoEducativoCIT");
  }

  public static SystemConfigDto provideSystemConfigDto() {
    return new SystemConfigDto(1, Configurations.EMAIL_CONTACT, "contactocit@ctpcit.co.cr", false);
  }

  public static ExamPeriodDto provideNonExistentExamPeriodDto() {
    return new ExamPeriodDto(null, LocalDate.parse("2021-01-01"), LocalDate.parse("2021-03-15"),
        provideNonExistentExamDayDtoList(),true);
  }

  public static ExamPeriodDto provideExistentExamPeriodDto() {
    return new ExamPeriodDto(1L, LocalDate.parse("2025-01-01"), LocalDate.parse("2025-03-15"),
        provideExistingExamDayDtoList(),true);
  }

  public static List<ExamDayDto> provideExistingExamDayDtoList() {
    List<ExamDayDto> examDayDtoList = new ArrayList<>();
    examDayDtoList.add(new ExamDayDto(1L, WeekDays.M, LocalTime.of(8, 0, 0)));
    examDayDtoList.add(new ExamDayDto(2L, WeekDays.K, LocalTime.of(8, 0, 0)));
    examDayDtoList.add(new ExamDayDto(3L, WeekDays.F, LocalTime.of(8, 0, 0)));
    return examDayDtoList;
  }

  public static List<ExamDayDto> provideNonExistentExamDayDtoList() {
    List<ExamDayDto> examDayDtoList = new ArrayList<>();
    examDayDtoList.add(new ExamDayDto(null, WeekDays.M, LocalTime.of(8, 0, 0)));
    examDayDtoList.add(new ExamDayDto(null, WeekDays.K, LocalTime.of(8, 0, 0)));
    examDayDtoList.add(new ExamDayDto(null, WeekDays.F, LocalTime.of(8, 0, 0)));
    return examDayDtoList;
  }

  public static ExamEntity provideNonSavedExam() {
    return ExamEntity.builder().id(null).enrollment(provideEnrollment()).examDate(Instant.now())
        .examType(ExamType.ACA).responses(new HashMap<>()).build();
  }

  public static EnglishExamEntity provideNonSavedEnglishExam() {
    return EnglishExamEntity.builder().id(null).trackTestId(1L).level(EnglishLevel.B2)
        .core((byte) 1).build();
  }

  public static DaiExamEntity provideNonSavedDaiExam() {
    return DaiExamEntity.builder().id(null).comment(
            "El estudiante menciona una situación de acoso escolar en su institución anterior, aparte de eso, todo bien.")
        .recommendation(Recommendation.ADMIT).build();
  }

  public static AcademicExamEntity provideNonSavedAcademicExam() {
    return AcademicExamEntity.builder().id(null).grade(new BigDecimal("85.00")).build();
  }

  public static QuestionAcaDto provideQuestionAcaDto() {
    return new QuestionAcaDto(2L, QuestionType.ACA, "¿Como se calcula el area de un circulo?", null,
        SelectionType.SINGLE, false, provideQuestionOptionAcaDtoList());
  }

  public static List<QuestionOptionAcaDto> provideQuestionOptionAcaDtoList() {
    List<QuestionOptionAcaDto> questionOptionAcaDtoList = new ArrayList<>();
    questionOptionAcaDtoList.add(new QuestionOptionAcaDto(1L, true, "π * radio^2", true));
    questionOptionAcaDtoList.add(new QuestionOptionAcaDto(2L, false, "2 * radio", false));
    questionOptionAcaDtoList.add(new QuestionOptionAcaDto(3L, false, "π * diámetro", false));
    questionOptionAcaDtoList.add(new QuestionOptionAcaDto(4L, false, "radio * altura", false));
    return questionOptionAcaDtoList;
  }

  public static QuestionDaiDto provideQuestionDaiDto() {
    return new QuestionDaiDto(1L, QuestionType.DAI, "¿Cómo te sientes el día de hoy?", null,
        SelectionType.PARAGRAPH, false, "Me siento muy bien, aunque con un poco de sueño.");
  }

  public static ExamAcaDto provideAcaExamDto() {
    return ExamAcaDto.builder().id(1L)
        .enrollment(EnrollmentMapper.convertToDto(provideEnrollment()).id()).examDate(Instant.EPOCH)
        .examType(ExamType.ACA).responses(List.of(provideQuestionAcaDto())).build();
  }

  public static ExamDaiDto provideDaiExamDto() {
    return ExamDaiDto.builder().id(2L)
        .enrollment(EnrollmentMapper.convertToDto(provideEnrollment()).id()).examDate(Instant.EPOCH)
        .examType(ExamType.DAI).responses(List.of(provideQuestionDaiDto())).build();
  }

  public static AcademicExamDetailsDto provideAcademicExamDto() throws JsonProcessingException {
    String responses = """
          [
           {
             "id": 26,
             "deleted": false,
             "imageUrl": null,
             "questionText": "Teniendo como referencia la relación del par base, elija la alternativa que mantiene dicha relación análoga. SUMAR : MULTIPLICAR",
             "questionType": "ACA",
             "selectionType": "SINGLE",
             "questionOptions": [
               {
                 "id": 81,
                 "option": "Restar : Dividir",
                 "selected": true,
                 "isCorrect": true
               },
               {
                 "id": 82,
                 "option": "Sumar : Restar",
                 "selected": false,
                 "isCorrect": false
               },
               {
                 "id": 83,
                 "option": "Multiplicar : Dividir",
                 "selected": false,
                 "isCorrect": false
               },
               {
                 "id": 84,
                 "option": "Sumar : Multiplicar",
                 "selected": false,
                 "isCorrect": false
               }
             ]
           },
           {
             "id": 29,
             "deleted": false,
             "imageUrl": null,
             "questionText": "Quienes hayan leído grandes obras literarias, haciendo de ellas parte de su vida,",
             "questionType": "ACA",
             "selectionType": "SINGLE",
             "questionOptions": [
               {
                 "id": 93,
                 "option": "Son personas cultas",
                 "selected": false,
                 "isCorrect": false
               },
               {
                 "id": 94,
                 "option": "Son personas sabias",
                 "selected": false,
                 "isCorrect": false
               },
               {
                 "id": 95,
                 "option": "Son personas educadas",
                 "selected": false,
                 "isCorrect": false
               },
               {
                 "id": 96,
                 "option": "Son personas que han enriquecido su vida",
                 "selected": true,
                 "isCorrect": true
               }
             ]
           },
           {
             "id": 12,
             "deleted": false,
             "imageUrl": null,
             "questionText": "Un frasco contiene cincuenta monedas de 100 colones y pesa 1400g, si el frasco vacío pesa 250g entones el peso, en gramos, de una moneda es",
             "questionType": "ACA",
             "selectionType": "SINGLE",
             "questionOptions": [
               {
                 "id": 25,
                 "option": "20g",
                 "selected": false,
                 "isCorrect": false
               },
               {
                 "id": 26,
                 "option": "25g",
                 "selected": false,
                 "isCorrect": false
               },
               {
                 "id": 27,
                 "option": "23g",
                 "selected": true,
                 "isCorrect": true
               },
               {
                 "id": 28,
                 "option": "35g",
                 "selected": false,
                 "isCorrect": false
               }
             ]
           },
           {
             "id": 25,
             "deleted": false,
             "imageUrl": null,
             "questionText": "Teniendo como referencia la relación del par base, elija la alternativa que mantiene dicha relación análoga. MONÁRQUICO : DESORDENADO",
             "questionType": "ACA",
             "selectionType": "SINGLE",
             "questionOptions": [
               {
                 "id": 77,
                 "option": "Dictador : Violento",
                 "selected": false,
                 "isCorrect": false
               },
               {
                 "id": 78,
                 "option": "Anarquía : Orden",
                 "selected": false,
                 "isCorrect": false
               },
               {
                 "id": 79,
                 "option": "Democrático : Justo",
                 "selected": false,
                 "isCorrect": false
               },
               {
                 "id": 80,
                 "option": "Anarquía : Desorden",
                 "selected": true,
                 "isCorrect": true
               }
             ]
           },
           {
             "id": 16,
             "deleted": false,
             "imageUrl": null,
             "questionText": "Una cocina eléctrica, con dos discos encendidos consume 1200 colones al estar encendidos por 3 horas. El gasto, en colones, si se encienden los 4 discos por dos horas corresponde a",
             "questionType": "ACA",
             "selectionType": "SINGLE",
             "questionOptions": [
               {
                 "id": 41,
                 "option": "1200 colones",
                 "selected": false,
                 "isCorrect": false
               },
               {
                 "id": 42,
                 "option": "1600 colones",
                 "selected": true,
                 "isCorrect": true
               },
               {
                 "id": 43,
                 "option": "2400 colones",
                 "selected": false,
                 "isCorrect": false
               },
               {
                 "id": 44,
                 "option": "3200 colones",
                 "selected": false,
                 "isCorrect": false
               }
             ]
           }
         ]
        """;

    ObjectMapper mapper = new ObjectMapper();
    List<QuestionAcaDto> questions = mapper.readValue(responses,
        mapper.getTypeFactory().constructCollectionLikeType(List.class, QuestionAcaDto.class));

    ExamAcaDto examAcaDto =
        ExamAcaDto.builder().id(1L).enrollment(10L).examType(ExamType.ACA).examDate(Instant.parse("2025-03-25T17:54:12Z"))
            .responses(questions).build();

    return AcademicExamDetailsDto.builder().id(1L).exam(examAcaDto).grade(new BigDecimal("100.00"))
        .build();
  }


}

