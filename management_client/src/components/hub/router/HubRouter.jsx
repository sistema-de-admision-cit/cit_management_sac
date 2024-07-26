import React from 'react'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import HubViewComponent from '../view/HubViewComponent'
import menuConfig from '../config/menuConfig'

const HubRouter = () => {
  return (
    <BrowserRouter>
      <Routes>
        {/* rutas dinamicas utilizando menuConfig, entonces es mas facil mantener las rutas y elementos del hub */}
        {menuConfig[0].items.map((item) => (
          <React.Fragment key={item.key}>
            <Route
              path={item.path}
              element={
                <>
                  <HubViewComponent />
                  <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
                    {item.label}
                  </div>
                </>
              }
            />
            {item.subItems && item.subItems.map((subItem) => (
              <Route
                key={subItem.key}
                path={subItem.path}
                element={
                  <>
                    <HubViewComponent />
                    <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
                      {subItem.label}
                    </div>
                  </>
                }
              />
            ))}
          </React.Fragment>
        ))}
      </Routes>
    </BrowserRouter>
  )
}

export default HubRouter
