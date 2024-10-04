// New email validation handler
export const isEmailValid = (email) => {
  const emailRegex = /^[a-zA-Z0-9._%+-]+@ctpcit\.co\.cr$/;
  return emailRegex.test(email);
};

// Handle form submission for just email and role
export const handleSubmit = (formValues, setLoading, setErrorMessage, setSuccessMessage) => {
  const { email, role } = formValues;

  // Check if email and role are provided
  if (!email || !role || role === 'Seleccionar Rol') {
    setErrorMessage('Por favor, complete todos los campos.');
    return;
  }

  // Check if email is valid
  if (!isEmailValid(email)) {
    setErrorMessage('El correo electrónico debe terminar en "@ctpcit.co.cr".');
    return;
  }

  // Simulating data to send
  const sendingData = {
    email,
    role
  };

  console.log(sendingData);
  console.log('Simulando envío de datos al servidor...');
  
  setLoading(true);

  // Simulate success response after 2 seconds
  setTimeout(() => {
    console.log('Datos simulados enviados correctamente.');
    setSuccessMessage('Usuario creado correctamente.');
    setLoading(false);
  }, 2000);
};

export const isFormValid = (formValues) => {
  const { email, role } = formValues;
  return isEmailValid(email) && role && role !== 'Seleccionar Rol'; 
};
