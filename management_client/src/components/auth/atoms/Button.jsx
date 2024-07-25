import React from 'react'

import '../../../assets/styles/auth/buttons.css'

const Button = ({ type, className, children, onClick }) => {
  return (
    <button type={type} className={className} onClick={onClick}>
      {children}
    </button>
  )
}

export default Button
