import React from 'react'
import RoleSelector from '../molecules/RoleSelector'
import EmailInput from '../molecules/EmailInput'
import UsernameInput from '../molecules/UsernameInput'

const AccessManagementSection = ({ email, realUsername, role, onEmailChange, onUsernameChange, onRoleChange }) => (
  <div className='access-management-section'>
    <div className='form-group'>
      <RoleSelector
        role={role}
        onRoleChange={onRoleChange}
      />
    </div>
    <div className='form-group'>
      <EmailInput
        field={{ type: 'email', name: 'email', label: 'Correo Electrónico' }}
        email={email}
        onEmailChange={onEmailChange}
      />
    </div>
    <div className='form-group'>
      <UsernameInput
        field={{ type: 'realUsername', name: 'realUsername', label: 'Nombre Usuario' }}
        realUsername={realUsername}
        onUsernameChange={onUsernameChange}
      />
    </div>
  </div>
)

export default AccessManagementSection
