import QuestionForm from '../molecules/QuestionForm'
import Button from '../../../../core/global/atoms/Button'

const QuestionConfigurator = ({ formValues, handleChange, onSave, loading }) => (
  <div>
    <QuestionForm formValues={formValues} handleChange={handleChange} />
    <Button onClick={onSave} disabled={loading}>
      {loading ? 'Guardando...' : 'Guardar'}
    </Button>
  </div>
)

export default QuestionConfigurator
