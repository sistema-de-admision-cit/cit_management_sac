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
  setQuestionData({
    ...questionData,
    examType: e.target.value,
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
    question: '',
    images: [],
    options: ['', '', '', ''],
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

  // Agregar los campos de texto
  formData.append('examType', questionData.examType)
  formData.append('question', questionData.question)
  formData.append('options', JSON.stringify(questionData.options))
  formData.append('correctOption', questionData.correctOption)

  // Agregar los archivos (imágenes)
  if (questionData.images && questionData.images.length) {
    for (const image of questionData.images) {
      formData.append('images', image)
    }
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
    clearForm(setQuestionData) // Limpia el formulario si es necesario
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
  searchParams.append('query', query)
  searchParams.append('examType', searchExamType)

  const url = `${searchQuestionByTitleUrl}?${searchParams.toString()}`

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
