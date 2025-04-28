import React, { useState, useMemo, useEffect } from 'react'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import ExamSourceSection from '../organisms/ExamSourceSection'
import AttendanceSection from '../organisms/AttendanceSection'
import '../../../../../assets/styles/reports/graphical-report-styles.css'

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

export default function GraphicalReportsView () {
  // Filter state
  const [startDate, setStartDate] = useState(new Date('2025-04-01'))
  const [endDate, setEndDate] = useState(new Date('2025-04-30'))
  const [sector, setSector] = useState('All')
  const [grade, setGrade] = useState('All')

  // Compute grade options based on selected sector
  const gradeOptions = useMemo(() => {
    let options = []

    if (sector === 'Primaria') {
      options = GRADE_MAPPINGS.Primaria
    } else if (sector === 'Secundaria') {
      options = GRADE_MAPPINGS.Secundaria
    } else {
      options = ALL_GRADES
    }

    // Always include the 'All' option at the top
    return [
      { value: 'All', label: 'All' },
      ...options
    ]
  }, [sector])

  // Reset grade to 'All' whenever sector changes
  useEffect(() => {
    setGrade('All')
  }, [sector])

  return (
    <SectionLayout title='Reportes Gráficos'>
      <div className='graphical-reports-container container'>
        <div className='exam-source-section'>
          <ExamSourceSection />
        </div>

        <div className='attendance-section'>
          <AttendanceSection
            startDate={startDate}
            endDate={endDate}
            sector={sector}
            grade={grade}
            setStartDate={setStartDate}
            setEndDate={setEndDate}
            setSector={setSector}
            setGrade={setGrade}
            sectorOptions={SECTOR_OPTIONS}
            gradeOptions={gradeOptions}
          />
        </div>
      </div>
    </SectionLayout>
  )
}
