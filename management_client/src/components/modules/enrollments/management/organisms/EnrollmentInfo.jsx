import EnrollmentInfoView from './EnrollmentInfoView'
import EnrollmentInfoEdit from './EnrollmentInfoEdit'

const EnrollmentInfo = ({
  enrollment,
  isEditing,
  onDateChange,
  onWhatsappChange,
  onPreviousGradesChange,
  setIsEditing,
  onDocClick,
  student,
  setSelectedFileType,
  onEnrollmentEdit
}) => (
  <>
    {isEditing
      ? (
        <EnrollmentInfoEdit
          enrollment={enrollment}
          onDateChange={onDateChange}
          onWhatsappChange={onWhatsappChange}
          onPreviousGradesChange={onPreviousGradesChange}
          setIsEditing={setIsEditing}
          handleEnrollmentEdit={onEnrollmentEdit}
        />
        )
      : (
        <EnrollmentInfoView
          enrollment={enrollment}
          onDocClick={onDocClick}
          student={student}
          setSelectedFileType={setSelectedFileType}
          setIsEditing={setIsEditing}
        />
        )}
  </>
)

export default EnrollmentInfo
