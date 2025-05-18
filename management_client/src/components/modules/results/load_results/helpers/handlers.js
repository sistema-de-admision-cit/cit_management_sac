import axios from '../../../../../config/axiosConfig'

import { convertToJson, formatLogMessage, parseCsvToArray, parseXlsxToArray, validateScore } from './helpers'

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
/**
 *
 * @param {Array} scores - Array of scores to validate (e.g. [{ id: '123', names: 'John', lastNames: 'Doe', lastTest: '2023-10-01', core: 90, level: 'B2' }])
 * @returns
 */
const validateEnglishScores = (scores) => {
  console.log('Validando todos los scores:', scores)

  const allValid = scores.every(score => {
    const isValid = validateScore(score)
    if (!isValid) {
      console.error('Score inválido encontrado:', score)
    }
    return isValid
  })

  console.log('Resultado validación completa:', allValid)
  return allValid
}

// this function is called when the user clicks the process button
const sendEnglishExamsResultsUrl = `${import.meta.env.VITE_API_BASE_URL}${import.meta.env.VITE_SEND_ENGLISH_EXAM_RESULTS_ENDPOINT}`
export const handleEnglishScoresFileProcess = (englishScores, setSuccessMessage, setErrorMessage, setLogs, isManualProcessing) => {
  console.log('englishScores', englishScores)
  if (isManualProcessing) {
    mapDatesToISOString(englishScores)
  }
  console.log('englishScores mapped', englishScores)

  // Validate the scores
  if (!validateEnglishScores(englishScores)) {
    setErrorMessage('Notas inválidas.')
    return
  }

  const logs = [{ message: 'Procesando notas...', status: 'info' }]

  // Send the scores to the API
  axios.post(sendEnglishExamsResultsUrl, englishScores)
    .then((response) => {
      // Agregar mensaje de éxito y procesar los logs de la respuesta
      setSuccessMessage('Notas procesadas correctamente.')

      // Procesar los logs y contar
      const responseLogs = response.data.map(formatLogMessage)
      const logCount = responseLogs.length

      // Agregar los logs procesados y mensaje final
      const completionMessage = {
        message: `Proceso completado. ${logCount} ${logCount === 1 ? 'registro' : 'registros'} procesado${logCount === 1 ? '' : 's'}.`,
        status: 'info'
      }

      setLogs([...logs, ...responseLogs, completionMessage]) // Combinar todos los logs
    })
    .catch((error) => {
      console.error(error)
      setErrorMessage('Error al procesar las notas.')
    })
}

/**
 *
 * @param {Array} englishScores - Array of English scores to map (e.g. [{ id: '123', names: 'John', lastNames: 'Doe', lastTest: DateObject, core: 90, level: 'B2' }])
 *
 * @returns {Array} - Array of English scores with mapped dates to string (e.g. [{ id: '123', names: 'John', lastNames: 'Doe', lastTest: '2023-10-01', core: 90, level: 'B2' }])
 */
const mapDatesToISOString = (englishScores) => {
  // from Thu Jan 11 2024 00:00:00 GMT-0600 (Central Standard Time)
  // to 2024-01-11
  return englishScores.map((score) => {
    if (score.lastTest instanceof Date) {
      score.lastTest = score.lastTest.toISOString().split('T')[0]
    }

    return score
  })
}
