import { useState } from 'react'
import Button from '../../../../core/global/atoms/Button'
import InputField from '../../../../core/global/atoms/InputField'

const ManualGradeSection = ({ handleAddScore, handleProcessScore, scores }) => {
  const [tracktestId, setTracktestId] = useState('') // TrackTest ID (E.g. 1234567890)
  const [level, setLevel] = useState('') // CERF level (A1 - C2)
  const [core, setCore] = useState('') // Grading score (E.g. 90%)
  const [names, setNames] = useState('') // Full name of the student
  const [lastNames, setLastNames] = useState('') // Both last names of the student
  const [lastTest, setLastTest] = useState(null) // Date of the last test taken by the student (this maps to the examDate field in the database)

  const addNote = () => {
    if (!tracktestId || !core) return

    const newGrade = {
      id: tracktestId,
      level,
      core,
      names,
      lastNames,
      lastTest
    }

    if (!handleAddScore(newGrade)) {
      setTracktestId('')
      setCore('')
      setLevel('')
      setNames('')
      setLastNames('')
      setLastTest(null)
    }
  }

  return (
    <div className='manual-grade-section'>
      <h2>Información del Estudiante</h2>

      <InputField
        field={{
          type: 'text',
          name: 'names',
          placeholder: 'Nombres',
          label: 'Nombres'
        }}
        value={names}
        handleChange={(e) => setNames(e.target.value)}
        className='form-group'
      />

      <InputField
        field={{
          type: 'text',
          name: 'lastNames',
          placeholder: 'Apellidos',
          label: 'Apellidos'
        }}
        value={lastNames}
        handleChange={(e) => setLastNames(e.target.value)}
        className='form-group'
      />

      <InputField
        field={{
          type: 'date',
          name: 'lastTest',
          placeholder: 'Fecha del examen',
          label: 'Último examen'
        }}
        value={lastTest}
        handleChange={(date) => setLastTest(date)}
        className='form-group'
      />

      <h2>Datos del Examen</h2>

      <InputField
        field={{
          type: 'text',
          name: 'tracktestId',
          placeholder: 'ID generado por TrackTest',
          label: 'ID de TrackTest'
        }}
        value={tracktestId}
        handleChange={(e) => setTracktestId(e.target.value)}
        className='form-group'
      />

      <InputField
        field={{
          type: 'text',
          name: 'level',
          placeholder: 'Nivel (A1 - C2)',
          label: 'Nivel'
        }}
        value={level}
        handleChange={(e) => setLevel(e.target.value)}
        className='form-group'
      />

      <InputField
        field={{
          type: 'text',
          name: 'core',
          placeholder: 'Nota (Ej: 90%)',
          label: 'Nota',
          required: true
        }}
        value={core}
        handleChange={(e) => setCore(e.target.value)}
        className='form-group'
      />

      <Button onClick={addNote} className='btn btn-secondary'>
        Añadir Nota
      </Button>

      {scores.length > 0 && (
        <Button onClick={handleProcessScore} className='btn btn-primary'>
          Procesar Notas Manuales
        </Button>
      )}

    </div>
  )
}

export default ManualGradeSection
