import React from 'react'
import Form from '../molecules/Form'
import Button from '../atoms/Button'

import '../../../assets/styles/auth/right-column.css'
import '../../../assets/styles/auth/links.css'

const AuthContent = ({ isRegisterSuccess, fields, formData, handleChange, onSubmit, sectionName }) => {
  return (
    <div className='right-column'>
      {isRegisterSuccess
        ? (
          <div className='confirmation-message'>
            <p>¡Registro exitoso! Debe esperar a que un administrador apruebe su cuenta.</p>
            <Button className='btn btn-secondary' onClick={() => { window.location.href = '/login' }}>
              Iniciar Sesión
            </Button>
          </div>
          )
        : (
          <div className='form'>
            <h2>{sectionName}</h2>
            <Form
              fields={fields}
              formData={formData}
              handleChange={handleChange}
              onSubmit={onSubmit}
              sectionName={sectionName}
            />
            <Button
              className='btn btn-secondary' onClick={() => {
                window.location.href = sectionName === 'Iniciar Sesión' ? '/register' : '/login'
              }}
            >
              {sectionName === 'Iniciar Sesión' ? 'Registrarse' : 'Iniciar Sesión'}
            </Button>
            <div className='copyright'>
              <p>Copyright &copy; 2024. Todos los derechos reservados.</p>
            </div>
          </div>
          )}
    </div>
  )
}

export default AuthContent
