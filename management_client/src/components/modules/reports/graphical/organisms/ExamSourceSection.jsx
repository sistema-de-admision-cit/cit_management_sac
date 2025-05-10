import React, { useCallback } from 'react'
import { useChartData } from '../hooks/useChartData'
import { fetchExamSource } from '../helpers/handlers'
import Spinner from '../../../../core/global/atoms/Spinner'
import ErrorMessage from '../atoms/ErrorMessage'
import ChartTitle from '../atoms/ChartTitle'
import ExamPieChart from '../molecules/charts/ExamPieChart'

/**
 * Section displaying a pie chart of students by exam source.
 * @param {Object} props
 * @param {Date} props.startDate - Filter start date
 * @param {Date} props.endDate - Filter end date
 * @param {string} props.grade - Selected grade or 'All'
 * @param {string} props.sector - Selected sector or 'All'
 */
const ExamSourceSection = ({ startDate, endDate, grade, sector }) => {
  const fmt = d => d?.toISOString().split('T')[0]
  const fetcher = useCallback(
    () => fetchExamSource(fmt(startDate), fmt(endDate), grade === 'All' ? 'All' : grade, sector === 'All' ? 'All' : sector),
    [startDate, endDate, grade, sector]
  )
  const { data, isLoading, error } = useChartData(fetcher, [fetcher])

  if (isLoading) return <Spinner />
  if (error) return <ErrorMessage message={error} />

  return (
    <div className='exam-source-chart-container'>
      <ChartTitle>Origen de los Estudiantes según Canal de Referencia</ChartTitle>
      <ExamPieChart data={data} />
    </div>
  )
}

export default React.memo(ExamSourceSection)
