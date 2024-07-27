import React from 'react'

import '../../../assets/styles/global/buttons.css'

const Button = ({ type, className, children, onClick }) => {
  return (
    <button type={type} className={className} onClick={onClick}>
      {children}
    </button>
  )
}

export default Button
