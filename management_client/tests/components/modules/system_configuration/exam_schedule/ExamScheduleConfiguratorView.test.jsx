import { it, expect, describe } from 'vitest'
import { render, screen } from '@testing-library/react'
import ExamScheduleConfiguratorView from '../../../../../src/components/modules/system_configuration/exam_schedule/view/ExamScheduleConfiguratorView'
import '@testing-library/jest-dom/vitest'

describe('ExamScheduleConfiguratorView', () => {
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
})
