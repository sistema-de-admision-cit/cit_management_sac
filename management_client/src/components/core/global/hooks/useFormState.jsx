import { useState } from 'react'

const useFormState = (initialState = null) => {
  const [formData, setFormData] = useState(initialState)
  const resetForm = () => setFormData(initialState)
  return { formData, setFormData, resetForm }
}

export default useFormState
