import axios from '../../../../../config/axiosConfig'

const deleteQuestionUrl = import.meta.env.VITE_DELETE_QUESTION_ENDPOINT
export const handleDeleteFromList = (code, setErrorMessage, setSuccessMessage) => {
  setErrorMessage('')
  setSuccessMessage('')

  axios.delete(`${deleteQuestionUrl}/${code}`).then(response => {
    console.log(response)
    setSuccessMessage('La pregunta fue deshabilitada exitosamente.')
  }
  ).catch(error => {
    console.error(error)
    setErrorMessage('Hubo un error al eliminar la pregunta. Por favor, intenta de nuevo.')
  })
}

const getAllQuestionsUrl = import.meta.env.VITE_GET_ALL_QUESTIONS_ENDPOINT
export const handleGetAllQuestions = async (
  page = 0,
  size = 10,
  setQuestions,
  setTotalPages,
  setLoading,
  setErrorMessage
) => {
  try {
    setLoading(true)
    const response = await axios.get(getAllQuestionsUrl, {
      params: { page, size }
    })
    const data = response.data
    setQuestions(data.content)
    setTotalPages(data.totalPages)
  } catch (err) {
    setErrorMessage(err.message)
  } finally {
    setLoading(false)
  }
}
