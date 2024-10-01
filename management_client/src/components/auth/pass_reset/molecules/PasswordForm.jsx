import InputField from '../../../core/global/atoms/InputField'
import Button from '../../../core/global/atoms/Button'

const PasswordForm = ({ isDefaultPassword, currentPassword, setCurrentPassword, newPassword, setNewPassword, confirmNewPassword, setConfirmNewPassword, handleSubmit }) => (
  <form className='reset-password-form' onSubmit={handleSubmit}>
    {!isDefaultPassword && (
      <InputField
        field={{ type: 'password', name: 'currentPassword', placeholder: 'Contraseña Actual', label: 'Contraseña Actual', required: true }}
        value={currentPassword}
        handleChange={(e) => setCurrentPassword(e.target.value)}
        className='form-group'
      />
    )}
    <InputField
      field={{ type: 'password', name: 'newPassword', placeholder: 'Nueva Contraseña', label: 'Nueva Contraseña', required: true }}
      value={newPassword}
      handleChange={(e) => setNewPassword(e.target.value)}
      className='form-group'
    />
    <InputField
      field={{ type: 'password', name: 'confirmNewPassword', placeholder: 'Confirmar Nueva Contraseña', label: 'Confirmar Nueva Contraseña', required: true }}
      value={confirmNewPassword}
      handleChange={(e) => setConfirmNewPassword(e.target.value)}
      className='form-group'
    />
    <Button type='submit' className='btn btn-primary'>
      {isDefaultPassword ? 'Cambiar Contraseña' : 'Guardar Cambios'}
    </Button>
  </form>
)

export default PasswordForm
