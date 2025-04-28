import React, { useCallback } from 'react'
import { useChartData } from '../hooks/useChartData'
import { fetchExamAttendance } from '../helpers/handlers'
import Spinner from '../../../../core/global/atoms/Spinner'
import ErrorMessage from '../atoms/ErrorMessage'
import ChartTitle from '../atoms/ChartTitle'
import FiltersPanel from '../molecules/helpers/FiltersPanel'
import AttendanceChart from '../molecules/charts/AttendanceChart'

const AttendanceSection = ({
  startDate,
  endDate,
  grade,
  sector,
  setStartDate,
  setEndDate,
  setGrade,
  setSector,
  sectorOptions,
  gradeOptions
}) => {
  const formatDate = d => d?.toISOString().split('T')[0]

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

  const { data, isLoading, error } = useChartData(attendanceFetcher, [attendanceFetcher])

  if (isLoading) return <Spinner />
  if (error) return <ErrorMessage message={error} />

  return (
    <div className='attendance-chart-container'>
      <FiltersPanel
        startDate={startDate}
        endDate={endDate}
        onStartDateChange={setStartDate}
        onEndDateChange={setEndDate}
        sector={sector}
        onSectorChange={e => setSector(e.target.value)}
        grade={grade}
        onGradeChange={e => setGrade(e.target.value)}
        sectorOptions={sectorOptions}
        gradeOptions={gradeOptions}
      />
      <ChartTitle>Exam Attendance</ChartTitle>
      <AttendanceChart data={data} />
    </div>
  )
}

export default React.memo(AttendanceSection)
