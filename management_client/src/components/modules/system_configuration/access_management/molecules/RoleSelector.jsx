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
      <option value='A'>Administrador</option>
      <option value='S'>Super Usuario</option>
      <option value='T'>Profesor</option>
      <option value='P'>Psicólogo</option>
    </InputField>
  )
}

export default RoleSelect
