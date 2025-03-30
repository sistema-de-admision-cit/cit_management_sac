import { useState } from 'react'
import Button from '../../../core/global/atoms/Button'
import InputField from '../../../core/global/atoms/InputField'
import useMessages from '../../../core/global/hooks/useMessages'
import {handleSaveDAIComment, handleDownloadDAIExam} from '../helpers/handlers'
import '../../../../assets/styles/grades/modal-grade.css'

const ModalDaiExam = ({ examData, student, onClose, onSave }) => {
    const { setErrorMessage, setSuccessMessage } = useMessages()
    const [comment, setComment] = useState(examData?.comment || '')
    const [status, setStatus] = useState(examData?.status || ''); // Nuevo estado para el select
    const [loading, setLoading] = useState(false)

    // La logogica se trabaja mediante el uso de handelers
    const handleSubmit = () => { }
    const handleDownload = () => { }

    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <div className="modal-header">
                    <h2>Calificar Examen DAI</h2>
                </div>

                <div className="student-info">
                    <p><strong>Cédula:</strong> {student.person.idNumber}</p>
                    <p><strong>Estudiante:</strong> {student.person.firstName} {student.person.firstSurname}</p>
                </div>

                <form onSubmit={handleSubmit} className="grade-form">
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
                    <InputField
                        field={{ 
                            name: 'dai_comment', 
                            label: 'Comentarios', 
                            type: 'textArea', 
                            placeholder: 'Ingrese observaciones del examen...',
                            required: true 
                        }}
                        value={comment}
                        handleChange={(e) => setComment(e.target.value)}
                        className="form-group"
                    />
                    <InputField
                        field={{ 
                            name: 'dai_status', 
                            label: 'Estado', 
                            type: 'dropdown', 
                            options: [
                                { value: '', label: 'Seleccione un estado' },
                                { value: 'ADMITIR', label: 'Admitir' },
                                { value: 'RECHAZAR', label: 'Rechazar' },
                            ],
                            required: true 
                        }}
                        value={status}
                        handleChange={(e) => setStatus(e.target.value)}
                        className="form-group"
                    />                    
                    <div className="form-actions">
                        <Button
                            type="button"
                            onClick={onClose}
                            className="cancel-button"
                        >
                            Cancelar
                        </Button>
                        <Button
                            type="submit"
                            disabled={loading || !comment || comment === 'porDefecto'}
                            className="submit-button"
                        >
                            {loading ? 'Guardando...' : 'Guardar Cambios'}
                        </Button>
                    </div>
                </form>
            </div>
        </div>
    )
}

export default ModalDaiExam