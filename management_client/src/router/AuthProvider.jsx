import { createContext, useEffect, useState } from 'react'
import PropTypes from 'prop-types'

export const AuthContext = createContext({ isAuthenticated: false })

export default function AuthProvider ({ children }) {
  const [user, setUser] = useState({ isAuthenticated: false })

  useEffect(() => {
    // window.sessionStorage.setItem('user', JSON.stringify(user))
  }, [user])

  return (
    <AuthContext.Provider value={{ user, setUser }}>
      {children}
    </AuthContext.Provider>
  )
}

AuthProvider.propTypes = {
  children: PropTypes.node.isRequired
}
