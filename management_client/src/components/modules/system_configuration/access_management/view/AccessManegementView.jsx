import { useState } from 'react'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import '../../../../../assets/styles/global/input-fields.css'
import '../../../../../assets/styles/global/view.css'
import Button from '../../../../core/global/atoms/Button'
import AccessManegementSection from '../organisms/AccessManagementSection'
import DeletedUsersTable from '../organisms/UserTable'
import AccessManegementSearchBar from '../molecules/AccessManegementSearchBar'
import useMessages from '../../../../core/global/hooks/useMessages'
import useFormState from '../../../../core/global/hooks/useFormState'
import { handleSubmit } from '../handlers/handler'

const AccessManagementView = () => {
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages()
  const [loading, setLoading] = useState(false)

  const { formData: formValues, setFormData: setFormValues } = useFormState({
    email: '',
    role: ''
  })

  const [deletedUsers, setDeletedUsers] = useState([
    { email: 'test1@ctpcit.co.cr', role: 'admin' },
    { email: 'test2@ctpcit.co.cr', role: 'user' },
    { email: 'test3@ctpcit.co.cr', role: 'user' },
    { email: 'test4@ctpcit.co.cr', role: 'admin' }
  ])

  const handleChange = (field, value) => {
    setFormValues({
      ...formValues,
      [field]: value
    })
  }

  const handleDeleteUser = (email) => {
    // Lógica para eliminar usuario por correo
    setDeletedUsers(deletedUsers.filter((user) => user.email !== email))
    setSuccessMessage(`Usuario con correo ${email} eliminado`)
  }

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
            onEmailChange={(e) => handleChange('email', e.target.value)}
            onRoleChange={(e) => handleChange('role', e.target.value)}
          />
          <div className='buttons'>
            <Button
              className='btn btn-primary'
              onClick={() => handleSubmit(formValues, setLoading, setErrorMessage, setSuccessMessage)}
              disabled={loading}
            >
              {loading ? 'Creando...' : 'Crear Usuario'}
            </Button>
          </div>
        </div>
        {/* Tabla de usuarios*/}
        <DeletedUsersTable deletedUsers={deletedUsers} onDeleteUser={handleDeleteUser} />
      </div>
      {renderMessages()}
    </SectionLayout>
  )
}

export default AccessManagementView
