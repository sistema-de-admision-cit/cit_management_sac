import MenuPage from '../../../../ui/menu_overview/templates/MenuPage'
import menuConfig from '../../../../core/hub/config/menuConfig'

const ExamMenuPageView = () => {
  const menuExamsSection = menuConfig[0].items.find(item => item.key === 'exams')

  return (
    <MenuPage
      menuTitle={menuExamsSection.label}
      menuDescription={menuExamsSection.description}
      menuItems={menuExamsSection.subItems}
    />
  )
}

export default ExamMenuPageView
