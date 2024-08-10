import { AuthContext } from './AuthProvider.jsx'
import { useLocation, useNavigate } from 'react-router-dom'
import { useContext, useEffect } from 'react'
import { PropTypes } from 'prop-types'

export const ProtectedRoute = ({ children }) => {
  const { user } = useContext(AuthContext)
  const location = useLocation()
  const navigate = useNavigate()

  useEffect(() => {
    if (user != null) {
      if (user.isAuthenticated === false) {
        navigate('/', { replace: true })
      } else {
        if (location.pathname === '/') {
          navigate('/home', { replace: true })
        }
      }
    }
  }, [user])

  return children
}

ProtectedRoute.propTypes = {
  children: PropTypes.node.isRequired
}
