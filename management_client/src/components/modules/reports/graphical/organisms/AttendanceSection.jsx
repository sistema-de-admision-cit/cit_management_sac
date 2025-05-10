import React, { useCallback } from 'react'
import { useChartData } from '../hooks/useChartData'
import { fetchExamAttendance } from '../helpers/handlers'
import Spinner from '../../../../core/global/atoms/Spinner'
import ErrorMessage from '../atoms/ErrorMessage'
import ChartTitle from '../atoms/ChartTitle'
import AttendanceChart from '../molecules/charts/AttendanceChart'

/**
 * Section displaying a stacked bar chart for exam attendance.
 * @param {Object} props
 * @param {Date} props.startDate - Filter start date
 * @param {Date} props.endDate - Filter end date
 * @param {string} props.grade - Selected grade or 'All'
 * @param {string} props.sector - Selected sector or 'All'
 */
const AttendanceSection = ({ startDate, endDate, grade, sector }) => {
  const fmt = d => d?.toISOString().split('T')[0]
  const fetcher = useCallback(
    () => fetchExamAttendance(fmt(startDate), fmt(endDate), grade === 'All' ? 'All' : grade, sector === 'All' ? 'All' : sector),
    [startDate, endDate, grade, sector]
  )
  const { data, isLoading, error } = useChartData(fetcher, [fetcher])

  if (isLoading) return <Spinner />
  if (error) return <ErrorMessage message={error} />

  return (
    <div className='attendance-chart-container'>
      <ChartTitle>Exam Attendance</ChartTitle>
      <AttendanceChart data={data} />
    </div>
  )
}

export default React.memo(AttendanceSection)
