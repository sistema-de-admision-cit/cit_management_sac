import React, { useCallback } from 'react'
import { useChartData } from '../hooks/useChartData'
import { fetchAcademicExam } from '../helpers/handlers'
import Spinner from '../../../../core/global/atoms/Spinner'
import ErrorMessage from '../atoms/ErrorMessage'
import ChartTitle from '../atoms/ChartTitle'
import {
  AcademicGradeAverageChart
} from '../molecules/charts/AcademicCharts'

/**
 * Section displaying academic exam analysis charts (grade averages).
 * @param {Object} props
 * @param {Date} props.startDate - Filter start date
 * @param {Date} props.endDate - Filter end date
 * @param {string} props.grade - Selected grade or 'All'
 * @param {string} props.sector - Selected sector or 'All'
 */
const AcademicSection = ({ startDate, endDate, grade, sector }) => {
  const fmt = d => d?.toISOString().split('T')[0]
  const fetcher = useCallback(
    () => fetchAcademicExam(fmt(startDate), fmt(endDate), grade === 'All' ? 'All' : grade, sector === 'All' ? 'All' : sector),
    [startDate, endDate, grade, sector]
  )
  const { data, isLoading, error } = useChartData(fetcher, [fetcher])

  if (isLoading) return <Spinner />
  if (error) return <ErrorMessage message={error} />

  const { gradeAverages } = data

  return (
    <div className='academic-section'>
      <ChartTitle>Análisis del Examen Académico</ChartTitle>
      <AcademicGradeAverageChart gradeAverages={gradeAverages} />
    </div>
  )
}

export default React.memo(AcademicSection)
