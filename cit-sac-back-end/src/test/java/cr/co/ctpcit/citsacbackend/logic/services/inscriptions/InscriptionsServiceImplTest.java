package cr.co.ctpcit.citsacbackend.logic.services.inscriptions;

import cr.co.ctpcit.citsacbackend.TestProvider;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.EnrollmentEntity;
import cr.co.ctpcit.citsacbackend.data.repositories.inscriptions.DocumentRepository;
import cr.co.ctpcit.citsacbackend.data.repositories.inscriptions.EnrollmentRepository;
import cr.co.ctpcit.citsacbackend.data.repositories.inscriptions.PersonRepository;
import cr.co.ctpcit.citsacbackend.data.repositories.inscriptions.StudentRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.EnrollmentDto;
import cr.co.ctpcit.citsacbackend.logic.mappers.inscriptions.EnrollmentMapper;
import cr.co.ctpcit.citsacbackend.logic.services.storage.StorageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InscriptionsServiceImplTest {
  @Mock
  private PersonRepository personRepository;
  @Mock
  private StorageService storageService;
  @Mock
  private EnrollmentRepository enrollmentRepository;
  @Mock
  private DocumentRepository documentRepository;
  @Mock
  private StudentRepository studentRepository;
  @InjectMocks
  private InscriptionsServiceImpl inscriptionsService;

  @Test
  void getAllInscriptionsTest() {
    //Configure the mock objects
    Page<EnrollmentEntity> enrollmentEntities = TestProvider.provideEnrollmentPage();
    Pageable pageable =
        PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "student.studentPerson.idNumber"));
    when(enrollmentRepository.findAllEnrollmentsInProcess(pageable)).thenReturn(enrollmentEntities);

    List<EnrollmentDto> inscriptions = inscriptionsService.getAllInscriptions(pageable);

    //Verify the results
    assertThat(inscriptions).isNotNull();
    assertThat(inscriptions.size()).isEqualTo(1);
    assertThat(inscriptions.getFirst()).isEqualTo(
        EnrollmentMapper.convertToDto(TestProvider.provideEnrollment()));
  }

  @Test
  void findStudentByValueTest() {
    //Configure the mock objects
    String value = "200123654";
    when(studentRepository.findStudentByStudentPerson_IdNumberContaining(value)).thenReturn(
        List.of(TestProvider.provideStudent()));
    when(
        enrollmentRepository.findAllByStudentInTheListThatHasEnrollmentsInProcess(List.of(TestProvider.provideStudent()))).thenReturn(
        TestProvider.provideEnrollmentList());

    List<EnrollmentDto> inscriptions = inscriptionsService.findStudentByValue(value);

    assertThat(inscriptions).isNotNull();
    assertThat(inscriptions.size()).isEqualTo(1);
    assertThat(inscriptions.getFirst()).isEqualTo(
        EnrollmentMapper.convertToDto(TestProvider.provideEnrollment()));
  }
}
