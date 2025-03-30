import { useState } from 'react'
import Button from '../../../../core/global/atoms/Button'
import InputField from '../../../../core/global/atoms/InputField'
import useMessages from '../../../../core/global/hooks/useMessages'
import { handleSaveEnglishLevel, handleDownloadEnglishExam } from '../helpers/handlers'
import '../../../../../assets/styles/results/grades/modal-grades.css'

const ModalEnglishExam = ({ examData, student, onClose, onSave }) => {
    const { setErrorMessage, setSuccessMessage } = useMessages()
    const [level, setLevel] = useState(examData?.level || 'porDefecto')
    const [loading, setLoading] = useState(false)

    // La logogica se trabaja mediante el uso de handelers
    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        
        const formData = {
          englishLevel: level,
          studentId: student.id,
          examId: examData?.id
        };
      
        try {
          await handleSaveEnglishLevel(
            formData,
            () => {}, // setIsEditing si lo necesitas
            setErrorMessage,
            setSuccessMessage,
            onClose
          );
        } catch (error) {
          // El error ya se maneja en handleSaveEnglishLevel
        } finally {
          setLoading(false);
        }
      };

    const handleDownload = async () => {
    }

    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <div className="modal-header">
                    <h2>Calificar Examen de Inglés</h2>
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
                            name: 'level',
                            label: 'Nivel',
                            type: 'select',
                            required: true
                        }}
                        value={level}
                        handleChange={(e) => setLevel(e.target.value)}
                    >
                        <option value="porDefecto">Seleccione un nivel</option>
                        <option value="A1">A1 (Principiante)</option>
                        <option value="A2">A2 (Básico)</option>
                        <option value="B1">B1 (Intermedio)</option>
                        <option value="B2">B2 (Intermedio Alto)</option>
                        <option value="C1">C1 (Avanzado)</option>
                        <option value="C2">C2 (Maestría)</option>
                    </InputField>

                    <div className="form-actions">
                        <Button
                            type="button"
                            onClick={onClose}
                            className="cancel-button"
                            disabled={loading}
                        >
                            Cancelar
                        </Button>
                        <Button
                            type="submit"
                            disabled={loading || !level || level === 'porDefecto'}
                            className="submit-button"
                        >
                            {loading ? 'Guardando...' : 'Guardar Cambios'}
                        </Button>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default ModalEnglishExam