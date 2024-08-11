import HubViewComponent from '../components/hub/view/HubViewComponent'
import ProtectedRoute from './ProtectedRoute'

// Función para generar rutas dinámicas desde menuConfig
const generateRoutesFromConfig = (items) => {
  return items.map((item) => {
    const children = item.subItems?.map((subItem) => ({
      path: subItem.path,
      element: (
        <ProtectedRoute roles={item.roleRequired || []}>
          <subItem.component label={subItem.label} />
        </ProtectedRoute>
      )
    })) || []

    // Componente por defecto para las rutas padre
    if (item.parentComponent) {
      children.unshift({
        index: true, // index true significa que es el componente principal (padre)
        element: (
          <ProtectedRoute roles={item.roleRequired || []}>
            <item.parentComponent label={item.label} />
          </ProtectedRoute>)
      })
    }

    return {
      path: item.path,
      element: <HubViewComponent />,
      children
    }
  })
}

export default generateRoutesFromConfig
