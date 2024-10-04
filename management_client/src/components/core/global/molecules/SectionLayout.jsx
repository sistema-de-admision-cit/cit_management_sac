const SectionLayout = ({ title, children, className }) => (
  <div className={`section-container ${className}`}>
    <title>{title}</title>
    {children}
  </div>
)

export default SectionLayout
