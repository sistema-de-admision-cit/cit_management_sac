import NavItem from '../atoms/NavItem.jsx'
import NavLink from '../atoms/NavLink.jsx'
import menuConfig from '../config/menuConfig.jsx' // Importa la configuración del menú
import '../../../../assets/styles/hub/nav-group.css'

const NavGroup = ({ currentPage, logout, userRole }) => {
  const currentPageString = String(currentPage).slice(1)
  const currentSubPage = `/${currentPageString}`
  const currentMainPage = `/${currentPageString.split('/')[0]}`

  return (
    <div className='left-side-nav-container'>
      {menuConfig.map((menu) => (
        <NavItem key={menu.title}>
          <div className='side-nav-group-header-label'>
            {menu.icon}
            <p>{menu.title}</p>
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
          </ul>
        </NavItem>
      ))}
    </div>
  )
}

export default NavGroup
