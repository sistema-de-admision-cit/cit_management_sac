import React from 'react'
import RoleSelector from '../molecules/RoleSelector'
import EmailInput from '../molecules/EmailInput'

const AccessManagementSection = ({ email, role, onEmailChange, onRoleChange }) => (
  <div className="access-management-section">
     <div className="form-group">
      <RoleSelector
        value={role}
        onChange={onRoleChange}
      />
    </div>
    <div className="form-group">
      <EmailInput
        field={{ type: 'email', name: 'email', label: 'Correo Electrónico' }}
        value={email}
        handleChange={onEmailChange}
      />
    </div>
  </div>
);

export default AccessManagementSection;

