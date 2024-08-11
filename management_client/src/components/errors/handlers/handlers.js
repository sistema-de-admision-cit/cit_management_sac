export const goToHome = (navigate) => {
  return () => navigate('/dashboard') // lo manda al dashboard, si esta logueado se queda en el dashboard, si no lo manda al login
}
