import axios from '../../../../../config/axiosConfig'

export const isEmailValid = (email) => {
  const emailRegex = /^[a-zA-Z0-9._%+-]+@ctpcit\.co\.cr$/
  return emailRegex.test(email)
}

export const isFormValid = (formValues) => {
  const { email, realUsername, role } = formValues
  return email && realUsername && role && role !== 'porDefecto'
}

export const handleSubmit = async (formValues, setLoading, setErrorMessage, setSuccessMessage, fetchUsers, setUsers, resetForm) => {
  const { email, realUsername, role } = formValues

  if (!email || !realUsername || !role || role === 'porDefecto') {
    setErrorMessage('Por favor, complete todos los campos.')
    return
  }

  if (!isEmailValid(email)) {
    setErrorMessage('El correo electrónico debe terminar en "@ctpcit.co.cr".')
    return
  }

  const sendingData = {
    password: 'campus',
    email,
    realUsername,
    role
  }

  const createUserUrl = import.meta.env.VITE_CREATE_USER_ENDPOINT

  setLoading(true)

  try {
    await axios.post(createUserUrl, sendingData, { timeout: 10000 })
    setSuccessMessage('Usuario creado correctamente.')

    await fetchUsers(setUsers, setLoading, setErrorMessage)
    resetForm()
  } catch (error) {
    if (error.response && error.response.status === 409) {
      setErrorMessage('Error, el usuario ya ha sido creado.')
    } else {
      setErrorMessage('Error al crear usuario. Por favor, intente de nuevo.')
    }
  } finally {
    setLoading(false)
  }
}

export const fetchUsers = async (setUsers, setLoading, setErrorMessage) => {
  const getUsersUrl = import.meta.env.VITE_GET_USERS_ENDPOINT
  setLoading(true)
  try {
    const response = await axios.get(getUsersUrl)
    setUsers(response.data)
  } catch (error) {
    setErrorMessage('Error al cargar la lista de usuarios.')
  } finally {
    setLoading(false)
  }
}

export const handleDeleteUser = (email, fetchUsers, setSuccessMessage, setErrorMessage, currentUserEmail, setShowConfirmationModal, setPendingDeleteEmail) => {
  if (email === currentUserEmail) {
    setErrorMessage('No puedes eliminar tu propia cuenta.')
    return
  }

  setPendingDeleteEmail(email)
  setShowConfirmationModal(true)
}

export const confirmDeleteUser = async (email, fetchUsers, setSuccessMessage, setErrorMessage, setShowConfirmationModal, setUsers, setLoading) => {
  const deleteUserUrl = import.meta.env.VITE_DELETE_USER_ENDPOINT
  setLoading(true)
  try {
    await axios.delete(`${deleteUserUrl}?email=${encodeURIComponent(email)}`)
    setSuccessMessage(`Usuario con correo ${email} eliminado`)

    await fetchUsers(setUsers, setLoading, setErrorMessage)
  } catch (error) {
    setErrorMessage('Error al eliminar el usuario. Intente de nuevo.')
  } finally {
    setShowConfirmationModal(false)
    setLoading(false)
  }
}
