import axios from '../../../../config/axiosConfig'
import { validatePassword } from './helpers'

const changePasswordUrl = `${import.meta.env.VITE_CHANGE_PASSWORD_ENDPOINT}`
export const handleChangePassword = (e,
  currentPassword,
  newPassword,
  confirmPassword,
  setErrorMessage,
  setShouldTheSessionBeClosed) => {
  e.preventDefault()

  const errorMessage = validatePassword(currentPassword, newPassword, confirmPassword)
  if (errorMessage) {
    setErrorMessage(errorMessage)
    return
  }

  const body = {
    currentPassword,
    newPassword,
    confirmPassword
  }

  // todo: add jwt token
  axios.put(changePasswordUrl, body)
    .then(() => {
      setShouldTheSessionBeClosed(true)
    }).catch((error) => {
      setErrorMessage(error.response.data.message)
    })
}
