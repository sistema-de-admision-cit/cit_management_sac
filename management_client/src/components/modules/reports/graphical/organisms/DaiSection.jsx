import React, { useCallback } from 'react'
import { useChartData } from '../hooks/useChartData'
import { fetchDaiExam } from '../helpers/handlers'
import Spinner from '../../../../core/global/atoms/Spinner'
import ErrorMessage from '../atoms/ErrorMessage'
import ChartTitle from '../atoms/ChartTitle'
import FiltersPanel from '../molecules/helpers/FiltersPanel'
import DaiRadarChart from '../molecules/charts/DaiRadarChart'

const DaiSection = ({
  startDate,
  endDate,
  grade,
  sector,
  setStartDate,
  setEndDate,
  setGrade,
  setSector,
  sectorOptions,
  gradeOptions
}) => {
  const fmt = d => d?.toISOString().split('T')[0]
  const fetcher = useCallback(
    () =>
      fetchDaiExam(
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
    <div className='dai-section'>
      <ChartTitle>Evaluación del Examen DAI</ChartTitle>
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
      <DaiRadarChart
        details={data.details}
        areaAverages={data.areaAverages}
      />
    </div>
  )
}

export default React.memo(DaiSection)
