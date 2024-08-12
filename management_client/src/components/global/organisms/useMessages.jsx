// useMessages.js
import { useState } from 'react'
import PopupComponent from '../../popups/PopupComponent'

const useMessages = () => {
  const [errorMessage, setErrorMessage] = useState('')
  const [successMessage, setSuccessMessage] = useState('')

  const renderMessages = () => (
    <>
      {errorMessage && <PopupComponent message={errorMessage} onClose={() => setErrorMessage('')} type='error' />}
      {successMessage && <PopupComponent message={successMessage} onClose={() => setSuccessMessage('')} type='confirmation' />}
    </>
  )

  return { setErrorMessage, setSuccessMessage, renderMessages }
}

export default useMessages
