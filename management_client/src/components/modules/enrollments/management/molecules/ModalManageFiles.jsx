import Button from '../../../../core/global/atoms/Button'
import Modal from '../../../../core/global/molecules/Modal'
import '../../../../../assets/styles/enrollments/modal-manage-files.css'
import UploadFileForm from './UploadFileForm'

const ModalManageFiles = ({ 
  enrollment,
  selectedFileType, 
  selectedFile, 
  onFileUpload, 
  onFileDownload, 
  onFileDelete, 
  onClose 
}) => {
  

  return (
    <Modal onClose={onClose} className='modal-manage-files'>
      <h2>Gestionar {selectedFileType === 'OT' ? 'Documento de Notas' : 'Documento de Adaptaciones'}</h2>
      {!selectedFile
        ? (
          <UploadFileForm
            enrollment={enrollment}
            fileType={selectedFileType}
            onFileUpload={onFileUpload}
          />
        )
        : (
          <div className='file-list-container'>
            <ul className='file-list'>

              <li className='file-list-item'>
                {selectedFile.documentName}
                <Button
                  onClick={onFileDownload}
                  className='btn btn-primary'
                >
                  Descargar
                </Button>
                <Button
                  onClick={() => onFileDelete(selectedFile)}
                  className='btn btn-danger'
                >
                  Eliminar
                </Button>
              </li>
            </ul>
          </div>
        )}

    </Modal>
  )
}

export default ModalManageFiles
