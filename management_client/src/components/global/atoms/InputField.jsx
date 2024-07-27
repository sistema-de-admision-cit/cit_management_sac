import React from 'react'
import '../../../assets/styles/auth/input-fields.css'

const InputField = ({ field, value, handleChange, children, className }) => {
  const textInput = () => {
    return (
      <input
        type='text'
        name={field.name}
        placeholder={field.placeholder}
        value={value}
        onChange={handleChange}
        required
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

  const radioInput = () => {
    return (
      <div className='radio-group'>
        {children}
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
      case 'radio':
        return radioInput()
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
