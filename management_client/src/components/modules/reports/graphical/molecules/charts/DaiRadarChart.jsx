import React from 'react'
import {
  ResponsiveContainer,
  RadarChart,
  PolarGrid,
  PolarAngleAxis,
  PolarRadiusAxis,
  Radar,
  Legend,
  Tooltip
} from 'recharts'

/**
 * Build data per area with min, max, and average scores
 */
const buildRadarData = (details) => {
  const areas = Array.from(new Set(details.map(d => d.area)))
  return areas.map(area => {
    const scores = details.filter(d => d.area === area).map(d => d.score)
    const min = Math.min(...scores)
    const max = Math.max(...scores)
    const avg = scores.reduce((sum, s) => sum + s, 0) / scores.length
    return {
      area,
      min: +min.toFixed(2),
      max: +max.toFixed(2),
      average: +avg.toFixed(2)
    }
  })
}

const DaiRadarChart = ({ details, areaAverages /* areaAverages no longer needed */ }) => {
  const data = buildRadarData(details)

  return (
    <div className='dai-radar-chart'>
      <h3 className='mb-2'>Evaluación DAI (Radar)</h3>
      <ResponsiveContainer width='100%' height={400}>
        <RadarChart data={data}>
          <PolarGrid />
          <PolarAngleAxis dataKey='area' />
          <PolarRadiusAxis />

          {/* Range band: max to min */}
          <Radar
            name='Rango (Min-Max)'
            dataKey='max'
            stroke='#BBBBBB'
            fill='#BBBBBB'
            fillOpacity={0.2}
          />
          {/* overlay a second Radar for min to mask inner area */}
          <Radar
            name=' ' // no legend entry
            dataKey='min'
            stroke='none'
            fill='#FFFFFF'
            fillOpacity={1}
            legendType='none'
          />

          {/* average line on top */}
          <Radar
            name='Promedio'
            dataKey='average'
            stroke='#82ca9d'
            strokeWidth={2}
            fill='#82ca9d'
            fillOpacity={0.2}
          />

          <Legend />
          <Tooltip />
        </RadarChart>
      </ResponsiveContainer>
    </div>
  )
}

export default React.memo(DaiRadarChart)
