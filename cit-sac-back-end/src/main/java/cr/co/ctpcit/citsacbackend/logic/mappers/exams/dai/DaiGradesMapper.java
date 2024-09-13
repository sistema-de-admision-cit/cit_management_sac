package cr.co.ctpcit.citsacbackend.logic.mappers.exams.dai;

import cr.co.ctpcit.citsacbackend.data.entities.exams.dai.DaiGradesEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.DaiGradesDto;

import java.util.List;
import java.util.stream.Collectors;

public class DaiGradesMapper {
    public static DaiGradesDto toDto(DaiGradesEntity entity) {
        return new DaiGradesDto(
                entity.getId(),
                entity.getEnrollmentId().getId()
        );
    }

    public static List<DaiGradesDto> toDtoList(List<DaiGradesEntity> entities) {
        return entities.stream()
                .map(DaiGradesMapper::toDto)
                .collect(Collectors.toList());
    }
}