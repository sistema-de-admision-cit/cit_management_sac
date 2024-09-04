import axios from 'axios'

const mapDays = (days) => {
  const daysMap = {
    Lunes: 'M',
    Martes: 'K',
    Miércoles: 'W',
    Jueves: 'T',
    Viernes: 'F'
  }

  return days.map(day => daysMap[day])
}

const saveExamScheduleUrl = `${import.meta.env.VITE_API_BASE_URL}${import.meta.env.VITE_SAVE_EXAM_SCHEDULE_ENDPOINT}`

const possibleErrors = {
  startDate: 'La fecha inicial no puede ser mayor a la fecha final',
  endDate: 'La fecha final no puede ser menor a la fecha inicial',
  applicationDays: 'Debes seleccionar al menos un día de aplicación',
  startTime: 'Debes seleccionar una hora de aplicación',
  default: 'Ocurrió un error al guardar la configuración'
}

export const handleSubmit = (formValues, setLoading, setErrorMessage, setSuccessMessage) => {
  const startDate = formValues.startDate.toISOString().split('T')[0]
  const endDate = formValues.endDate.toISOString().split('T')[0]

  const sendingData = {
    allYear: formValues.allYear,
    startDate,
    endDate,
    applicationDays: mapDays(formValues.applicationDays),
    startTime: formValues.startTime
  }

  console.log(sendingData)
  console.log('Sending data to the server...')
  console.log('Endpoint:', saveExamScheduleUrl)
  // TODO: add jwt token to the request (when implemented)
  setLoading(true)
  axios.post(
    saveExamScheduleUrl,
    sendingData
  ).then(response => {
    console.log(response)
    setSuccessMessage('Configuración guardada correctamente')
  }).catch(error => {
    console.error(error)
    // TODO: define the error message based on the error response
    const errorMessage = possibleErrors.default
    setErrorMessage(errorMessage)
  }).finally(() => {
    setLoading(false)
  })
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

export const isFormValid = (formValues) => {
  return ((formValues.startDate && formValues.endDate) || formValues.allYear) && formValues.applicationDays.length > 0 && formValues.startTime
}

export const handleCheckboxChange = (day, setFormValues) => {
  setFormValues((prevValues) => {
    const newDays = prevValues.applicationDays.includes(day)
      ? prevValues.applicationDays.filter(d => d !== day)
      : [...prevValues.applicationDays, day]
    return {
      ...prevValues,
      applicationDays: newDays
    }
  })
}
