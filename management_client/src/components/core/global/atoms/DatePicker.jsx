import InputField from './InputField'

const DatePicker = ({ name, label, placeholder, value, onChange, availableDates, required = false }) => (
  <div className='date-picker-container'>
    <InputField
      field={{ name, label, placeholder, type: 'date', required }}
      value={value}
      handleChange={onChange}
      className='date-picker'
      availableDates={availableDates}
    />
  </div>
)

export default DatePicker
