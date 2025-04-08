import Button from '../../../../core/global/atoms/Button'
import InputField from '../../../../core/global/atoms/InputField'
import Modal from '../../../../core/global/molecules/Modal'
import '../../../../../assets/styles/enrollments/modal-manage-files.css'

const ModalManageFiles = ({ selectedFileType, selectedFile, onFileUpload, onFileDownload, onFileDelete, onClose }) => (
  <Modal onClose={onClose} className='modal-manage-files'>
    <h2>Gestionar {selectedFileType}</h2>
    {!selectedFile
      ? (
        <form onSubmit={(e) => onFileUpload(e)} className='file-upload-form'>
          <InputField
            field={{
              type: 'file',
              name: 'file',
              id: 'file',
              label: 'Subir archivo'
            }}
            accept='.pdf, .doc,.docx, .jpg, .jpeg, .png'
            onChange={onFileUpload}
            className='form-group'
          />
          <Button
            type='submit'
            className='btn btn-primary'
          >
            Subir
          </Button>
        </form>
        )
      : (
        <div className='file-list-container'>
          <ul className='file-list'>

            <li className='file-list-item'>
              {selectedFile.documentName}
              <Button
                onClick={() => onFileDownload(selectedFile.documentUrl)}
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

export default ModalManageFiles
