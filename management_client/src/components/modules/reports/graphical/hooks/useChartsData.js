import { useState, useEffect } from 'react'

/**
 * Generic hook to fetch multiple chart data sources.
 * @param {Array<{ key: string, fetcher: () => Promise<any> }>} configs
 */
export function useChartsData (configs) {
  const [data, setData] = useState({})
  const [isLoading, setIsLoading] = useState(false)
  const [error, setError] = useState({})

  useEffect(() => {
    let isMounted = true

    async function fetchAll () {
      setIsLoading(true)
      const results = {}
      const errors = {}

      await Promise.all(
        configs.map(async ({ key, fetcher }) => {
          try {
            const result = await fetcher()
            if (isMounted) results[key] = result
          } catch (err) {
            if (isMounted) errors[key] = err.message || 'Error fetching data'
          }
        })
      )

      if (isMounted) {
        setData(results)
        setError(errors)
        setIsLoading(false)
      }
    }

    if (configs.length) fetchAll()

    return () => {
      isMounted = false
    }
  }, [configs])

  return { data, isLoading, error }
}
