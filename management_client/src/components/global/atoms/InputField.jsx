import React from 'react'
import '../../../assets/styles/global/input-fields.css'

const InputField = ({ field, value, handleChange, children, className, autoComplete }) => {
  const textInput = () => {
    return (
      <input
        type='text'
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

  const textAreaInput = () => {
    return (
      <textarea
        name={field.name}
        placeholder={field.placeholder}
        value={value}
        onChange={handleChange}
        required
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
              required
            />
            {option.label}
          </label>
        ))}
      </div>
    )
  }

  const renderInput = () => {
    switch (field.type) {
      case 'text':
        return textInput()
      case 'select':
        return selectInput()
      case 'textarea':
        return textAreaInput()
      case 'radio-group':
        return radioGroupInput()
      default:
        return textInput()
    }
  }

  return (
    <div className={className}>
      <label htmlFor={field.name}>{field.label}</label>
      {renderInput()}
    </div>
  )
}

export default InputField
