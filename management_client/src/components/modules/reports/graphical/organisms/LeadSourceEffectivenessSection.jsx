import React, { useCallback } from 'react'
import { useChartData } from '../hooks/useChartData'
import { fetchLeadSourceEffectiveness } from '../helpers/handlers'
import Spinner from '../../../../core/global/atoms/Spinner'
import ErrorMessage from '../atoms/ErrorMessage'
import ChartTitle from '../atoms/ChartTitle'
import LeadSourceMatrixChart from '../molecules/charts/LeadSourceMatrixChart'

/**
 * Section displaying a matrix chart for lead source effectiveness.
 * It shows the number of applicants and acceptance rate by lead source.
 * @param {Object} props
 * @param {Date} props.startDate - Filter start date
 * @param {Date} props.endDate - Filter end date
 * @param {string} props.grade - Selected grade or 'All'
 * @param {string} props.sector - Selected sector or 'All'
 */
const LeadSourceEffectivenessSection = ({ startDate, endDate, grade, sector }) => {
  const fmt = d => d.toISOString().slice(0, 10)
  const fetcher = useCallback(
    () => fetchLeadSourceEffectiveness(
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
    <div className='lead-source-matrix-section'>
      {/* Lead-Source Effectiveness */}
      <ChartTitle>
        Desempeño de los Canales de Referencia
        <span className='chart__subtitle'>
          (Aplicantes vs Tasa de Aceptación por Canal)
        </span>
      </ChartTitle>
      <LeadSourceMatrixChart data={data} />
    </div>
  )
}

export default React.memo(LeadSourceEffectivenessSection)
