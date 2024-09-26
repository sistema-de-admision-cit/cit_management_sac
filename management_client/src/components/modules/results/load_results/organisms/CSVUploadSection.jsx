import Button from '../../../../core/global/atoms/Button'
import InputField from '../../../../core/global/atoms/InputField'

import { useState } from 'react'

const CSVUploadSection = ({ handleFileLoad, processSelectedFile, setErrorMessage }) => {
  const [selectedFile, setSelectedFile] = useState(null)

  const handleLoadFile = (e) => {
    if (!e.target.files.length) return

    const file = e.target.files[0]
    setSelectedFile(file)

    handleFileLoad(file, e)
  }

  return (
    <div className='upload-section'>
      <h2>Cargar Notas desde CSV</h2>
      <InputField
        field={{ type: 'file', name: 'csv', placeholder: 'Selecciona un archivo CSV' }}
        handleChange={(e) => handleLoadFile(e)}
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
