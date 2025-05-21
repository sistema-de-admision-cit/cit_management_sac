import { useEffect, useState } from 'react'
import LoginHeader from '../organisms/LoginHeader'
import LoginContent from '../organisms/LoginContent'
import '../../../../assets/styles/auth/wrap.css'
import useMessages from '../../../core/global/hooks/useMessages'
import { useAuth } from '../../../../router/AuthProvider'
import { useNavigate } from 'react-router-dom'

const LoginSection = () => {
  const [credentials, setCredentials] = useState({})
  const [isLoginSuccessful, setIsLoginSuccessful] = useState(false)
  const [isDefaultPassword, setIsDefaultPassword] = useState(false)
  const { setErrorMessage, renderMessages } = useMessages()
  const { login } = useAuth()
  const navigate = useNavigate()

  useEffect(() => {
    if (!isLoginSuccessful) return
    if (isDefaultPassword) {
      navigate('/change-password', { state: { isDefaultPassword } })
    } else {
      navigate('/dashboard')
    }
  }, [isLoginSuccessful, isDefaultPassword, navigate])

  const fields = [
    { name: 'username', label: 'Correo Electrónico', type: 'email', placeholder: 'Ej. nombre@ctpcit.co.cr' },
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
          onSubmit={(e) => { e.preventDefault(); login(credentials, setErrorMessage, setIsDefaultPassword, setIsLoginSuccessful) }}
        />
        {renderMessages()}
      </div>
    </>
  )
}

export default LoginSection
