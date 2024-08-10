import React from 'react'
import { useNavigate } from 'react-router-dom'
import Button from '../global/atoms/Button'
import '../../assets/styles/errors/error.css'

const UnauthorizedAccessPage = () => {
  const navigate = useNavigate()

  const goToHome = () => {
    navigate('/dashboard')
  }

  return (
    <div className='error-container'>
      <h1 className='error-title'>Error 403</h1>
      <p className='error-message'>Lo sentimos, no tienes permiso para acceder a esta página.</p>
      <div className='button-container'>
        <Button className='btn btn-primary' onClick={goToHome}>
          Ir a la página de inicio
        </Button>
      </div>
    </div>
  )
}

export default UnauthorizedAccessPage
