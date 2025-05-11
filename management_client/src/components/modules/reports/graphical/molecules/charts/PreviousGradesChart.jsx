import React from 'react'
import {
  VictoryChart,
  VictoryBoxPlot,
  VictoryScatter,
  VictoryAxis,
  VictoryTheme
} from 'victory'

const STATUS_COLORS = {
  PENDING: '#FFA726',
  ELIGIBLE: '#29B6F6',
  ACCEPTED: '#66BB6A',
  REJECTED: '#EF5350'
}

const PreviousGradesChart = ({ data = [] }) => {
  // group y-values by status
  const grouped = data.reduce((acc, { status, previousGrades }) => {
    acc[status] = acc[status] || []
    acc[status].push(+previousGrades)
    return acc
  }, {})

  // box-plot format: [{ x: status, y: [grades...] }, ...]
  const boxData = Object.entries(grouped).map(
    ([status, values]) => ({ x: status, y: values })
  )

  // scatter format: one point per individual grade, for jitter
  const scatterData = data.map(({ status, previousGrades }) => ({
    x: status,
    y: +previousGrades
  }))

  return (
    <VictoryChart
      theme={VictoryTheme.clean}
      domainPadding={{ x: 50, y: [10, 10] }}
      padding={{ top: 20, bottom: 80, left: 60, right: 40 }}
    >
      {/* X axis: rotated labels */}
      <VictoryAxis
        style={{
          tickLabels: { angle: -45, fontSize: 12, padding: 15 }
        }}
      />

      {/* Y axis: grid lines */}
      <VictoryAxis
        dependentAxis
        tickFormat={t => `${t}`}
        style={{
          grid: { stroke: '#e6e6e6' },
          tickLabels: { fontSize: 12, padding: 5 }
        }}
      />

      {/* Box plots */}
      <VictoryBoxPlot
        data={boxData}
        style={{
          max: { stroke: '#555' },
          q3: { fill: '#CDE9F9' },
          median: { stroke: '#333', strokeWidth: 2 },
          q1: { fill: '#CDE9F9' },
          min: { stroke: '#555' }
        }}
        boxWidth={20}
      />

      <VictoryScatter
        data={scatterData}
        size={3}
        style={{
          data: {
            fill: ({ datum }) => STATUS_COLORS[datum.x] || '#888',
            opacity: 0.7
          }
        }}
        jitter={{ x: 20 }}
      />
    </VictoryChart>
  )
}

export default React.memo(PreviousGradesChart)
