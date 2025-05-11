// src/components/modules/reports/graphical/molecules/charts/FunnelTrendChart.jsx
import React from 'react'
import {
  ResponsiveContainer,
  LineChart,
  Line,
  CartesianGrid,
  XAxis,
  YAxis,
  Tooltip,
  Legend
} from 'recharts'

const COLORS = {
  interested: '#8884d8',
  eligible: '#82ca9d',
  accepted: '#ffc658'
}

/**
 * FunnelTrendChart component displays a line chart for the admission funnel trend.
 * It shows the number of interested, eligible, and accepted candidates over time.
 * @param {Object} props - Component props
 * @param {Array} props.data - Data for the chart, should contain enrollmentDate, interestedCount, eligibleCount, and acceptedCount.
 * @returns
 */
const FunnelTrendChart = ({ data }) => (
  <ResponsiveContainer width='100%' height={300}>
    <LineChart data={data} margin={{ top: 20, right: 30, left: 0, bottom: 5 }}>
      <CartesianGrid strokeDasharray='3 3' />
      <XAxis dataKey='enrollmentDate' />
      <YAxis />
      <Tooltip />
      <Legend verticalAlign='top' />
      <Line
        type='monotone'
        dataKey='interestedCount'
        name='Interesados'
        stroke={COLORS.interested}
        dot={{ r: 4 }}
      />
      <Line
        type='monotone'
        dataKey='eligibleCount'
        name='Elegibles'
        stroke={COLORS.eligible}
        dot={{ r: 4 }}
      />
      <Line
        type='monotone'
        dataKey='acceptedCount'
        name='Aceptados'
        stroke={COLORS.accepted}
        dot={{ r: 4 }}
      />
    </LineChart>
  </ResponsiveContainer>
)

export default React.memo(FunnelTrendChart)
