import PdfIcon from '../../../../../assets/icons/pdf-svgrepo-com.svg'
import DatePicker from '../../../../core/global/atoms/DatePicker'
import InputField from '../../../../core/global/atoms/InputField'
import Button from '../../../../core/global/atoms/Button'
import { statusText } from '../helpers/helpers'

const EnrollmentRow = ({ enrollment, index, onStudentIdClick, onDateChange, onWhatsappChange, onDocClick }) => (
  <tr>
    <td className='applicant-id' onClick={() => onStudentIdClick(enrollment)}>{enrollment.idNumber}</td>
    <td>{enrollment.firstName}</td>
    <td>{enrollment.firstSurname}</td>
    <td>{enrollment.secondSurname}</td>
    <td>
      <DatePicker
        name={`interviewDate-${index}`}
        label={`Fecha Entrevista ${index + 1}`}
        value={new Date(enrollment.enrollments[0].examDate)}
        onChange={(date) => onDateChange(enrollment, date)}
        required
        showLabel={false}
      />
    </td>
    <td>
      {statusText[enrollment.enrollments[0].status]}
    </td>
    <td>
      <InputField
        field={{
          type: 'checkbox',
          name: `whatsapp-${index}`,
          label: `Whatsapp ${index + 1}`,
          required: true
        }}
        value={enrollment.enrollments[0].whatsapp}
        handleChange={(e) => onWhatsappChange(enrollment, e.target.value)}
        showLabel={false}
      />

    </td>
    <td>
      <Button
        className='pdf-icon'
        onClick={() => onDocClick(enrollment, 'Notas', enrollment.notes)}
      >
        <img src={PdfIcon} alt='logo de un pdf' />
      </Button>
    </td>
    <td>
      <Button
        className='pdf-icon'
        onClick={() => onDocClick(enrollment, 'Adecuación', enrollment.adaptation)}
      >
        <img src={PdfIcon} alt='logo de un pdf' />
      </Button>

    </td>
  </tr>
)

export default EnrollmentRow
