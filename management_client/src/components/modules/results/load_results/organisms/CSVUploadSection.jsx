import InputField from '../../../../core/global/atoms/InputField'

const CSVUploadSection = ({ handleCSVLoad }) => (
  <div className='upload-section'>
    <h2>Cargar Notas desde CSV</h2>
    <InputField
      field={{ type: 'file', name: 'csv', placeholder: 'Selecciona un archivo CSV' }}
      handleChange={(e) => handleCSVLoad(e.target.files[0])}
      autoComplete='off'
      className='form-group'
      accept='.csv, .xls, .xlsx'
    />
  </div>
)

export default CSVUploadSection
