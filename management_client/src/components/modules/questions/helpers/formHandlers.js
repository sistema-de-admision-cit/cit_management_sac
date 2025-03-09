import axios from '../../../../config/axiosConfig'
import { validateFields } from './helpers'

export const handleChange = (e, setQuestionData, isFile = false) => {
  const { name, value, files } = e.target

  if (isFile && files && files[0]) {
    setQuestionData(prevState => ({
      ...prevState,
      [name]: files[0]
    }))
  } else {
    // Manejar campos de texto
    setQuestionData(prevState => ({
      ...prevState,
      [name]: value
    }))
  }
}

export const handleTestOptionChange = (e, _questionData, setQuestionData) => {
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
    questionOptionsText: newOptions
  })
}

export const clearForm = (setQuestionData) => {
  setQuestionData({
    questionType: '',
    questionText: '',
    file: null,
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

  console.log(questionData)

  // Crear un nuevo objeto FormData
  const formData = new FormData()

  const questionOptions = questionData.questionOptionsText.map((option, index) => ({
    isCorrect: index === questionData.correctOption,
    option
  }))

  const questionDto = {
    questionType: questionData.questionType,
    questionText: questionData.questionText,
    questionGrade: questionData.questionGrade || 'FIFTH', // remove magic numbers or make this fields optional
    selectionType: questionData.selectionType || 'SINGLE', // remove magic numbers or make this fields optional
    questionLevel: 'MEDIUM', // remove magic numbers or make this fields optional
    deleted: false,
    questionOptions: questionOptions || ['', '', '', ''] // remove magic numbers or make this fields optional
  }

  console.log(questionDto)

  formData.append('question', new Blob([JSON.stringify(questionDto)], { type: 'application/json' }))

  if (questionData.images) {
    console.log('handleCreateQuestionSubmit', questionData.images)
    formData.append('file', questionData.images)
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

const modifyQuestionUrl = import.meta.env.VITE_UPDATE_QUESTION_ENDPOINT
export const handleModifySubmit = (e, questionData, setErrorMessage, setSuccessMessage, setIsLoading) => {
  e.preventDefault()
  setErrorMessage('')
  setSuccessMessage('')

  // Validar los campos del formulario
  validateFields(questionData, setErrorMessage)

  setIsLoading(true)

  const questionOptions = questionData.questionOptionsText.map((option, index) => ({
    isCorrect: index === questionData.correctOption,
    option
  }))

  const formData = new FormData()

  const questionDto = {
    id: questionData.id,
    questionType: questionData.questionType,
    questionText: questionData.questionText,
    questionGrade: questionData.questionGrade || 'FIFTH', // remove magic numbers or make this fields optional
    selectionType: questionData.selectionType || 'SINGLE', // remove magic numbers or make this fields optional
    questionLevel: 'MEDIUM', // remove magic numbers or make this fields optional
    deleted: false,
    questionOptions: questionOptions || ['', '', '', ''] // remove magic numbers or make this fields optional
  }

  formData.append('question', new Blob([JSON.stringify(questionDto)], { type: 'application/json' }))

  if (questionData.images) {
    console.log('handleUpdateQuestion', questionData.images)
    formData.append('file', questionData.images, { type: 'multipart/form-data' })
  }

  axios.post(
    `${modifyQuestionUrl}`,
    formData,
    {
      headers: { 'Content-Type': 'multipart/form-data' },
      timeout: 10000
    }
  ).then(response => {
    console.log(response)
    setIsLoading(false)
    setSuccessMessage('Pregunta modificada exitosamente')
  }).catch(error => {
    console.error(error)
    setErrorMessage('Ocurrió un error al modificar la pregunta')
    setIsLoading(false)
  })
}

/**
 *
 * @param {string} query - looking for
 * @param {function} setQuestions - setter function for the questions
 * @param {string} searchExamType - exam type to search (both, dai or academic)
 * @param {string} setSearchCode - setter function for the search code
*/
const searchQuestionByTitleUrl = import.meta.env.VITE_SEARCH_QUESTIONS_ENDPOINT
export const handleSearchPaginated = (
  query,
  setQuestions,
  totalPages,
  setLoading,
  searchExamType,
  page = 0,
  pageSize = 10
) => {
  console.log('handleSearchPaginated', query, searchExamType, page, pageSize)
  const searchParams = new URLSearchParams()
  searchParams.append('questionText', query)
  searchParams.append('page', page)
  searchParams.append('size', pageSize)

  const url = `${searchQuestionByTitleUrl}?${searchParams.toString()}`

  axios.get(url)
    .then(response => {
      const questions = response.data.content
      setQuestions(questions)
      totalPages(response.data.totalPages)
      setLoading(false)
    })
    .catch(error => {
      console.error(error)
      setLoading(false)
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
