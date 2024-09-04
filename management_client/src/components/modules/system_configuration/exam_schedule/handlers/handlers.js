export const handleSubmit = (formValues) => {
  const startDate = formValues.startDate.toISOString().split('T')[0]
  const endDate = formValues.endDate.toISOString().split('T')[0]

  const sendingData = {
    allYear: formValues.allYear,
    startDate,
    endDate,
    applicationDays: formValues.applicationDays,
    startTime: formValues.startTime,
    endTime: formValues.endTime
  }

  console.log(sendingData)
  // TODO: Send data to the server (when the endpoint is ready)
}
