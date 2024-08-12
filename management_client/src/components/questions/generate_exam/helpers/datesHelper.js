const availableDays = [2, 5] // Martes y Viernes // TODO: implementar fetch de días disponibles

export const availableDates = (date) => {
  const day = date.getDay()
  return availableDays.includes(day)
}

export const getNearestAvailableDate = (date) => {
  let newDate = new Date(date)
  while (!availableDates(newDate)) {
    newDate = new Date(newDate.setDate(newDate.getDate() + 1))
  }
  return newDate
}
