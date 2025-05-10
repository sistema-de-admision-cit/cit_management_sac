import React from 'react'
import {
  ResponsiveContainer,
  ScatterChart,
  Scatter,
  XAxis,
  YAxis,
  ZAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  LabelList
} from 'recharts'

const COLORS = {
  interested: '#8884d8',
  eligible: '#82ca9d',
  accepted: '#ffc658'
}

const LeadSourceMatrixChart = ({ data = [] }) => {
  // ensure it's always an array
  const chartData = Array.isArray(data) ? data : []

  return (
    <ResponsiveContainer width='100%' height={400}>
      <ScatterChart
        margin={{ top: 20, right: 40, bottom: 40, left: 60 }}
      >
        <CartesianGrid strokeDasharray='3 3' />

        {/* X‐axis: number of applicants */}
        <XAxis
          type='number'
          dataKey='studentCount'
          name='Applicantes'
          domain={[0, 'dataMax + 2']}
          tickCount={6}
          label={{
            value: '# Aplicantes',
            position: 'bottom',
            offset: 0
          }}
        />

        {/* Y‐axis: acceptance rate % */}
        <YAxis
          type='number'
          dataKey='acceptanceRate'
          name='Tasa de Aceptación'
          unit='%'
          domain={[0, 'dataMax + 10']}
          tickCount={6}
          tickFormatter={t => `${t}%`}
          label={{
            value: 'Tasa de Aceptación',
            angle: -90,
            position: 'insideLeft',
            offset: 0
          }}
        />

        {/* Z‐axis: bubble size mapped to avgExamScore */}
        <ZAxis
          type='number'
          dataKey='avgExamScore'
          name='Puntaje Promedio'
          range={[100, 100]}
        />

        <Tooltip cursor={{ strokeDasharray: '3 3' }} />

        {/* place legend on top so it doesn't obscure points */}
        <Legend verticalAlign='top' height={36} />

        <Scatter
          name='Channels'
          data={chartData}
          fill={COLORS.interested}
          opacity={0.7}
        >
          <LabelList
            dataKey='examSource'
            position='right'
            style={{ fontSize: 12, fill: '#333' }}
          />
        </Scatter>
      </ScatterChart>
    </ResponsiveContainer>
  )
}

export default React.memo(LeadSourceMatrixChart)
