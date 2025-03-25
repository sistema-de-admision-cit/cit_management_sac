import React from 'react'
import trashIcon from '../../../../../assets/icons/trash-bin-minimalistic-svgrepo-com.svg'
import { statusRole } from '../handlers/helpers'
import '../../../../../assets/styles/global/buttons.css'
const UserRow = ({ user, onDeleteClick }) => {
  return (
    <tr>
      <td>{user.email}</td>
      <td>{user.realUsername}</td>
      <td>{statusRole[user.role]}</td>
      <td>
        <button className='delete-button' onClick={() => onDeleteClick(user.email)}>
          <img src={trashIcon} alt='Eliminar' width='20' height='20' />
        </button>
      </td>
    </tr>
  )
}

export default UserRow
