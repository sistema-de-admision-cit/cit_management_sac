import '../../../../../assets/styles/enrollments/enrollment-info-edit.css'
import Button from '../../../../core/global/atoms/Button'
import InputField from '../../../../core/global/atoms/InputField'
import DatePicker from '../../../../core/global/atoms/DatePicker'
import { statusOptions } from '../helpers/helpers'

const EnrollmentInfoEdit = ({ enrollment, onStatusChange, onDateChange, onWhatsappChange, setIsEditing }) => (
  <div className='tab-content enrollment-info-edit'>
    <h2 className='enrollment-title'>Inscripción - {enrollment.id}</h2>

    <InputField
      field={{
        type: 'dropdown',
        name: 'status',
        label: 'Estado',
        options: statusOptions
      }}
      value={enrollment.status}
      handleChange={(e) => onStatusChange(enrollment, e.target.value)}
      className='form-group'
    />

    <DatePicker
      label='Fecha del Examen'
      value={new Date(enrollment.examDate)}
      onChange={(date) => onDateChange(enrollment, date)}
      className='form-group'
    />

    <InputField
      field={{
        type: 'checkbox',
        name: 'whatsapp',
        label: 'Recibir notificación por WhatsApp'
      }}
      value={enrollment.whatsappNotification}
      handleChange={(e) => onWhatsappChange(enrollment, e.target.checked)}
      className='form-group'
    />

    <div className='button-container'>
      <Button
        className='btn btn-primary'
        ariaLabel='Guardar Cambios'
        onClick={() => setIsEditing(false)}
      >
        Guardar Cambios
      </Button>
    </div>
  </div>
)

export default EnrollmentInfoEdit
