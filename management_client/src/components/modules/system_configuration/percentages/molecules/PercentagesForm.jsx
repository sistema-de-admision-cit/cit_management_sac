import InputField from '../../../../core/global/atoms/InputField'
import Button from '../../../../core/global/atoms/Button'
import { getSaveButtonState } from '../helpers/handlers'

const PercentagesForm = ({ formValues, handleChange, onSave, loading }) => (
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
      onClick={onSave}
      disabled={loading || !getSaveButtonState(formValues)}
    >
      {loading ? 'Guardando...' : 'Guardar'}
    </Button>
  </form>
)

export default PercentagesForm
