// se agregan los parametros setFormData y setIsRegisterSuccess, pero no se utilizan solo se agregan para que no haya errores
export const onLoginSubmit = async (event, formData, setErrorMessage, setFormData, setIsRegisterSuccess) => {
  console.log(formData)
  // mostrar en consola los datos del formulario
  // evitar que el formulario se envie
  event.preventDefault()
  // mostrar mensaje de error
  setErrorMessage('Correo o contraseña incorrectos')
  // limpiar los campos del formulario
  setFormData({})
}

export const onRegisterSubmit = async (event, formData, setErrorMessage, setFormData, setIsRegisterSuccess) => {
  console.log(formData)
  // mostrar en consola los datos del formulario
  // evitar que el formulario se envie
  event.preventDefault()
  // mostrar mensaje de error
  setErrorMessage('El correo ya está en uso')
  // limpiar los campos del formulario
  setFormData({})
}
