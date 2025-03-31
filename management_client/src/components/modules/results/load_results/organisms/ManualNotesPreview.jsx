import { useState } from 'react'
import Button from '../../../../core/global/atoms/Button'

const ManualNotesPreview = ({ notes, onDelete }) => {
  const [isListExpanded, setIsListExpanded] = useState(true)
  const [expandedCards, setExpandedCards] = useState({})

  const toggleList = () => {
    setIsListExpanded((prev) => !prev)
  }

  const toggleCard = (index) => {
    setExpandedCards((prev) => ({
      ...prev,
      [index]: !prev[index]
    }))
  }

  if (notes.length === 0) return null

  return (
    <div className='manual-notes-preview'>
      {/* Header principal */}
      <div
        onClick={toggleList}
        style={{
          cursor: 'pointer',
          fontWeight: 'bold',
          display: 'flex',
          alignItems: 'center',
          marginBottom: '1rem'
        }}
      >
        <span style={{ marginRight: '0.5rem' }}>
          {isListExpanded ? '▼' : '▶'}
        </span>
        <span>Notas manuales ({notes.length})</span>
      </div>

      {/* Lista de notas */}
      {isListExpanded && notes.map((note, index) => {
        const isCardExpanded = expandedCards[index]

        return (
          <div key={index} className='note-card' style={{ marginBottom: '1rem' }}>
            {/* Header de la tarjeta */}
            <div
              onClick={() => toggleCard(index)}
              style={{
                cursor: 'pointer',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'space-between',
                backgroundColor: '#f5f5f5',
                padding: '0.5rem 1rem',
                borderRadius: '4px'
              }}
            >
              <div style={{ display: 'flex', alignItems: 'center' }}>
                <span style={{ marginRight: '0.5rem' }}>
                  {isCardExpanded ? '▼' : '▶'}
                </span>
                <span>{note.names} {note.lastNames}</span>
              </div>
              <span style={{ fontSize: '0.85rem', color: '#999' }}>ID: {note.id}</span>
            </div>

            {/* Contenido expandido */}
            {isCardExpanded && (
              <div className='note-info' style={{ padding: '0.75rem 1rem', backgroundColor: '#fff' }}>
                <p><strong>Nombres:</strong> {note.names}</p>
                <p><strong>Apellidos:</strong> {note.lastNames}</p>
                <p><strong>Nivel:</strong> {note.level}</p>
                <p><strong>Nota:</strong> {note.core}</p>
                <div className='note-actions' style={{ marginTop: '0.5rem' }}>
                  <Button
                    onClick={() => onDelete(index)}
                    className='btn btn-danger btn-sm'
                  >
                    Eliminar
                  </Button>
                </div>
              </div>
            )}
          </div>
        )
      })}
    </div>
  )
}

export default ManualNotesPreview
