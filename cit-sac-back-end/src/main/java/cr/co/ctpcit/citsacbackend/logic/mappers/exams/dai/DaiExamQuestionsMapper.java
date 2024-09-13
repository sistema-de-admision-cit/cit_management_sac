package cr.co.ctpcit.citsacbackend.logic.mappers.exams.dai;

import cr.co.ctpcit.citsacbackend.data.entities.exams.dai.DaiExamQuestionsEntity;
import cr.co.ctpcit.citsacbackend.data.entities.exams.dai.DaiExamsEntity;
import cr.co.ctpcit.citsacbackend.data.entities.exams.dai.DaiQuestionsEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.DaiExamQuestionsDto;

import java.util.List;
import java.util.stream.Collectors;

public class DaiExamQuestionsMapper {
    public static DaiExamQuestionsDto toDto(DaiExamQuestionsEntity entity) {
        return new DaiExamQuestionsDto(
                entity.getExamId().getId(),
                entity.getDaiQuestions().getId(),
                entity.getStudentAnswer()
        );
    }

    public static List<DaiExamQuestionsDto> toDtoList(List<DaiExamQuestionsEntity> entities) {
        return entities.stream()
                .map(DaiExamQuestionsMapper::toDto)
                .collect(Collectors.toList());
    }

    public static DaiExamQuestionsEntity toEntity(DaiExamQuestionsDto dto) {
        DaiExamQuestionsEntity entity = new DaiExamQuestionsEntity();
        entity.setStudentAnswer(dto.studentAnswer());

        DaiQuestionsEntity questionEntity = new DaiQuestionsEntity();
        questionEntity.setId(dto.questionId());
        entity.setDaiQuestions(questionEntity);

        //Aqui esta el problema digamos
        DaiExamsEntity examEntity = new DaiExamsEntity();
        examEntity.setId(dto.examId());
        entity.setExamId(examEntity);

        return entity;
    }
}