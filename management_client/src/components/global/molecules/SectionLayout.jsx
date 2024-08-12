const SectionLayout = ({ title, children }) => (
  <div className='section-container'>
    <title>{title}</title>
    {children}
  </div>
)

export default SectionLayout
