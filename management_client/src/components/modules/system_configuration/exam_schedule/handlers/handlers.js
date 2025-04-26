import axios from '../../../../../config/axiosConfig'

const mapDays = (days) => {
  const daysMap = {
    Lunes: 'M',
    Martes: 'K',
    Miércoles: 'W',
    Jueves: 'T',
    Viernes: 'F',
    Sábado: 'S'
  }

  return days.map(day => daysMap[day])
}

const saveExamScheduleUrl = import.meta.env.VITE_SAVE_EXAM_SCHEDULE_ENDPOINT

const possibleErrors = {
  startDate: 'La fecha inicial no puede ser mayor a la fecha final',
  endDate: 'La fecha final no puede ser menor a la fecha inicial',
  applicationDays: 'Debes seleccionar al menos un día de aplicación',
  startTime: 'Debes seleccionar una hora de aplicación',
  default: 'Ocurrió un error al guardar la configuración'
}

export const handleSubmit = (formValues, setLoading, setErrorMessage, setSuccessMessage) => {
  const getFirstAndLastDayOfYear = () => {
    const year = new Date().getFullYear()
    return {
      firstDay: `${year}-01-01`,
      lastDay: `${year}-12-31`
    }
  }

  const { firstDay, lastDay } = getFirstAndLastDayOfYear()

  const startDate = formValues.allYear ? firstDay : formValues.startDate.toISOString().split('T')[0]
  const endDate = formValues.allYear ? lastDay : formValues.endDate.toISOString().split('T')[0]

  const sendingData = {
    allYear: formValues.allYear,
    startDate,
    endDate,
    examDays: mapDays(formValues.applicationDays).map(day => ({
      examDay: day,
      startTime: formValues.startTime
    }))
  }
  // TODO: add jwt token to the request (when implemented)
  setLoading(true)
  axios.post(
    saveExamScheduleUrl,
    sendingData
  ).then(response => {
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


export const handleGetAllExamPeriods = (setExamPeriods, setLoading, setErrorMessage) => {
  const getAllExamPeriodsUrl = import.meta.env.VITE_GET_CURRENT_EXAM_PERIODS_ENDPOINT

  setLoading(true)
  axios.get(getAllExamPeriodsUrl, { timeout: 10000 })
    .then(response => {
      if (response.data.length === 0) {
        setErrorMessage('No se encontraron periodos de examen para este año.')
        return
      }
      const periods = response.data?.map(period => ({
        id: period.id,
        startDate: new Date(period.startDate),
        endDate: new Date(period.endDate),
        days: period.examDays.map(day => day.examDay)
      }));
      setExamPeriods(periods)
    })
    .catch(error => {
      console.error(error)
      setErrorMessage('No se pudieron obtener los periodos de examen para este año.')
    })
    .finally(() => {
      setLoading(false)
    })
}