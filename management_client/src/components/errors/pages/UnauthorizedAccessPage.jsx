import React from 'react'
import { useNavigate } from 'react-router-dom'
import ErrorPage from '../templates/ErrorPage'
import { goToHome } from '../handlers/handlers'

const UnauthorizedAccessPage = () => {
  const navigate = useNavigate()

  return (
    <ErrorPage
      title='Error 403'
      message='Lo sentimos, no tienes permiso para acceder a esta página.'
      buttonText='Ir a la página de inicio'
      onButtonClick={goToHome(navigate)}
    />
  )
}

export default UnauthorizedAccessPage
