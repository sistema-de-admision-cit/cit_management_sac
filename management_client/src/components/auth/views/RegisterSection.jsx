import { useState } from 'react'
import AuthComponent from './AuthComponent'
import { onRegisterSubmit } from './formsHandler'

const RegisterSection = ({ sectionName }) => {
  const [formData, setFormData] = useState({})
  const [isRegisterSuccess, setIsRegisterSuccess] = useState(false)
  const [errorMessage, setErrorMessage] = useState('')

  const fields = [
    { name: 'nombre', label: 'Nombre', type: 'text', placeholder: 'Ej. Juan' },
    { name: 'telefono', label: 'Teléfono', type: 'tel', placeholder: 'Ej. 1234567890' },
    { name: 'correo', label: 'Correo Electrónico', type: 'email', placeholder: 'Ej. name@example.com' },
    { name: 'contrasena', label: 'Contraseña', type: 'password', placeholder: 'Ej. ********' }
  ]

  return (
    <>
      <title>{sectionName}</title>
      <AuthComponent
        formData={formData}
        setFormData={setFormData}
        onSubmit={onRegisterSubmit}
        fields={fields}
        sectionName={sectionName}
        isRegisterSuccess={isRegisterSuccess}
        setIsRegisterSuccess={setIsRegisterSuccess}
        errorMessage={errorMessage}
        setErrorMessage={setErrorMessage}
      />
    </>
  )
}

export default RegisterSection
