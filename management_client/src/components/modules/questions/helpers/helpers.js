import {
  ERROR_SELECT_EXAM_TYPE,
  ERROR_ENTER_QUESTION,
  ERROR_ENTER_OPTION,
  ERROR_SELECT_CORRECT_OPTION,
  NUMBER_OF_OPTIONS,
  UNIQUE_QUESTION_TYPE
} from './questionConstants'

export const validateFields = (questionData, setErrorMessage) => {
  if (!questionData.questionType) {
    setErrorMessage(ERROR_SELECT_EXAM_TYPE)
    return
  }
  if (!questionData.questionText) {
    setErrorMessage(ERROR_ENTER_QUESTION)
    return
  }
  // Check for unique question type (e.g., 'ACA') to validate options and correct answer
  if (questionData.questionType === UNIQUE_QUESTION_TYPE) {
    for (let i = 0; i < NUMBER_OF_OPTIONS; i++) {
      if (!questionData.questionOptionsText[i]) {
        setErrorMessage(ERROR_ENTER_OPTION(i + 1))
        return
      }
    }
    if (questionData.correctOption === '') {
      setErrorMessage(ERROR_SELECT_CORRECT_OPTION)
    }
  }
}

export const getButtonState = (questionData, isLoading) => {
  // Deshabilitar el botón si se está cargando
  if (isLoading) {
    return true
  }

  // Deshabilitar si campos esenciales están vacíos
  if (!questionData.questionType || !questionData.questionText) {
    return true
  }

  // Si el tipo de pregunta es único, validar opciones y respuesta correcta
  if (questionData.questionType === UNIQUE_QUESTION_TYPE) {
    // Verificar que no haya opciones duplicadas
    const optionsSet = new Set(questionData.questionOptionsText)
    if (optionsSet.size !== NUMBER_OF_OPTIONS) {
      return true
    }

    // Verificar que todas las opciones estén completas
    for (let i = 0; i < NUMBER_OF_OPTIONS; i++) {
      if (!questionData.questionOptionsText[i]) {
        return true
      }
    }

    // Verificar que se haya seleccionado una respuesta correcta
    if (questionData.correctOption === '') {
      return true
    }
  }

  return false
}
