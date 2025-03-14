import InputField from '../../../../core/global/atoms/InputField'

const UsernameInput = ({ realUsername, onUsernameChange }) => {
  const UsernameField = {
    type: 'realUsername',
    name: 'realUsername',
    label: 'Nombre Usuario',
    required: true
  }

  return (
    <div className='realUsername-input'>
      <InputField
        field={{ type: 'realUsername', name: 'realUsername', label: 'Nombre Usuario', placeholder: 'Ingresa Nombre de Usuario', required: true }}
        value={realUsername}
        handleChange={(e) => onUsernameChange(e.target.value)}
        className='realUsername-input'
        autoComplete='realUsername'
      />
    </div>
  )
}

export default UsernameInput
