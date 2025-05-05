import { useState } from 'react'
import InputField from '../../../../core/global/atoms/InputField'
import Button from '../../../../core/global/atoms/Button'

const UploadFileForm = ({
    enrollment,
    fileType,
    onFileUpload

}) => {
    const [formData, setFormData] = useState({
        enrollmentId: enrollment.id,
        documentType: fileType,
        file: null,
    })

    const handleFileChange = (e) => {
        const file = e.target.files[0]
        setFormData({ ...formData, file })
    }

    return (
        <form onSubmit={(e) => onFileUpload(e, formData)} className='file-upload-form'>
            <InputField
                field={{
                    type: 'file',
                    name: 'file',
                    id: 'file',
                    label: 'Subir archivo'
                }}
                accept='.pdf, .doc, .docx, .jpg, .jpeg, .png'
                handleChange={handleFileChange}
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
}

export default UploadFileForm