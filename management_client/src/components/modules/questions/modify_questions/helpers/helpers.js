import axios from '../../../../../config/axiosConfig'

const getQuestionByCodeUrl = `${import.meta.env.VITE_GET_QUESTION_BY_CODE_ENDPOINT}/`
// } router.get('/get-by-code/:code', (req, res) =>
export const getQuestionById = (id, setIncomingData, setErrorMessage, setLoading) => {
  setLoading(true)

  console.log(`${getQuestionByCodeUrl}${id}`)

  axios.get(`${getQuestionByCodeUrl}${id}`)
    .then(response => {
      const data = response.data // Extract the actual data
      console.log(data)
      setIncomingData(data)
    })
    .catch(error => {
      console.error(error)
      setErrorMessage('No se encontró la pregunta')
    })
    .finally(() => setLoading(false)) // Ensure loading state is reset
}
