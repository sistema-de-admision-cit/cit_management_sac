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

const CATEGORICAL_PALETTE = ['#4CAF50', '#F44336'] // green for accepted, red for rejected

/**
 * Displays a stacked bar chart of admission outcomes over time.
 * Each bar shows the number of rejected and accepted students per enrollment date.
 *
 * @component
 * @param {Object} props
 * @param {{ enrollmentDate: string, totalAccepted: number, totalRejected: number }[]} props.data - Raw admission data.
 * @param {string} [props.title] - Optional title displayed above the chart.
 * @returns {JSX.Element}
 */

const AdmissionFinalChart = ({ data, title }) => {
  const chartData = data.map(d => ({
    enrollmentDate: d.enrollmentDate,
    Rechazados: d.totalRejected,
    Admitidos: d.totalAccepted
  }))

  return (
    <div className='admission-final-chart'>
      {title && <h3 className='mb-2'>{title}</h3>}
      <ResponsiveContainer width='100%' height={300}>
        <BarChart data={chartData} margin={{ top: 10, right: 20, left: 0, bottom: 5 }}>
          <CartesianGrid strokeDasharray='3 3' />
          <XAxis dataKey='enrollmentDate' />
          <YAxis />
          <Tooltip formatter={(value, name) => [value, name]} />
          <Legend />

          {/* first the rejected */}
          <Bar
            dataKey='Rechazados'
            name='Rechazados'
            stackId='a'
            fill={CATEGORICAL_PALETTE[0]}
          />

          {/* then the accepted on top */}
          <Bar
            dataKey='Admitidos'
            name='Admitidos'
            stackId='a'
            fill={CATEGORICAL_PALETTE[1]}
          />
        </BarChart>
      </ResponsiveContainer>
    </div>
  )
}

export default React.memo(AdmissionFinalChart)
