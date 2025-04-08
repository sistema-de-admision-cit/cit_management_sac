import InputField from '../../../../core/global/atoms/InputField'

const QuestionTypeOptions = ({ value, handleChange, options, isRequired = true }) => (
  <InputField
    field={{ name: 'questionType', label: 'Tipo de Examen', type: 'radio-group', options, required: isRequired }}
    value={value}
    handleChange={handleChange}
    className='form-group'
  />
)

export default QuestionTypeOptions
