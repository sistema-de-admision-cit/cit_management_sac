import { Navigate } from 'react-router-dom'
import { useAuth } from './AuthProvider'

const ProtectedRoute = ({ children }) => {
  const { user } = useAuth()

  if (!user?.isAuthenticated) {
    // Redirigir a la página de inicio de sesión si el usuario no está autenticado
    return <Navigate to='/login' />
  }

  return children
}

export default ProtectedRoute
