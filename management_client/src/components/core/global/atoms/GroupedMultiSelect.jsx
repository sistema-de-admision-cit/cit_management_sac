import React from 'react'
import Select from 'react-select'

/**
 * options: [
 *   { label: 'Primaria', options: [{ value: 'FIRST', label: 'Primero' }, …] },
 *   { label: 'Secundaria', options: [...] }
 * ]
 * value: array of selected {value,label}
 * onChange: nuevos valores array
 */
export default function GroupedMultiSelect ({ options, value, onChange, placeholder }) {
  return (
    <Select
      isMulti
      options={options}
      value={value}
      onChange={onChange}
      placeholder={placeholder}
      closeMenuOnSelect={false}
    />
  )
}
