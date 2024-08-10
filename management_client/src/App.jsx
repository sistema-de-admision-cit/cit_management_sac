import AppRouter from './router/AppRouter'
import AuthProvider from './router/AuthProvider'

function App () {
  return (
    <div className='App'>
      <AuthProvider>
        <AppRouter />
      </AuthProvider>
    </div>
  )
}

export default App
