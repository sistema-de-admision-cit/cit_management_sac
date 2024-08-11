// src/components/pages/UnauthorizedAccessPage.js
import React from 'react'
import { useNavigate } from 'react-router-dom'
import ErrorPage from '../templates/ErrorPage'

const UnauthorizedAccessPage = () => {
  const navigate = useNavigate()

  const goToHome = () => {
    navigate('/dashboard')
  }

  return (
    <ErrorPage
      title='Error 403'
      message='Lo sentimos, no tienes permiso para acceder a esta página.'
      buttonText='Ir a la página de inicio'
      onButtonClick={goToHome}
    />
  )
}

export default UnauthorizedAccessPage
