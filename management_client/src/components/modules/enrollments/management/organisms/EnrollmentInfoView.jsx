import Button from '../../../../core/global/atoms/Button'
import PdfIcon from '../../../../../assets/icons/pdf-svgrepo-com.svg'
import EditIcon from '../../../../../assets/icons/pencil-svgrepo-com.svg'
import { formatDate, statusText } from '../helpers/helpers'

const PENDING_STATUS = 'PENDING'
const ELIGIBLE_STATUS = 'ELIGIBLE'

const EnrollmentInfoView = ({ enrollment, onDocClick, student, setIsEditing }) => {
  const [year, month, day] = enrollment.examDate.split('-')

  return (
    <div className='tab-content'>
      <p><strong>Estado:</strong> {statusText[enrollment.status]}</p>
      <p><strong>Fecha del Examen:</strong> {formatDate(new Date(year, month - 1, day))}</p>
      <p><strong>Promedio de Notas:</strong> {enrollment.student.previousGrades ?? 'N/A'}</p>
      <p><strong>Notificación por WhatsApp:</strong> {enrollment.whatsappNotification ? 'Sí' : 'No'}</p>
      <p><strong>Consentimiento Informado:</strong> {enrollment.consentGiven ? 'Aceptado' : 'No Aceptado'}</p>

      <div
        className='edit-icon'
        title={enrollment.status !== PENDING_STATUS && enrollment.status !== ELIGIBLE_STATUS ? `Edición no disponible: el estado actual es ${statusText[enrollment.status]}, lo que impide modificar la información de este usuario.` : ''}
      >
        <Button
          className={`edit-icon pseudo-btn ${enrollment.status !== PENDING_STATUS && enrollment.status !== ELIGIBLE_STATUS ? 'disabled' : 'enabled'}`}
          onClick={() => setIsEditing(true)}
          disabled={enrollment.status !== PENDING_STATUS && enrollment.status !== ELIGIBLE_STATUS}
        >
          <img src={EditIcon} alt='icono de editar' />
        </Button>
        {enrollment.status !== PENDING_STATUS && enrollment.status !== ELIGIBLE_STATUS && (
          <div className='tooltip'>
            Edición no disponible: el estado del usuario es {statusText[enrollment.status]}, lo que impide modificar la información.
          </div>
        )}
        {enrollment.status === PENDING_STATUS || enrollment.status === ELIGIBLE_STATUS && (
          <div className='tooltip'>
            Haga clic para editar la información del usuario.
          </div>
        )}
      </div>

      <div className='documents-sections'>
        <div className='document-item'>
          <p><strong>Documento de Notas:</strong></p>
          <Button
            className='pdf-icon' onClick={() => { onDocClick(enrollment.documents?.find((doc) => doc.documentType === 'OT'), 'OT') }}
          >
            <img src={PdfIcon} alt='logo de un pdf' />
          </Button>
        </div>
        {student.hasAccommodations && (
          <div className='document-item'>
            <p><strong>Documento de Adaptaciones:</strong></p>
            <Button
              className='pdf-icon' onClick={() => { onDocClick(enrollment.documents?.find((doc) => doc.documentType === 'AC'), 'AC') }}
            >
              <img src={PdfIcon} alt='logo de un pdf' />
            </Button>
          </div>
        )}
      </div>
    </div>
  )
}

export default EnrollmentInfoView
