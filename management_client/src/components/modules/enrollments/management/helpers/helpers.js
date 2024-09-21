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
