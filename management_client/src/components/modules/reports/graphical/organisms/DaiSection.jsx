import React, { useCallback } from 'react'
import { useChartData } from '../hooks/useChartData'
import { fetchDaiExam } from '../helpers/handlers'
import Spinner from '../../../../core/global/atoms/Spinner'
import ErrorMessage from '../atoms/ErrorMessage'
import ChartTitle from '../atoms/ChartTitle'

/**
 * Section displaying DAI exam analysis charts ().
 * @param {Object} props
 * @param {Date} props.startDate - Filter start date
 * @param {Date} props.endDate - Filter end date
 * @param {string} props.grade - Selected grade or 'All'
 * @param {string} props.sector - Selected sector or 'All'
 */
const DaiSection = ({ startDate, endDate, grade, sector }) => {
  const fmt = d => d?.toISOString().split('T')[0]
  const fetcher = useCallback(
    () => fetchDaiExam(fmt(startDate), fmt(endDate), grade === 'All' ? 'All' : grade, sector === 'All' ? 'All' : sector),
    [startDate, endDate, grade, sector]
  )
  const { data, isLoading, error } = useChartData(fetcher, [fetcher])

  if (isLoading) return <Spinner />
  if (error) return <ErrorMessage message={error} />

  return (
    <div className='dai-section'>
      <ChartTitle>Evaluación del Examen DAI</ChartTitle>
      <div className='chart-container'>
        <div className='chart'>
          <h2>Gráfica de Resultados</h2>
          <pre>{JSON.stringify(data, null, 2)}</pre>
        </div>
      </div>
    </div>
  )
}

export default React.memo(DaiSection)
