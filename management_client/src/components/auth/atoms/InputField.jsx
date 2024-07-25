import React from 'react'

import '../../../assets/styles/auth/input-fields.css'

const InputField = ({ field, value, handleChange }) => {
  return (
    <div className='form-group'>
      <label htmlFor={field.name}>{field.label}</label>
      <input
        type={field.type}
        name={field.name}
        placeholder={field.placeholder}
        value={value}
        onChange={handleChange}
        required
      />
    </div>
  )
}

export default InputField
