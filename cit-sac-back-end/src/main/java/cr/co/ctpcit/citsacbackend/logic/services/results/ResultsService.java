package cr.co.ctpcit.citsacbackend.logic.services.results;

import cr.co.ctpcit.citsacbackend.logic.dto.results.ResultDTO;
import cr.co.ctpcit.citsacbackend.logic.dto.results.StudentResultsDetailsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ResultsService {

    Page<ResultDTO> getExamResults(Pageable pageable);
    Page<ResultDTO> searchResults(String query, Pageable pageable);
    StudentResultsDetailsDTO getStudentExamDetails(String idNumber);
}
