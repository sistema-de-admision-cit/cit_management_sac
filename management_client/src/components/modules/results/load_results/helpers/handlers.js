import { convertToJson, parseCsvToArray, parseXlsxToArray } from './helpers'

export const fetchTrackTestScores = async (setSuccessMessage, setErrorMessage) => {
  // todo: fetch track test scores from the API
  // axios.get('/api/track-test-scores')
  setTimeout(() => {
    setSuccessMessage('Notas cargadas correctamente desde la API.')
  }, 1000)
}

/* handle file upload */

// handle XLSX files
const handleXlsxFile = (file, setEnglishScores, setLoading, setErrorMessage) => {
  // parse data from XLSX to json (xlsxToJson is a promise)
  parseXlsxToArray(file)
    .then((data) => {
      // data is an array with the headers in the first position and the rows in the rest
      const headers = data[0]
      if (!validateFileStructure(headers)) {
        setErrorMessage('Estructura de archivo inválida.')
        setLoading(false)
        return
      }

      const rows = data.slice(1)
      const jsonData = convertToJson(headers, rows)
      setEnglishScores(jsonData)
      setLoading(false)
    })
    .catch((error) => {
      console.error(error)
      setErrorMessage('Error al leer el archivo XLSX.')
      setLoading(false)
    })
}

// handle CSV files
const handleCsvFile = (file, setEnglishScores, setLoading, setErrorMessage) => {
  // parse data from CSV to json (csvToJson is a promise)
  parseCsvToArray(file)
    .then((data) => {
      // data is an array with the headers in the first position and the rows in the rest
      const headers = data[0]
      if (!validateFileStructure(headers)) {
        setErrorMessage('Estructura de archivo CSV inválida.')
        setLoading(false)
        return
      }

      const rows = data.slice(1)
      const jsonData = convertToJson(headers, rows)
      setEnglishScores(jsonData)
      setLoading(false)
    })
    .catch((error) => {
      console.error(error)
      setErrorMessage('Error al leer el archivo CSV.')
      setLoading(false)
    })
}

// the file structure should have at least the following headers: 'ID', 'First and Middle Name', 'Last Name(s)', 'Last Test', 'Core'
const validateFileStructure = (headers) => {
  const requiredHeaders = ['ID', 'First and Middle Name', 'Last Name(s)', 'Last Test', 'Core']
  const missingHeaders = requiredHeaders.filter((header) => !headers.includes(header))

  return missingHeaders.length === 0
}

// this function is called when the user uploads a file
export const handleEnglishScoresFileUpload = (file, setEnglishScores, setLoading, setErrorMessage) => {
  setLoading(true)

  const fileExtension = file.name.split('.').pop().toLowerCase()

  if (fileExtension === 'xlsx' || fileExtension === 'xls') {
    handleXlsxFile(file, setEnglishScores, setLoading, setErrorMessage)
  } else if (fileExtension === 'csv') {
    handleCsvFile(file, setEnglishScores, setLoading, setErrorMessage)
  } else {
    setErrorMessage('Formato de archivo no soportado.')
    setLoading(false)
  }
}
