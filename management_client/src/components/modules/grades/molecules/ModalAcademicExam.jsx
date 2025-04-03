import { useState } from 'react'
import Button from '../../../core/global/atoms/Button'
import InputField from '../../../core/global/atoms/InputField'
import useMessages from '../../../core/global/hooks/useMessages'
import { handleDownloadAcademicExam, generateExamPDF } from '../helpers/handlers'
import '../../../../assets/styles/grades/modal-grade.css'

const ModalAcademicExam = ({ examData, student, onClose }) => {
    const [grade, setGrade] = useState(examData?.grade || 'porDefecto')
    const [loading, setLoading] = useState(false)

    // La logogica se trabaja mediante el uso de handelers
    const handleDownload = async (e) => {
        // Se hace el request al servidor y con el JSON que se recupera crear el PDF
        // El request se maneja con el handleDowloadAcademicExam

        // Llamar al creador de PDF para que lo convierta y descargarlo
        // Por ahora utilizar una variable local debido a que no hay conexion con el backEnd
        generateExamPDF(examDataExp);
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

const examDataExp = {
    "id": 1,
    "grade": 100.00,
    "exam": {
        "id": 1,
        "enrollment": 10,
        "examDate": "2025-03-25T17:54:12Z",
        "examType": "ACA",
        "responses": [
            {
                "id": 26,
                "questionType": "ACA",
                "questionText": "Teniendo como referencia la relación del par base, elija la alternativa que mantiene dicha relación análoga. SUMAR : MULTIPLICAR",
                "imageUrl": null,
                "selectionType": "SINGLE",
                "deleted": false,
                "questionOptions": [
                    {
                        "id": 81,
                        "isCorrect": true,
                        "option": "Restar : Dividir",
                        "selected": true
                    },
                    {
                        "id": 82,
                        "isCorrect": false,
                        "option": "Sumar : Restar",
                        "selected": false
                    },
                    {
                        "id": 83,
                        "isCorrect": false,
                        "option": "Multiplicar : Dividir",
                        "selected": false
                    },
                    {
                        "id": 84,
                        "isCorrect": false,
                        "option": "Sumar : Multiplicar",
                        "selected": false
                    }
                ]
            },
            {
                "id": 29,
                "questionType": "ACA",
                "questionText": "Quienes hayan leído grandes obras literarias, haciendo de ellas parte de su vida,",
                "imageUrl": null,
                "selectionType": "SINGLE",
                "deleted": false,
                "questionOptions": [
                    {
                        "id": 93,
                        "isCorrect": false,
                        "option": "Son personas cultas",
                        "selected": false
                    },
                    {
                        "id": 94,
                        "isCorrect": false,
                        "option": "Son personas sabias",
                        "selected": false
                    },
                    {
                        "id": 95,
                        "isCorrect": false,
                        "option": "Son personas educadas",
                        "selected": false
                    },
                    {
                        "id": 96,
                        "isCorrect": true,
                        "option": "Son personas que han enriquecido su vida",
                        "selected": true
                    }
                ]
            },
            {
                "id": 12,
                "questionType": "ACA",
                "questionText": "Un frasco contiene cincuenta monedas de 100 colones y pesa 1400g, si el frasco vacío pesa 250g entones el peso, en gramos, de una moneda es",
                "imageUrl": null,
                "selectionType": "SINGLE",
                "deleted": false,
                "questionOptions": [
                    {
                        "id": 25,
                        "isCorrect": false,
                        "option": "20g",
                        "selected": false
                    },
                    {
                        "id": 26,
                        "isCorrect": false,
                        "option": "25g",
                        "selected": false
                    },
                    {
                        "id": 27,
                        "isCorrect": true,
                        "option": "23g",
                        "selected": true
                    },
                    {
                        "id": 28,
                        "isCorrect": false,
                        "option": "35g",
                        "selected": false
                    }
                ]
            },
            {
                "id": 25,
                "questionType": "ACA",
                "questionText": "Teniendo como referencia la relación del par base, elija la alternativa que mantiene dicha relación análoga. MONÁRQUICO : DESORDENADO",
                "imageUrl": null,
                "selectionType": "SINGLE",
                "deleted": false,
                "questionOptions": [
                    {
                        "id": 77,
                        "isCorrect": false,
                        "option": "Dictador : Violento",
                        "selected": false
                    },
                    {
                        "id": 78,
                        "isCorrect": false,
                        "option": "Anarquía : Orden",
                        "selected": false
                    },
                    {
                        "id": 79,
                        "isCorrect": false,
                        "option": "Democrático : Justo",
                        "selected": false
                    },
                    {
                        "id": 80,
                        "isCorrect": true,
                        "option": "Anarquía : Desorden",
                        "selected": true
                    }
                ]
            },
            {
                "id": 16,
                "questionType": "ACA",
                "questionText": "Una cocina eléctrica, con dos discos encendidos consume 1200 colones al estar encendidos por 3 horas. El gasto, en colones, si se encienden los 4 discos por dos horas corresponde a",
                "imageUrl": null,
                "selectionType": "SINGLE",
                "deleted": false,
                "questionOptions": [
                    {
                        "id": 41,
                        "isCorrect": false,
                        "option": "1200 colones",
                        "selected": false
                    },
                    {
                        "id": 42,
                        "isCorrect": true,
                        "option": "1600 colones",
                        "selected": true
                    },
                    {
                        "id": 43,
                        "isCorrect": false,
                        "option": "2400 colones",
                        "selected": false
                    },
                    {
                        "id": 44,
                        "isCorrect": false,
                        "option": "3200 colones",
                        "selected": false
                    }
                ]
            }
        ]
    }
};