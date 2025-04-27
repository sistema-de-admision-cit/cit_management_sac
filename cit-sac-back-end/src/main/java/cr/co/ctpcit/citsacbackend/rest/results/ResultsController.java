package cr.co.ctpcit.citsacbackend.rest.results;

import cr.co.ctpcit.citsacbackend.logic.dto.results.ResultDTO;
import cr.co.ctpcit.citsacbackend.logic.dto.results.StudentResultsDetailsDTO;
import cr.co.ctpcit.citsacbackend.logic.dto.results.UpdateStatusDTO;
import cr.co.ctpcit.citsacbackend.logic.services.results.ResultsServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * REST Controller for handling requests related to student exam results.
 * Provides endpoints to fetch exam results, search results by query, view detailed student results,
 * and update the enrollment status of students.
 */

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
public class ResultsController {

    private final ResultsServiceImpl resultsServiceImpl;

    /**
     * Retrieves a paginated list of exam results.
     *
     * @param pageable Pagination details such as page number and size.
     * @return A ResponseEntity containing the list of ResultDTOs representing the exam results.
     */

    @GetMapping
    public ResponseEntity<List<ResultDTO>> getExamResults(
            @PageableDefault(page = 0, size = 25) Pageable pageable) {
        Page<ResultDTO> page = resultsServiceImpl.getExamResults(pageable);
        return ResponseEntity.ok(page.getContent());
    }

    /**
     * Searches for exam results based on a provided query string. If the query is null or empty,
     * it returns all exam results. The search is case-insensitive and searches across multiple fields.
     *
     * @param value The query string to search for in exam results.
     * @param pageable Pagination details for the search results.
     * @return A ResponseEntity containing the list of ResultDTOs matching the search query.
     */

    @GetMapping("/search")
    public ResponseEntity<List<ResultDTO>> searchExamResults(
            @RequestParam(required = false) String value,
            @PageableDefault(page = 0, size = 25) Pageable pageable) {

        if (value == null || value.trim().isEmpty()) {
            return getExamResults(pageable);
        }

        Page<ResultDTO> results = resultsServiceImpl.searchResults(value, pageable);
        return ResponseEntity.ok(results.getContent());
    }

    /**
     * Retrieves detailed exam results for a specific student identified by their ID number.
     *
     * @param idNumber The ID number of the student whose exam results are to be fetched.
     * @return A ResponseEntity containing the detailed student exam results.
     */

    @GetMapping("/details/{idNumber}")
    public ResponseEntity<StudentResultsDetailsDTO> getStudentDetails(@PathVariable String idNumber) {
        StudentResultsDetailsDTO details = resultsServiceImpl.getStudentExamDetails(idNumber);
        return ResponseEntity.ok(details);
    }

    /**
     * Updates the enrollment status of a student identified by their ID number.
     * Only the statuses "ACCEPTED" and "REJECTED" are allowed.
     *
     * @param idNumber The ID number of the student whose enrollment status is to be updated.
     * @param updateStatusDTO The new status information provided for the update.
     * @return A ResponseEntity indicating the outcome of the update operation.
     */

    @PutMapping("/update-status/{idNumber}")
    public ResponseEntity<Void> updateStudentStatus(
            @PathVariable String idNumber,
            @Valid @RequestBody UpdateStatusDTO updateStatusDTO) {

        resultsServiceImpl.updateEnrollmentStatus(idNumber, updateStatusDTO);
        return ResponseEntity.ok().build();
    }


}
