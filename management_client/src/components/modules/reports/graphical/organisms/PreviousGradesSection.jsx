import React, { useCallback } from 'react'
import { useChartData } from '../hooks/useChartData'
import { fetchPreviousGradesStatus } from '../helpers/handlers'
import Spinner from '../../../../core/global/atoms/Spinner'
import ErrorMessage from '../atoms/ErrorMessage'
import ChartTitle from '../atoms/ChartTitle'
import PreviousGradesChart from '../molecules/charts/PreviousGradesChart'

/**
 * Section displaying previous grades analysis charts (grade distribution).
 * @param {Object} props
 * @param {Date} props.startDate - Filter start date
 * @param {Date} props.endDate - Filter end date
 * @param {string} props.grade - Selected grade or 'All'
 * @param {string} props.sector - Selected sector or 'All'
 */
const PreviousGradesSection = ({ startDate, endDate, grade, sector }) => {
  const fmt = d => d.toISOString().slice(0, 10)
  const fetcher = useCallback(
    () => fetchPreviousGradesStatus(
      fmt(startDate),
      fmt(endDate),
      grade === 'All' ? 'All' : grade,
      sector === 'All' ? 'All' : sector
    ),
    [startDate, endDate, grade, sector]
  )
  const { data, isLoading, error } = useChartData(fetcher, [fetcher])

  if (isLoading) return <Spinner />
  if (error) return <ErrorMessage message={error} />

  return (
    <div className='previous-grades-section'>
      <ChartTitle>
        Distribución de Calificaciones Anteriores por Estado de Postulación
      </ChartTitle>

      <PreviousGradesChart data={data} />
    </div>
  )
}

export default React.memo(PreviousGradesSection)
