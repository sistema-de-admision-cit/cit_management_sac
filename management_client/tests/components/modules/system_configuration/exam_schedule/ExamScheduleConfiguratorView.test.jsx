import { it, expect, describe, afterEach } from 'vitest'
import { render, screen, fireEvent, cleanup, waitFor } from '@testing-library/react'
import ExamScheduleConfiguratorView from '../../../../../src/components/modules/system_configuration/exam_schedule/view/ExamScheduleConfiguratorView'
import '@testing-library/jest-dom/vitest'

describe('ExamScheduleConfiguratorView', () => {
  afterEach(() => {
    cleanup()
  })

  it('debería renderizar el formulario correctamente', () => {
    render(<ExamScheduleConfiguratorView />)

    // Verificar que el título y descripción se muestran
    expect(screen.getByText('Configuración de citas')).toBeInTheDocument()
    expect(screen.getByText('Configura las fechas y horarios de aplicación de los exámenes.')).toBeInTheDocument()

    // Verificar que los subcomponentes estén presentes
    expect(screen.getByText(/Días de Aplicación/i)).toBeInTheDocument()
    expect(screen.getByText(/Hora de Aplicación/i)).toBeInTheDocument()

    // Verificar la presencia de botones
    expect(screen.getByRole('button', { name: /Guardar/i })).toBeInTheDocument()
    expect(screen.getByRole('button', { name: /Cancelar/i })).toBeInTheDocument()
  })

  it('debería manejar el envío del formulario correctamente cuando el formulario es válido', async () => {
    render(<ExamScheduleConfiguratorView />)

    // Simular la selección de fechas de inicio y fin
    const allYearCheckBox = screen.getByText(/Todo el año/i, { selector: 'label' }).nextElementSibling
    fireEvent.click(allYearCheckBox)

    // Simular la selección de días de aplicación
    const mondayCheckBox = screen.getByText(/lunes/i, { selector: 'label' }).nextElementSibling
    fireEvent.click(mondayCheckBox)

    // Simular la selección de hora de aplicación
    const startTimeInput = screen.getByText(/Hora de Aplicación/i).nextElementSibling.querySelector('input')
    fireEvent.change(startTimeInput, { target: { value: '08:00' } })

    // Simular el envío del formulario
    const saveButton = screen.getByRole('button', { name: /Guardar/i })
    fireEvent.click(saveButton)

    // Verificar que el mensaje de éxito se muestra
    await waitFor(() => {
      expect(screen.getByText(/Guardando.../i)).toBeInTheDocument()
    })
  })
})
