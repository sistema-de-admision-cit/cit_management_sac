import Button from '../../../../core/global/atoms/Button'
import PdfIcon from '../../../../../assets/icons/pdf-svgrepo-com.svg'
import EditIcon from '../../../../../assets/icons/pencil-svgrepo-com.svg'

const EnrollmentInfoView = ({ enrollment, onDocClick, student, setSelectedFileType, setIsEditing }) => (
  <div className='tab-content'>
    <h2>Inscripción - {enrollment.id}</h2>
    <p><strong>Estado:</strong> {enrollment.status}</p>
    <p><strong>Fecha del Examen:</strong> {enrollment.examDate}</p>
    <p><strong>Notificación por WhatsApp:</strong> {enrollment.whatsappNotification ? 'Sí' : 'No'}</p>
    <p><strong>Consentimiento:</strong> {enrollment.consentGiven ? 'Dado' : 'No Dado'}</p>

    <Button className='edit-icon pseudo-btn' onClick={() => setIsEditing(true)} disabled={enrollment.status !== 'P' && enrollment.status !== 'E'}>
      <img src={EditIcon} alt='icono de editar' />
    </Button>

    <div className='documents-sections'>
      <div className='document-item'>
        <p><strong>Documento de Notas:</strong></p>
        <Button
          className='pdf-icon' onClick={() => {
            onDocClick(enrollment.document.find((doc) => doc.documentType === 'OT'))
            setSelectedFileType('Documento de Notas')
          }}
        >
          <img src={PdfIcon} alt='logo de un pdf' />
        </Button>
      </div>
      {student.hasAccommodations && (
        <div className='document-item'>
          <p><strong>Documento de Adaptaciones:</strong></p>
          <Button
            className='pdf-icon' onClick={() => {
              onDocClick(enrollment.document.find((doc) => doc.documentType === 'HC'))
              setSelectedFileType('Documento de Adaptaciones')
            }}
          >
            <img src={PdfIcon} alt='logo de un pdf' />
          </Button>
        </div>
      )}
    </div>
  </div>
)

export default EnrollmentInfoView
