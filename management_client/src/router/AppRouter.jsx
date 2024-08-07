// src/AppRouter.js
import React from 'react'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import menuConfig from '../components/hub/config/menuConfig'
import LoginSection from '../components/auth/views/LoginSection'
import RegisterSection from '../components/auth/views/RegisterSection'
import generateRoutesFromConfig from './GenerateRoutesFromConfig'

const authRoutes = [
  { path: '/', element: <LoginSection sectionName='Iniciar Sesión' /> },
  { path: '/login', element: <LoginSection sectionName='Iniciar Sesión' /> },
  { path: '/register', element: <RegisterSection sectionName='Registrarse' /> }
]

const routes = [
  ...authRoutes,
  // Rutas dinámicas
  ...generateRoutesFromConfig(menuConfig)
]

const router = createBrowserRouter(routes)

const AppRouter = () => {
  return <RouterProvider router={router} />
}

export default AppRouter
