import InputField from '../../../../core/global/atoms/InputField'

const ExamTypeOptions = ({ value, handleChange, options }) => (
  <InputField
    field={{ name: 'examType', label: 'Tipo de Examen', type: 'radio-group', options }}
    value={value}
    handleChange={handleChange}
    className='form-group'
  />
)

export default ExamTypeOptions
