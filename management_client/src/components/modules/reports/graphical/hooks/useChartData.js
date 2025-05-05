import { useState, useEffect } from 'react'

/**
 * Fetch a single chart’s data.
 * @param {() => Promise<any>} fetcher  – function that returns a promise for the chart data
 * @param {Array<any>} deps             – dependency array to re-run the fetch
 */
export function useChartData (fetcher, deps = []) {
  const [data, setData] = useState([])
  const [isLoading, setIsLoading] = useState(false)
  const [error, setError] = useState(null)

  useEffect(() => {
    let mounted = true
    setIsLoading(true)

    fetcher()
      .then(result => {
        if (mounted) {
          setData(result)
          setError(null)
        }
      })
      .catch(err => {
        if (mounted) {
          setError(err.message || 'Error fetching data')
        }
      })
      .finally(() => {
        if (mounted) setIsLoading(false)
      })

    return () => {
      mounted = false
    }
  }, deps) // re-runs only when deps change

  return { data, isLoading, error }
}
