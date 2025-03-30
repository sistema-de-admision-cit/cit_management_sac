import { useState } from 'react'
import Button from '../../../core/global/atoms/Button'
import InputField from '../../../core/global/atoms/InputField'
import useMessages from '../../../core/global/hooks/useMessages'
import {handleDownloadAcademicExam} from '../helpers/handlers'
import '../../../../assets/styles/grades/modal-grade.css'

const ModalAcademicExam = ({ examData, student, onClose}) => {
    const [grade, setGrade] = useState(examData?.grade || 'porDefecto')
    const [loading, setLoading] = useState(false)
    
    // La logogica se trabaja mediante el uso de handelers
    const handleDownload = async (e) => {
    }

    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <div className="modal-header">
                    <h2>Resultado Examen Academico</h2>
                </div>
                <div className="student-info">
                    <p><strong>Cédula:</strong> {student.person.idNumber}</p>
                    <p><strong>Estudiante:</strong> {student.person.firstName} {student.person.firstSurname}</p>
                    <p><strong>Nota obtenida:</strong> {examData.grade}</p>
                </div>

                <form className="grade-form">
                    <div className="form-actions">
                        <Button
                            type="button"
                            onClick={handleDownload}
                            className="download-button"
                            disabled={loading}
                        >
                            Descargar Examen
                        </Button>
                    </div>
                    <div className="form-actions">
                        <Button
                            type="button"
                            onClick={onClose}
                            className="cancel-button"
                        >
                            Cerrar
                        </Button>
                    </div>
                </form>
            </div>
        </div>
    )
}

export default ModalAcademicExam