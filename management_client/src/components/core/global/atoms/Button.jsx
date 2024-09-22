import '../../../../assets/styles/global/buttons.css'

const Button = ({ type, className, children, onClick, disabled, ariaLabel }) => {
  return (
    <button type={type} className={className} onClick={onClick} disabled={disabled || false} aria-label={ariaLabel}>
      {children}
    </button>
  )
}

export default Button
