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
    const data = response.data.reduce((acc, item) => {
      if (item.configName === 'dai_weight') {
        acc.daiExam = item.configValue * 100
      } else if (item.configName === 'academic_weight') {
        acc.academicExam = item.configValue * 100
      } else if (item.configName === 'english_weight') {
        acc.englishExam = item.configValue * 100
      }
      return acc
    }, { academicExam: 0, daiExam: 0, englishExam: 0 })

    console.log(data)

    return data
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
