import React, { useState, useMemo, useCallback, useEffect } from 'react'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import Spinner from '../../../../core/global/atoms/Spinner'
import ErrorMessage from '../atoms/ErrorMessage'
import ChartTitle from '../atoms/ChartTitle'
import { useChartsData } from '../hooks/useChartsData'
import { fetchExamSource, fetchExamAttendance } from '../helpers/handlers'
import ExamPieChart from '../molecules/ExamPieChart'
import AttendanceChart from '../molecules/AttendanceChart'
import InputField from '../../../../core/global/atoms/InputField'

function GraphicalReportsView () {
  const [startDate, setStartDate] = useState(new Date('2025-04-01'))
  const [endDate, setEndDate] = useState(new Date('2025-04-30'))
  const [grade, setGrade] = useState('All')
  const [sector, setSector] = useState('All')

  // Define grade lists by sector
  const primariaGrades = ['Primero', 'Segundo', 'Tercero', 'Cuarto', 'Quinto', 'Sexto']
  const secundariaGrades = ['Septimo', 'Octavo', 'Noveno', 'Décimo']

  // Dynamically build grade options based on selected sector
  const gradeOptions = useMemo(() => {
    if (sector === 'Primaria') {
      return [
        { value: 'All', label: 'All' },
        ...primariaGrades.map(g => ({ value: g, label: g }))
      ]
    }
    if (sector === 'Secundaria') {
      return [
        { value: 'All', label: 'All' },
        ...secundariaGrades.map(g => ({ value: g, label: g }))
      ]
    }
    // For 'All' sector, show all available grades
    const allGrades = Array.from(new Set([...primariaGrades, ...secundariaGrades]))
    return [
      { value: 'All', label: 'All' },
      ...allGrades.map(g => ({ value: g, label: g }))
    ]
  }, [sector])

  // Options for sector dropdown
  const sectorOptions = [
    { value: 'All', label: 'All' },
    { value: 'Primaria', label: 'Primaria' },
    { value: 'Secundaria', label: 'Secundaria' }
  ]

  // Reset grade to 'All' whenever sector changes
  useEffect(() => {
    setGrade('All')
  }, [sector])

  // Format Date to 'YYYY-MM-DD'
  const formatDate = date => date?.toISOString().split('T')[0]

  // Stable fetcher for attendance data
  const attendanceFetcher = useCallback(
    () =>
      fetchExamAttendance(
        formatDate(startDate),
        formatDate(endDate),
        // For API, send CSV if not 'All'
        grade === 'All' ? '' : grade,
        sector === 'All' ? '' : sector
      ),
    [startDate, endDate, grade, sector]
  )

  // Chart data configurations
  const configs = useMemo(
    () => [
      { key: 'examSource', fetcher: fetchExamSource },
      { key: 'examAttendance', fetcher: attendanceFetcher }
    ],
    [attendanceFetcher]
  )

  const { data, isLoading, error } = useChartsData(configs)

  return (
    <SectionLayout title='Reportes Gráficos'>
      <div className='graphical-reports-container container space-y-6'>
        <ChartTitle>Reportes Gráficos</ChartTitle>

        {isLoading && <Spinner />}

        {Object.keys(error).length > 0 &&
          Object.entries(error).map(([key, msg]) => (
            <ErrorMessage key={key} message={`${key}: ${msg}`} />
          ))}

        {!isLoading && !error.examSource && (
          <ExamPieChart
            data={data.examSource || []}
            title='Students by Exam Source'
          />
        )}

        {!isLoading && !error.examAttendance && (
          <div className='attendance-chart-container'>
            <div className='filters flex space-x-4'>
              <InputField
                field={{ name: 'startDate', label: 'Start Date', placeholder: 'Select start date', type: 'date', required: true }}
                value={startDate}
                handleChange={date => setStartDate(date)}
                className='w-full'
                showLabel={false}
              />

              <InputField
                field={{ name: 'endDate', label: 'End Date', placeholder: 'Select end date', type: 'date', required: true }}
                value={endDate}
                handleChange={date => setEndDate(date)}
                className='w-full'
                showLabel={false}
              />

              <InputField
                field={{ name: 'sector', label: 'Sector', type: 'dropdown', required: true, options: sectorOptions }}
                value={sector}
                handleChange={e => setSector(e.target.value)}
                className='w-full'
                showLabel={false}
              />

              <InputField
                field={{ name: 'grade', label: 'Grade', type: 'dropdown', required: true, options: gradeOptions }}
                value={grade}
                handleChange={e => setGrade(e.target.value)}
                className='w-full'
                showLabel={false}
              />
            </div>

            <AttendanceChart
              data={data.examAttendance || []}
              title='Exam Attendance'
            />
          </div>
        )}
      </div>
    </SectionLayout>
  )
}

export default GraphicalReportsView
