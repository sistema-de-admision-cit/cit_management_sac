import React, { useState, useMemo, useEffect } from 'react'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import ExamSourceSection from '../organisms/ExamSourceSection'
import AttendanceSection from '../organisms/AttendanceSection'

export default function GraphicalReportsView () {
  const [startDate, setStartDate] = useState(new Date('2025-04-01'))
  const [endDate, setEndDate] = useState(new Date('2025-04-30'))
  const [sector, setSector] = useState('All')
  const [grade, setGrade] = useState('All')

  // dropdown options
  const primariaGrades = ['Primero', 'Segundo', 'Tercero', 'Cuarto', 'Quinto', 'Sexto']
  const secundariaGrades = ['Septimo', 'Octavo', 'Noveno', 'Décimo']

  const sectorOptions = useMemo(() => [
    { value: 'All', label: 'All' },
    { value: 'Primaria', label: 'Primaria' },
    { value: 'Secundaria', label: 'Secundaria' }
  ], [])

  const gradeOptions = useMemo(() => {
    const list = sector === 'Primaria'
      ? primariaGrades
      : sector === 'Secundaria'
        ? secundariaGrades
        : [...primariaGrades, ...secundariaGrades]

    return [
      { value: 'All', label: 'All' },
      ...Array.from(new Set(list)).map(g => ({ value: g, label: g }))
    ]
  }, [sector])

  // reset grade on sector change
  useEffect(() => { setGrade('All') }, [sector])

  return (
    <SectionLayout title='Reportes Gráficos'>
      <div className='graphical-reports-container container space-y-6'>
        <ExamSourceSection />
        <AttendanceSection
          startDate={startDate}
          endDate={endDate}
          grade={grade}
          sector={sector}
          setStartDate={setStartDate}
          setEndDate={setEndDate}
          setGrade={setGrade}
          setSector={setSector}
          sectorOptions={sectorOptions}
          gradeOptions={gradeOptions}
        />
      </div>
    </SectionLayout>
  )
}
