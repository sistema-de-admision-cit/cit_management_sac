import React from 'react'
import '../../../../assets/styles/global/input-fields.css'
import DatePicker from 'react-datepicker'
import 'react-datepicker/dist/react-datepicker.css'

const InputField = ({ field, value, handleChange, children, className, autoComplete, availableDates }) => {
  const textRelatedInput = (type) => {
    return (
      <input
        type={type}
        name={field.name}
        placeholder={field.placeholder}
        value={value}
        onChange={handleChange}
        required
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
        required
      >
        {children}
      </select>
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
              required
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
        required={field.required}
        multiple={field.multiple}
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
      />
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
    date: () => datePicker()
  }

  const renderInput = () => {
    return (inputRenderers[field.type] || inputRenderers.text)()
  }

  return (
    <div className={className}>
      <label htmlFor={field.name}>{field.label}</label>
      {renderInput()}
    </div>
  )
}

export default InputField
