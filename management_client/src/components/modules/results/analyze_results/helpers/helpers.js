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

export const formatAcademicGrade = (grade) => {
  const gradeMap = {
    FIRST: 'Primero',
    SECOND: 'Segundo',
    THIRD: 'Tercero',
    FOURTH: 'Cuarto',
    FIFTH: 'Quinto',
    SIXTH: 'Sexto',
    SEVENTH: 'Séptimo',
    EIGHTH: 'Octavo',
    NINTH: 'Noveno',
    TENTH: 'Décimo'
  };

  return gradeMap[grade] || grade;
};

export const formatRecommendation = (recommendation) => {
  const recommendationMap = {
    ADMIT: 'Admitir',
    REJECT: 'Rechazar',
  };

  return recommendationMap[recommendation] || recommendation;
};

export const formatStatus = (status) => {
  const statusMap = {
    PENDING: 'Pendiente',
    ACCEPTED: 'Aceptado',
    REJECTED: 'Rechazado',
  };

  return statusMap[status] || status;
}