import axios from '../../../../config/axiosConfig'
import { validateFields } from './helpers'
import {
  DEFAULT_QUESTION_GRADE,
  DEFAULT_SELECTION_TYPE,
  DEFAULT_QUESTION_LEVEL,
  DEFAULT_QUESTION_OPTIONS,
  SUCCESS_MESSAGE_CREATE,
  SUCCESS_MESSAGE_MODIFY,
  ERROR_MESSAGE_CREATE,
  ERROR_MESSAGE_MODIFY,
  FORM_TIMEOUT,
  MULTIPART_FORM_DATA,
  BLOB_KEY_QUESTION,
  SEARCH_CODE_PARAM,
  SEARCH_EXAM_TYPE_PARAM
} from './questionConstants'

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

  if (name === 'questionType') {
    setQuestionData(prevState => ({
      ...prevState,
      questionType: value
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
    questionOptionsText: [...DEFAULT_QUESTION_OPTIONS],
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
    questionGrade: questionData.questionGrade || DEFAULT_QUESTION_GRADE,
    selectionType: questionData.selectionType || DEFAULT_SELECTION_TYPE,
    questionLevel: DEFAULT_QUESTION_LEVEL,
    deleted: false,
    questionOptions: questionOptions.length > 0 ? questionOptions : [...DEFAULT_QUESTION_OPTIONS]
  }

  console.log(questionDto)

  formData.append(BLOB_KEY_QUESTION, new Blob([JSON.stringify(questionDto)], { type: 'application/json' }))

  if (questionData.images) {
    console.log('handleCreateQuestionSubmit', questionData.images)
    formData.append('file', questionData.images)
  }

  setIsLoading(true)

  axios.post(
    createQuestionUrl,
    formData,
    {
      headers: { 'Content-Type': MULTIPART_FORM_DATA },
      timeout: FORM_TIMEOUT
    }
  ).then(response => {
    console.log(response)
    setIsLoading(false)
    setSuccessMessage(SUCCESS_MESSAGE_CREATE)
    clearForm(setQuestionData)
  }).catch(error => {
    console.error(error)
    setErrorMessage(ERROR_MESSAGE_CREATE)
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
    questionGrade: questionData.questionGrade || DEFAULT_QUESTION_GRADE,
    selectionType: questionData.selectionType || DEFAULT_SELECTION_TYPE,
    questionLevel: DEFAULT_QUESTION_LEVEL,
    deleted: false,
    questionOptions: questionOptions.length > 0 ? questionOptions : [...DEFAULT_QUESTION_OPTIONS]
  }

  formData.append(BLOB_KEY_QUESTION, new Blob([JSON.stringify(questionDto)], { type: 'application/json' }))

  if (questionData.images) {
    console.log('handleUpdateQuestion', questionData.images)
    formData.append('file', questionData.images, { type: MULTIPART_FORM_DATA })
  }

  axios.post(
    `${modifyQuestionUrl}`,
    formData,
    {
      headers: { 'Content-Type': MULTIPART_FORM_DATA },
      timeout: FORM_TIMEOUT
    }
  ).then(response => {
    console.log(response)
    setIsLoading(false)
    setSuccessMessage(SUCCESS_MESSAGE_MODIFY)
  }).catch(error => {
    console.error(error)
    setErrorMessage(ERROR_MESSAGE_MODIFY)
    setIsLoading(false)
  })
}

const searchQuestionByCodeUrl = import.meta.env.VITE_SEARCH_QUESTION_BY_CODE_ENDPOINT
export const handleSearchByCode = (e, setQuery, setSearchCode, setQuestions, searchExamType) => {
  e.preventDefault()
  setQuery('')

  const searchParams = new URLSearchParams()
  searchParams.append(SEARCH_CODE_PARAM, e.target.value)
  searchParams.append(SEARCH_EXAM_TYPE_PARAM, searchExamType)

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
