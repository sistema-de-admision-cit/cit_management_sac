// src/features/reports/molecules/AttendanceChart.jsx
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

const AttendanceChart = ({ data, title }) => (
  <div className='attendance-chart'>
    {title && <h3 className='mb-2'>{title}</h3>}
    <ResponsiveContainer width='100%' height={300}>
      <BarChart data={data}>
        <CartesianGrid strokeDasharray='3 3' />
        {/* the SP returns `enrollmentDate` */}
        <XAxis dataKey='enrollmentDate' />
        <YAxis />
        <Tooltip />
        <Legend />
        {/* show both totalEnrolled & totalAttended */}
        <Bar dataKey='totalEnrolled' name='Enrolled' />
        <Bar dataKey='totalAttended' name='Attended' />
      </BarChart>
    </ResponsiveContainer>
  </div>
)

export default AttendanceChart
