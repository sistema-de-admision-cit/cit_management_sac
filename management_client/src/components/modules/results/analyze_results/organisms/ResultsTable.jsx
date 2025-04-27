import { useState } from 'react'
import Button from '../../../../core/global/atoms/Button'
import Spinner from '../../../../core/global/atoms/Spinner'
import useMessages from '../../../../core/global/hooks/useMessages'
import ResultRow from '../molecules/ResultRow'
import ModalResult from '../molecules/ModalResult'
import '../../../../../assets/styles/results/analyze_results/result-table.css'

const ResultTable = ({ results, loading, onPageChange, currentPage, totalPages }) => {
  const [activeModal, setActiveModal] = useState(null)
  const [selectedResult, setSelectedResult] = useState(null)
  const { setErrorMessage, setSuccessMessage, renderMessages } = useMessages()

  const handleOpenModal = (modalType, result) => {
    setSelectedResult(result)
    setActiveModal(modalType)
  }

  const handleCloseModal = () => {
    setSelectedResult(null)
    setActiveModal(null)
  }

  return (
    <>
      {renderMessages()}
      <div className='grades-table-container'>
        <table className='grades-table'>
          <thead>
            <tr>
              <th>Cédula</th>
              <th>Nombre</th>
              <th>Primer Apellido</th>
              <th>Segundo Apellido</th>
              <th>Fecha de Examen</th>
              <th>Grado Académico</th>
              <th>Nota Final</th>
              <th>Estado</th>
            </tr>
          </thead>
          <tbody>
            {loading
              ? (
                <tr>
                  <td colSpan='9'>
                    <Spinner />
                  </td>
                </tr>
                )
              : Array.isArray(results) && results.length > 0
                ? (
                    results.map((result, index) => (
                      <ResultRow
                        key={`${result.idNumber}-${index}`}
                        result={result}
                        onResultClick={() => handleOpenModal('result', result.idNumber)}
                      />
                    ))
                  )
                : (
                  <tr>
                    <td colSpan='9' className='no-students'>
                      No se ha encontrado estudiantes
                    </td>
                  </tr>
                  )}
          </tbody>
        </table>
        {totalPages > 1 && (
          <div className='pagination'>
            <Button
              onClick={() => onPageChange(currentPage - 1)}
              disabled={currentPage === 0}
            >
              Anterior
            </Button>

            {Array.from({ length: Math.min(5, totalPages) }, (_, i) => {
              let pageNum
              if (totalPages <= 5) {
                pageNum = i
              } else if (currentPage <= 2) {
                pageNum = i
              } else if (currentPage >= totalPages - 3) {
                pageNum = totalPages - 5 + i
              } else {
                pageNum = currentPage - 2 + i
              }

              return (
                <Button
                  key={pageNum}
                  onClick={() => onPageChange(pageNum)}
                  className={currentPage === pageNum ? 'active' : ''}
                >
                  {pageNum + 1}
                </Button>
              )
            })}

            <Button
              onClick={() => onPageChange(currentPage + 1)}
              disabled={currentPage >= totalPages - 1}
            >
              Siguiente
            </Button>
          </div>
        )}
        {/* Modal */}
        {activeModal === 'result' && (
          <ModalResult
            idNumber={selectedResult}
            onClose={handleCloseModal}
            setSuccessMessage={setSuccessMessage}
            setErrorMessage={setErrorMessage}
          />
        )}
      </div>
    </>
  )
}

export default ResultTable
