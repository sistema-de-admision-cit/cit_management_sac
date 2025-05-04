package cr.co.ctpcit.citsacbackend.data.utils;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.*;
import cr.co.ctpcit.citsacbackend.logic.dto.reports.ReportRequestDto;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for building dynamic specifications for enrollment report queries.
 */

public class BuildReport {

    /**
     * Builds a JPA {@link Specification} based on the filters provided in the {@link ReportRequestDto}.
     * This specification is used to dynamically construct queries for enrollment reports,
     * including date ranges and various report types such as knownThrough, gradeToEnroll, processStatus, and province.
     *
     * @param request The {@link ReportRequestDto} containing the filter criteria.
     * @return A {@link Specification} for querying {@link EnrollmentEntity} with the given filters.
     */

    public static Specification<EnrollmentEntity> buildSpecification(ReportRequestDto request) {
        return (root, query, cb) -> {
            List<Predicate> predicados = new ArrayList<>();

            // Filtro por rango de fechas
            predicados.add(cb.between(root.get("enrollmentDate"),
                    request.getStartDate().atStartOfDay(),
                    request.getEndDate().atTime(LocalTime.MAX)));

            // Filtros según tipo de reporte
            switch (request.getReportType()) {
                case KNOWN_THROUGH:
                    if (request.getKnownThroughFilter() != null) {
                        predicados.add(cb.equal(root.get("knownThrough"), request.getKnownThroughFilter()));
                    }
                    break;
                case GRADE_TO_ENROLL:
                    if (request.getGradeFilter() != null) {
                        predicados.add(cb.equal(root.get("gradeToEnroll"), request.getGradeFilter()));
                    }
                    break;
                case PROCESS_STATUS:
                    if (request.getStatusFilter() != null) {
                        predicados.add(cb.equal(root.get("status"), request.getStatusFilter()));
                    }
                    break;
                case PROVINCE:
                    if (request.getProvinceFilter() != null && !request.getProvinceFilter().isEmpty()) {
                        Join<EnrollmentEntity, StudentEntity> estudiante = root.join("student");
                        Join<StudentEntity, ParentsStudentsEntity> padreEstudiante = estudiante.join("parents");
                        Join<ParentsStudentsEntity, ParentEntity> padre = padreEstudiante.join("parent");
                        Join<ParentEntity, AddressEntity> direccion = padre.join("addresses");

                        predicados.add(cb.equal(direccion.get("province"), request.getProvinceFilter()));
                    }
                    break;
            }

            return cb.and(predicados.toArray(new Predicate[0]));
        };
    }
}
