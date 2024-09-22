import InputField from './InputField'

const DatePicker = ({ name, label, placeholder, value, onChange, availableDates, required = false, showLabel }) => (
  <div className='date-picker-container'>
    <InputField
      field={{ name, label, placeholder, type: 'date', required }}
      value={value}
      handleChange={onChange}
      className='date-picker'
      availableDates={availableDates}
      showLabel={showLabel}
    />
  </div>
)

export default DatePicker
