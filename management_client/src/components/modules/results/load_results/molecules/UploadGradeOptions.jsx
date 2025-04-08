import InputField from '../../../../core/global/atoms/InputField'

const UploadGradeOptions = ({ value, handleChange, options, isRequired = false }) => (
  <InputField
    field={{ name: 'uploadType', label: 'Tipo de Carga', type: 'radio-group', options, required: isRequired }}
    value={value}
    handleChange={handleChange}
    className='form-group'
  />
)

export default UploadGradeOptions
