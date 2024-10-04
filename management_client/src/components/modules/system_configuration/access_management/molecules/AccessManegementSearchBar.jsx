import { useState } from 'react'
import '../../../../../assets/styles/global/view.css'
import InputField from '../../../../core/global/atoms/InputField'
import searchIcon from '../../../../../assets/icons/search-alt-2-svgrepo-com.svg' 

const AccessManegementSearchBar = ({ onSearch }) => {
  const [search, setSearch] = useState('')

  const handleSearch = (e) => {
    setSearch(e.target.value)
    onSearch(e.target.value)
  }

  return (
    <div className='search-bar-container'>
      <div className='search-bar'>
        <input
          type='text'
          value={search}
          onChange={handleSearch}
          placeholder='Buscar por correo'
          className='search-input'
        />
        <img src={searchIcon} alt='search' className='search-icon' />
      </div>
    </div>
  )
}

export default AccessManegementSearchBar
