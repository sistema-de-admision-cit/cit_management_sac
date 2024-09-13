package cr.co.ctpcit.citsacbackend.logic.mappers.exams.dai;

import cr.co.ctpcit.citsacbackend.data.entities.exams.dai.DaiExamsEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.DaiExamsDto;

import java.util.List;
import java.util.stream.Collectors;

public class DaiExamsMapper {
    public static DaiExamsDto toDto(DaiExamsEntity entity) {
        return new DaiExamsDto(
                entity.getId(),
                entity.getExamDate(),
                entity.getGrade()
        );
    }

    public static List<DaiExamsDto> toDtoList(List<DaiExamsEntity> entities) {
        return entities.stream()
                .map(DaiExamsMapper::toDto)
                .collect(Collectors.toList());
    }
}