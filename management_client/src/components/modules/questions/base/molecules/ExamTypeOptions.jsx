import InputField from '../../../../core/global/atoms/InputField'

const ExamTypeOptions = ({ value, handleChange, options, isRequired = true }) => (
  <InputField
    field={{ name: 'examType', label: 'Tipo de Examen', type: 'radio-group', options, required: isRequired }}
    value={value}
    handleChange={handleChange}
    className='form-group'
  />
)

export default ExamTypeOptions
