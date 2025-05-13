import axios from '../../../../../config/axiosConfig'

import { format } from 'date-fns'

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
  invalidDate: 'La fecha proporcionada no es válida',
  default: 'Ocurrió un error al guardar la configuración'
}

export const handleSubmit = async (formValues, setLoading, setErrorMessage, setSuccessMessage) => {
  try {
    setLoading(true)
    setErrorMessage(null)

    const getFirstAndLastDayOfYear = () => {
      const year = new Date().getFullYear()
      return {
        firstDay: `${year}-01-01`,
        lastDay: `${year}-12-31`
      }
    }

    const { firstDay, lastDay } = getFirstAndLastDayOfYear()

    if (!formValues.allYear) {
      if (!(formValues.startDate instanceof Date) || isNaN(formValues.startDate.getTime())) {
        throw new Error(possibleErrors.invalidDate)
      }
      if (!(formValues.endDate instanceof Date) || isNaN(formValues.endDate.getTime())) {
        throw new Error(possibleErrors.invalidDate)
      }
    }

    const formatDate = (date) => format(date, 'yyyy-MM-dd')

    const startDate = formValues.allYear ? firstDay : formatDate(formValues.startDate)
    const endDate = formValues.allYear ? lastDay : formatDate(formValues.endDate)

    if (!isFormValid(formValues)) {
      throw new Error(possibleErrors.default)
    }

    const sendingData = {
      allYear: formValues.allYear,
      startDate,
      endDate,
      examDays: mapDays(formValues.applicationDays).map(day => ({
        examDay: day,
        startTime: formValues.startTime
      }))
    }

    const response = await axios.post(saveExamScheduleUrl, sendingData)
    setSuccessMessage('Configuración guardada correctamente')
    setTimeout(() => {
      window.location.reload()
    }, 2000)

    return response.data
  } catch (error) {
    console.error('Error en handleSubmit:', error)
    const errorMessage = error.response?.data?.message ||
      possibleErrors[error.message] ||
      error.message ||
      possibleErrors.default
    setErrorMessage(errorMessage)
    throw error
  } finally {
    setLoading(false)
  }
}

export const onStartDateChange = (date, formValues, setErrorMessage, handleChange) => {
  try {
    if (!date) {
      throw new Error('invalidDate')
    }

    const newDate = new Date(date)
    if (isNaN(newDate.getTime())) {
      throw new Error('invalidDate')
    }

    if (formValues.endDate && newDate > new Date(formValues.endDate)) {
      throw new Error('startDate')
    }

    handleChange('startDate', newDate)
    setErrorMessage(null)
  } catch (error) {
    setErrorMessage(possibleErrors[error.message] || possibleErrors.default)
  }
}

export const onEndDateChange = (date, formValues, setErrorMessage, handleChange) => {
  try {
    if (!date) {
      throw new Error('invalidDate')
    }

    const newDate = new Date(date)
    if (isNaN(newDate.getTime())) {
      throw new Error('invalidDate')
    }

    if (formValues.startDate && newDate < new Date(formValues.startDate)) {
      throw new Error('endDate')
    }

    handleChange('endDate', newDate)
    setErrorMessage(null)
  } catch (error) {
    setErrorMessage(possibleErrors[error.message] || possibleErrors.default)
  }
}

export const isFormValid = (formValues) => {
  try {
    // Validar fechas si no es "todo el año"
    if (!formValues.allYear) {
      const startDateValid = formValues.startDate instanceof Date && !isNaN(formValues.startDate.getTime())
      const endDateValid = formValues.endDate instanceof Date && !isNaN(formValues.endDate.getTime())

      if (!startDateValid || !endDateValid) {
        return false
      }

      if (formValues.startDate > formValues.endDate) {
        return false
      }
    }

    // Validar días y hora
    if (formValues.applicationDays.length === 0 || !formValues.startTime) {
      return false
    }

    return true
  } catch (error) {
    console.error('Error en isFormValid:', error)
    return false
  }
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

export const handleGetAllExamPeriods = (setExamPeriods, setLoading, setErrorMessage, setSuccessMessage) => {
  const getAllExamPeriodsUrl = import.meta.env.VITE_GET_CURRENT_EXAM_PERIODS_ENDPOINT

  setLoading(true)
  axios.get(getAllExamPeriodsUrl, { timeout: 10000 })
    .then(response => {
      if (response.data.length === 0) {
        setSuccessMessage('No se encontraron periodos de examen para este año.')
        return
      }
      const periods = response.data?.map(period => ({
        id: period.id,
        startDate: adjustDateFromBackend(period.startDate),
        endDate: adjustDateFromBackend(period.endDate),
        days: period.examDays.map(day => day.examDay)
      }))
      setExamPeriods(periods)
    })
    .catch(error => {
      console.error(error)
      setSuccessMessage('No se pudieron obtener los periodos de examen para este año.')
    })
    .finally(() => {
      setLoading(false)
    })
}

const adjustDateFromBackend = (dateString) => {
  const date = new Date(dateString)
  date.setMinutes(date.getMinutes() + date.getTimezoneOffset())
  return date
}

export const onDeleteSelectedItems = (selectedItems, setSelectedItems, setLoading, setErrorMessage) => {
  const deleteExamPeriodsUrl = import.meta.env.VITE_DELETE_EXAM_PERIOD_ENDPOINT
  const deleteErrors = []
  const deleteSuccess = []
  setLoading(true)

  selectedItems.map(item => axios.delete(`${deleteExamPeriodsUrl}/${item}`)
    .then(response => {
      deleteSuccess.push(item)
    })
    .catch(error => {
      deleteErrors.push({
        perid: item.startDate.toISOString() + ' -  ' + item.endDate.toISOString()
      })
    })
    .finally(() => {
      if (deleteErrors.length > 0) {
        setErrorMessage(`No se pudieron eliminar los siguientes periodos de examen: \n${deleteErrors.map(item => item.period)}\n`)
      }
      setSelectedItems(
        selectedItems.filter(item => !deleteSuccess.includes(item))
      )
      setLoading(false)
    })
  )
}
