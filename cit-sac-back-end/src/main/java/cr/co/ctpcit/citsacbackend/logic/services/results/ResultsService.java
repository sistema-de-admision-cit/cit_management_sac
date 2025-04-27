package cr.co.ctpcit.citsacbackend.logic.services.results;

import cr.co.ctpcit.citsacbackend.logic.dto.results.ResultDTO;
import cr.co.ctpcit.citsacbackend.logic.dto.results.StudentResultsDetailsDTO;
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
}
