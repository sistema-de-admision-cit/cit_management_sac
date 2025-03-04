import InputField from '../../../../core/global/atoms/InputField'

const QuestionGradeOptions = ({ value, handleChange, options, isRequired = true }) => (
  <InputField
    field={{ name: 'questionGrade', label: 'Grado del Examen', type: 'dropdown', options, required: isRequired }}
    value={value}
    handleChange={handleChange}
    className='form-group'
  />
)

export default QuestionGradeOptions
