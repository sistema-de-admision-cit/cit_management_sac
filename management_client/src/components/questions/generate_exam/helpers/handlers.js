import questionsExample from './dummyData'

// simular el fetch de preguntas
const fetchQuestions = () => {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve(questionsExample)
    }, 1000)
  })
}

export const generateExam = async (setLoading, setErrorMessage, setExam, examDate) => {
  if (!setLoading || !setErrorMessage || !setExam) {
    return
  }

  if (!examDate) {
    setErrorMessage('Por favor selecciona una fecha para el examen')
    return
  }

  try {
    setLoading(true)
    const allQuestions = await fetchQuestions()
    setLoading(false)

    if (allQuestions.length < 10) {
      setErrorMessage('Preguntas insuficientes en el banco de preguntas.')
      return
    }

    setErrorMessage('')
    const shuffledQuestions = allQuestions.sort(() => Math.random() - 0.5)
    const selectedQuestions = shuffledQuestions.slice(0, 10)

    setExam(selectedQuestions)
    console.log('Examen generado:', selectedQuestions)
  } catch (err) {
    setErrorMessage('Error al generar el examen.')
  }
}

export const saveExamHandler = (exam, examDate, setSuccess, setErrorMessage, setLoading, setExam) => {
  const examToSave = {
    questionIds: exam.map((q) => q.questionId),
    date: examDate
  }

  // simular el guardado del examen
  setLoading(true)
  setTimeout(() => {
    console.log('Examen guardado:', examToSave)
    setLoading(false)
    setSuccess('Examen guardado exitosamente.')
    // limpiar el examen generado
    setExam([])
    setErrorMessage('')
  }, 1000)
}

export const discardExamHandler = (setExam, setExamDate) => {
  setExam([])
  setExamDate(null)
}
