import axios from 'axios'
import { validatePassword } from './helpers'

const changePasswordUrl = `${import.meta.env.VITE_API_BASE_URL}${import.meta.env.VITE_CHANGE_PASSWORD_ENDPOINT}`
export const handleChangePassword = (e,
  currentPassword,
  newPassword,
  confirmPassword,
  setErrorMessage,
  setSuccessMessage,
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
    .then((response) => {
      setSuccessMessage(response.data.message)
      setShouldTheSessionBeClosed(true)
    }).catch((error) => {
      setErrorMessage(error.response.data.message)
    })
}
