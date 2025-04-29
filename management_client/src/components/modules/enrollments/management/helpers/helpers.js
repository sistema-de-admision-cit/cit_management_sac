export const statusText = {
  PENDING: 'Inscrito',
  ELIGIBLE: 'Permitido',
  INELIGIBLE: 'Inelegible',
  ACCEPTED: 'Aceptado',
  REJECTED: 'Rechazado'
}

export const buildGuardianAddress = (guardianAddress) => {
  const direction = `${guardianAddress.province}, ${guardianAddress.city}, ${guardianAddress.district}, ${guardianAddress.addressInfo}`
  return direction
}

export const guardianTabText = {
  M: 'Informacion de la Madre',
  F: 'Informacion del Padre',
  G: 'Informacion del Encargado'
}

export const statusOptions = [
  { value: 'PENDING', label: 'Inscrito' },
  { value: 'ELIGIBLE', label: 'Permitido' },
  { value: 'INELIGIBLE', label: 'Inelegible' },
  { value: 'ACCEPTED', label: 'Aceptado' },
  { value: 'REJECTED', label: 'Rechazado' }
]

export const statusOptionsForEnrollment = [
  { value: 'PENDING', label: 'Inscrito' },
  { value: 'ELIGIBLE', label: 'Permitido' },
  { value: 'INELIGIBLE', label: 'Inelegible' },
]

export const isCommentRequired = (formData, enrollment) => {
  if (formData.status !== enrollment.status || formData.examDate !== enrollment.examDate) {
    return true
  }
}

export const isEnabled = (formData, enrollment) => {
  if (formData.status === enrollment.status &&
    formData.examDate === enrollment.examDate &&
    formData.whatsappNotification === enrollment.whatsappNotification) {
    return false
  }

  if (isCommentRequired(formData, enrollment) && formData.comment === '') {
    return false
  }
  return true
}

// formtDates: parse the dates in the object to Date objects
export const formatDateToObj = (obj) => {
  if (obj.birthDate) {
    obj.birthDate = new Date(obj.birthDate)
  }

  // if the object has enrollments, parse the examDate to Date objects
  if (obj.enrollments && Array.isArray(obj.enrollments)) {
    obj.enrollments.forEach(enrollment => {
      if (enrollment.examDate) {
        enrollment.examDate = new Date(enrollment.examDate)
      }
    })
  }

  return obj
}

// parse the object dates to "day/month/year" format
export const formatDate = (date) => {
  let day = date.getUTCDate()
  let month = date.getUTCMonth() + 1
  const year = date.getUTCFullYear()

  day = day < 10 ? '0' + day : day
  month = month < 10 ? '0' + month : month

  return `${day}/${month}/${year}`
}

// parse the date to "year-month-day" format
export const formatDateForApi = (date) => {
  console.log('date', date)

  let day = date.getUTCDate()
  let month = date.getUTCMonth() + 1
  const year = date.getUTCFullYear()

  day = day < 10 ? '0' + day : day
  month = month < 10 ? '0' + month : month

  console.log('date', `${year}-${month}-${day}`)

  return `${year}-${month}-${day}`
}
