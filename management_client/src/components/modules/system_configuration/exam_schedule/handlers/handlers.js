export const handleSubmit = (formValues) => {
  const startDate = formValues.startDate.toISOString().split('T')[0]
  const endDate = formValues.endDate.toISOString().split('T')[0]

  const sendingData = {
    allYear: formValues.allYear,
    startDate,
    endDate,
    applicationDays: formValues.applicationDays,
    startTime: formValues.startTime,
    endTime: formValues.endTime
  }

  console.log(sendingData)
  // TODO: Send data to the server (when the endpoint is ready)
}

export const onStartDateChange = (date, formValues, setErrorMessage, handleChange) => {
  if (date > formValues.endDate) {
    setErrorMessage('La fecha inicial no puede ser mayor a la fecha final')
    return
  }

  handleChange('startDate', date)
}

export const onEndDateChange = (date, formValues, setErrorMessage, handleChange) => {
  if (date < formValues.startDate) {
    setErrorMessage('La fecha final no puede ser menor a la fecha inicial')
    return
  }

  handleChange('endDate', date)
}
