/**
 * Function to format a date to YYYY-MM-DD
 * @param {string} date
 * @returns {string} date in format YYYY-MM-DD
 */
export const formatDate = (dateString) => {
  if (!dateString) {
    return 'Parece que no hay fecha'
  }

  const date = new Date(dateString)

  const day = String(date.getDate()).padStart(2, '0')
  const month = String(date.getMonth() + 1).padStart(2, '0') // Meses son 0-11
  const year = date.getFullYear()
  return `${day}/${month}/${year}` // Formato: DD/MM/AAAA
}
