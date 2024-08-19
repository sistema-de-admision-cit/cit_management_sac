import HubViewComponent from '../components/core/hub/view/HubViewComponent'
import ProtectedRoute from './ProtectedRoute'

// Función para generar rutas dinámicas a partir de un objeto de configuración (menuConfig)
const generateRoutesFromConfig = (items) => {
  return items.map((item) => {
    // si existen subitems, se mapean, para crear rutas anidadas
    const children = item.subItems?.map((subItem) => ({
      path: subItem.path, // Ruta del subitem
      element: (
        // una protected route que envuelve al componente del subitem
        <ProtectedRoute roles={item.roleRequired || []}>
          <subItem.component label={subItem.label} />
        </ProtectedRoute>
      )
    })) || [] // Si no hay subitems, children será un array vacío

    // Si hay un componente padre definido, se agrega como ruta principal
    if (item.parentComponent) {
      children.unshift({
        index: true, // `index: true` indica que este es el componente principal de la ruta padre
        element: (
          // Ruta protegida para el componente padre
          <ProtectedRoute roles={item.roleRequired || []}>
            <item.parentComponent label={item.label} />
          </ProtectedRoute>
        )
      })
    }

    // Devuelve un objeto de ruta con sus rutas hijas
    return {
      path: item.path, // Ruta padre
      element: <HubViewComponent />, // Componente principal (HubViewComponent) para la ruta padre. Esto es porque siempre se muestra el hub
      children // Rutas hijas generadas
    }
  })
}

export default generateRoutesFromConfig
