import { useEffect, useMemo, useState } from 'react'

// Map of grade enums to their display labels
const GRADE_MAPPINGS = {
  Primaria: [
    { value: 'FIRST', label: 'Primero' },
    { value: 'SECOND', label: 'Segundo' },
    { value: 'THIRD', label: 'Tercero' },
    { value: 'FOURTH', label: 'Cuarto' },
    { value: 'FIFTH', label: 'Quinto' },
    { value: 'SIXTH', label: 'Sexto' }
  ],
  Secundaria: [
    { value: 'SEVENTH', label: 'Séptimo' },
    { value: 'EIGHTH', label: 'Octavo' },
    { value: 'NINTH', label: 'Noveno' },
    { value: 'TENTH', label: 'Décimo' }
  ]
}

// Flattened list of all grades
const ALL_GRADES = [...GRADE_MAPPINGS.Primaria, ...GRADE_MAPPINGS.Secundaria]

// Sector dropdown options
const SECTOR_OPTIONS = [
  { value: 'All', label: 'All' },
  { value: 'Primaria', label: 'Primaria' },
  { value: 'Secundaria', label: 'Secundaria' }
]

// Default dates
const DEFAULT_START = new Date(new Date().setFullYear(new Date().getFullYear() - 1))
const DEFAULT_END = new Date()

/**
 * Custom hook to manage section filters for graphical reports.
 * It provides state and handlers for start date, end date, sector, and grade.
 * @returns {Object} - An object containing the state and handlers for the section filters.
 */
export const useSectionFilters = () => {
  const [startDate, setStartDate] = useState(DEFAULT_START)
  const [endDate, setEndDate] = useState(DEFAULT_END)
  const [sector, setSector] = useState('All')
  const [grade, setGrade] = useState('All')

  // Compute grade options based on selected sector
  const gradeOptions = useMemo(() => {
    const opts =
      sector === 'Primaria'
        ? GRADE_MAPPINGS.Primaria
        : sector === 'Secundaria'
          ? GRADE_MAPPINGS.Secundaria
          : ALL_GRADES
    return [{ value: 'All', label: 'All' }, ...opts]
  }, [sector])

  // Reset grade to 'All' whenever sector changes
  useEffect(() => { setGrade('All') }, [sector])

  return {
    startDate,
    endDate,
    sector,
    grade,
    setStartDate,
    setEndDate,
    setSector,
    setGrade,
    sectorOptions: SECTOR_OPTIONS,
    gradeOptions
  }
}
