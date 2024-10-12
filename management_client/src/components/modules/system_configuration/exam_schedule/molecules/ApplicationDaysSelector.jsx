import InputField from '../../../../core/global/atoms/InputField'

const ApplicationDaysSelector = ({ selectedDays, onDayChange }) => (
  <div className='days'>
    {['Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'].map((day) => (
      <div key={day}>
        <InputField
          field={{ type: 'checkbox', name: day, label: day }}
          value={selectedDays.includes(day)}
          handleChange={() => onDayChange(day)}
        />
      </div>
    ))}
  </div>
)

export default ApplicationDaysSelector
