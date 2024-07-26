import SideNavbar from '../organisms/SideNavbar'
import { useLocation } from 'react-router-dom'

const HubViewComponent = () => {
  const location = useLocation()
  const currentPage = location.pathname
  return (
    <SideNavbar currentPage={currentPage} />
  )
}

export default HubViewComponent
