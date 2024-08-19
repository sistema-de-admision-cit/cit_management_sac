import NavGroup from '../molecules/NavGroup.jsx'

import '../../../../assets/styles/hub/side-navbar.css'
import { useAuth } from '../../../../router/AuthProvider.jsx'

const SideNavbar = ({ currentPage }) => {
  const { logout, user } = useAuth()

  return (
    <ul className='nav'>
      <NavGroup
        currentPage={currentPage}
        logout={logout}
        userRole={user.role}
      />
    </ul>
  )
}

export default SideNavbar
