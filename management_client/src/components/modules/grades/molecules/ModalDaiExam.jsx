import { useState } from 'react'
import Button from '../../../core/global/atoms/Button'
import InputField from '../../../core/global/atoms/InputField'
import useMessages from '../../../core/global/hooks/useMessages'
import { handleSaveDAIComment, generateDAIExamPDF } from '../helpers/handlers'
import '../../../../assets/styles/grades/modal-grade.css'

const ModalDaiExam = ({ grade, onClose, setErrorMessage, setSuccessMessage }) => {

    const [comment, setComment] = useState(grade.daiExams[0]?.comment || '');
    
    const [status, setStatus] = useState(() => {
        const recommendation = grade.daiExams[0].recommendation;
        if (recommendation === 'ADMIT') return 'ADMIT';
        if (recommendation === 'REJECT') return 'REJECT';
        return '';
    });
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        await handleSaveDAIComment(
            grade,
            comment,
            status,
            setErrorMessage,
            setSuccessMessage,
            setLoading,
            onClose
        );
    };

    const handleDownload = async () => {
        generateDAIExamPDF(grade);
    };

    return (

        <div className="modal-overlay">
            <div className="modal-content">
                <div className="modal-header">
                    <h2>Calificar Examen DAI</h2>
                </div>

                <div className="student-info">
                    <p><strong>Cédula:</strong> {grade.person.idNumber}</p>
                    <p><strong>Estudiante:</strong> {grade.person.firstName} {grade.person.firstSurname}</p>
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
                            required: true,
                            maxLength: 255
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
                                { value: 'ADMIT', label: 'Admitir' },
                                { value: 'REJECT', label: 'Rechazar' },
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

    );
};

export default ModalDaiExam