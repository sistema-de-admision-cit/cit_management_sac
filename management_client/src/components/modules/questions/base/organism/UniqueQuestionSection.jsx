import QuestionOptions from '../molecules/QuestionOptions'
import InputField from '../../../../core/global/atoms/InputField'

const UniqueQuestionSection = ({ options, correctOption, handleOptionChange, handleInputChange }) => {
  return (
    <>
      <QuestionOptions
        options={options}
        handleOptionChange={handleOptionChange}
      />

      <InputField
        field={{ name: 'correctOption', label: 'Respuesta Correcta', type: 'select' }}
        value={correctOption}
        handleChange={handleInputChange}
        className='form-group'
      >
        <option value=''>Seleccionar</option>
        {options.map((option, index) => (
          <option key={index} value={index}>{`Opción ${index + 1}`}</option>
        ))}
      </InputField>
    </>
  )
}

export default UniqueQuestionSection
