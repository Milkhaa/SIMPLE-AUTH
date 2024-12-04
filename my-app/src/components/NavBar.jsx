import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const NavBar = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  if (!user) return null;

  return (
    <nav className="navbar">
      <div className="navbar-brand" onClick={() => navigate('/')} style={{ cursor: 'pointer' }}>
        My App
      </div>
      <div className="navbar-links">
        <button 
          className="nav-link"
          onClick={() => navigate('/posts')}
        >
          My Posts
        </button>
        <button 
          className="nav-link"
          onClick={() => navigate('/')}
        >
          Profile
        </button>
        <button 
          className="logout-button"
          onClick={logout}
        >
          Logout
        </button>
      </div>
    </nav>
  );
};

export default NavBar; 