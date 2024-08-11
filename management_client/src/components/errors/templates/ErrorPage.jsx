// src/components/global/templates/ErrorPage.js
import React from 'react'
import Button from '../../global/atoms/Button'
import '../../../assets/styles/errors/error.css'

const ErrorPage = ({ title, message, buttonText, onButtonClick }) => {
  return (
    <div className='error-container'>
      <title>{title}</title>
      <h1 className='error-title'>{title}</h1>
      <p className='error-message'>{message}</p>
      <div className='button-container'>
        <Button className='btn btn-primary' onClick={onButtonClick}>
          {buttonText}
        </Button>
      </div>
    </div>
  )
}

export default ErrorPage
