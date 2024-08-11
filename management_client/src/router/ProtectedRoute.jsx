import { Navigate } from 'react-router-dom'
import { useAuth } from './AuthProvider'

const ProtectedRoute = ({ children, roles }) => {
  const { user } = useAuth()

  if (!user?.isAuthenticated) {
    // mandar a la pagina de login si el usuario no esta autenticado
    return <Navigate to='/login' />
  }

  // Si el usuario no tiene acceso a la ruta, se redirige a la página de acceso no autorizado
  if (!user || !roles.includes(user.role)) {
    return <Navigate to='/unauthorized' />
  }

  return children
}

export default ProtectedRoute
