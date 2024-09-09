import { it, expect, describe, afterEach, vi } from 'vitest'
import { render, screen, fireEvent, waitFor, cleanup } from '@testing-library/react'
import PercentagesConfiguratorView from '../../../../../src/components/modules/system_configuration/percentages/view/PercentagesConfiguratorView'
import '@testing-library/jest-dom/vitest'
import { getSaveButtonState, getCurrentPercentages, handleSave } from '../../../../../src/components/modules/system_configuration/percentages/helpers/handlers'
import useMessages from '../../../../../src/components/core/global/hooks/useMessages'

// Mock the external functions and hooks
vi.mock('../../../../../src/components/modules/system_configuration/percentages/helpers/handlers', () => ({
  getSaveButtonState: vi.fn(),
  getCurrentPercentages: vi.fn(),
  handleSave: vi.fn()
}))

vi.mock('../../../../../src/components/core/global/hooks/useMessages', () => ({
  __esModule: true,
  default: () => ({
    setErrorMessage: vi.fn(),
    setSuccessMessage: vi.fn(),
    renderMessages: vi.fn().mockReturnValue(<div>Messages</div>)
  })
}))

describe('PercentagesConfiguratorView', () => {
  afterEach(() => {
    cleanup()
  })

  it('renders correctly and displays initial data', async () => {
    // Mock the API response
    getCurrentPercentages.mockResolvedValue({
      academicExam: 30,
      daiExam: 30,
      englishExam: 40
    })

    render(<PercentagesConfiguratorView />)

    // Check for the title and description
    expect(screen.getByText('Configurar Porcentajes')).toBeInTheDocument()
    expect(screen.getByText('Configura los porcentajes de cada examen para el cálculo de la nota final.')).toBeInTheDocument()

    // Wait for the initial data to be fetched and displayed
    await waitFor(() => {
      expect(screen.getByText(/Académico/i).nextElementSibling.value).toBe('30')
      expect(screen.getByText(/Psicológico/i).nextElementSibling.value).toBe('30')
      expect(screen.getByText(/Inglés/i).nextElementSibling.value).toBe('40')
    })
  })

  it('displays loading state while fetching data', async () => {
    getCurrentPercentages.mockImplementation(() => new Promise(resolve => setTimeout(() => resolve({
      academicExam: 30,
      daiExam: 30,
      englishExam: 40
    }), 1000)))

    render(<PercentagesConfiguratorView />)

    await waitFor(() => {
      expect(screen.getByText(/Guardando.../i)).toBeInTheDocument()
    })
  })

  it('handles form value changes', async () => {
    getCurrentPercentages.mockResolvedValue({
      academicExam: 30,
      daiExam: 30,
      englishExam: 40
    })

    render(<PercentagesConfiguratorView />)

    // Wait for initial data
    await waitFor(() => {
      expect(screen.getByText(/Académico/i).nextElementSibling.value).toBe('30')
      expect(screen.getByText(/Psicológico/i).nextElementSibling.value).toBe('30')
      expect(screen.getByText(/Inglés/i).nextElementSibling.value).toBe('40')
    })

    // Change the values
    const academicExamPercentage = screen.getByText(/Académico/i).nextElementSibling
    fireEvent.change(academicExamPercentage, { target: { value: 40 } })

    const daiExamPercentage = screen.getByText(/Psicológico/i).nextElementSibling
    fireEvent.change(daiExamPercentage, { target: { value: 30 } })

    const englishExamPercentage = screen.getByText(/Inglés/i).nextElementSibling
    console.log(englishExamPercentage)
    fireEvent.change(englishExamPercentage, { target: { value: 30 } })

    // Check if the values have been updated
    await waitFor(() => {
      expect(screen.getByText(/Académico/i).nextElementSibling.value).toBe('40')
      expect(screen.getByText(/Psicológico/i).nextElementSibling.value).toBe('30')
      expect(screen.getByText(/Inglés/i).nextElementSibling.value).toBe('30')
    })
  })
})
