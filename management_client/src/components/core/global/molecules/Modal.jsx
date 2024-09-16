import '../../../../assets/styles/global/modal.css'

const Modal = ({ children, onClose }) => (
  <div className='modal'>
    <div className='modal-content'>
      <span className='close-btn' onClick={onClose} aria-label='Close'>&times;</span>
      {children}
    </div>
  </div>
)

export default Modal
