import React, { useState, useMemo, useCallback, useEffect } from 'react'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import Spinner from '../../../../core/global/atoms/Spinner'
import ErrorMessage from '../atoms/ErrorMessage'
import ChartTitle from '../atoms/ChartTitle'
import { useChartData } from '../hooks/useChartData'
import { fetchExamSource, fetchExamAttendance } from '../helpers/handlers'
import ExamPieChart from '../molecules/ExamPieChart'
import AttendanceChart from '../molecules/AttendanceChart'
import InputField from '../../../../core/global/atoms/InputField'

function GraphicalReportsView () {
  // — filter state —
  const [startDate, setStartDate] = useState(new Date('2025-04-01'))
  const [endDate, setEndDate] = useState(new Date('2025-04-30'))
  const [grade, setGrade] = useState('All')
  const [sector, setSector] = useState('All')

  // — dropdown options —
  const primariaGrades = ['Primero', 'Segundo', 'Tercero', 'Cuarto', 'Quinto', 'Sexto']
  const secundariaGrades = ['Septimo', 'Octavo', 'Noveno', 'Décimo']
  const sectorOptions = [
    { value: 'All', label: 'All' },
    { value: 'Primaria', label: 'Primaria' },
    { value: 'Secundaria', label: 'Secundaria' }
  ]
  const gradeOptions = useMemo(() => {
    if (sector === 'Primaria') { return [{ value: 'All', label: 'All' }, ...primariaGrades.map(g => ({ value: g, label: g }))] }
    if (sector === 'Secundaria') { return [{ value: 'All', label: 'All' }, ...secundariaGrades.map(g => ({ value: g, label: g }))] }
    const all = Array.from(new Set([...primariaGrades, ...secundariaGrades]))
    return [{ value: 'All', label: 'All' }, ...all.map(g => ({ value: g, label: g }))]
  }, [sector])

  // Reset grade when sector changes
  useEffect(() => { setGrade('All') }, [sector])

  // Format dates for API
  const formatDate = d => d?.toISOString().split('T')[0]

  // Stable fetcher for attendance, changes only when filters change
  const attendanceFetcher = useCallback(
    () =>
      fetchExamAttendance(
        formatDate(startDate),
        formatDate(endDate),
        grade === 'All' ? '' : grade,
        sector === 'All' ? '' : sector
      ),
    [startDate, endDate, grade, sector]
  )

  // — 1) load exam-source once on mount
  const {
    data: sourceData,
    isLoading: loadingSource,
    error: errorSource
  } = useChartData(fetchExamSource, []) // empty deps = run only once

  // — 2) load attendance whenever deps change
  const {
    data: attendanceData,
    isLoading: loadingAttendance,
    error: errorAttendance
  } = useChartData(attendanceFetcher, [attendanceFetcher])

  return (
    <SectionLayout title='Reportes Gráficos'>
      <div className='graphical-reports-container container space-y-6'>
        <ChartTitle>Reportes Gráficos</ChartTitle>

        {/* Global spinner if either one is loading */}
        {(loadingSource || loadingAttendance) && <Spinner />}

        {/* Errors */}
        {[
          ['Source', errorSource],
          ['Attendance', errorAttendance]
        ].map(([key, msg]) =>
          msg && <ErrorMessage key={key} message={`${key}: ${msg}`} />
        )}

        {/* Pie only depends on sourceData */}
        {!loadingSource && !errorSource && (
          <ExamPieChart
            data={sourceData}
            title='Students by Exam Source'
          />
        )}

        {/* Attendance filters + bar chart */}
        {!loadingAttendance && !errorAttendance && (
          <div className='attendance-chart-container'>
            <div className='filters flex space-x-4'>
              <InputField
                field={{ name: 'startDate', type: 'date' }}
                value={startDate}
                handleChange={setStartDate}
                className='w-full'
                showLabel={false}
              />
              <InputField
                field={{ name: 'endDate', type: 'date' }}
                value={endDate}
                handleChange={setEndDate}
                className='w-full'
                showLabel={false}
              />
              <InputField
                field={{ name: 'sector', type: 'dropdown', options: sectorOptions }}
                value={sector}
                handleChange={e => setSector(e.target.value)}
                className='w-full'
                showLabel={false}
              />
              <InputField
                field={{ name: 'grade', type: 'dropdown', options: gradeOptions }}
                value={grade}
                handleChange={e => setGrade(e.target.value)}
                className='w-full'
                showLabel={false}
              />
            </div>
            <AttendanceChart
              data={attendanceData}
              title='Exam Attendance'
            />
          </div>
        )}
      </div>
    </SectionLayout>
  )
}

export default GraphicalReportsView
