import axios from '../../../../../config/axiosConfig'

let initValues = {
  academicExam: 0,
  prevGradesExam: 0, // Se corrigió 'daiExam' a 'prevGradesExam'
  englishExam: 0
}

const getErrorMessage = (error) => {
  if (error.response) {
    if (error.response.status === 400) {
      return 'Porcentajes inválidos. La suma de los porcentajes debe ser 100 y no pueden ser negativos.'
    } else if (error.response.status === 500) {
      return 'Error al guardar los porcentajes.'
    } else if (error.response.status === 404) {
      return 'No se encontraron configuraciones de porcentaje.'
    }
  }
  return 'Error al cargar los porcentajes.'
}

/**
 *
 * @param {JSON Object} formValues
 * @returns Boolean (true si los valores suman 100 y han cambiado con respecto a initValues)
 */
export const getSaveButtonState = (formValues) => {
  const total = Object.values(formValues).reduce((acc, value) => acc + Number(value), 0)
  const isSame = Object.keys(formValues).every(key => formValues[key] === initValues[key])

  return total === 100 && !isSame
}

const getExamPercentagesUrl = import.meta.env.VITE_GET_EXAM_PERCENTAGES_ENDPOINT

// Mapeo de datos entrantes desde el backend
const mapIncomingData = (data) => {
  return data.reduce((acc, item) => {
    if (item.configName === 'PREV_GRADES_WEIGHT') {
      acc.prevGradesExam = parseFloat(item.configValue) * 100
    } else if (item.configName === 'ACADEMIC_WEIGHT') {
      acc.academicExam = parseFloat(item.configValue) * 100
    }
    return acc
  }, { academicExam: 0, prevGradesExam: 0 })
}

// Mapeo de datos antes de enviarlos al backend
const mapOutgoingData = (data) => {
  return {
    prevGradesWeight: data.prevGradesExam / 100,
    academicWeight: data.academicExam / 100
  }
}

export const getCurrentPercentages = async () => {
  try {
    const response = await axios.get(getExamPercentagesUrl, { timeout: 5000 })
    const data = mapIncomingData(response.data)

    initValues = { ...data }
    return data
  } catch (error) {
    throw new Error(getErrorMessage(error))
  }
}

const saveExamPercentagesUrl = import.meta.env.VITE_UPDATE_EXAM_PERCENTAGES_ENDPOINT

export const updateExamPercentages = async (formValues, setFormValues, setLoading, setSuccessMessage, setErrorMessage) => {
  setLoading(true)

  try {
    const dataToSend = mapOutgoingData(formValues)
    await axios.put(saveExamPercentagesUrl, dataToSend, { timeout: 5000 })
    setSuccessMessage('Porcentajes actualizados correctamente.')
  } catch (error) {
    setErrorMessage(getErrorMessage(error))
  } finally {
    setLoading(false)
  }
}
