import { useState } from 'react'
import '../../../../../assets/styles/enrollments/enrollment-search-bar.css'
import InputField from '../../../../core/global/atoms/InputField'

const StudentSearchBar = ({ onSearch }) => {
  const [search, setSearch] = useState('')

  const handleSearch = (e) => {
    setSearch(e.target.value)
    onSearch(e.target.value)
  }

  return (
    <div className='search-bar-container'>
      <div className='enrollment-search-bar'>
        <InputField
          field={{
            name: 'search',
            label: 'Buscar Inscripción',
            type: 'text',
            placeholder: 'Buscar por cedula, nombre o apellidos'
          }}
          value={search}
          handleChange={handleSearch}
          className='form-group'
        />
      </div>
    </div>
  )
}

export default StudentSearchBar
