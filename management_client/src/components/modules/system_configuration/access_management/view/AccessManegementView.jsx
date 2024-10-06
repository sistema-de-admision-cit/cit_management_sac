import { useEffect, useState } from 'react'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import '../../../../../assets/styles/global/input-fields.css'
import '../../../../../assets/styles/global/view.css'
import Button from '../../../../core/global/atoms/Button'
import AccessManegementSection from '../organisms/AccessManagementSection'
import DeletedUsersTable from '../organisms/UserTable'
import AccessManegementSearchBar from '../molecules/AccessManegementSearchBar'
import useMessages from '../../../../core/global/hooks/useMessages'
import useFormState from '../../../../core/global/hooks/useFormState'
import { handleSubmit, handleChange, fetchUsers, handleDeleteUser, isFormValid } from '../handlers/handler'

const AccessManagementView = () => {
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages();
  const [loading, setLoading] = useState(false);
  const [users, setUsers] = useState([]);

  const { formData: formValues, setFormData: setFormValues } = useFormState({
    email: '',
    role: ''
  });

  useEffect(() => {
    fetchUsers(setUsers, setLoading, setErrorMessage);
  }, []);

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
            onEmailChange={(value) => handleChange(formValues, setFormValues, 'email', value)}
            onRoleChange={(value) => handleChange(formValues, setFormValues, 'role', value)}
          />
          <div className='buttons'>
            <Button
              className='btn btn-primary'
              onClick={() => handleSubmit(
                formValues,
                setLoading,
                setErrorMessage,
                setSuccessMessage,
                () => fetchUsers(setUsers, setLoading, setErrorMessage),
                () => setFormValues({ email: '', role: '' })
              )}
              disabled={loading || !isFormValid(formValues)}
            >
              {loading ? 'Creando...' : 'Crear Usuario'}
            </Button>
          </div>
        </div>
        <DeletedUsersTable deletedUsers={users} onDeleteClick={(email) => handleDeleteUser(
          email,
          () => fetchUsers(setUsers, setLoading, setErrorMessage),
          setSuccessMessage,
          setErrorMessage
        )} />
      </div>
      {renderMessages()}
    </SectionLayout>
  );
};

export default AccessManagementView;
