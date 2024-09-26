import { convertToJson, xlsxToJson } from './helpers'

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
  xlsxToJson(file)
    .then((data) => {
      // data is an array with the headers in the first position and the rows in the rest
      const headers = data[0]
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
  // eslint-disable-next-line no-undef
  const reader = new FileReader()
  reader.onload = (e) => {
    const data = e.target.result
    const lines = data.split('\n')
    const headers = lines[0].split(',')
    const rows = lines.slice(1).map((line) => line.split(','))

    const jsonData = convertToJson(headers, rows)
    setEnglishScores(jsonData)
    setLoading(false)
  }
  reader.onerror = (error) => {
    console.error(error)
    setErrorMessage('Error al leer el archivo CSV.')
    setLoading(false)
  }
  reader.readAsText(file)
}

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
