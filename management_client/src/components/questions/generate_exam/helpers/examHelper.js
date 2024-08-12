import questionsExample from './dummyData'

// simular el fetch de preguntas
const fetchQuestions = () => {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve(questionsExample)
    }, 1000)
  })
}

export const generateExam = async (setLoading, setError, setExam) => {
  try {
    setLoading(true)
    const allQuestions = await fetchQuestions()
    setLoading(false)

    if (allQuestions.length < 10) {
      setError('Preguntas insuficientes en el banco de preguntas.')
      return
    }

    setError('')
    const shuffledQuestions = allQuestions.sort(() => Math.random() - 0.5)
    const selectedQuestions = shuffledQuestions.slice(0, 10)

    setExam(selectedQuestions)
  } catch (err) {
    setError('Error al generar el examen.')
  }
}
