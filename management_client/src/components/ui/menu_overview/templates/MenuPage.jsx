import MenuCard from '../molecules/MenuCard.jsx'
import '../../../../assets/styles/menu_overview/menu-page.css'

const MenuPage = ({ menuTitle, menuItems, menuDescription }) => {
  return (
    <div className='menu-container'>
      <title>{menuTitle}</title>
      <div className='menu-section'>
        <div className='menu-section-header'>
          <h1>{menuTitle}</h1>
          <span>{menuDescription}</span>
        </div>
        <div className='menu-section-content'>
          {menuItems.map(item => (
            <MenuCard key={item.key} item={item} />
          ))}
        </div>
      </div>
    </div>
  )
}

export default MenuPage
