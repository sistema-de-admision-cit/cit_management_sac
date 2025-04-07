import InputField from '../../../../core/global/atoms/InputField'
import Button from '../../../../core/global/atoms/Button'

const QuestionForm = ({ formValues, handleChange }) => (
  <div>
    <InputField
      field={{ name: 'daiExam', label: 'Preguntas DAI', type: 'number', required: true }}
      value={formValues.daiExam}
      handleChange={(e) => handleChange('daiExam', e.target.value)}
    />
    <InputField
      field={{ name: 'academicExam', label: 'Preguntas Académico', type: 'number', required: true }}
      value={formValues.academicExam}
      handleChange={(e) => handleChange('academicExam', e.target.value)}
    />
  </div>
)

export default QuestionForm
