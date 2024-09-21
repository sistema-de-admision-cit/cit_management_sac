package cr.co.ctpcit.citsacbackend.logic.mappers.exams.dai;

import cr.co.ctpcit.citsacbackend.data.entities.exams.dai.DaiExamQuestionsEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.DaiExamQuestionsDto;

import java.util.List;
import java.util.stream.Collectors;

public class DaiExamQuestionsMapper {

    public static DaiExamQuestionsDto toDto(DaiExamQuestionsEntity entity) {
        return new DaiExamQuestionsDto(
                entity.getId().getExamId(),
                entity.getId().getQuestionId(),
                entity.getStudentAnswer()
        );
    }

    public static DaiExamQuestionsEntity toEntity(DaiExamQuestionsDto dto) {
        DaiExamQuestionsEntity entity = new DaiExamQuestionsEntity();
        entity.getId().setExamId(dto.examId());
        entity.getId().setQuestionId(dto.questionId());
        entity.setStudentAnswer(dto.studentAnswer());
        return entity;
    }

    public static List<DaiExamQuestionsDto> toDtoList(List<DaiExamQuestionsEntity> entities) {
        return entities.stream()
                .map(DaiExamQuestionsMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<DaiExamQuestionsEntity> toEntityList(List<DaiExamQuestionsDto> dtos) {
        return dtos.stream()
                .map(DaiExamQuestionsMapper::toEntity)
                .collect(Collectors.toList());
    }
}