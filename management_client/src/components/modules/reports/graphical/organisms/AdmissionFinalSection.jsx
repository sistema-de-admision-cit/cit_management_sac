import React, { useCallback } from 'react'
import { useChartData } from '../hooks/useChartData'
import { fetchAdmissionFinal } from '../helpers/handlers'
import Spinner from '../../../../core/global/atoms/Spinner'
import ErrorMessage from '../atoms/ErrorMessage'
import ChartTitle from '../atoms/ChartTitle'
import AddmissionFinalChart from '../molecules/charts/AddmissionFinalChart'

/**
 * Section displaying a stacked bar chart for final admissions (accepted/rejected).
 * @param {Object} props
 * @param {Date} props.startDate - Filter start date
 * @param {Date} props.endDate - Filter end date
 * @param {string} props.grade - Selected grade or 'All'
 * @param {string} props.sector - Selected sector or 'All'
 */
const AdmissionFinalSection = ({ startDate, endDate, grade, sector }) => {
  const fmt = d => d?.toISOString().split('T')[0]
  const fetcher = useCallback(
    () => fetchAdmissionFinal(fmt(startDate), fmt(endDate), grade === 'All' ? 'All' : grade, sector === 'All' ? 'All' : sector),
    [startDate, endDate, grade, sector]
  )
  const { data, isLoading, error } = useChartData(fetcher, [fetcher])

  if (isLoading) return <Spinner />
  if (error) return <ErrorMessage message={error} />

  return (
    <div className='admission-final-chart-container'>
      <ChartTitle>Admisión Final</ChartTitle>
      <AddmissionFinalChart data={data} />
    </div>
  )
}

export default React.memo(AdmissionFinalSection)
