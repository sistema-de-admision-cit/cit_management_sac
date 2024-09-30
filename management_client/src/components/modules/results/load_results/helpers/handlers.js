import axios from 'axios'
import { convertToJson, parseCsvToArray, parseXlsxToArray } from './helpers'

export const fetchTrackTestScores = async (setSuccessMessage, setErrorMessage) => {
  // todo: fetch track test scores from the API
  // axios.get('/api/track-test-scores')
  setTimeout(() => {
    setSuccessMessage('Notas cargadas correctamente desde la API.')
  }, 1000)
}

/* handle file upload */

const handleFile = async (file, e, parser, setEnglishScores, setLoading, setErrorMessage) => {
  try {
    setLoading(true)
    // Parse data using the provided parser (csv or xlsx)
    const data = await parser(file)

    // Validate headers
    const headers = data[0]
    if (!validateFileStructure(headers)) {
      setErrorMessage('Estructura de archivo inválida.')
      e.target.value = null
      setLoading(false)
      return
    }

    // Convert to JSON and set the scores
    const rows = data.slice(1)
    const jsonData = convertToJson(headers, rows)
    setEnglishScores(jsonData)
  } catch (error) {
    console.error(error)
    setErrorMessage('Error al leer el archivo.')
  } finally {
    setLoading(false)
  }
}

// Llamadas específicas
const handleXlsxFile = (file, e, setEnglishScores, setLoading, setErrorMessage) => {
  handleFile(file, e, parseXlsxToArray, setEnglishScores, setLoading, setErrorMessage)
}

const handleCsvFile = (file, e, setEnglishScores, setLoading, setErrorMessage) => {
  handleFile(file, e, parseCsvToArray, setEnglishScores, setLoading, setErrorMessage)
}

// the file structure should have at least the following headers: 'ID', 'First and Middle Name', 'Last Name(s)', 'Last Test', 'Core'
const validateFileStructure = (headers) => {
  const requiredHeaders = ['ID', 'First and Middle Name', 'Last Name(s)', 'Last test', 'Core']
  const missingHeaders = requiredHeaders.filter((header) => !headers.includes(header))

  return missingHeaders.length === 0
}

// this function is called when the user uploads a file
export const handleEnglishScoresFileUpload = (file, e, setEnglishScores, setLoading, setErrorMessage) => {
  setLoading(true)

  const fileExtension = file.name.split('.').pop().toLowerCase()

  if (fileExtension === 'xlsx' || fileExtension === 'xls') {
    handleXlsxFile(file, e, setEnglishScores, setLoading, setErrorMessage)
  } else if (fileExtension === 'csv') {
    handleCsvFile(file, e, setEnglishScores, setLoading, setErrorMessage)
  } else {
    setErrorMessage('Formato de archivo no soportado.')
    setLoading(false)
  }
}

// Validate the English scores
const validateEnglishScores = (scores) => {
  const containsAllFields = scores.every((score) => {
    return score.id && score.names && score.lastNames && score.lastTest && score.core && score.level
  })

  const areDatesValid = scores.every((score) => {
    return !isNaN(Date.parse(score.lastTest))
  })

  return containsAllFields && areDatesValid
}

// this function is called when the user clicks the process button
const sendEnglishExamsResultsUrl = `${import.meta.env.VITE_API_BASE_URL}${import.meta.env.VITE_SEND_ENGLISH_EXAM_RESULTS_ENDPOINT}`
export const handleEnglishScoresFileProcess = (englishScores, setSuccessMessage, setErrorMessage, setLogs) => {
  console.log('englishScores', englishScores)
  // Validate the scores
  if (!validateEnglishScores(englishScores)) {
    setErrorMessage('Notas inválidas.')
    return
  }

  // Send the scores to the API
  axios.post(sendEnglishExamsResultsUrl, englishScores)
    .then((response) => {
      setSuccessMessage('Notas procesadas correctamente.')
      console.log(response.data)
    })
    .catch((error) => {
      console.error(error)
      setErrorMessage('Error al procesar las notas.')
    })
}
