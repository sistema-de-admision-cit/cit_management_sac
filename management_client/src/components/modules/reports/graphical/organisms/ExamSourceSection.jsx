import React, { useCallback } from 'react'
import { useChartData } from '../hooks/useChartData'
import { fetchExamSource } from '../helpers/handlers'
import Spinner from '../../../../core/global/atoms/Spinner'
import ErrorMessage from '../atoms/ErrorMessage'
import ChartTitle from '../atoms/ChartTitle'
import FiltersPanel from '../molecules/helpers/FiltersPanel'
import ExamPieChart from '../molecules/charts/ExamPieChart'

const ExamSourceSection = ({
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
  const formatDate = d => d?.toISOString().split('T')[0]

  const examSourceFetcher = useCallback(
    () =>
      fetchExamSource(
        formatDate(startDate),
        formatDate(endDate),
        grade === 'All' ? '' : grade,
        sector === 'All' ? '' : sector
      ),
    [startDate, endDate, grade, sector]
  )

  const { data, isLoading, error } = useChartData(examSourceFetcher, [examSourceFetcher])

  if (isLoading) return <Spinner />
  if (error) return <ErrorMessage message={error} />

  return (
    <div className='exam-source-chart-container'>
      <ChartTitle>Students by Exam Source</ChartTitle>
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
      <ExamPieChart data={data} />
    </div>
  )
}

export default React.memo(ExamSourceSection)
