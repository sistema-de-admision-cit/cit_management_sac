import axios from '../../../../../config/axiosConfig'

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL
const GET_QUESTIONS_QUANTITY_ENDPOINT = import.meta.env.VITE_GET_QUESTIONS_QUANTITY_ENDPOINT
const UPDATE_QUESTIONS_QUANTITY_ENDPOINT = import.meta.env.VITE_UPDATE_EXAMS_QUESTION_QUANTITY_ENDPOINT

export const getQuestionsQuantity = async () => {
  try {
    const response = await axios.get(`${API_BASE_URL}${GET_QUESTIONS_QUANTITY_ENDPOINT}`)
    return response.data // Devuelve el array completo de configuración
  } catch (error) {
    console.error('Error getting questions quantity:', error)
    throw new Error('No se pudo obtener la configuración de preguntas')
  }
}

export const updateQuestionsQuantity = async (formValues) => {
  try {
    await axios.put(`${API_BASE_URL}${UPDATE_QUESTIONS_QUANTITY_ENDPOINT}`, {
      daiQuestionsQuantity: formValues.daiQuestionsQuantity,
      academicQuestionsQuantity: formValues.academicQuestionsQuantity
    })
  } catch (error) {
    console.error('Error updating questions quantity:', error)
    throw new Error(error.response?.data?.message || 'Error al actualizar las preguntas')
  }
}

export const questionsQuantityHandler = {
  get: getQuestionsQuantity,
  update: updateQuestionsQuantity
}
