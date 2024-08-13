import InputField from '../../../global/atoms/InputField'

const QuestionTypeOptions = ({ value, handleChange, options }) => (
  <InputField
    field={{ name: 'questionType', label: 'Tipo de Pregunta', type: 'radio-group', options }}
    value={value}
    handleChange={handleChange}
    className='form-group'
  />
)

export default QuestionTypeOptions
