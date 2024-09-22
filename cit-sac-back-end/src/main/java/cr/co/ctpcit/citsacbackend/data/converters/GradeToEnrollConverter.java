package cr.co.ctpcit.citsacbackend.data.converters;

import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GradeToEnrollConverter implements AttributeConverter<Grades, String> {

  @Override
  public String convertToDatabaseColumn(Grades attribute) {
    return attribute == null ? null : attribute.getGrade();
  }

  @Override
  public Grades convertToEntityAttribute(String dbData) {
    return Grades.fromGrade(dbData);
  }
}
