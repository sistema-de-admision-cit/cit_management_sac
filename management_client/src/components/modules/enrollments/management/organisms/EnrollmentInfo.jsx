import { useState } from 'react'
import EnrollmentInfoView from './EnrollmentInfoView'
import EnrollmentInfoEdit from './EnrollmentInfoEdit'

const EnrollmentInfo = ({
  enrollment,
  onUpdateEnrollment,
  onDateChange,
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
          onUpdateEnrollment={onUpdateEnrollment}
          onDateChange={onDateChange}
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
