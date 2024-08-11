// src/AppRouter.js
import React from 'react'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import menuConfig from '../components/hub/config/menuConfig'
import LoginSection from '../components/auth/views/LoginSection'
import RegisterSection from '../components/auth/views/RegisterSection'
import generateRoutesFromConfig from './GenerateRoutesFromConfig'
import UnauthorizedAccessPage from '../components/errors/pages/UnauthorizedAccessPage'
import NotFoundPage from '../components/errors/pages/NotFoundPage'

const authRoutes = [
  {
    path: '/',
    errorElement: <NotFoundPage />,
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

const errorRoutes = [
  {
    path: '/unauthorized',
    element: <UnauthorizedAccessPage />
  }
]

const routes = [
  // Rutas estáticas
  ...authRoutes,
  // Rutas dinámicas
  ...generateRoutesFromConfig(menuConfig[0].items),
  // Rutas de error
  ...errorRoutes
]

const router = createBrowserRouter(routes)

const AppRouter = () => {
  return <RouterProvider router={router} />
}

export default AppRouter
