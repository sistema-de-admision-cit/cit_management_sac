package cr.co.ctpcit.citsacbackend.logic.mappers.exams.academic;

import cr.co.ctpcit.citsacbackend.data.entities.exams.academic.AcademicExamQuestionsEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.AcademicExamQuestionsDto;

import java.util.List;
import java.util.stream.Collectors;

public class AcademicExamQuestionsMapper {

    public static AcademicExamQuestionsDto toDto(AcademicExamQuestionsEntity entity) {
        return new AcademicExamQuestionsDto(
                entity.getId().getExamId(),
                entity.getId().getQuestionId(),
                entity.getStudentAnswer()
        );
    }

    public static AcademicExamQuestionsEntity toEntity(AcademicExamQuestionsDto dto) {
        AcademicExamQuestionsEntity entity = new AcademicExamQuestionsEntity();
        entity.getId().setExamId(dto.examId());
        entity.getId().setQuestionId(dto.questionId());
        entity.setStudentAnswer(dto.studentAnswer());
        return entity;
    }

    public static List<AcademicExamQuestionsDto> toDtoList(List<AcademicExamQuestionsEntity> entities) {
        return entities.stream()
                .map(AcademicExamQuestionsMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<AcademicExamQuestionsEntity> toEntityList(List<AcademicExamQuestionsDto> dtos) {
        return dtos.stream()
                .map(AcademicExamQuestionsMapper::toEntity)
                .collect(Collectors.toList());
    }
}
