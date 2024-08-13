// se agregan los parametros setFormData y setIsRegisterSuccess, pero no se utilizan solo se agregan para que no haya errores
export const handleSubmit = async (event, formData, setErrorMessage, setFormData) => {
  console.log(formData)
  // mostrar en consola los datos del formulario
  // evitar que el formulario se envie
  event.preventDefault()
  // mostrar mensaje de error
  setErrorMessage('Correo o contraseña incorrectos')
  // limpiar los campos del formulario
  setFormData({})
}
