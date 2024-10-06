import { useEffect, useState } from 'react';
import SectionLayout from '../../../../core/global/molecules/SectionLayout';
import '../../../../../assets/styles/global/input-fields.css';
import '../../../../../assets/styles/global/view.css';
import Button from '../../../../core/global/atoms/Button';
import AccessManegementSection from '../organisms/AccessManagementSection';
import DeletedUsersTable from '../organisms/UserTable';
import AccessManegementSearchBar from '../molecules/AccessManegementSearchBar'
import useMessages from '../../../../core/global/hooks/useMessages';
import useFormState from '../../../../core/global/hooks/useFormState';
import { handleSubmit } from '../handlers/handler';
import axios from '../../../../../config/axiosConfig'; 

const AccessManagementView = () => {
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages()
  const [loading, setLoading] = useState(false)
  const [users, setUsers] = useState([]);

  const { formData: formValues, setFormData: setFormValues } = useFormState({
    email: '',
    role: ''
  })

  const getUsersUrl = import.meta.env.VITE_GET_USERS_ENDPOINT;
  const deleteUserUrl = import.meta.env.VITE_DELETE_USER_ENDPOINT;

  const fetchUsers = async () => {
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

  useEffect(() => {
    fetchUsers();
  }, []);

  const handleChange = (field, value) => {
    setFormValues({
      ...formValues,
      [field]: value
    })
  }

  const handleDeleteUser = async (email) => {
    try {
      await axios.delete(`${deleteUserUrl}?email=${encodeURIComponent(email)}`);
      setSuccessMessage(`Usuario con correo ${email} eliminado`);
      fetchUsers(); 
    } catch (error) {
      setErrorMessage('Error al eliminar el usuario. Intente de nuevo.');
    }
  };

  return (
    <SectionLayout title='Gestión de Acceso'>
      <div className='container'>
        <h1>Configuración del sistema</h1>
        <p className='description'>
          Esta pestaña permite dar accesos a nuevos usuarios. Cada vez que se agrega un nuevo usuario, por defecto tendrá la contraseña "campus", la cual deberá ser sustituida la primera vez que se inicie la sesión.
        </p>
        <div className='access-management'>
          <AccessManegementSection
            email={formValues.email}
            role={formValues.role}
            onEmailChange={(value) => { handleChange('email', value)}}
            onRoleChange={(value) => {handleChange('role', value);}}
          />
          <div className='buttons'>
            <Button
              className='btn btn-primary'
              onClick={() => handleSubmit(formValues, setLoading, setErrorMessage, setSuccessMessage,fetchUsers,() =>  setFormValues({ email: '', role: '' }))}
              disabled={loading}
            >
              {loading ? 'Creando...' : 'Crear Usuario'}
            </Button>
          </div>
        </div>
        {/* Tabla de usuarios*/}
        <DeletedUsersTable deletedUsers={users} onDeleteClick={handleDeleteUser} />
      </div>
      {renderMessages()}
    </SectionLayout>
  )
}

export default AccessManagementView
