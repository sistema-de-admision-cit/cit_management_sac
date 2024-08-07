import SideNavbar from '../organisms/SideNavbar'
import { Outlet, useLocation } from 'react-router-dom'

const HubViewComponent = () => {
  const location = useLocation()
  const currentPage = location.pathname
  return (
    <>
      <SideNavbar currentPage={currentPage} />
      <Outlet />
    </>
  )
}

export default HubViewComponent
