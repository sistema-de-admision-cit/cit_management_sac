import '../../../../assets/styles/global/modal.css'

const Modal = ({ children, onClose, className }) => (
  <div className='modal'>
    <div className={`modal-content ${className}`}>
      <span className='close-btn' onClick={onClose} aria-label='Close'>&times;</span>
      {children}
    </div>
  </div>
)

export default Modal
