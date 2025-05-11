import React, { useCallback } from 'react'
import { useChartData } from '../hooks/useChartData'
import Spinner from '../../../../core/global/atoms/Spinner'
import ErrorMessage from '../atoms/ErrorMessage'
import ChartTitle from '../atoms/ChartTitle'
import FunnelTrendChart from '../molecules/charts/FunnelTrendChart'
import { fetchFunnelTrend } from '../helpers/handlers'

/**
 * Section showing the daily admission funnel trend.
 * @param {Object} props
 * @param {Date} props.startDate - Filter start date
 * @param {Date} props.endDate - Filter end date
 * @param {string} props.grade - Selected grade or 'All'
 * @param {string} props.sector - Selected sector or 'All'
 */
const FunnelTrendSection = ({ startDate, endDate, grade, sector }) => {
  const fmt = d => d.toISOString().slice(0, 10)
  const fetcher = useCallback(
    () =>
      fetchFunnelTrend(
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
    <div className='funnel-trend-section'>
      <ChartTitle>Tendencia del Embudo de Admisión</ChartTitle>
      <FunnelTrendChart data={data} />
    </div>
  )
}

export default React.memo(FunnelTrendSection)
