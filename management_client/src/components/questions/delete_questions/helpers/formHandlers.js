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
