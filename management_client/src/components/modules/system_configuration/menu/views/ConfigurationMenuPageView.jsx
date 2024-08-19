import MenuPage from '../../../../menu_overview/templates/MenuPage'
import menuConfig from '../../../../hub/config/menuConfig'

const ConfigurationMenuPageView = () => {
  const menuExamsSection = menuConfig[0].items.find(item => item.key === 'configurations')

  return (
    <MenuPage
      menuTitle={menuExamsSection.label}
      menuDescription={menuExamsSection.description}
      menuItems={menuExamsSection.subItems}
    />
  )
}

export default ConfigurationMenuPageView
