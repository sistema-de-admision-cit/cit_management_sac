import { useState } from 'react'
import Button from '../../../../core/global/atoms/Button'
import InputField from '../../../../core/global/atoms/InputField'
import Spinner from '../../../../core/global/atoms/Spinner'

const ExamPeriodsTable = ({ examPeriods, onDelete, onCreate, loading }) => {
    const { selectedItems, setSelectedItems } = useState([])

    return (
        <div className="exam-periods-table-container">
            <div className="exam-periods-button-container">
                <Button onClick={onCreate} className='active'>+</Button>
            </div>
            <table className="exam-periods-table">
                <thead>
                    <tr>
                        <th></th>
                        <th>Fecha de Inicio</th>
                        <th>Fecha de Fin</th>
                        <th>Dias</th>
                    </tr>
                </thead>
                {loading ?
                    (
                        <tbody>
                            <tr>
                                <td colSpan='6'>
                                    <Spinner />
                                </td>
                            </tr>
                        </tbody>
                    ) :
                    (
                        <tbody>
                            {examPeriods?.map((period) => (
                                <tr key={period.id}>
                                    <td>
                                        <InputField
                                            field={{ type: 'checkbox', name: 'selector', label: '' }}
                                            value={period.id}
                                            handleChange={(e) => {
                                                if (e.target.checked) {
                                                    setSelectedItems([...selectedItems, period.id])
                                                } else {
                                                    setSelectedItems(selectedItems.filter(item => item !== period.id))
                                                }
                                            }}
                                        />
                                    </td>
                                    <td>{new Date(period.startDate).toLocaleDateString()}</td>
                                    <td>{new Date(period.endDate).toLocaleDateString()}</td>
                                    <td>{period.days.join(', ')}</td>
                                </tr>
                            ))}
                        </tbody>
                    )}
            </table>
        </div>
    )
}

export default ExamPeriodsTable