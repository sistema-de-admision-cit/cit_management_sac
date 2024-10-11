import InputField from '../../../../core/global/atoms/InputField'

const MethodSelection = ({ uploadMethod, handleChange }) => (
  <div className='method-selection'>
    <h2>Selecciona el método de carga</h2>
    <InputField
      field={{
        type: 'radio-group',
        name: 'uploadMethod',
        options: [{ value: 'csv', label: 'CSV' }, { value: 'api', label: 'API' }],
        required: true,
        label: 'Método de carga'
      }}
      value={uploadMethod}
      handleChange={handleChange}
    />
  </div>
)
export default MethodSelection
