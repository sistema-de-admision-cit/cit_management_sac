import { useState } from 'react'
import Button from '../../../../core/global/atoms/Button'
import InputField from '../../../../core/global/atoms/InputField'
import Spinner from '../../../../core/global/atoms/Spinner'

const ExamPeriodsTable = ({ examPeriods, onDelete, loading }) => {
  const [selectedItems, setSelectedItems] = useState([])

  const formatDateForDisplay = (date) => {
    return date.toLocaleDateString('es-ES', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      timeZone: 'UTC'
    })
  }

  const handleCheckboxChange = (period) => {
    if (selectedItems.includes(period.id)) {
      setSelectedItems(selectedItems.filter(item => item !== period.id))
    } else {
      setSelectedItems([...selectedItems, period.id])
    }
  }
  return (
    <div className='exam-periods-table-container'>
      <h1>Citas para el año en curso</h1>
      <table className='exam-periods-table'>
        <thead>
          <tr>
            <th />
            <th>Fecha de Inicio</th>
            <th>Fecha de Fin</th>
            <th>Dias</th>
          </tr>
        </thead>
        {loading
          ? (
            <tbody>
              <tr>
                <td colSpan='6'>
                  <Spinner />
                </td>
              </tr>
            </tbody>
            )
          : (
            <tbody>
              {examPeriods?.map((period) => (
                <tr key={period.id}>
                  <td>
                    <InputField
                      field={{ type: 'checkbox', name: 'toDelete', label: '' }}
                      value={selectedItems.includes(period.id)}
                      handleChange={() => handleCheckboxChange(period)}
                    />
                  </td>
                  <td>{formatDateForDisplay(period.startDate)}</td>
                  <td>{formatDateForDisplay(period.endDate)}</td>
                  <td>{period.days.join(', ')}</td>
                </tr>
              ))}
            </tbody>
            )}
      </table>
      <div className='exam-periods-button-container'>
        <Button
          onClick={() => {
            onDelete(selectedItems, setSelectedItems)
            setSelectedItems([])
          }} className='btn btn-primary' disabled={selectedItems.length === 0}
        >Borrar Selección
        </Button>
      </div>
    </div>
  )
}

export default ExamPeriodsTable
