import InputField from '../../../../core/global/atoms/InputField'

const QuestionTypeOptions = ({ value, handleChange, options }) => (
  <InputField
    field={{ name: 'questionType', label: 'Tipo de Pregunta', type: 'radio-group', options, required: true }}
    value={value}
    handleChange={handleChange}
    className='form-group'
  />
)

export default QuestionTypeOptions
