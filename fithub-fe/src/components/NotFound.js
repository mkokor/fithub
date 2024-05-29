import '../css/NotFound.css';


const NotFound = () => {
  return (
    <div className="not-found-container">
      <h1 className="not-found-title">Page Not Found</h1>
      <p className="not-found-message">Oops! The page you're looking for does not exist.</p>
      <a href="http://localhost:3000/" className="not-found-button">Go Back to Home</a>
    </div>
  );
};

export default NotFound;