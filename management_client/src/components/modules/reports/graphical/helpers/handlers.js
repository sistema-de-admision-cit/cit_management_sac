import axios from '../../../../../config/axiosConfig'

export const fetchExamSourceData = async (setData, setIsLoading, setError) => {
  setIsLoading(true)
  try {
    const { data } = await axios.get('reports/exam-source')
    setData(data)
  } catch (error) {
    setError(error)
  } finally {
    setIsLoading(false)
  }
}
