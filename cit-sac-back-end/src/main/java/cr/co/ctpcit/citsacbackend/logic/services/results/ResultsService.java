package cr.co.ctpcit.citsacbackend.logic.services.results;

import cr.co.ctpcit.citsacbackend.logic.dto.results.ResultDTO;
import cr.co.ctpcit.citsacbackend.logic.dto.results.StudentResultsDetailsDTO;
import cr.co.ctpcit.citsacbackend.logic.dto.results.UpdateStatusDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service interface for managing exam results and student enrollment statuses.
 */

@Service
public interface ResultsService {
    /**
     * Retrieves a paginated list of exam results for all students with eligible enrollment statuses.
     *
     * @param pageable the pagination information
     * @return a paginated list of exam results
     */

    Page<ResultDTO> getExamResults(Pageable pageable);

    /**
     * Searches for exam results based on an ID number or a student's name.
     * If the query is numeric, searches by ID number; otherwise, searches by student name.
     *
     * @param query the ID number or name to search
     * @param pageable the pagination information
     * @return a paginated list of matching exam results
     */

    Page<ResultDTO> searchResults(String query, Pageable pageable);


    /**
     * Retrieves detailed exam results and enrollment information for a specific student based on their ID number.
     *
     * @param idNumber the student's ID number
     * @return detailed student exam results
     */

    StudentResultsDetailsDTO getStudentExamDetails(String idNumber);

    /**
     * Updates the enrollment status of a student identified by their ID number.
     *
     * @param idNumber the identification number of the student whose enrollment status will be updated
     * @param updateStatusDTO the data transfer object containing the new status to be set
     */

    void updateEnrollmentStatus(String idNumber, UpdateStatusDTO updateStatusDTO);

    /**
     * Returns the number of students who have completed all required exams:
     * academic (ACA), DAI, and English (ENG), and whose enrollment status is
     * ELIGIBLE, ACCEPTED, or REJECTED.
     *
     * @return the count of students with complete exams
     */
    Long getExamsCount();

    /**
     * Returns the number of students who match the given search term and have
     * completed all required exams: academic (ACA), DAI, and English (ENG),
     * and whose enrollment status is ELIGIBLE, ACCEPTED, or REJECTED.
     *
     * @param value the search term used to filter students by ID number or name fields
     * @return the count of matched students with complete exams
     */
    Long getSearchCountByCompleteExams(String value);

}
