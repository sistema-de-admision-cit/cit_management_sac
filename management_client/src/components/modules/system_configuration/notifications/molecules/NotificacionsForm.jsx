import InputField from '../../../../core/global/atoms/InputField'
import Button from '../../../../core/global/atoms/Button'
import { getSaveButtonState } from '../helpers/handlers'
import '../../../../../assets/styles/notification/config.css'

const NotificationForm = ({ formValues, handleChange, onSave, loading }) => (
  <form onSubmit={(e) => e.preventDefault()}>
    <div className='form-row'>
      <InputField
        field={{ name: 'email_contact', label: 'Correo Electrónico de Consultas', type: 'text', placeholder: 'ejemplo@ctpcit.com', required: true }}
        value={formValues.email_contact}
        handleChange={(e) => handleChange('email_contact', e.target.value)}
        className='form-group'
      />
      <InputField
        field={{ name: 'email_notification_contact', label: 'Correo Electrónico de Notificaciones', type: 'text', placeholder: 'ejemplo@ctpcit.com', required: true }}
        value={formValues.email_notification_contact}
        handleChange={(e) => handleChange('email_notification_contact', e.target.value)}
        className='form-group'
      />
    </div>
    <div className='form-row'>
      <InputField
        field={{ name: 'whatsapp_contact', label: 'Número de WhastApp', type: 'text', placeholder: '88887777', required: true }}
        value={formValues.whatsapp_contact}
        handleChange={(e) => handleChange('whatsapp_contact', e.target.value)}
        className='form-group'
      />
      <InputField
        field={{ name: 'office_contact', label: 'Número de Contacto', type: 'text', placeholder: '88886666', required: true }}
        value={formValues.office_contact}
        handleChange={(e) => handleChange('office_contact', e.target.value)}
        className='form-group'
      />
    </div>
    <div className='form-row'>
      <InputField
        field={{ name: 'instagram_contact', label: 'Página de Instagram', type: 'text', placeholder: 'complejoeducativocit', required: true }}
        value={formValues.instagram_contact}
        handleChange={(e) => handleChange('instagram_contact', e.target.value)}
        className='form-group'
      />
      <InputField
        field={{ name: 'facebook_contact', label: 'Página de Facebook', type: 'text', placeholder: 'complejoeducativocit', required: true }}
        value={formValues.facebook_contact}
        handleChange={(e) => handleChange('facebook_contact', e.target.value)}
        className='form-group'
      />
    </div>

    <Button
      className='btn btn-primary'
      onClick={onSave}
      disabled={!getSaveButtonState(formValues)} 
    >
      {loading ? 'Guardando...' : 'Guardar'}
    </Button>
  </form>
);

export default NotificationForm
