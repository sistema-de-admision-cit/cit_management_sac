import Button from '../../../../core/global/atoms/Button'
import InputField from '../../../../core/global/atoms/InputField'
import DatePicker from '../../../../core/global/atoms/DatePicker'
import { statusOptions } from '../helpers/helpers'

const EnrollmentInfoEdit = ({ enrollment, onStatusChange, onDateChange, onWhatsappChange, setIsEditing }) => (
  <div className='tab-content'>
    <h2>Inscripción - {enrollment.id}</h2>
    <div>
      <strong>Estado:</strong>
      <InputField
        field={{
          type: 'dropdown',
          name: 'status',
          options: statusOptions,
          required: true
        }}
        value={enrollment.status}
        handleChange={(e) => onStatusChange(enrollment, e.target.value)}
        showLabel={false}
      />
    </div>
    <div>
      <strong>Fecha del Examen:</strong>
      <DatePicker
        value={new Date(enrollment.examDate)}
        onChange={(date) => onDateChange(enrollment, date)}
      />
    </div>
    <div>
      <strong>Notificación por WhatsApp:</strong>
      <InputField
        field={{
          type: 'checkbox',
          name: 'whatsapp',
          required: true
        }}
        value={enrollment.whatsappNotification}
        handleChange={(e) => onWhatsappChange(enrollment, e.target.checked)}
        showLabel={false}
      />
    </div>
    <Button onClick={() => setIsEditing(false)}>Guardar Cambios</Button>
  </div>
)

export default EnrollmentInfoEdit
