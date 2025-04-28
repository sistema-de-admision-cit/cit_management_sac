import React from 'react'
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

const CATEGORICAL_PALETTE = ['#EA801C', '#1A80BB']

/**
 * Stacked-bar chart showing, per date:
 * - No Presentados  (en naranja)
 * - Presentados     (en azul)
 */
const AttendanceChart = ({ data, title }) => {
  // derive “noPresented” so we can stack it under “presentados”
  const chartData = data.map(d => ({
    enrollmentDate: d.enrollmentDate,
    Presentados: d.totalAttended,
    NoPresentados: d.totalEnrolled - d.totalAttended
  }))

  return (
    <div className='attendance-chart'>
      {title && <h3 className='mb-2'>{title}</h3>}
      <ResponsiveContainer width='100%' height={300}>
        <BarChart data={chartData} margin={{ top: 10, right: 20, left: 0, bottom: 5 }}>
          <CartesianGrid strokeDasharray='3 3' />
          <XAxis dataKey='enrollmentDate' />
          <YAxis />
          <Tooltip formatter={(value, name) => [value, name]} />
          <Legend />

          {/* first the no-shows */}
          <Bar
            dataKey='NoPresentados'
            name='No Presentados'
            stackId='a'
            fill={CATEGORICAL_PALETTE[0]}
          />

          {/* then the attended on top */}
          <Bar
            dataKey='Presentados'
            name='Presentados'
            stackId='a'
            fill={CATEGORICAL_PALETTE[1]}
          />
        </BarChart>
      </ResponsiveContainer>
    </div>
  )
}

export default React.memo(AttendanceChart)
