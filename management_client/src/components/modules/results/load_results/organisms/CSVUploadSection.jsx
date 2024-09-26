import Button from '../../../../core/global/atoms/Button'
import InputField from '../../../../core/global/atoms/InputField'

import { useState } from 'react'

const CSVUploadSection = ({ handleFileLoad, processSelectedFile, setErrorMessage }) => {
  const [selectedFile, setSelectedFile] = useState(null)

  const handleLoadFile = (file) => {
    if (!file) return

    setSelectedFile(file)
    handleFileLoad(file)
  }

  return (
    <div className='upload-section'>
      <h2>Cargar Notas desde CSV</h2>
      <InputField
        field={{ type: 'file', name: 'csv', placeholder: 'Selecciona un archivo CSV' }}
        handleChange={(e) => handleLoadFile(e.target.files[0])}
        autoComplete='off'
        className='form-group'
        accept='.csv, .xls, .xlsx'
      />
      <Button
        onClick={() => processSelectedFile(selectedFile)}
        className='btn btn-primary'
        disabled={!selectedFile}
      >
        Procesar
      </Button>
    </div>
  )
}

export default CSVUploadSection
