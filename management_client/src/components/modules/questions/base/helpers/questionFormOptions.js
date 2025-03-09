export const ACADEMIC_EXAM_TYPE_OPTIONS =
  { value: 'ACA', label: 'Académico' }

export const DAI_EXAM_TYPE_OPTIONS =
  { value: 'DAI', label: 'DAI' }

export const EXAM_GRADE_OPTIONS = [
  { value: 'FIRST', label: 'Primero' },
  { value: 'SECOND', label: 'Segundo' },
  { value: 'THIRD', label: 'Tercero' },
  { value: 'FOURTH', label: 'Cuarto' },
  { value: 'FIFTH', label: 'Quinto' },
  { value: 'SIXTH', label: 'Sexto' },
  { value: 'SEVENTH', label: 'Séptimo' },
  { value: 'EIGHTH', label: 'Octavo' },
  { value: 'NINTH', label: 'Noveno' },
  { value: 'TENTH', label: 'Décimo' }
]

export const mapExamTypeFilter = (userRole) => {
  const examTypeMap = { SYS: 'both', ADMIN: 'both', PSYCHOLOGIST: 'DAI', TEACHER: 'ACA' }
  return examTypeMap[userRole]
}
