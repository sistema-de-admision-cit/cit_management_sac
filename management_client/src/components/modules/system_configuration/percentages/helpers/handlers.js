export const getSaveButtonState = (formValues) => {
  // sum all values in formValues object
  const total = Object.values(formValues).reduce((acc, value) => acc + Number(value), 0)
  return total === 100
}

export const handleSave = (formValues) => {
  getSaveButtonState(formValues) ? console.log('Guardado') : console.log('Los porcentajes deben sumar 100')
}
