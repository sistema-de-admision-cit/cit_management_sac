import axios from 'axios'

const getQuestionByCodeUrl = `${import.meta.env.VITE_API_BASE_URL}${import.meta.env.VITE_GET_QUESTION_BY_CODE_ENDPOINT}`
// router.get('/get-by-code/:code', (req, res) =>
export const getQuestionByCode = (code, setIncomingData, setErrorMessage, setLoading) => {
  setLoading(true)

  axios.get(`${getQuestionByCodeUrl}/${code}`)
    .then(response => {
      const data = response.data
      setIncomingData(data)
      setLoading(false)
    })
    .catch(error => {
      console.error(error)
      setErrorMessage('No se encontró la pregunta')
      setLoading(false)
    })
}
