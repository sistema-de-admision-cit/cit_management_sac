import React, { useEffect, useState } from 'react'
import { PieChart, Pie, Cell, Tooltip, Legend, ResponsiveContainer } from 'recharts'
import { fetchExamSourceData } from '../helpers/handlers'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import { useFormState } from 'react-dom'

const GraphicalReportsView = () => {
  const [data, setData] = useState([])
  const [isLoading, setIsLoading] = useState(false)
  const [error, setError] = useState(null)

  useEffect(() => {
    fetchExamSourceData(setData, setIsLoading, setError)
  }, [])

  if (isLoading) return <div>Loading...</div>
  if (error) return <div>Error loading data</div>

  const COLORS = ['#0088FE', '#00C49F', '#FFBB28', '#FF8042', '#A28CF2']

  return (
    <SectionLayout title='Reportes Gráficos'>
      <div className='graphical-reports-container container'>
        <h1>Reportes Graficos</h1>
        <ResponsiveContainer width='100%' height={400}>
          <PieChart>
            <Pie
              data={data}
              dataKey='studentCount'
              nameKey='examSource'
              cx='50%'
              cy='50%'
              outerRadius={120}
              label
            >
              {data?.map((entry, index) => (
                <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
              ))}
            </Pie>
            <Tooltip />
            <Legend />
          </PieChart>
        </ResponsiveContainer>
      </div>
    </SectionLayout>
  )
}

export default GraphicalReportsView
