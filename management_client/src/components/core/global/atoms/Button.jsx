import '../../../../assets/styles/global/buttons.css'

const Button = ({ type, className, children, onClick, disabled, key }) => {
  return (
    <button key={key} type={type} className={className} onClick={onClick} disabled={disabled || false}>
      {children}
    </button>
  )
}

export default Button
