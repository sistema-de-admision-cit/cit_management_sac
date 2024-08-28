export const getSaveButtonState = (formValues) => {
  const total = formValues.academicExam + formValues.daiExam + formValues.englishExam
  return total === 100
}

export const handleSave = (formValues) => {
  getSaveButtonState(formValues) ? console.log('Guardado') : console.log('Los porcentajes deben sumar 100')
}
