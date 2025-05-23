import '../../../../assets/styles/popup/popup.css'
import PopupIcon from '../atoms/PopupIcon.jsx'

const PopupComponent = ({ message, onClose, type, messageTitle }) => {
  const popupClass = type === 'confirmation' ? 'confirmation' : 'error'
  const buttonClass = type === 'error' ? 'btn-danger' : 'btn-success'
  const textClass = type === 'error' ? 'text-danger' : 'text-success'
  const title = type === 'error' ? 'Error' : 'Éxito'

  return (
    <div className='overlay'>
      <div className={`popup ${popupClass}`}>
        <span className='close-btn' onClick={onClose} aria-label='Close'>&times;</span>
        <div className='popup-icon'>
          <PopupIcon type={type} />
        </div>
        <h4 className={`message-title ${textClass}`}>
          {messageTitle || title}
        </h4>
        <p className='message'>{message}</p>
        <button className={`btn ${buttonClass}`} onClick={onClose}>
          Ok
        </button>
      </div>
    </div>
  )
}

export default PopupComponent
