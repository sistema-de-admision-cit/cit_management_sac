import InputField from '../../../../core/global/atoms/InputField'

const EmailInput = ({ email, onEmailChange }) => {
  const emailField = {
    type: 'email',
    name: 'email',
    label: 'Correo Electrónico',
    placeholder: 'ejemplo@ctpcit.co.cr',
    required: true
  }

  return (
    <div className='email-input'>
      <InputField
        field={{type:'email', name: 'email', label:'Correo Electrónico', placeholder:'ejemplo@ctpcit.co.cr', required: true}}
        value={email}
        handleChange={(e) => onEmailChange(e.target.value)}
        className='email-input' 
        autoComplete='email'
      />
    </div>
  )
}

export default EmailInput
