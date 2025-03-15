import { useEffect, useState } from 'react'
import SectionLayout from '../../../../core/global/molecules/SectionLayout'
import '../../../../../assets/styles/global/input-fields.css'
import '../../../../../assets/styles/global/view.css'
import Button from '../../../../core/global/atoms/Button'
import AccessManegementSection from '../organisms/AccessManagementSection'
import DeletedUsersTable from '../organisms/UserTable'
import useMessages from '../../../../core/global/hooks/useMessages'
import useFormState from '../../../../core/global/hooks/useFormState'
import { handleSubmit, fetchUsers, isFormValid, handleDeleteUser, confirmDeleteUser } from '../handlers/handler'
import { useAuth } from '../../../../../router/AuthProvider' // Importa el contexto de autenticación
import ConfirmationModal from '../../../../ui/confirmation_modal/view/ConfirmationModal'

const AccessManagementView = () => {
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages()
  const [loading, setLoading] = useState(false)
  const [users, setUsers] = useState([])
  const [showConfirmationModal, setShowConfirmationModal] = useState(false) // Estado para mostrar/ocultar modal de confirmación
  const [pendingDeleteEmail, setPendingDeleteEmail] = useState('') // Email del usuario a eliminar

  const { formData: formValues, setFormData: setFormValues } = useFormState({
    email: '',
    realUsername: '',
    role: ''
  })

  const { user } = useAuth() // Accede al contexto de autenticación
  const currentUserEmail = user.name // Obtén el correo electrónico del usuario en sesión

  useEffect(() => {
    fetchUsers(setUsers, setLoading, setErrorMessage)
  }, [])

  const handleChange = (field, value) => {
    setFormValues({
      ...formValues,
      [field]: value
    })
  }

  const handleConfirmDelete = () => {
    confirmDeleteUser(
      pendingDeleteEmail,
      fetchUsers,
      setSuccessMessage,
      setErrorMessage,
      setShowConfirmationModal,
      setUsers, // Pasamos setUsers para actualizar la lista
      setLoading // Pasamos setLoading para manejar el estado de cargando
    )
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
            realUsername={formValues.realUsername}
            role={formValues.role}
            onEmailChange={(value) => handleChange('email', value)}
            onUsernameChange={(value) => handleChange('realUsername', value)}
            onRoleChange={(value) => handleChange('role', value)}
          />
          <div className='buttons'>
            <Button
              className='btn btn-primary'
              onClick={() => handleSubmit(formValues, setLoading, setErrorMessage, setSuccessMessage, fetchUsers, setUsers, () => setFormValues({ email: '', role: '' }))} // Pasamos setUsers aquí
              disabled={loading || !isFormValid(formValues)}
            >
              {loading ? 'Creando...' : 'Crear Usuario'}
            </Button>
          </div>
        </div>
        {/* Tabla de usuarios */}
        <DeletedUsersTable
          deletedUsers={users}
          onDeleteClick={(email) => handleDeleteUser(email, fetchUsers, setSuccessMessage, setErrorMessage, currentUserEmail, setShowConfirmationModal, setPendingDeleteEmail)} // Pasa el email del usuario en sesión
          currentUserEmail={currentUserEmail} // Pasa también el email para evitar borrar el usuario en sesión
        />
      </div>
      {renderMessages()}

      {/* Modal de confirmación */}
      {showConfirmationModal && (
        <ConfirmationModal
          title='¿Estás seguro?'
          message={`¿Deseas eliminar al usuario con correo ${pendingDeleteEmail}?`}
          onClose={() => setShowConfirmationModal(false)}
          onConfirm={handleConfirmDelete}
          extraMessage='Una vez eliminada, deberas pedirle a un administrador que la restaure.'
          cancelLabel='Conservar'
          confirmLabel='Eliminar'
        />
      )}
    </SectionLayout>
  )
}

export default AccessManagementView
