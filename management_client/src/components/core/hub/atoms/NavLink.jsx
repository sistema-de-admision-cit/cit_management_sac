import { Link } from 'react-router-dom'

import '../../../../assets/styles/hub/nav-link.css'

const NavLink = ({ to, className, children, onClick }) => {
  return (
    <Link to={to} className={className} onClick={onClick}>
      {children}
    </Link>
  )
}

export default NavLink
