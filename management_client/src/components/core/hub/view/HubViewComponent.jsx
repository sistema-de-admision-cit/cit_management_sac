import SideNavbar from '../organisms/SideNavbar.jsx'
import { Outlet, useLocation } from 'react-router-dom'
import '../../../../assets/styles/hub/hub-view.css'

const HubViewComponent = () => {
  const location = useLocation()
  const currentPage = location.pathname
  return (
    <div className='layout-container'>
      <SideNavbar currentPage={currentPage} />
      <main className='content'>
        <Outlet />
      </main>
    </div>
  )
}

export default HubViewComponent
