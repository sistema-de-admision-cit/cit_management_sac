import '../../../../../assets/styles/enrollments/enrollment-info-edit.css'
import Button from '../../../../core/global/atoms/Button'
import InputField from '../../../../core/global/atoms/InputField'
import DatePicker from '../../../../core/global/atoms/DatePicker'
import { statusOptions, isCommentRequired, isEnabled } from '../helpers/helpers'
import { useState } from 'react'

const EnrollmentInfoEdit = ({ enrollment, setIsEditing, handleEnrollmentEdit }) => {
  const [formData, setFormData] = useState({
    enrollmentId: enrollment.id,
    status: enrollment.status,
    examDate: enrollment.examDate,
    whatsappNotification: enrollment.whatsappNotification,
    comment: '',
    changedBy: 1 // todo: get the current user id
  })

  return (
    <div className='tab-content enrollment-info-edit'>
      <h2 className='enrollment-title'>Inscripción - {enrollment.id}</h2>

      <form className='enrollment-form' onSubmit={(e) => handleEnrollmentEdit(e, formData, enrollment)}>
        <InputField
          field={{
            type: 'dropdown',
            name: 'status',
            label: 'Estado',
            options: statusOptions
          }}
          value={formData.status}
          handleChange={(e) => setFormData({ ...formData, status: e.target.value })}
          className='form-group'
        />

        <DatePicker
          label='Fecha del Examen'
          value={formData.examDate}
          onChange={(date) => setFormData({ ...formData, examDate: date })}
          className='form-group'
        />

        <InputField
          field={{
            type: 'checkbox',
            name: 'whatsapp',
            label: 'Recibir notificación por WhatsApp'
          }}
          value={formData.whatsappNotification}
          handleChange={(e) => setFormData({ ...formData, whatsappNotification: e.target.checked })}
          className='form-group'
        />

        {isCommentRequired(formData, enrollment) && (
          <InputField
            field={{
              type: 'textarea',
              name: 'comment',
              label: 'Motivo del Cambio',
              required: true
            }}
            required
            value={formData.comment}
            handleChange={(e) => setFormData({ ...formData, comment: e.target.value })}
            className='form-group'
          />
        )}

        <div className='button-container'>
          <Button
            className='btn btn-primary'
            ariaLabel='Guardar Cambios'
            disabled={!isEnabled(formData, enrollment)}
          >
            Guardar Cambios
          </Button>
        </div>
      </form>
    </div>
  )
}

export default EnrollmentInfoEdit
