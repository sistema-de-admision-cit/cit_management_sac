import axios from 'axios'

let initValues = {
  academicExam: 0,
  daiExam: 0,
  englishExam: 0
}

const getErrorMessage = (error) => {
  if (error.response) {
    if (error.response.status === 400) {
      return 'Porcentajes inválidos. La suma de los porcentajes debe ser 100.'
    } else if (error.response.status === 500) {
      return 'Error al guardar los porcentajes.'
    } else if (error.response.status === 404) {
      return 'Parece que no se pudo resolver la solicitud. Inténtalo de nuevo.'
    }
  }
  return 'Error al cargar los porcentajes.'
}

/**
 *
 * @param {JSON Object} formValues
 * @param {JSON Object} initValues to compare with formValues (if they are the same, the save button will be disabled)
 * @returns
 */
export const getSaveButtonState = (formValues) => {
  // sum all values in formValues object
  const total = Object.values(formValues).reduce((acc, value) => acc + Number(value), 0)
  // compare formValues with initValues
  const isSame = Object.keys(formValues).every(key => formValues[key] === initValues[key])

  return total === 100 && !isSame
}

const getExamPercentagesUrl = `${import.meta.env.VITE_API_BASE_URL}${import.meta.env.VITE_GET_EXAM_PERCENTAGES_ENDPOINT}`

// mappear los datos que llegan
const mapIncomingData = (data) => {
  return data.reduce((acc, item) => {
    if (item.configName === 'dai_weight') {
      acc.daiExam = item.configValue * 100
    } else if (item.configName === 'academic_weight') {
      acc.academicExam = item.configValue * 100
    } else if (item.configName === 'english_weight') {
      acc.englishExam = item.configValue * 100
    }
    return acc
  }, { academicExam: 0, daiExam: 0, englishExam: 0 })
}

// mappear los datos antes de enviarlos
const mapOutgoingData = (data) => {
  return {
    academic_weight: data.academicExam / 100,
    dai_weight: data.daiExam / 100,
    english_weight: data.englishExam / 100
  }
}

export const getCurrentPercentages = async () => {
  try {
    const response = await axios.get(getExamPercentagesUrl)
    const data = mapIncomingData(response.data)

    initValues = { ...data }
    return data
  } catch (error) {
    throw new Error('Error al cargar los porcentajes.')
  }
}

const saveExamPercentagesUrl = `${import.meta.env.VITE_API_BASE_URL}${import.meta.env.VITE_UPDATE_EXAM_PERCENTAGES_ENDPOINT}`
export const updateExamPercentages = async (formValues, setFormValues, setLoading, setSuccessMessage, setErrorMessage) => {
  setLoading(true)

  try {
    const dataToSend = mapOutgoingData(formValues)
    const response = await axios.put(`${saveExamPercentagesUrl}?${new URLSearchParams(dataToSend)}`)
    setSuccessMessage('Porcentajes actualizados correctamente.')

    const data = mapIncomingData(response.data)
    initValues = { ...data }
    setFormValues(data)
  } catch (error) {
    setErrorMessage(getErrorMessage(error))
  } finally {
    setLoading(false)
  }
}
