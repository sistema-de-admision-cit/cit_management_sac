// src/components/modules/reports/graphical/organisms/CefrDistributionSection.jsx
import React, { useCallback } from 'react'
import { useChartData } from '../hooks/useChartData'
import { fetchCefrDistribution } from '../helpers/handlers' // you'll need to add this
import Spinner from '../../../../core/global/atoms/Spinner'
import ErrorMessage from '../atoms/ErrorMessage'
import ChartTitle from '../atoms/ChartTitle'
import CefrDistributionChart from '../molecules/charts/CefrDistributionChart'

const CefrDistributionSection = ({ startDate, endDate, grade, sector }) => {
  const fmt = d => d?.toISOString().slice(0, 10)
  const fetcher = useCallback(
    () => fetchCefrDistribution(
      fmt(startDate), fmt(endDate),
      grade === 'All' ? 'All' : grade,
      sector === 'All' ? 'All' : sector
    ),
    [startDate, endDate, grade, sector]
  )
  const { data, isLoading, error } = useChartData(fetcher, [fetcher])

  if (isLoading) return <Spinner />
  if (error) return <ErrorMessage message={error} />

  return (
    <div className='cefr-distribution-section'>
      {/* CEFR Level Distribution (Accepted) */}
      <ChartTitle>Distribución de Niveles CEFR (Aceptados)</ChartTitle>
      <CefrDistributionChart data={data} />
    </div>
  )
}

export default React.memo(CefrDistributionSection)
