import React from 'react'
import { PieChart, Pie, Cell, Tooltip, Legend, ResponsiveContainer } from 'recharts'

const COLORS = ['#003A7D', '#008DFF', '#FF73B6', '#C701FF', '#4ECB8D', '#FF9D3A']

const ExamPieChart = ({ data, title }) => (
  <div className='exam-pie-chart'>
    {title && <h3 className='mb-2'>{title}</h3>}
    <ResponsiveContainer width='100%' height={300}>
      <PieChart>
        <Pie data={data} dataKey='studentCount' nameKey='examSource' cx='50%' cy='50%' outerRadius={100} label>
          {data.map((entry, index) => (
            <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
          ))}
        </Pie>
        <Tooltip />
        <Legend />
      </PieChart>
    </ResponsiveContainer>
  </div>
)

export default React.memo(ExamPieChart)
