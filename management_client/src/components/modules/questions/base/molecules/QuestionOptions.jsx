import InputField from '../../../../core/global/atoms/InputField'

const QuestionOptions = ({ options, handleOptionChange }) => (
  <>
    {options.map((option, index) => (
      <InputField
        key={index}
        field={{ name: `option${index}`, label: `Opción ${index + 1}`, type: 'text', placeholder: `Opción ${index + 1}`, required: true }}
        value={option}
        handleChange={(e) => handleOptionChange(index, e.target.value)}
        className='form-group'
      />
    ))}
  </>
)

export default QuestionOptions
