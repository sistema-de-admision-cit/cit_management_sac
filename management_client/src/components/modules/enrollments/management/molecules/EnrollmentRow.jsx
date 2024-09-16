import PdfIcon from '../../../../../assets/icons/pdf-svgrepo-com.svg'
import DatePicker from '../../../../core/global/atoms/DatePicker'
import InputField from '../../../../core/global/atoms/InputField'
import Button from '../../../../core/global/atoms/Button'

const EnrollmentRow = ({ applicant, index, onStudentIdClick, onDateChange, onWhatsappChange, onDocClick }) => (
  <tr>
    <td className='applicant-id' onClick={() => onStudentIdClick(applicant)}>{applicant.studendtId}</td>
    <td>{applicant.firstName}</td>
    <td>{applicant.firstSurname}</td>
    <td>{applicant.secondSurname}</td>
    <td>{applicant.enrollmentDate}</td>
    <td>
      <DatePicker
        name={`interviewDate-${index}`}
        label={`Fecha Entrevista ${index + 1}`}
        value={new Date(applicant.examDate)}
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
        onClick={() => onDocClick(applicant, 'Notas', applicant.notes)}
      >
        <img src={PdfIcon} alt='logo de un pdf' />
      </Button>
    </td>
    <td>
      <Button
        className='pdf-icon'
        onClick={() => onDocClick(applicant, 'Adecuación', applicant.adaptation)}
      >
        <img src={PdfIcon} alt='logo de un pdf' />
      </Button>

    </td>
  </tr>
)

export default EnrollmentRow
