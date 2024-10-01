import { useState } from 'react'
import { useLocation } from 'react-router-dom'
import '../../../../assets/styles/auth/reset-password.css'
import useMessages from '../../../core/global/hooks/useMessages'
import SectionLayout from '../../../core/global/molecules/SectionLayout'
import PasswordForm from '../molecules/PasswordForm'

const PasswordResetSection = () => {
  const location = useLocation()
  const isDefaultPassword = location.state?.isDefaultPassword
  const [currentPassword, setCurrentPassword] = useState('')
  const [newPassword, setNewPassword] = useState('')
  const [confirmNewPassword, setConfirmNewPassword] = useState('')
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages()

  const handleSubmit = (e) => {
    e.preventDefault()

    if (newPassword !== confirmNewPassword) {
      setErrorMessage('Las contraseñas no coinciden.')
      return
    }

    // Aquí puedes agregar la lógica para manejar el cambio de contraseña
    console.log('Password actual:', currentPassword)
    console.log('Nueva password:', newPassword)

    if (isDefaultPassword) {
      console.log('Restablecer contraseña por primera vez')
    } else {
      console.log('Cambio de contraseña')
    }

    setCurrentPassword('')
    setNewPassword('')
    setConfirmNewPassword('')
    setErrorMessage('')
    setSuccessMessage('Contraseña restablecida con éxito.')
  }

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
          handleSubmit={handleSubmit}
        />
      </div>
      {renderMessages()}
    </SectionLayout>
  )
}

export default PasswordResetSection
