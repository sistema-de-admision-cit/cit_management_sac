import { useState } from 'react'
import EnrollmentInfoView from './EnrollmentInfoView'
import EnrollmentInfoEdit from './EnrollmentInfoEdit'

const EnrollmentInfo = ({
  enrollment,
  onDateChange,
  onWhatsappChange,
  onPreviousGradesChange,
  onDocClick,
  student,
  setSelectedFileType,
  setErrorMessage,
  setSuccessMessage
}) => {
  const [isEditing, setIsEditing] = useState(false)

  return (<>
    {isEditing
      ?
      (
        <EnrollmentInfoEdit
          enrollment={enrollment}
          onDateChange={onDateChange}
          onWhatsappChange={onWhatsappChange}
          onPreviousGradesChange={onPreviousGradesChange}
          setIsEditing={setIsEditing}
          setErrorMessage={setErrorMessage}
          setSuccessMessage={setSuccessMessage}
        />
      )
      :
      (
        <EnrollmentInfoView
          enrollment={enrollment}
          onDocClick={onDocClick}
          student={student}
          setSelectedFileType={setSelectedFileType}
          setIsEditing={setIsEditing}
        />
      )
    }
  </>
  )
}

export default EnrollmentInfo
