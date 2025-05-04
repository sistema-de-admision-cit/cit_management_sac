export const formatDate = (dateString) => {
  if (!dateString) {
    return 'Parece que no hay fecha'
  }

  const [year, month, day] = dateString.split('T')[0].split('-')
  return `${day}/${month}/${year}`
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
  }

  return gradeMap[grade] || grade
}

export const formatRecommendation = (recommendation) => {
  const recommendationMap = {
    ADMIT: 'Admitir',
    REJECT: 'Rechazar'
  }

  return recommendationMap[recommendation] || recommendation
}

export const formatStatus = (status) => {
  const statusMap = {
    ELIGIBLE: 'Permitido',
    ACCEPTED: 'Aceptado',
    REJECTED: 'Rechazado'
  }

  return statusMap[status] || status
}
