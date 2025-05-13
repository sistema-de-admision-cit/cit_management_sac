package cr.co.ctpcit.citsacbackend.logic.services.results;

import cr.co.ctpcit.citsacbackend.data.entities.exams.AcademicExamEntity;
import cr.co.ctpcit.citsacbackend.data.entities.exams.DaiExamEntity;
import cr.co.ctpcit.citsacbackend.data.entities.exams.EnglishExamEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.EnrollmentEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.PersonEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.StudentEntity;
import cr.co.ctpcit.citsacbackend.data.enums.ExamType;
import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import cr.co.ctpcit.citsacbackend.data.repositories.inscriptions.EnrollmentRepository;
import cr.co.ctpcit.citsacbackend.data.repositories.inscriptions.PersonRepository;
import cr.co.ctpcit.citsacbackend.data.repositories.inscriptions.StudentRepository;
import cr.co.ctpcit.citsacbackend.data.utils.ResultUtils;
import cr.co.ctpcit.citsacbackend.logic.dto.results.ResultDTO;
import cr.co.ctpcit.citsacbackend.logic.dto.results.StudentResultsDetailsDTO;
import cr.co.ctpcit.citsacbackend.logic.dto.results.UpdateStatusDTO;
import cr.co.ctpcit.citsacbackend.logic.mappers.results.ResultsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Service implementation for managing exam results and enrollments.
 */

@Service
@RequiredArgsConstructor
public class ResultsServiceImpl implements ResultsService {

    private final StudentRepository studentRepository;
    private final PersonRepository personRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ResultUtils resultUtils;


    /**
     * Retrieves a paginated list of exam results for students whose enrollment status is accepted, rejected, or eligible.
     *
     * @param pageable the pagination information
     * @return a page of {@link ResultDTO} containing the exam results
     */
    @Override
    public Page<ResultDTO> getExamResults(Pageable pageable) {
        BigDecimal academicWeight = resultUtils.getConfigValue("ACADEMIC_WEIGHT");
        BigDecimal prevGradesWeight = resultUtils.getConfigValue("PREV_GRADES_WEIGHT");

        Page<EnrollmentEntity> enrollmentsPage = enrollmentRepository.findAllWithCompleteExams(pageable);

        Map<StudentEntity, EnrollmentEntity> lastEnrollments = enrollmentsPage.getContent().stream()
                .collect(Collectors.toMap(
                        EnrollmentEntity::getStudent,
                        Function.identity(),
                        (existing, replacement) ->
                                existing.getEnrollmentDate().isAfter(replacement.getEnrollmentDate()) ?
                                        existing : replacement
                ));

        // Ordenar por grado académico (de primero a décimo)
        List<ResultDTO> content = lastEnrollments.values().stream()
                .sorted(Comparator.comparing(e -> e.getGradeToEnroll()))
                .map(e -> ResultsMapper.mapToExamResultDTO(e, academicWeight, prevGradesWeight))
                .collect(Collectors.toList());

        return new PageImpl<>(
                content,
                pageable,
                enrollmentsPage.getTotalElements()
        );
    }


    /**
     * Searches for exam results by student ID number or name.
     *
     * @param query the ID number or name to search
     * @param pageable the pagination information
     * @return a page of {@link ResultDTO} matching the search criteria
     */
    @Override
    public Page<ResultDTO> searchResults(String query, Pageable pageable) {
        Page<EnrollmentEntity> enrollments = Page.empty(pageable);

        if (query.matches("\\d+")) {
            enrollments = enrollmentRepository.findByIdNumberAndStatusIn(
                    query,
                    Arrays.asList(ProcessStatus.ACCEPTED, ProcessStatus.REJECTED, ProcessStatus.ELIGIBLE),
                    pageable
            );
        }

        if (enrollments.getContent().isEmpty()) {
            Page<PersonEntity> persons = personRepository.findByNamesContaining(query, pageable);
            List<StudentEntity> students = studentRepository.findAllByStudentPersonIn(persons.getContent());
            enrollments = enrollmentRepository.findAllByStudentsWithStatusIn(
                    students,
                    Arrays.asList(ProcessStatus.ACCEPTED, ProcessStatus.REJECTED, ProcessStatus.ELIGIBLE),
                    pageable
            );
        }

        BigDecimal academicWeight = resultUtils.getConfigValue("ACADEMIC_WEIGHT");
        BigDecimal prevGradesWeight = resultUtils.getConfigValue("PREV_GRADES_WEIGHT");

        // Filtrar inscripciones completas y obtener la última por estudiante
        Map<StudentEntity, EnrollmentEntity> lastEnrollments = enrollments.getContent().stream()
                .filter(this::hasCompleteExams)
                .collect(Collectors.toMap(
                        EnrollmentEntity::getStudent,
                        Function.identity(),
                        (existing, replacement) ->
                                existing.getEnrollmentDate().isAfter(replacement.getEnrollmentDate()) ?
                                        existing : replacement
                ));

        List<ResultDTO> content = lastEnrollments.values().stream()
                .map(e -> ResultsMapper.mapToExamResultDTO(e, academicWeight, prevGradesWeight))
                .collect(Collectors.toList());

        return new PageImpl<>(
                content,
                pageable,
                enrollments.getTotalElements()
        );
    }

    /**
     * Retrieves detailed exam results for a specific student by their ID number.
     *
     * @param idNumber the student's ID number
     * @return a {@link StudentResultsDetailsDTO} containing the detailed results
     * @throws RuntimeException if the student is not found
     */
    @Override
    public StudentResultsDetailsDTO getStudentExamDetails(String idNumber) {
        // Find the most recent enrollment for the student
        EnrollmentEntity enrollment = enrollmentRepository
                .findTopByStudentStudentPersonIdNumberAndStatusInOrderByEnrollmentDateDesc(
                        idNumber,
                        Arrays.asList(ProcessStatus.ACCEPTED, ProcessStatus.REJECTED, ProcessStatus.ELIGIBLE)
                )
                .orElseThrow(() -> new RuntimeException("No se encontró estudiante con cédula: " + idNumber));

        EnglishExamEntity englishExam = ResultUtils.getEnglishExam(enrollment);
        AcademicExamEntity academicExam = ResultUtils.getAcademicExam(enrollment);
        DaiExamEntity daiExam = ResultUtils.getDaiExam(enrollment);

        BigDecimal academicWeight = resultUtils.getConfigValue("ACADEMIC_WEIGHT");
        BigDecimal prevGradesWeight = resultUtils.getConfigValue("PREV_GRADES_WEIGHT");

        BigDecimal finalGrade = academicExam.getGrade()
                .multiply(academicWeight)
                .add(enrollment.getStudent().getPreviousGrades().multiply(prevGradesWeight))
                .setScale(2, RoundingMode.HALF_UP);

        PersonEntity person = enrollment.getStudent().getStudentPerson();
        String fullName = person.getFirstName() + " " +
                person.getFirstSurname() + " " +
                (person.getSecondSurname() != null ? person.getSecondSurname() : "");

        return new StudentResultsDetailsDTO(
                fullName.trim(),
                person.getIdNumber(),
                enrollment.getGradeToEnroll(),
                enrollment.getStudent().getPreviousGrades(),
                englishExam.getLevel(),
                academicExam.getGrade(),
                finalGrade,
                daiExam != null ? daiExam.getRecommendation() : null,
                daiExam != null ? daiExam.getComment() : null,
                enrollment.getStatus()
        );
    }

    /**
     *
     * Delegates to {@code enrollmentRepository.countStudentsWithCompleteExamsBySearch(value)}
     * to retrieve the count of students who have completed all required exams
     * and match the given search term.
     *
     * @param value the search term to filter students (ID number or name)
     * @return the count of matched students with complete exams
     */

    @Override
    public Long getSearchCountByCompleteExams(String value) {
        return enrollmentRepository.countStudentsWithCompleteExamsBySearch(value);
    }

    /**
     *
     * Delegates to {@code enrollmentRepository.countStudentsWithCompleteExams()}
     * to retrieve the count of students who have completed all required exams.
     *
     * @return the count of students with complete exams
     */

    @Override
    public Long getExamsCount() {
        return enrollmentRepository.countStudentsWithCompleteExams();
    }

    /**
     * Updates the enrollment status of a student by their ID number.
     *
     * @param idNumber the student's ID number
     * @param updateStatusDTO the new status to set
     * @throws RuntimeException if the student is not found or the status is not allowed
     */

    @Override
    @Transactional
    public void updateEnrollmentStatus(String idNumber, UpdateStatusDTO updateStatusDTO) {
        EnrollmentEntity enrollment = enrollmentRepository.findByStudentStudentPersonIdNumber(idNumber)
                .orElseThrow(() -> new RuntimeException("No se encontró estudiante con cédula: " + idNumber));

        if (updateStatusDTO.getNewStatus() != ProcessStatus.ACCEPTED &&
                updateStatusDTO.getNewStatus() != ProcessStatus.REJECTED) {
            throw new RuntimeException("Solo se permite cambiar a ADMITIDO o RECHAZADO");
        }

        enrollment.setStatus(updateStatusDTO.getNewStatus());
        enrollmentRepository.save(enrollment);
    }

    /**
     * Checks whether an enrollment has both an English and an Academic exam completed.
     *
     * @param enrollment the enrollment entity to check
     * @return true if both exams are completed, false otherwise
     */

    private boolean hasCompleteExams(EnrollmentEntity enrollment) {
        return enrollment.getExams().stream()
                .anyMatch(e -> e.getExamType() == ExamType.ENG) &&
                enrollment.getExams().stream()
                        .anyMatch(e -> e.getExamType() == ExamType.ACA);
    }


}
