import Button from '../../../../core/global/atoms/Button'
import InputField from '../../../../core/global/atoms/InputField'

import { useState } from 'react'

const CSVUploadSection = ({ handleCSVLoad, setErrorMessage }) => {
  const [selectedFile, setSelectedFile] = useState(null)

  const handleFileChange = (e) => {
    setSelectedFile(e.target.files[0])
  }

  const handleProcessFile = () => {
    if (selectedFile) {
      handleCSVLoad(selectedFile)
    } else {
      setErrorMessage('Debes seleccionar un archivo CSV o Excel para procesar.')
    }
  }

  return (
    <div className='upload-section'>
      <h2>Cargar Notas desde CSV</h2>
      <InputField
        field={{ type: 'file', name: 'csv', placeholder: 'Selecciona un archivo CSV' }}
        handleChange={handleFileChange}
        autoComplete='off'
        className='form-group'
        accept='.csv, .xls, .xlsx'
      />
      <Button
        onClick={handleProcessFile}
        className='btn btn-primary'
        disabled={!selectedFile}
      >
        Procesar
      </Button>
    </div>
  )
}

export default CSVUploadSection
