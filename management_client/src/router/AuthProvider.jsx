import { createContext, useState, useContext } from 'react'
import PropTypes from 'prop-types'
import Cookies from 'js-cookie' // Importa js-cookie
import axios from 'axios'
import { jwtDecode } from 'jwt-decode'

// contexto con el estado de autenticación
export const AuthContext = createContext({})

export default function AuthProvider ({ children }) {
  const [user, setUser] = useState({
    isAuthenticated: !!Cookies.get('token'), // Revisa si hay un token en las cookies
    name: Cookies.get('username') || '', // Recupera el username desde la cookie, si existe
    role: Cookies.get('role') || '', // Recupera el rol desde la cookie, si existe
    token: Cookies.get('token') || null // Recupera el token desde la cookie, si existe
  })

  const login = (credentials, setErrorMessage, setIsDefaultPassword, setIsLoginSuccessful) => {
    const loginUrl = `${import.meta.env.VITE_API_BASE_URL}${import.meta.env.VITE_LOGIN_ENDPOINT}` // URL de la API de login
    // basic auth
    const username = credentials.username
    const password = credentials.password

    const basicAuth = btoa(`${username}:${password}`)
    const headers = {
      Authorization: `Basic ${basicAuth}`
    }

    axios.post(loginUrl, {}, { headers })
      .then(response => {
        const token = response.data.token
        const isDefaultPassword = response.data.isDefaultPassword
        const decodedToken = jwtDecode(token)
        setUser({
          isAuthenticated: true,
          name: decodedToken.sub, // Nombre de usuario
          role: decodedToken.scope, // Rol de usuario
          token
        })

        Cookies.set('token', token, { expires: 1 / 24 }) // Guarda el token en una cookie con duración de 1 hora
        Cookies.set('username', decodedToken.sub, { expires: 1 / 24 }) // Guarda el username
        Cookies.set('role', decodedToken.scope, { expires: 1 / 24 }) // Guarda el rol

        setIsDefaultPassword(isDefaultPassword)
        setIsLoginSuccessful(true)
      })
      .catch(error => {
        console.error('Error en la autenticación:', error)
        setErrorMessage('Credenciales inválidas')
      })
  }

  const logout = () => {
    setUser({ isAuthenticated: false, name: '', role: '', token: null })
    Cookies.remove('token')
    Cookies.remove('username')
    Cookies.remove('role')
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
