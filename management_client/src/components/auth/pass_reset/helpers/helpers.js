export const validatePassword = (currentPassword, newPassword, confirmNewPassword) => {
  const regex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/
  if (!regex.test(newPassword)) {
    return 'La contraseña debe tener al menos 8 caracteres, una letra y un número.'
  }

  if (currentPassword === newPassword) {
    return 'La nueva contraseña no puede ser igual a la actual.'
  }

  if (newPassword !== confirmNewPassword) {
    return 'Las contraseñas no coinciden.'
  }

  return null
}
