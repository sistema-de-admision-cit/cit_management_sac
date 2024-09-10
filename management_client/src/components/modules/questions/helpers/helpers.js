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
