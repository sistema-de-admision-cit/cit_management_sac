import React, { useState } from 'react'
import { Alert } from '@mui/material'
import ReportGeneratorForm from '../organisms/ReportGeneratorForm'
import { generateReport } from '../helpers/handler'
import '../../../../../assets/styles/global/view.css'

const ReportsView = () => {
  const [isLoading, setIsLoading] = useState(false)
  const [error, setError] = useState(null)
  const [success, setSuccess] = useState(null)

  const handleGenerate = async (request, format) => {
    try {
      setIsLoading(true)
      setError(null)
      setSuccess(null)

      const blob = await generateReport(request, format)

      const url = window.URL.createObjectURL(blob)
      const link = document.createElement('a')
      link.href = url
      link.setAttribute('download', `reporte_${request.reportType}_${new Date().toISOString().slice(0, 10)}.${format.toLowerCase()}`)
      document.body.appendChild(link)
      link.click()
      link.remove()

      window.open(url, '_blank')

      setSuccess(`Reporte ${format} generado exitosamente`)
    } catch (err) {
      setError(`Error al generar el reporte: ${err.message}`)
    } finally {
      setIsLoading(false)
    }
  }

  const renderMessages = () => (
    <>
      {error && <Alert severity='error' className='message-alert'>{error}</Alert>}
      {success && <Alert severity='success' className='message-alert'>{success}</Alert>}
    </>
  )

  return (
    <div className='container percentages-configurator'>
      {renderMessages()}
      <h1>Generación de reportes</h1>
      <p className='description'>Genera reportes personalizados en formato PDF o CSV.</p>
      <div className='percentages-configurator'>
        <ReportGeneratorForm
          onGenerate={handleGenerate}
          isLoading={isLoading}
        />
      </div>
    </div>
  )
}

export default ReportsView
