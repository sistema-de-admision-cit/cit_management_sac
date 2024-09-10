import axios from 'axios'

export const handleDeleteFromList = (code, questions, setQuestions, setErrorMessage, setSuccessMessage) => {
  setErrorMessage('')
  setSuccessMessage('')

  // Aquí iría la llamada a la API para eliminar la pregunta
  setTimeout(() => {
    const updatedQuestions = questions.filter(question => question.code !== code)
    setQuestions(updatedQuestions)
    console.log(`Pregunta con código ${code} eliminada`)
    setSuccessMessage('Pregunta eliminada exitosamente')
  }, 1000)
}

const getAllQuestionsUrl = `${import.meta.env.VITE_API_BASE_URL}${import.meta.env.VITE_GET_ALL_QUESTIONS_ENDPOINT}`
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
