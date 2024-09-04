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

export const handleSubmit = (formValues) => {
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
  // TODO: add user feedback (setSuccessMessage, setErrorMessage)
  axios.post(
    saveExamScheduleUrl,
    sendingData
  ).then(response => {
    console.log('Response:', response)
  }).catch(error => {
    console.error('Error:', error)
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
