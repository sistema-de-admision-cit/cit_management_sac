const LogsList = ({ logs }) => (
  <div className='logs-section'>
    <h3>Logs</h3>
    <ul>
      {logs.map((log, index) => (
        <li key={index}>{log}</li>
      ))}
    </ul>
  </div>
)
export default LogsList
