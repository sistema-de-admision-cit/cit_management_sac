import React from 'react'
import NavGroup from '../molecules/NavGroup'

import '../../../assets/styles/hub/side-navbar.css'
import { useAuth } from '../../../router/AuthProvider'

const SideNavbar = ({ currentPage }) => {
  const { logout } = useAuth()
  return (
    <ul className='nav'>
      <NavGroup
        currentPage={currentPage}
        logout={logout}
      />
    </ul>
  )
}

export default SideNavbar
