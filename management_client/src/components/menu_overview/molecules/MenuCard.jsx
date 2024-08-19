import '../../../assets/styles/menu_overview/menu-card.css'

const MenuCard = ({ item }) => {
  const imageUrl = item.imagePath ? item.imagePath : 'https://raw.githubusercontent.com/link-u/avif-sample-images/master/fox.profile0.10bpc.yuv420.avif'

  return (
    <div key={item.key} className='menu-card background-image' style={{ background: `url(${imageUrl})` }}>
      <div className='menu-card-content'>
        <div className='menu-card-content-items'>
          <span className='menu-card-title'>{item.label}</span>
          <span className='menu-card-description'>{item.description}</span>
        </div>
      </div>
    </div>
  )
}

export default MenuCard
