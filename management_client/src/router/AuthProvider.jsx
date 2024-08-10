import { createContext, useState, useContext } from 'react'
import PropTypes from 'prop-types'

// contexto con el estado de autenticación
export const AuthContext = createContext({})

export default function AuthProvider ({ children }) {
  const [user, setUser] = useState({ isAuthenticated: true, name: 'John Doe', role: 'admin' }) // directamente autenticado para no tener que loguear (temporal)

  const login = () => {
    // TODO: implementar lógica de login
    setUser({ isAuthenticated: true, name: 'John Doe', role: 'admin' })
  }

  const logout = () => {
    setUser({ isAuthenticated: false, name: '', role: '' })
  }

  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  )
}

AuthProvider.propTypes = {
  children: PropTypes.node.isRequired
}

// hook para acceder al contexto de autenticación
export const useAuth = () => {
  const context = useContext(AuthContext)
  if (context === undefined) {
    throw new Error('useAuth debe ser usado dentro de un AuthProvider')
  }
  return context
}
