import axios from 'axios'

export const getSaveButtonState = (formValues) => {
  // sum all values in formValues object
  const total = Object.values(formValues).reduce((acc, value) => acc + Number(value), 0)
  return total === 100
}

const getExamPercentagesUrl = `${import.meta.env.VITE_API_BASE_URL}${import.meta.env.VITE_GET_EXAM_PERCENTAGES_ENDPOINT}`
export const getCurrentPercentages = async () => {
  try {
    const response = await axios.get(getExamPercentagesUrl)
    return response.data
  } catch (error) {
    throw new Error('Error al cargar los porcentajes.')
  }
}

const saveExamPercentagesUrl = `${import.meta.env.VITE_API_BASE_URL}${import.meta.env.VITE_SAVE_EXAM_PERCENTAGES_ENDPOINT}`
export const handleSave = async (formValues, setLoading, setSuccessMessage, setErrorMessage) => {
  setLoading(true)
  try {
    await axios.post(saveExamPercentagesUrl, formValues)
    setSuccessMessage('Porcentajes guardados correctamente.')
  } catch (error) {
    setErrorMessage(error.response ? error.response.data.error : 'Error al guardar los porcentajes.')
  } finally {
    setLoading(false)
  }
}
