import React from 'react'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import LoginSection from '../views/auth/LoginSection'
import RegisterSection from '../views/auth/RegisterSection'

const AuthRouter = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<LoginSection sectionName='Iniciar Sesión' />} />
        <Route path='/login' element={<LoginSection sectionName='Iniciar Sesión' />} />
        <Route path='/register' element={<RegisterSection sectionName='Registrarse' />} />
      </Routes>
    </BrowserRouter>
  )
}

export default AuthRouter
