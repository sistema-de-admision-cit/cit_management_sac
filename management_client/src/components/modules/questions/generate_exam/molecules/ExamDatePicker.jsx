// ExamDatePicker.jsx
import InputField from '../../../../core/global/atoms/InputField'
import { availableDates } from '../helpers/datesHelper'

const ExamDatePicker = ({ examDate, setExamDate }) => (
  <div className='exam-date-container'>
    <InputField
      field={{ name: 'examDate', label: 'Fecha del Examen', placeholder: 'Fecha del Examen', type: 'date', required: true }}
      value={examDate}
      handleChange={setExamDate}
      className='date-picker'
      availableDates={availableDates}
    />
  </div>
)

export default ExamDatePicker
