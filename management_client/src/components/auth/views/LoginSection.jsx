import { useState } from 'react'
import { handleSubmit } from './formsHandler'
import LoginHeader from '../organisms/LoginHeader'
import LoginContent from '../organisms/LoginContent'
import PopupComponent from '../../popups/PopupComponent'

const LoginSection = () => {
  const [formData, setFormData] = useState({})
  const [errorMessage, setErrorMessage] = useState('')

  const fields = [
    { name: 'correo', label: 'Correo Electrónico', type: 'email', placeholder: 'Ej. name@example.com' },
    { name: 'contrasena', label: 'Contraseña', type: 'password', placeholder: 'Ej. ********' }
  ]

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value })
  }

  return (
    <>
      <title>Iniciar Sesión</title>
      <div className='wrap'>
        <LoginHeader />
        <LoginContent
          fields={fields}
          formData={formData}
          handleChange={handleChange}
          onSubmit={(e) => handleSubmit(e, formData, setErrorMessage, setFormData)}
        />
        {errorMessage && <PopupComponent message={errorMessage} onClose={() => setErrorMessage('')} type='error' />}
      </div>
    </>
  )
}

export default LoginSection
