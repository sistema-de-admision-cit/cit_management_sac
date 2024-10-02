import { useState } from 'react'
import LoginHeader from '../organisms/LoginHeader'
import LoginContent from '../organisms/LoginContent'
import '../../../../assets/styles/auth/wrap.css'
import useMessages from '../../../core/global/hooks/useMessages'
import { useAuth } from '../../../../router/AuthProvider'

const LoginSection = () => {
  const [credentials, setCredentials] = useState({})
  const { setErrorMessage, renderMessages } = useMessages()
  const { login } = useAuth()

  const fields = [
    { name: 'username', label: 'Correo Electrónico', type: 'email', placeholder: 'Ej. name@example.com' },
    { name: 'password', label: 'Contraseña', type: 'password', placeholder: 'Ej. ********' }
  ]

  const handleChange = (e) => {
    setCredentials({ ...credentials, [e.target.name]: e.target.value })
  }

  return (
    <>
      <title>Iniciar Sesión</title>
      <div className='wrap'>
        <LoginHeader />
        <LoginContent
          fields={fields}
          formData={credentials}
          handleChange={handleChange}
          onSubmit={(e) => { e.preventDefault(); login(credentials, setErrorMessage) }}
        />
        {renderMessages()}
      </div>
    </>
  )
}

export default LoginSection
