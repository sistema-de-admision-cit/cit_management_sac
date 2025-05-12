import { useState } from 'react'
import '../../../../assets/styles/grades/grades-search-bar.css'
import InputField from '../../../core/global/atoms/InputField'

const DaiSearchBar = ({ onSearch, onCheckedEvaluados }) => {
  const [search, setSearch] = useState('')
  const [onlyReviewed, setOnlyReviewed] = useState(false)

  const handleSearch = (e) => {
    const value = e.target.value
    setSearch(value)
    onSearch(value)
  }

  const handleCheckboxChange = (e) => {
    const checked = e.target.checked
    setOnlyReviewed(checked)
    onCheckedEvaluados(checked) // notificamos al padre
  }

  return (
    <div className='search-bar-container'>
      <div className='grades-search-bar'>
        <InputField
          field={{
            name: 'search',
            label: 'Buscar resultado',
            type: 'text',
            placeholder: 'Buscar por cédula, nombre o apellidos'
          }}
          value={search}
          handleChange={handleSearch}
          className='form-group'
        />
      </div>
      <div className='grades-search-bar'>
        <InputField
          field={{
            name: 'evaluados',
            label: 'No Revisados',
            type: 'checkbox'
          }}
          checked={onlyReviewed}
          handleChange={handleCheckboxChange}
          className='form-group'
        />
      </div>
    </div>
  )
}

export default DaiSearchBar
