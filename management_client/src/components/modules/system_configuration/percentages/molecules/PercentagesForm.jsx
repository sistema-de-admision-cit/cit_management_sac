// components/questions/molecules/PercentagesForm.jsx
import InputField from '../../../../core/global/atoms/InputField'
import Button from '../../../../core/global/atoms/Button'
import { handleSave, getSaveButtonState } from '../helpers/handlers'

const PercentagesForm = ({ formValues, handleChange }) => (
  <form onSubmit={(e) => e.preventDefault()}>
    <InputField
      field={{ name: 'academicExam', label: 'Académico', type: 'text', placeholder: '20', required: true }}
      value={formValues.academicExam}
      handleChange={(e) => handleChange('academicExam', e.target.value)}
      className='form-group'
    />
    <InputField
      field={{ name: 'daiExam', label: 'Psicológico', type: 'text', placeholder: '20', required: true }}
      value={formValues.daiExam}
      handleChange={(e) => handleChange('daiExam', e.target.value)}
      className='form-group'
    />
    <InputField
      field={{ name: 'englishExam', label: 'Inglés', type: 'text', placeholder: '20', required: true }}
      value={formValues.englishExam}
      handleChange={(e) => handleChange('englishExam', e.target.value)}
      className='form-group'
    />
    <Button
      className='btn btn-primary'
      onClick={handleSave(formValues)}
      disabled={!getSaveButtonState(formValues)}
    >
      Guardar
    </Button>
  </form>
)

export default PercentagesForm
