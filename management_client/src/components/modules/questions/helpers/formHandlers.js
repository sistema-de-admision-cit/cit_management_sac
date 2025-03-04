import axios from '../../../../config/axiosConfig'
import { validateFields } from './helpers'

export const handleChange = (e, setQuestionData, isFile = false) => {
  const { name, value, files } = e.target

  if (isFile && files) {
    const fileArray = Array.from(files) // Convierte los archivos a un array
    setQuestionData(prevState => ({
      ...prevState,
      [name]: fileArray
    }))
  } else {
    // Manejar campos de texto
    setQuestionData(prevState => ({
      ...prevState,
      [name]: value
    }))
  }
}

export const handleTestOptionChange = (e, questionData, setQuestionData) => {
  const { name, value } = e.target
  console.log('handleTestOptionChange', name, value)

  // If you want to reset certain things only when `questionType` changes,
  // you can conditionally handle that here. For example:
  if (name === 'questionType') {
    setQuestionData(prevState => ({
      ...prevState,
      questionType: value,
      correctOption: ''
    }))
  } else if (name === 'questionGrade') {
    setQuestionData(prevState => ({
      ...prevState,
      questionGrade: value
    }))
  }
}

export const handleOptionChange = (index, value, questionData, setQuestionData) => {
  console.log('handleOptionChange', index, value)
  const newOptions = [...questionData.questionOptionsText]
  newOptions[index] = value
  setQuestionData({
    ...questionData,
    questionOptionsTextds: newOptions
  })
}

export const clearForm = (setQuestionData) => {
  setQuestionData({
    questionType: '',
    questionText: '',
    images: [],
    questionOptionsText: ['', '', '', ''],
    correctOption: ''
  })
}

const createQuestionUrl = import.meta.env.VITE_CREATE_QUESTION_ENDPOINT
export const handleCreateQuestionSubmit = (e, questionData, setErrorMessage, setSuccessMessage, setIsLoading, setQuestionData) => {
  e.preventDefault()
  setErrorMessage('')
  setSuccessMessage('')
  validateFields(questionData, setErrorMessage)

  // Crea un nuevo objeto FormData
  const formData = new FormData()

  const questionOptions = questionData.questionOptionsText.map((option, index) => ({
    isCorrect: index === questionData.correctOption,
    option
  }))

  const questionDto = {
    questionType: questionData.examType,
    questionText: questionData.question,
    imageUrl: questionData.imageUrl,
    questionGrade: questionData.questionGrade || 'FIFTH', // update hardcoded value
    selectionType: questionData.selectionType || 'SINGLE', // update hardcoded value
    deleted: false,
    questionOptions
  }

  formData.append('question', new Blob([JSON.stringify(questionDto)], { type: 'application/json' }))

  if (questionData.file) {
    formData.append('file', questionData.file)
  }

  setIsLoading(true)

  axios.post(
    createQuestionUrl,
    formData,
    {
      headers: { 'Content-Type': 'multipart/form-data' },
      timeout: 10000
    }
  ).then(response => {
    console.log(response)
    setIsLoading(false)
    setSuccessMessage('Pregunta guardada exitosamente')
    clearForm(setQuestionData)
  }).catch(error => {
    console.error(error)
    setErrorMessage('Ocurrió un error al guardar la pregunta')
    setIsLoading(false)
  })
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

/**
 *
 * @param {string} query - looking for
 * @param {function} setQuestions - setter function for the questions
 * @param {string} searchExamType - exam type to search (both, dai or academic)
 * @param {string} setSearchCode - setter function for the search code
*/
const searchQuestionByTitleUrl = import.meta.env.VITE_SEARCH_QUESTIONS_ENDPOINT
export const handleSearch = (query, setQuestions, searchExamType, setSearchCode) => {
  setSearchCode('')

  const searchParams = new URLSearchParams()
  searchParams.append('questionText', query)

  const url = `${searchQuestionByTitleUrl}?${searchParams.toString()}`

  axios.get(url)
    .then(response => {
      const questions = response.data.content
      console.log(questions)
      setQuestions(questions)
    })
    .catch(error => {
      console.error(error)
    })
}

const searchQuestionByCodeUrl = import.meta.env.VITE_SEARCH_QUESTION_BY_CODE_ENDPOINT
export const handleSearchByCode = (e, setQuery, setSearchCode, setQuestions, searchExamType) => {
  e.preventDefault()
  setQuery('')

  const searchParams = new URLSearchParams()
  searchParams.append('code', e.target.value)
  searchParams.append('examType', searchExamType)

  const searchCode = e.target.value
  setSearchCode(searchCode)

  const url = `${searchQuestionByCodeUrl}?${searchParams.toString()}`
  axios.get(url)
    .then(response => {
      const questions = response.data
      console.log(questions)
      setQuestions(questions)
    })
    .catch(error => {
      console.error(error)
    })
}
