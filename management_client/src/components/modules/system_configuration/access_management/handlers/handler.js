import axios from '../../../../../config/axiosConfig'

export const isEmailValid = (email) => {
  const emailRegex = /^[a-zA-Z0-9._%+-]+@cit\.co\.cr$/;
  return emailRegex.test(email);
};


export const handleSubmit = (formValues, setLoading, setErrorMessage, setSuccessMessage,fetchUsers,resetForm) => {
  const { email, role } = formValues;

  if (!email || !role || role === 'Seleccionar Rol') {
    setErrorMessage('Por favor, complete todos los campos.');
    return;
  }

  if (!isEmailValid(email)) {
    setErrorMessage('El correo electrónico debe terminar en "@cit.co.cr".');
    return;
  }

  const sendingData = {
    username: email,
    role: role,
    password: 'campus'
  };

  const createUserUrl = import.meta.env.VITE_CREATE_USER_ENDPOINT;

  setLoading(true);

  axios.post(createUserUrl, sendingData, { timeout: 10000 })
  .then(response => {
    setSuccessMessage('Usuario creado correctamente.');
    fetchUsers();
    resetForm();
  })
  .catch(error => {
    setErrorMessage('Error al crear usuario. Por favor, intente de nuevo.');
  })
  .finally(() => {
    setLoading(false);
  });

};

export const isFormValid = (formValues) => {
  const { email, role } = formValues;
  return isEmailValid(email) && role && role !== 'Seleccionar Rol'; 
};
