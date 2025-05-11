import React from 'react'
import {
  ResponsiveContainer,
  BarChart,
  CartesianGrid,
  XAxis,
  YAxis,
  Tooltip,
  Bar,
  LabelList
} from 'recharts'

const LEVEL_ORDER = ['A1', 'A2', 'B1', 'B2', 'C1', 'C2']

/**
 * Displays a vertical bar chart of CEFR level distribution.
 *
 * @component
 * @param {Object} props
 * @param {{ level: string, count: number }[]} props.data - Array of CEFR levels and applicant counts.
 * @param {string} [props.title] - Optional title to show above the chart.
 * @returns {JSX.Element}
 */

const CefrDistributionChart = ({ data = [], title }) => {
  // sort the incoming data A1 → C2
  const sorted = [...data].sort(
    (a, b) => LEVEL_ORDER.indexOf(a.level) - LEVEL_ORDER.indexOf(b.level)
  )

  return (
    <div className='cefr-distribution-chart'>
      {title && <h3 className='mb-2'>{title}</h3>}
      <ResponsiveContainer width='100%' height={300}>
        <BarChart
          layout='vertical'
          data={sorted}
          margin={{ top: 20, right: 30, left: 80, bottom: 20 }}
        >
          <CartesianGrid strokeDasharray='3 3' />
          {/* X axis: counts */}
          <XAxis
            type='number'
            allowDecimals={false}
            tick={{ fontSize: 12 }}
          />
          {/* Y axis: CEFR levels */}
          <YAxis
            type='category'
            dataKey='level'
            width={60}
            tick={{ fontSize: 12 }}
          />
          <Tooltip
            formatter={(value) => [value, 'Applicants']}
          />
          <Bar dataKey='count' fill='#3f51b5' barSize={20}>
            {/* label at end of each bar */}
            <LabelList
              dataKey='count'
              position='right'
              style={{ fontSize: 12 }}
            />
          </Bar>
        </BarChart>
      </ResponsiveContainer>
    </div>
  )
}

export default React.memo(CefrDistributionChart)
