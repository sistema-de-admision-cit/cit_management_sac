import { useEffect, useState } from 'react'
import { useLocation } from 'react-router-dom'
import '../../../../assets/styles/auth/reset-password.css'
import useMessages from '../../../core/global/hooks/useMessages'
import SectionLayout from '../../../core/global/molecules/SectionLayout'
import PasswordForm from '../molecules/PasswordForm'
import { DEFAULT_PASSWORD } from '../../../core/global/helpers/helpers'
import { handleChangePassword } from '../helpers/handlers'
import { useAuth } from '../../../../router/AuthProvider'

const PasswordResetSection = () => {
  // location state
  const location = useLocation()
  const isDefaultPassword = location.state?.isDefaultPassword
  // form states
  const [currentPassword, setCurrentPassword] = useState(isDefaultPassword ? DEFAULT_PASSWORD : '') // if isDefaultPassword is true, set the default password
  const [newPassword, setNewPassword] = useState('')
  const [confirmNewPassword, setConfirmNewPassword] = useState('')
  // messages
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages()
  // close session
  const { logout } = useAuth()
  const [shouldTheSessionBeClosed, setShouldTheSessionBeClosed] = useState(false)

  // Logout when the password has been changed successfully
  useEffect(() => {
    if (shouldTheSessionBeClosed) {
      setTimeout(() => {
        logout()
      }, 3000) // Wait 3 seconds before logout to give user time to read success message
    }
  }, [shouldTheSessionBeClosed, logout])

  return (
    <SectionLayout title='Restablecer Contraseña' className='reset-password-container'>
      <div className='container'>
        <h2>Restablecer Contraseña</h2>
        <h3 className='text-muted'>
          {isDefaultPassword
            ? 'Parece que es la primera vez que inicias sesión, por favor cambia tu contraseña.'
            : 'Parece que necesitas cambiar tu contraseña.'}
        </h3>
        <PasswordForm
          isDefaultPassword={isDefaultPassword}
          currentPassword={currentPassword}
          setCurrentPassword={setCurrentPassword}
          newPassword={newPassword}
          setNewPassword={setNewPassword}
          confirmNewPassword={confirmNewPassword}
          setConfirmNewPassword={setConfirmNewPassword}
          handleSubmit={(e) => handleChangePassword(e,
            currentPassword,
            newPassword,
            confirmNewPassword,
            setErrorMessage,
            setSuccessMessage,
            setShouldTheSessionBeClosed
          )}
        />
      </div>
      {renderMessages()}
    </SectionLayout>
  )
}

export default PasswordResetSection
