import React from 'react'
import menuConfig from '../../../../core/hub/config/menuConfig'
import { useAuth } from '../../../../../router/AuthProvider'
import MenuPage from '../../../../ui/menu_overview/templates/MenuPage'

const DashboardView = () => {
  const { user } = useAuth()

  // Filter modules based on userRole if needed
  const mainModules = menuConfig[0].items.filter(module =>
    module.roleRequired ? module.roleRequired.includes(user.role) : true
  ).filter(module => module.key !== 'dashboard')

  const menuExamsSection = menuConfig[0].items.find(item => item.key === 'dashboard')

  return (
    <MenuPage
      menuTitle={menuExamsSection.label}
      menuDescription={menuExamsSection.description}
      menuItems={mainModules}
    />
  )
}

export default DashboardView
