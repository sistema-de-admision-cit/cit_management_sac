import { guardianTabText, buildGuardianAddress } from '../helpers/helpers'

const GuardianInfo = ({ guardian }) => (
  <div className='tab-content'>
    <h2>{guardianTabText[guardian.relationship]}</h2>
    <p><strong>Nombre:</strong> {guardian.firstName} {guardian.firstSurname} {guardian.secondSurname}</p>
    <p><strong>Tipo de ID:</strong> {guardian.idType}</p>
    <p><strong>Número de ID:</strong> {guardian.idNumber}</p>
    <p><strong>Teléfono:</strong> {guardian.phoneNumber}</p>
    <p><strong>Email:</strong> {guardian.email}</p>
    <p><strong>Dirección:</strong> {buildGuardianAddress(guardian.addresses[0])}</p>
  </div>
)

export default GuardianInfo
