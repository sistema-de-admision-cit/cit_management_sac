import InputField from '../../../../core/global/atoms/InputField'
import Button from '../../../../core/global/atoms/Button'
import '../../../../../assets/styles/notification/config.css'

const SendNotifications = ({ handleChange, onSave, loading }) => (
  <form onSubmit={(e) => e.preventDefault()}>
    <InputField
      field={{ name: 'notification_type', label: 'Tipo de notificación', type: 'select', placeholder: 'Seleccione un tipo de notificacion', required: true }}
      handleChange={(e) => handleChange('notification_type', e.target.value)}
      className='form-group'
    />
    <InputField
      field={{ name: 'notification_text', label: 'Mensaje a enviar', type: 'textArea', placeholder: 'Escriba el mensaje a enviar', required: true }}
      handleChange={(e) => handleChange('notification_text', e.target.value)}
      className='form-group'
    />
    <Button
      className='btn btn-primary'
      onClick={onSave}
    >
      {loading ? 'Enviando...' : 'Enviar'}
    </Button>
  </form>
)

export default SendNotifications


/* div agregar en el view.
<div className='notifications-configurator'>
          <p className='description'><b>Notificaciones.</b></p>
          <SendNotificacions
            handleChange={handleChange}
            loading={loading}
          />
        </div>
*/