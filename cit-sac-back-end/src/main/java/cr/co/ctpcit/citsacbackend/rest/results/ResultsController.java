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

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
public class ResultsController {

    private final ResultsServiceImpl resultsServiceImpl;


    @GetMapping
    public ResponseEntity<List<ResultDTO>> getExamResults(
            @PageableDefault(page = 0, size = 25) Pageable pageable) {
        Page<ResultDTO> page = resultsServiceImpl.getExamResults(pageable);
        return ResponseEntity.ok(page.getContent());
    }

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

    @GetMapping("/details/{idNumber}")
    public ResponseEntity<StudentResultsDetailsDTO> getStudentDetails(@PathVariable String idNumber) {
        StudentResultsDetailsDTO details = resultsServiceImpl.getStudentExamDetails(idNumber);
        return ResponseEntity.ok(details);
    }

    @PutMapping("/update-status/{idNumber}")
    public ResponseEntity<Void> updateStudentStatus(
            @PathVariable String idNumber,
            @Valid @RequestBody UpdateStatusDTO updateStatusDTO) {

        resultsServiceImpl.updateEnrollmentStatus(idNumber, updateStatusDTO);
        return ResponseEntity.ok().build();
    }


}
