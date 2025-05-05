import { guardianTabText, buildGuardianAddress } from '../helpers/helpers'

const ParentInfo = ({ parent }) => (
  <div className='tab-content'>
    <p><strong>Nombre:</strong> {parent.person.firstName} {parent.person.firstSurname} {parent.person.secondSurname}</p>
    <p><strong>Tipo de ID:</strong> {parent.person.idType}</p>
    <p><strong>Número de ID:</strong> {parent.person.idNumber}</p>
    <p><strong>Teléfono:</strong> {parent.phoneNumber}</p>
    <p><strong>Email:</strong> {parent.email}</p>
    <p><strong>Dirección:</strong> {buildGuardianAddress(parent.addresses[0])}</p>
  </div>
)

export default ParentInfo
