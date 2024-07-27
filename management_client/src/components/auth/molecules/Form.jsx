import React from 'react'
import InputField from '../../global/atoms/InputField'
import Button from '../../global/atoms/Button'

const Form = ({ fields, formData, handleChange, onSubmit, sectionName }) => {
  return (
    <form onSubmit={onSubmit}>
      {fields.map((field) => (
        <InputField
          key={field.name}
          field={field}
          value={formData[field.name] || ''}
          handleChange={handleChange}
        />
      ))}
      <Button type='submit' className='btn btn-primary'>{sectionName}</Button>
    </form>
  )
}

export default Form
