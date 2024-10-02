import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import menuConfig from '../components/core/hub/config/menuConfig'
import LoginSection from '../components/auth/login/views/LoginSection'
import generateRoutesFromConfig from './GenerateRoutesFromConfig'
import UnauthorizedAccessPage from '../components/errors/pages/UnauthorizedAccessPage'
import NotFoundPage from '../components/errors/pages/NotFoundPage'
import PasswordResetSection from '../components/auth/pass_reset/view/PasswordResetSection'
import ProtectedRoute from './ProtectedRoute'
import { ROLE_SUPERADMIN, ROLE_ADMIN, ROLE_PSYCHOLOGIST, ROLE_TEACHER } from '../components/core/global/helpers/constants'

const authRoutes = [
  {
    path: '/',
    errorElement: <NotFoundPage />,
    children: [
      {
        index: true, // ruta por defecto
        element: <LoginSection />
      },
      {
        path: '/login',
        element: <LoginSection />
      },
      {
        path: '/change-password',
        element: (
          <ProtectedRoute roles={[ROLE_SUPERADMIN, ROLE_ADMIN, ROLE_TEACHER, ROLE_PSYCHOLOGIST]}>
            <PasswordResetSection />
          </ProtectedRoute>
        )
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
