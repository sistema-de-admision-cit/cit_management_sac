import { useState } from 'react'
import UserRow from '../molecules/UserRow'
import Button from '../../../../core/global/atoms/Button'
import Spinner from '../../../../core/global/atoms/Spinner'
import '../../../../../assets/styles/enrollments/enrollment-table.css'
import AccessManegementSearchBar from '../molecules/AccessManegementSearchBar'

const UserTable = ({ deletedUsers, onDeleteClick, loading }) => {
  const [currentPage, setCurrentPage] = useState(1)
  const [searchTerm, setSearchTerm] = useState('')
  const itemsPerPage = 10

  const indexOfLastItem = currentPage * itemsPerPage
  const indexOfFirstItem = indexOfLastItem - itemsPerPage

  const filteredUsers = deletedUsers?.filter(user =>
    user.email && user.email.toLowerCase().includes(searchTerm.toLowerCase())
  )

  const currentUsers = filteredUsers?.slice(indexOfFirstItem, indexOfFirstItem + itemsPerPage)
  const totalPages = Math.ceil(filteredUsers?.length / itemsPerPage)

  const paginate = (pageNumber) => setCurrentPage(pageNumber)

  const handleSearch = (term) => {
    setSearchTerm(term)
    setCurrentPage(1)
  }

  return (
    <div className='enrollment-table-container'>
      <div className='table-header'>
        <h3>Usuarios</h3>
        <AccessManegementSearchBar onSearch={handleSearch} />
      </div>

      <table className='enrollment-table'>
        <thead>
        <tr>
          <th>Correo</th>
          <th>Username</th>
          <th>Rol</th>
          <th>Acciones</th>
        </tr>
        </thead>
        {loading
          ? (
            <tbody>
            <tr>
              <td colSpan='4'>
                <Spinner />
              </td>
            </tr>
            </tbody>
          )
          : (
            <tbody>
            {currentUsers && currentUsers.length > 0
              ? (
                currentUsers.map((user) => (
                  <UserRow
                    key={user.id}
                    user={user}
                    onDeleteClick={onDeleteClick}
                  />
                ))
              )
              : (
                <tr>
                  <td colSpan='4' className='no-deleted-users'>
                    No hay usuarios
                  </td>
                </tr>
              )}
            </tbody>
          )}
      </table>

      <div className='pagination'>
        {Array.from({ length: totalPages }, (_, i) => i + 1).map((number) => (
          <Button
            key={number}
            onClick={() => paginate(number)}
            className={currentPage === number ? 'active' : ''}
          >
            {number}
          </Button>
        ))}
      </div>
    </div>
  )
}

export default UserTable
