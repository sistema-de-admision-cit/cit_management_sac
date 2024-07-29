// para validar los campos del formulario
import { dummyData } from './dummyData'

const validateFields = (questionData, setErrorMessage) => {
  if (!questionData.examType) {
    setErrorMessage('Por favor, seleccione el tipo de examen.')
    return
  }
  if (!questionData.questionType) {
    setErrorMessage('Por favor, seleccione el tipo de pregunta.')
    return
  }
  if (!questionData.question) {
    setErrorMessage('Por favor, ingrese la pregunta.')
    return
  }
  if (questionData.questionType === 'unique') {
    for (let i = 0; i < 4; i++) {
      if (!questionData.options[i]) {
        setErrorMessage(`Por favor, ingrese la opción ${i + 1}.`)
        return
      }
    }
    if (questionData.correctOption === '') {
      setErrorMessage('Por favor, seleccione la respuesta correcta.')
    }
  }
}

export const handleChange = (e, questionData, setQuestionData) => {
  const { name, value } = e.target
  setQuestionData({
    ...questionData,
    [name]: value
  })
}

export const handleTestOptionChange = (e, questionData, setQuestionData) => {
  setQuestionData({
    ...questionData,
    examType: e.target.value,
    questionType: '',
    question: questionData.question,
    options: questionData.options,
    correctOption: ''
  })
}

export const handleOptionChange = (index, value, questionData, setQuestionData) => {
  const newOptions = [...questionData.options]
  newOptions[index] = value
  setQuestionData({
    ...questionData,
    options: newOptions
  })
}

export const clearForm = (setQuestionData) => {
  setQuestionData({
    examType: '',
    questionType: '',
    question: '',
    options: ['', '', '', ''],
    correctOption: ''
  })
}

export const handleSubmit = (e, questionData, setErrorMessage, setSuccessMessage, setIsLoading, setQuestionData) => {
  e.preventDefault()
  setErrorMessage('')
  setSuccessMessage('')

  // Validar los campos del formulario
  validateFields(questionData, setErrorMessage)

  setIsLoading(true)

  // hacer el papel de enviar los datos al servidor (TODO: reemplazar por la API)
  setTimeout(() => {
    console.log(questionData)
    setIsLoading(false)
    setSuccessMessage('Pregunta guardada exitosamente')
    setQuestionData({
      examType: '',
      questionType: '',
      question: '',
      options: ['', '', '', ''],
      correctOption: ''
    })
  }, 1000)
}

export const getButtonState = (questionData, isLoading) => {
  // Si se está cargando, deshabilitar el botón
  if (isLoading) {
    return true
  }

  // si alguno de los campos está vacío, deshabilitar el botón
  if (!questionData.examType || !questionData.questionType || !questionData.question) {
    return true
  }

  // si es seleccion unica
  if (questionData.questionType === 'unique') {
    // crear un set para verificar que no haya opciones repetidas
    const set = new Set(questionData.options)
    if (set.size !== 4) {
      return true
    }

    // verificar que no haya campos vacíos
    for (let i = 0; i < 4; i++) {
      if (!questionData.options[i]) {
        return true
      }
    }

    // verificar que la respuesta correcta no esté vacía
    if (questionData.correctOption === '') {
      return true
    }
  }

  return false
}

// modif
export const handleModifySubmit = (e, questionData, setErrorMessage, setSuccessMessage, setIsLoading) => {
  e.preventDefault()
  setErrorMessage('')
  setSuccessMessage('')

  // Validar los campos del formulario
  validateFields(questionData, setErrorMessage)

  setIsLoading(true)

  // Hacer la llamada para modificar los datos en el servidor (TODO: reemplazar por la API)
  setTimeout(() => {
    console.log(questionData)
    setIsLoading(false)
    setSuccessMessage('Pregunta modificada exitosamente')
  }, 1000)
}

const mockFetchQuestions = (query, dummyData) => {
  return dummyData.filter(item => item.question.toLowerCase().includes(query.toLowerCase()))
}

export const handleModifyForSearch = (query, setErrorMessage, setSuggestions) => {
  // Simular una llamada a la API
  const suggestions = mockFetchQuestions(query, dummyData)
  setSuggestions(suggestions)
  console.log(suggestions)
}

export const handleDeleteForSearch = (query, onResultsUpdate) => {
  const filteredQuestions = mockFetchQuestions(query, dummyData)
  onResultsUpdate(filteredQuestions)
}
