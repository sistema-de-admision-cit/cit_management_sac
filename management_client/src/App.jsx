import AuthRouter from './routers/AuthRouter'
import HubRouter from './components/hub/router/HubRouter'

function App () {
  return (
    <div className='App'>
      <AuthRouter />
      <HubRouter />
    </div>
  )
}

export default App
