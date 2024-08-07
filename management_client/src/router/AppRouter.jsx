// src/AppRouter.js
import React from 'react'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import menuConfig from '../components/hub/config/menuConfig'
import LoginSection from '../components/auth/views/LoginSection'
import RegisterSection from '../components/auth/views/RegisterSection'
import generateRoutesFromConfig from './GenerateRoutesFromConfig'

const authRoutes = [
  {
    path: '/',
    element: <LoginSection sectionName='Iniciar Sesión' />,
    errorElement: <div className='error'>Error 404</div>,
    children: [
      {
        index: true, // ruta por defecto
        element: <LoginSection sectionName='Iniciar Sesión' />
      },
      {
        path: '/login',
        element: <LoginSection sectionName='Iniciar Sesión' />
      },
      {
        path: '/register',
        element: <RegisterSection sectionName='Registrarse' />
      }
    ]
  }
]

const routes = [
  // Rutas estáticas
  ...authRoutes,
  // Rutas dinámicas
  ...generateRoutesFromConfig(menuConfig[0].items)
]

const router = createBrowserRouter(routes)

const AppRouter = () => {
  return <RouterProvider router={router} />
}

export default AppRouter
