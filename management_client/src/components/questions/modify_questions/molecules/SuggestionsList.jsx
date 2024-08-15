import '../../../../assets/styles/questions/suggestions-list.css'

const SuggestionsList = ({ suggestions, onSuggestionClick }) => {
  return (
    <ul className='suggestions-list'>
      {suggestions.map((item) => (
        <li key={item.code} onClick={() => onSuggestionClick(item)}>
          {item.question}
        </li>
      ))}
    </ul>
  )
}

export default SuggestionsList
