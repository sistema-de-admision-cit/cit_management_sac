// src/components/NavGroup.js
import React from 'react'
import Icon from '../atoms/Icon'
import NavItem from '../atoms/NavItem'
import NavLink from '../atoms/NavLink'
import menuConfig from './menuConfig' // Importa la configuración del menú
import '../../../assets/styles/hub/nav-group.css'

const NavGroup = ({ currentPage }) => {
  const currentPageString = String(currentPage).slice(1)
  const currentSubPage = currentPageString.split('/')[1]

  return (
    <div className='left-side-nav-container'>
      {menuConfig.map((menu) => (
        <NavItem key={menu.title}>
          <div className='side-nav-group-header-label'>
            <Icon>{menu.icon}</Icon>
            <p>{menu.title}</p>
          </div>
          <ul className='menu-group-label'>
            {menu.items.map((item) => (
              <NavItem key={item.key} className={item.subItems ? 'has-submenu' : ''}>
                <NavLink
                  to={item.path}
                  className={currentPageString.startsWith(item.key) ? 'active' : ''}
                  onClick={item.onClick || null}
                >
                  {item.label}
                </NavLink>
                {item.subItems && (
                  <ul className='submenu'>
                    {item.subItems.map((subItem) => (
                      <NavItem key={subItem.key}>
                        <NavLink
                          to={subItem.path}
                          className={currentSubPage === subItem.key ? 'active' : ''}
                        >
                          {subItem.label}
                        </NavLink>
                      </NavItem>
                    ))}
                  </ul>
                )}
              </NavItem>
            ))}
          </ul>
        </NavItem>
      ))}
    </div>
  )
}

export default NavGroup
