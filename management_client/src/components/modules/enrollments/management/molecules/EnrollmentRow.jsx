import PdfIcon from '../../../../../assets/icons/pdf-svgrepo-com.svg'
import DatePicker from '../../../../core/global/atoms/DatePicker'
import InputField from '../../../../core/global/atoms/InputField'
import Button from '../../../../core/global/atoms/Button'

const EnrollmentRow = ({ applicant, index, onCedulaClick, onDateChange, onWhatsappChange, onDocClick }) => (
  <tr>
    <td className='applicant-id' onClick={() => onCedulaClick(applicant)}>{applicant.cedula}</td>
    <td>{applicant.nombre}</td>
    <td>{applicant.apellido1}</td>
    <td>{applicant.apellido2}</td>
    <td>{applicant.fechaInscripcion}</td>
    <td>
      <DatePicker
        name={`interviewDate-${index}`}
        label={`Fecha Entrevista ${index + 1}`}
        value={new Date(applicant.fechaExamen)}
        onChange={(date) => onDateChange(applicant, date)}
        required
        showLabel={false}
      />
    </td>
    <td>
      <InputField
        field={{
          type: 'checkbox',
          name: `whatsapp-${index}`,
          label: `Whatsapp ${index + 1}`,
          required: true
        }}
        value={applicant.whatsapp}
        handleChange={(e) => onWhatsappChange(applicant, e.target.value)}
        showLabel={false}
      />

    </td>
    <td>
      <Button
        className='pdf-icon'
        onClick={() => onDocClick(applicant, 'Notas')}
      >
        <img src={PdfIcon} alt='logo de un pdf' />
      </Button>
    </td>
    <td>
      <Button
        className='pdf-icon'
        onClick={() => onDocClick(applicant, 'Adecuación')}
      >
        <img src={PdfIcon} alt='logo de un pdf' />
      </Button>

    </td>
  </tr>
)

export default EnrollmentRow
