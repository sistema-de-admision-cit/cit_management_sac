import { useEffect, useState } from 'react'
import Button from '../../../../core/global/atoms/Button'
import InputField from '../../../../core/global/atoms/InputField'
import useMessages from '../../../../core/global/hooks/useMessages'
import { handleSaveStatus, handleGetStudentDetails } from '../helpers/handlers'
import { formatAcademicGrade, formatRecommendation } from '../helpers/helpers'
import '../../../../../assets/styles/results/analyze_results/modal-result.css'

const ModalResult = ({ idNumber, onClose, setErrorMessage, setSuccessMessage }) => {
    const [newStatus, setNewStatus] = useState('');
    const [loading, setLoading] = useState(false);
    const [details, setDetails] = useState(null);
    
    const loadDetails = async (idNumber) => {
        await handleGetStudentDetails(
            idNumber,
            setDetails,
            setLoading,
            setErrorMessage,
            setSuccessMessage
        )
    }
    
    useEffect(() => {
        loadDetails(idNumber)
    }, [idNumber]) 

    const handleSubmit = async (e) => {
        e.preventDefault();
        
        if (!newStatus) {
            setErrorMessage?.('Por favor seleccione un estado');
            return;
        }

        const updateStatusDTO = {
            newStatus: newStatus // Asegúrate que coincida con el DTO del backend
        };

        try {
            await handleSaveStatus(
                idNumber,
                updateStatusDTO,
                setErrorMessage,
                setSuccessMessage,
                setLoading,
                onClose
            );
        } catch (error) {
            console.error('Error en handleSubmit:', error);
        }
    };

    return (
        <div className='modal-overlay'>
            <div className='modal-content'>
                <div className='modal-header'>
                    <h2>Gestión de postulante</h2>
                </div>

                <div className='student-info'>
                    <h3>Información del Estudiante</h3>
                    <p><strong>Nombre:</strong> {details?.fullName || 'No disponible'}</p>
                    <p><strong>Cédula:</strong> {details?.idNumber || 'No disponible'}</p>

                    <h3>Información de las Notas</h3>
                    <p><strong>Grado Académico:</strong> {details?.academicGrade ? formatAcademicGrade(details.academicGrade) : 'No disponible'}</p>
                    <p><strong>Promedio de Notas Anteriores:</strong> {details?.prevGrades ?? 'No disponible'}</p>
                    <p><strong>Nota Examen Inglés:</strong> {details?.englishLevel ?? 'No disponible'}</p>
                    <p><strong>Nota Examen Académico:</strong> {details?.academicGradeScore ?? 'No disponible'}</p>
                    <p><strong>Nota Final:</strong> {details?.finalGrade ?? 'No disponible'}</p>

                    <h3>Información del DAI</h3>
                    <p><strong>Recomendación del Psicólogo:</strong> {details?.daiRecommendation ? formatRecommendation(details.daiRecommendation) : 'No disponible'}</p>
                    <p><strong>Comentario del Psicólogo:</strong> {details?.daiComment ?? 'No disponible'}</p>
                </div>

                <form onSubmit={handleSubmit} className='result-form'>
                    <InputField
                        field={{
                            name: 'newStatus',
                            label: 'Seleccione el estado de admisión',
                            type: 'dropdown',
                            options: [
                                { value: '', label: 'Seleccione un estado' },
                                { value: 'ACCEPTED', label: 'Aceptado' },
                                { value: 'REJECTED', label: 'Rechazado' },
                            ],
                            required: true
                        }}
                        value={newStatus}
                        handleChange={(e) => setNewStatus(e.target.value)}
                        className='form-group'
                    />
                    <div className='form-actions'>
                        <Button
                            type='button'
                            onClick={onClose}
                            className='cancel-button'
                            disabled={loading}
                        >
                            Cancelar
                        </Button>
                        <Button
                            type='submit'
                            disabled={loading || !newStatus}
                            className='submit-button'
                        >
                            {loading ? 'Guardando...' : 'Guardar Cambios'}
                        </Button>
                    </div>
                </form>
            </div>
        </div>
    )
}

export default ModalResult