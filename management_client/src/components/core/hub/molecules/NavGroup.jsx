import NavItem from '../atoms/NavItem.jsx'
import NavLink from '../atoms/NavLink.jsx'
import menuConfig from '../config/menuConfig.jsx'
import '../../../../assets/styles/hub/nav-group.css'
import { ROLE_SUPERADMIN, ROLE_ADMIN, ROLE_TEACHER, ROLE_PSYCHOLOGIST } from '../../global/helpers/constants.js'

const NavGroup = ({ currentPage, logout, userRole, userName }) => {
  const currentPageString = String(currentPage).slice(1)
  const currentSubPage = `/${currentPageString}`
  const currentMainPage = `/${currentPageString.split('/')[0]}`

  const formatRole = (role) => {
    const roleNames = {
      [ROLE_SUPERADMIN]: 'Super Administrador',
      [ROLE_ADMIN]: 'Administrador',
      [ROLE_TEACHER]: 'Docente',
      [ROLE_PSYCHOLOGIST]: 'Psicólogo'
    }
    return roleNames[role] || role
  }

  return (
    <div className='left-side-nav-container'>
      {menuConfig.map((menu) => (
        <NavItem key={menu.title}>
          <div className='side-nav-group-header'>
            <div className='side-nav-group-header-icon'>
              {menu.icon}
            </div>
            <div className='side-nav-group-header-content'>
              <p className='side-nav-group-header-title'>{menu.title}</p>
              <div className='user-info'>
                <span className='user-name'>{userName}</span>
                <span className='user-role'>{formatRole(userRole)}</span>
              </div>
            </div>
          </div>
          <ul className='menu-group-label'>
            {menu.items.map((item) => (
              item.roleRequired?.includes(userRole) && (
                <NavItem key={item.key} className={item.subItems ? 'has-submenu' : ''}>
                  <NavLink
                    to={item.path}
                    className={currentMainPage === item.path ? 'active' : ''}
                    onClick={item.key === 'logout' ? logout : item.onClick || null}
                  >
                    {item.label}
                  </NavLink>
                  {item.subItems && (
                    <ul className='submenu'>
                      {item.subItems.map((subItem) => (
                        <NavItem key={subItem.key}>
                          <NavLink
                            to={subItem.path}
                            className={currentSubPage === subItem.path ? 'active' : ''}
                          >
                            {subItem.label}
                          </NavLink>
                        </NavItem>
                      ))}
                    </ul>
                  )}
                </NavItem>
              )
            ))}
            <li className='nav-link' onClick={logout}>
              Cerrar Sesión
            </li>
          </ul>
        </NavItem>
      ))}
    </div>
  )
}

export default NavGroup
