import HubViewComponent from '../components/hub/view/HubViewComponent'

// Función para generar rutas dinámicas desde menuConfig
const generateRoutesFromConfig = (items) => {
  return items.map((item) => {
    const children = item.subItems?.map((subItem) => ({
      path: subItem.path,
      element: subItem.component
        ? <subItem.component />
        : <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>{subItem.label}</div>
    })) || []

    // Componente por defecto para las rutas padre
    if (children.length > 0 && item.parentComponent) {
      children.unshift({
        index: true, // index true significa que es el componente principal (padre)
        element: <item.parentComponent label={item.label} />
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
