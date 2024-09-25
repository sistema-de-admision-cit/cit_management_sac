import '../../../../assets/styles/global/input-fields.css'
import DatePicker from 'react-datepicker'
import 'react-datepicker/dist/react-datepicker.css'

const InputField = ({ field, value, handleChange, children, className, autoComplete, availableDates, showLabel = true, accept }) => {
  const textRelatedInput = (type) => {
    return (
      <input
        type={type}
        name={field.name}
        placeholder={field.placeholder}
        value={value}
        onChange={handleChange}
        required={field.required || false}
        autoComplete={autoComplete || 'off'}
      />
    )
  }

  const selectInput = () => {
    return (
      <select
        name={field.name}
        value={value}
        onChange={handleChange}
        required={field.required || false}
      >
        {children}
      </select>
    )
  }

  const checkboxInput = () => {
    return (
      <input
        type='checkbox'
        name={field.name}
        checked={value}
        onChange={handleChange}
        required={field.required || false}
      />
    )
  }

  const radioGroupInput = () => {
    return (
      <div className='radio-group'>
        {field.options.map((option) => (
          <label key={option.value}>
            <input
              type='radio'
              name={field.name}
              value={option.value}
              checked={value === option.value}
              onChange={handleChange}
              required={field.required || false}
            />
            {option.label}
          </label>
        ))}
      </div>
    )
  }

  const fileInput = () => {
    return (
      <input
        type='file'
        name={field.name}
        onChange={handleChange}
        required={field.required || false}
        multiple={field.multiple}
        accept={accept || ''}
      />
    )
  }

  const datePicker = () => {
    return (
      <DatePicker
        selected={value}
        onChange={(date) => handleChange(date)}
        filterDate={availableDates}
        dateFormat='dd/MM/yyyy'
        placeholderText={field.placeholder}
        className={className}
        onInputError={(err) => console.log(err)}
        required={field.required || false}
      />
    )
  }

  const timePicker = () => {
    return (
      <input
        type='time'
        className={className}
        value={value}
        min={field.min || '00:00'}
        max={field.max || '23:59'}
        onChange={handleChange}
      />
    )
  }

  const dropdownInput = () => {
    return (
      <select
        name={field.name}
        value={value}
        onChange={handleChange}
        required={field.required || false}
      >
        {field.options.map((option) => (
          <option key={option.value} value={option.value}>
            {option.label}
          </option>
        ))}
      </select>
    )
  }

  // map
  const inputRenderers = {
    text: () => textRelatedInput('text'),
    password: () => textRelatedInput('password'),
    email: () => textRelatedInput('email'),
    tel: () => textRelatedInput('tel'),
    textArea: () => textRelatedInput('textArea'),
    select: () => selectInput(),
    'radio-group': () => radioGroupInput(),
    file: () => fileInput(),
    date: () => datePicker(),
    checkbox: () => checkboxInput(),
    time: () => timePicker(),
    dropdown: () => dropdownInput()
  }

  const renderInput = () => {
    return (inputRenderers[field.type] || inputRenderers.text)()
  }

  return (
    <div className={className}>
      {showLabel && (
        <label htmlFor={field.name}>{field.label} {(field.required || false) && <span className='required'>*</span>}</label>
      )}
      {renderInput()}
    </div>
  )
}

export default InputField
