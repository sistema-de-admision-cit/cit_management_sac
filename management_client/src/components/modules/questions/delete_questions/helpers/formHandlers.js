import axios from '../../../../../config/axiosConfig'

const deleteQuestionUrl = import.meta.env.VITE_DELETE_QUESTION_ENDPOINT
export const handleDeleteFromList = (code, questions, setQuestions, setErrorMessage, setSuccessMessage) => {
  setErrorMessage('')
  setSuccessMessage('')

  axios.delete(`${deleteQuestionUrl}/${code}`).then(response => {
    console.log(response)
    const updatedQuestions = questions.filter(question => question.code !== code)
    setQuestions(updatedQuestions)
    setSuccessMessage('La pregunta fue eliminada exitosamente.')
  }
  ).catch(error => {
    console.error(error)
    setErrorMessage('Hubo un error al eliminar la pregunta. Por favor, intenta de nuevo.')
  })
}

const getAllQuestionsUrl = import.meta.env.VITE_GET_ALL_QUESTIONS_ENDPOINT
export const handleGetAllQuestions = (setQuestions, setLoading, setErrorMessage) => {
  setLoading(true)

  axios.get(getAllQuestionsUrl).then(response => {
    console.log(response)
    setQuestions(response.data)
    setLoading(false)
  }).catch(error => {
    console.error(error)
    setLoading(false)
    setErrorMessage('Hubo un error al cargar las preguntas. Por favor, intenta de nuevo.')
  })
}
