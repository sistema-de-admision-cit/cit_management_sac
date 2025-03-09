package cr.co.ctpcit.citsacbackend.logic.mappers.exam;

import cr.co.ctpcit.citsacbackend.data.entities.exam.ExamEntity;
import cr.co.ctpcit.citsacbackend.data.entities.exam.ExamQuestionsEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.exam.ExamDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exam.ExamQuestionsDto;
import cr.co.ctpcit.citsacbackend.logic.mappers.questions.QuestionMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExamMapper {

    /**
     * Converts an ExamEntity to an ExamDto.
     *
     * @param entity the ExamEntity instance
     * @return an ExamDto with data copied from the entity
     */
    public static ExamDto entityToDto(ExamEntity entity) {
        if (entity == null) {
            return null;
        }

        List<ExamQuestionsDto> examQuestionsDtos = entity.getExamQuestions().stream()
                .map(ExamMapper::examQuestionEntityToDto)
                .collect(Collectors.toList());

        return new ExamDto(entity.getId(), entity.getEnrollment().getId(), entity.getExamDate(),
                entity.getExamType(), entity.getResponses(), examQuestionsDtos);
    }

    /**
     * Converts an ExamDto to an ExamEntity.
     *
     * @param dto the ExamDto instance
     * @return an ExamEntity with data copied from the dto
     */
    public static ExamEntity dtoToEntity(ExamDto dto) {
        if (dto == null) {
            return null;
        }

        ExamEntity examEntity = ExamEntity.builder()
                .id(dto.id())
                .examDate(dto.examDate())
                .examType(dto.examType())
                .responses(dto.responses())
                .build();

        List<ExamQuestionsEntity> examQuestionsEntities = dto.examQuestions().stream()
                .map(dtoExamQuestion -> examQuestionDtoToEntity(dtoExamQuestion, examEntity))
                .collect(Collectors.toList());

        examEntity.setExamQuestions(examQuestionsEntities);

        return examEntity;
    }

    /**
     * Converts an ExamQuestionsEntity to an ExamQuestionsDto.
     *
     * @param entity the ExamQuestionsEntity instance
     * @return an ExamQuestionsDto with data copied from the entity
     */
    private static ExamQuestionsDto examQuestionEntityToDto(ExamQuestionsEntity entity) {
        return new ExamQuestionsDto(entity.getId(), entity.getExam().getId(),
                QuestionMapper.entityToDto(entity.getQuestion()), entity.getAnswer());
    }

    /**
     * Converts an ExamQuestionsDto to an ExamQuestionsEntity.
     *
     * @param dto the ExamQuestionsDto instance
     * @param examEntity the parent ExamEntity
     * @return an ExamQuestionsEntity with data copied from the dto
     */
    private static ExamQuestionsEntity examQuestionDtoToEntity(ExamQuestionsDto dto, ExamEntity examEntity) {
        return ExamQuestionsEntity.builder()
                .id(dto.id())
                .exam(examEntity)
                .question(QuestionMapper.dtoToEntity(dto.question()))
                .answer(dto.answer())
                .build();
    }
}

