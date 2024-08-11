import React from 'react'
import { useNavigate } from 'react-router-dom'
import ErrorPage from '../templates/ErrorPage'
import { goToHome } from '../handlers/handlers'

const NotFoundPage = () => {
  const navigate = useNavigate()

  return (
    <ErrorPage
      title='Error 404'
      message='Lo sentimos, la página que buscas no existe.'
      buttonText='Ir a la página de inicio'
      onButtonClick={goToHome(navigate)}
    />
  )
}

export default NotFoundPage
