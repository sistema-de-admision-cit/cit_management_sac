// src/AppRouter.js
import React from 'react'
import { createBrowserRouter, RouterProvider, Outlet } from 'react-router-dom'
import HubViewComponent from '../components/hub/view/HubViewComponent'
import menuConfig from '../components/hub/config/menuConfig'
import LoginSection from '../components/auth/views/LoginSection'
import RegisterSection from '../components/auth/views/RegisterSection'

// Función para generar rutas dinámicas desde menuConfig
const generateRoutesFromConfig = (config) => {
  const routes = []

  config.forEach((menuItem) => {
    menuItem.items.forEach((item) => {
      routes.push({
        path: item.path,
        element: (
          <>
            <HubViewComponent />
            <Outlet />
          </>
        ),
        children: item.subItems?.map((subItem) => ({
          path: subItem.path,
          element: (
            <>
              {
                subItem.component
                  ? <subItem.component />
                  : (
                    <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
                      {subItem.label}
                    </div>
                    )
              }
            </>
          )
        })) || []
      })
    })
  })

  return routes
}

const routes = [
  // Rutas de autenticación
  { path: '/', element: <LoginSection sectionName='Iniciar Sesión' /> },
  { path: '/login', element: <LoginSection sectionName='Iniciar Sesión' /> },
  { path: '/register', element: <RegisterSection sectionName='Registrarse' /> },
  // Rutas dinámicas
  ...generateRoutesFromConfig(menuConfig)
]

console.log(routes)

const router = createBrowserRouter(routes)

const AppRouter = () => {
  return <RouterProvider router={router} />
}

export default AppRouter
