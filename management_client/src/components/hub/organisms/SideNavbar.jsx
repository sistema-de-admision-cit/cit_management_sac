import React from 'react'
import NavGroup from '../molecules/NavGroup'

import '../../../assets/styles/hub/side-navbar.css'

const SideNavbar = ({ currentPage }) => {
  return (
    <ul className='nav'>
      <NavGroup currentPage={currentPage} />
    </ul>
  )
}

export default SideNavbar
