import MenuPage from '../../../../ui/menu_overview/templates/MenuPage'
import menuConfig from '../../../../core/hub/config/menuConfig'

const ResultsMenuPageView = () => {
  const menuExamsSection = menuConfig[0].items.find(item => item.key === 'results')

  return (
    <MenuPage
      menuTitle={menuExamsSection.label}
      menuDescription={menuExamsSection.description}
      menuItems={menuExamsSection.subItems}
    />
  )
}

export default ResultsMenuPageView
