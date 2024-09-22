export const statusText = {
  P: 'Inscrito',
  E: 'Permitido',
  I: 'Inelegible',
  A: 'Aceptado',
  R: 'Rechazado'
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
  { value: 'P', label: 'Inscrito' },
  { value: 'E', label: 'Permitido' },
  { value: 'I', label: 'Inelegible' },
  { value: 'A', label: 'Aceptado' },
  { value: 'R', label: 'Rechazado' }
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
