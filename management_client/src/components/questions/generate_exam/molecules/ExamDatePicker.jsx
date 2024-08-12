// ExamDatePicker.jsx
import InputField from '../../../global/atoms/InputField'
import { availableDates } from '../helpers/datesHelper'

const ExamDatePicker = ({ examDate, setExamDate }) => (
  <div className='exam-date-container'>
    <h3>Fecha del Examen:</h3>
    <InputField
      field={{ name: 'examDate', placeholder: 'Fecha del Examen', type: 'date' }}
      value={examDate}
      handleChange={setExamDate}
      className='date-picker'
      availableDates={availableDates}
    />
  </div>
)

export default ExamDatePicker
