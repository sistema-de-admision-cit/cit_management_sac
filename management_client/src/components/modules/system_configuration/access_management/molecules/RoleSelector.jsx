import InputField from '../../../../core/global/atoms/InputField'

const RoleSelect = ({ role, onRoleChange }) => {
  return (
    <InputField
      field={{ name: 'role', label: 'Rol', type: 'select', required: true }}
      value={role}
      handleChange={(e) => onRoleChange(e.target.value)}
      className='input-field'
    >
      <option value='porDefecto'>Seleccione un rol para el usuario</option>
      <option value='ADMIN'>Administrador</option>
      <option value='SYS'>Super Usuario</option>
      <option value='TEACHER'>Profesor</option>
      <option value='PSYCHOLOGIST'>Psicólogo</option>
    </InputField>
  )
}

export default RoleSelect
