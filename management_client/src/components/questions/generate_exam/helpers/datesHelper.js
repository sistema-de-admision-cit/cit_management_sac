const availableDays = [2, 5] // Martes y Viernes // TODO: implementar fetch de días disponibles

export const availableDates = (date) => {
  const day = date.getDay()
  return availableDays.includes(day)
}
