import axios from '../../../../../config/axiosConfig';

export const isEmailValid = (email) => {
  const emailRegex = /^[a-zA-Z0-9._%+-]+@ctpcit\.co\.cr$/;
  return emailRegex.test(email);
};

export const isFormValid = (formValues) => {
  const { email, role } = formValues;
  return email && role && role !== 'porDefecto';
};

export const handleSubmit = (formValues, setLoading, setErrorMessage, setSuccessMessage, fetchUsers, resetForm) => {
  const { email, role } = formValues;

  if (!email || !role || role === 'porDefecto') {
    setErrorMessage('Por favor, complete todos los campos.');
    return;
  }

  if (!isEmailValid(email)) {
    setErrorMessage('El correo electrónico debe terminar en "@ctpcit.co.cr".');
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

export const handleChange = (formValues, setFormValues, field, value) => {
  setFormValues({
    ...formValues,
    [field]: value
  });
};

export const fetchUsers = async (setUsers, setLoading, setErrorMessage) => {
  const getUsersUrl = import.meta.env.VITE_GET_USERS_ENDPOINT;
  setLoading(true);
  try {
    const response = await axios.get(getUsersUrl);
    setUsers(response.data);
  } catch (error) {
    setErrorMessage('Error al cargar la lista de usuarios.');
  } finally {
    setLoading(false);
  }
};

export const handleDeleteUser = async (email, fetchUsers, setSuccessMessage, setErrorMessage) => {
  const deleteUserUrl = import.meta.env.VITE_DELETE_USER_ENDPOINT;
  try {
    await axios.delete(`${deleteUserUrl}?email=${encodeURIComponent(email)}`);
    setSuccessMessage(`Usuario con correo ${email} eliminado`);
    fetchUsers(); 
  } catch (error) {
    setErrorMessage('Error al eliminar el usuario. Intente de nuevo.');
  }
};
