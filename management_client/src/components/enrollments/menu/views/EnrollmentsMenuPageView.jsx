import MenuPage from '../../../menu_overview/templates/MenuPage'
import menuConfig from '../../../hub/config/menuConfig'

const EnrollmentsMenuPageView = () => {
  const menuExamsSection = menuConfig[0].items.find(item => item.key === 'enrollments')

  return (
    <MenuPage
      menuTitle={menuExamsSection.label}
      menuDescription={menuExamsSection.description}
      menuItems={menuExamsSection.subItems}
    />
  )
}

export default EnrollmentsMenuPageView
