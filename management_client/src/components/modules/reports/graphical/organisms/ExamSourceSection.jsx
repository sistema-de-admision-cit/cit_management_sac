import React from 'react'
import { useChartData } from '../hooks/useChartData'
import { fetchExamSource } from '../helpers/handlers'
import Spinner from '../../../../core/global/atoms/Spinner'
import ErrorMessage from '../atoms/ErrorMessage'
import ChartTitle from '../atoms/ChartTitle'
import ExamPieChart from '../molecules/charts/ExamPieChart'

const ExamSourceSection = () => {
  const { data, isLoading, error } = useChartData(fetchExamSource, [])

  if (isLoading) return <Spinner />
  if (error) return <ErrorMessage message={error} />

  return (
    <>
      <ChartTitle>Students by Exam Source</ChartTitle>
      <ExamPieChart data={data} />
    </>
  )
}

export default React.memo(ExamSourceSection)
