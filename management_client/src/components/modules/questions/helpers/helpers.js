export const validateFields = (questionData, setErrorMessage) => {
  if (!questionData.examType) {
    setErrorMessage('Por favor, seleccione el tipo de examen.')
    return
  }
  if (!questionData.question) {
    setErrorMessage('Por favor, ingrese la pregunta.')
    return
  }
  if (questionData.examType === 'academic') {
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

export const getButtonState = (questionData, isLoading) => {
  // Si se está cargando, deshabilitar el botón
  if (isLoading) {
    return true
  }

  // si alguno de los campos está vacío, deshabilitar el botón
  if (!questionData.examType || !questionData.question) {
    return true
  }

  // si es seleccion unica
  if (questionData.examType === 'academic') {
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
