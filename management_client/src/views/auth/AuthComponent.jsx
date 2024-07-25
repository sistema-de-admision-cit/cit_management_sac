import React from 'react'
import PopupComponent from '../../popups/PopupComponent'
import AuthHeader from '../../components/auth/organisms/AuthHeader'
import AuthContent from '../../components/auth/organisms/AuthContent'

import '../../assets/styles/global.css'

import '../../assets/styles/auth/wrap.css'

const AuthComponent = ({ formData, setFormData, onSubmit, fields, sectionName, isRegisterSuccess, setIsRegisterSuccess, errorMessage, setErrorMessage }) => {
  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value })
  }

  const handleSubmit = (event) => {
    event.preventDefault()
    onSubmit(event, formData, setErrorMessage, setFormData, setIsRegisterSuccess)
  }

  return (
    <div className='wrap'>
      <AuthHeader sectionName={sectionName} />
      <AuthContent
        isRegisterSuccess={isRegisterSuccess}
        fields={fields}
        formData={formData}
        handleChange={handleChange}
        onSubmit={handleSubmit}
        sectionName={sectionName}
      />
      {errorMessage && <PopupComponent message={errorMessage} onClose={() => setErrorMessage('')} type='error' />}
    </div>
  )
}

export default AuthComponent
