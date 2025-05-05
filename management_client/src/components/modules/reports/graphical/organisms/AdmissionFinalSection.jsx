import React, { useCallback } from 'react'
import { useChartData } from '../hooks/useChartData'
import { fetchAdmissionFinal } from '../helpers/handlers'
import Spinner from '../../../../core/global/atoms/Spinner'
import ErrorMessage from '../atoms/ErrorMessage'
import ChartTitle from '../atoms/ChartTitle'
import FiltersPanel from '../molecules/helpers/FiltersPanel'
import AddmissionFinalChart from '../molecules/charts/AddmissionFinalChart'

const AdmissionFinalSection = ({ startDate, endDate, grade, sector, setStartDate, setEndDate, setGrade, setSector, sectorOptions, gradeOptions }) => {
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
      <AddmissionFinalChart data={data} />
    </div>
  )
}

export default React.memo(AdmissionFinalSection)
