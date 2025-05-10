import React, { useMemo } from 'react'
import {
  ResponsiveContainer,
  BarChart,
  CartesianGrid,
  XAxis,
  YAxis,
  Tooltip,
  Legend,
  Bar
} from 'recharts'

const DIFF_COLORS = ['#8884d8']
const GRADE_COLORS = ['#82ca9d']

export const AcademicDifficultyChart = ({ distribution }) => {
  // compute avg per difficulty
  const data = useMemo(() => {
    const agg = {}
    distribution?.forEach(({ difficulty, examScore }) => {
      if (!agg[difficulty]) agg[difficulty] = { sum: 0, cnt: 0 }
      agg[difficulty].sum += examScore
      agg[difficulty].cnt++
    })
    return Object.entries(agg).map(([difficulty, { sum, cnt }]) => ({
      difficulty,
      averageScore: +(sum / cnt).toFixed(2)
    }))
  }, [distribution])

  return (
    <div className='academic-difficulty-chart'>
      <h3 className='mb-2'>Promedio por Dificultad</h3>
      <ResponsiveContainer width='100%' height={250}>
        <BarChart data={data}>
          <CartesianGrid strokeDasharray='3 3' />
          <XAxis dataKey='difficulty' />
          <YAxis />
          <Tooltip />
          <Legend />
          <Bar
            dataKey='averageScore'
            name='Promedio'
            fill={DIFF_COLORS[0]}
          />
        </BarChart>
      </ResponsiveContainer>
    </div>
  )
}

export const AcademicGradeAverageChart = ({ gradeAverages }) => (
  <div className='academic-grade-average-chart mt-6'>
    <h3 className='mb-2'>Promedio de Puntajes por Grado</h3>
    <ResponsiveContainer width='100%' height={250}>
      <BarChart data={gradeAverages}>
        <CartesianGrid strokeDasharray='3 3' />
        <XAxis dataKey='grade' />
        <YAxis />
        <Tooltip />
        <Legend />
        <Bar
          dataKey='averageScore'
          name='Promedio'
          fill={GRADE_COLORS[0]}
        />
      </BarChart>
    </ResponsiveContainer>
  </div>
)
