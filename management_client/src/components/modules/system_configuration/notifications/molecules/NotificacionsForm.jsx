import InputField from '../../../../core/global/atoms/InputField'
import Button from '../../../../core/global/atoms/Button'
import { getSaveButtonState } from '../helpers/handlers'

const PercentagesForm = ({ formValues, handleChange, onSave, loading }) => (
  <form onSubmit={(e) => e.preventDefault()}>
    <InputField
      field={{ name: 'queryEmail', label: 'Correo Electronico de consultas', type: 'text', placeholder: 'ejemplo@ctpcit.com', required: true }}
      value={formValues.queryEmail}
      handleChange={(e) => handleChange('queryEmail', e.target.value)}
      className='form-group'
    />
    <InputField
      field={{ name: 'notificationsEmail', label: 'Correo Electronico de notificaciones', type: 'text', placeholder: 'ejemplo@ctpcit.com', required: true }}
      value={formValues.notificationsEmail}
      handleChange={(e) => handleChange('notificationsEmail', e.target.value)}
      className='form-group'
    />
    <InputField
      field={{ name: 'whatsapp', label: 'Número de WhastApp', type: 'text', placeholder: '88887777', required: true }}
      value={formValues.whatsapp}
      handleChange={(e) => handleChange('whatsapp', e.target.value)}
      className='form-group'
    />
    <InputField
      field={{ name: 'contactPhoneNumber', label: 'Teléfono de Contacto', type: 'text', placeholder: '88886666', required: true }}
      value={formValues.contactPhoneNumber}
      handleChange={(e) => handleChange('contactPhoneNumber', e.target.value)}
      className='form-group'
    /> 
    <InputField
      field={{ name: 'instagram', label: 'Página de Instagram', type: 'text', placeholder: 'complejoeducativocit', required: true }}
      value={formValues.instagram}
      handleChange={(e) => handleChange('instagram', e.target.value)}
      className='form-group'
    />
    <InputField
      field={{ name: 'facebook', label: 'Página de Facebook', type: 'text', placeholder: 'complejoeducativocit', required: true }}
      value={formValues.facebook}
      handleChange={(e) => handleChange('facebook', e.target.value)}
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
