import '../../../../assets/styles/popup/confirmation-modal.css'
import Button from '../../../core/global/atoms/Button'

const ConfirmationModal = ({ onClose, onConfirm, title, message, extraMessage, confirmLabel, cancelLabel }) => {
  return (
    <div className='modal-overlay'>
      <div className='modal-content'>
        <h2 className='modal-title'>{title}</h2>
        <p className='modal-text'>{message}</p>
        {extraMessage && <p id='extra-text'>{extraMessage}</p>}
        <div className='button-container'>
          <Button className='btn btn-cancel' onClick={onClose}>{cancelLabel}</Button>
          <Button className='btn btn-danger' onClick={onConfirm}>{confirmLabel}</Button>
        </div>
      </div>
    </div>
  )
}

export default ConfirmationModal
