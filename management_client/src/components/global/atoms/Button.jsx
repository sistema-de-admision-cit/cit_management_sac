import '../../../assets/styles/global/buttons.css'

const Button = ({ type, className, children, onClick, disabled }) => {
  return (
    <button type={type} className={className} onClick={onClick} disabled={disabled || false}>
      {children}
    </button>
  )
}

export default Button
