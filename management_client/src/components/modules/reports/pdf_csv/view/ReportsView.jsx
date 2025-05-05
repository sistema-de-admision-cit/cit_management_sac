import React, { useState } from 'react'
import ReportGeneratorForm from '../organisms/ReportGeneratorForm'
import { generateReport } from '../helpers/handler'
import PopupComponent from '../../../../ui/popups/view/PopupComponent'
import '../../../../../assets/styles/global/view.css'
import '../../../../../assets/styles/errors/error.css'

const ReportsView = () => {
  const [isLoading, setIsLoading] = useState(false)
  const [popupState, setPopupState] = useState({
    visible: false,
    message: '',
    type: 'info',
    title: ''
  })

  const handleGenerate = async (request, format, validationError) => {
    // Manejar errores de validación primero
    if (validationError) {
      setPopupState({
        visible: true,
        message: validationError,
        type: 'error',
        title: 'Error de validación'
      })
      return
    }

    try {
      setIsLoading(true)
      setPopupState({ visible: false, message: '', type: 'info', title: '' })

      const blob = await generateReport(request, format)

      // Crear y descargar el archivo
      const url = window.URL.createObjectURL(blob)
      const link = document.createElement('a')
      link.href = url
      const now = new Date()
      const dateStr = now.toISOString().slice(0, 10)
      const timeStr = `${now.getHours()}-${now.getMinutes()}-${now.getSeconds()}`

      link.setAttribute(
        'download',
        `reporte_${request.reportType}_${dateStr}_${timeStr}.${format.toLowerCase()}`
      )
      document.body.appendChild(link)
      link.click()
      link.remove()

      // Mostrar mensaje de éxito
      setPopupState({
        visible: true,
        message: `Reporte ${format} generado exitosamente`,
        type: 'confirmation',
        title: 'Éxito'
      })
    } catch (err) {
      setPopupState({
        visible: true,
        message: `Error al generar el reporte: ${err.message}`,
        type: 'error',
        title: 'Error'
      })
    } finally {
      setIsLoading(false)
    }
  }

  const handleClosePopup = () => {
    setPopupState(prev => ({ ...prev, visible: false }))
  }

  return (
    <div className='container percentages-configurator'>
      {popupState.visible && (
        <PopupComponent
          message={popupState.message}
          onClose={handleClosePopup}
          type={popupState.type}
          messageTitle={popupState.title}
        />
      )}
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
