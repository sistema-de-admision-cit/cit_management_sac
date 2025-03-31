import Button from '../../../../core/global/atoms/Button'

const ManualNotesPreview = ({ notes, onDelete }) => {
  if (notes.length === 0) return null

  return (
    <div className='manual-notes-preview'>
      {notes.map((note, index) => (
        <div key={index} className='note-card'>
          <div className='note-info'>
            <p><strong>ID:</strong> {note.id}</p>
            <p><strong>Nombres:</strong> {note.names}</p>
            <p><strong>Apellidos:</strong> {note.lastNames}</p>
            <p><strong>Nivel:</strong> {note.level}</p>
            <p><strong>Nota:</strong> {note.core}</p>
          </div>
          <div className='note-actions'>
            <Button
              onClick={() => onDelete(index)}
              className='btn btn-danger btn-sm'
            >
              Eliminar
            </Button>
          </div>
        </div>
      ))}
    </div>
  )
}

export default ManualNotesPreview
