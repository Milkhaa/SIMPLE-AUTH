import './App.css';
import { useAuth } from './context/AuthContext';
import { useState } from 'react';

function App() {
  const { user, login, logout, loading, error, isAuthenticated } = useAuth();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleLogin = async (e) => {
    e.preventDefault();
    setIsSubmitting(true);
    
    try {
      await login(username, password);
    } catch (err) {
      // Error is handled by AuthContext
      console.error('Login failed:', err);
    } finally {
      setIsSubmitting(false);
    }
  };

  if (loading) {
    return (
      <div className="App">
        <header className="App-header">
          <div className="loading-spinner">Loading...</div>
        </header>
      </div>
    );
  }

  if (isAuthenticated) {
    return (
      <div className="App">
        <header className="App-header">
          <div className="user-profile">
            <h1>Welcome, {user.name}!</h1>
            <p>Email: {user.email}</p>
            <button 
              className="logout-button" 
              onClick={logout}
              disabled={loading}
            >
              Logout
            </button>
          </div>
        </header>
      </div>
    );
  }

  return (
    <div className="App">
      <header className="App-header">
        <div className="login-container">
          <h2>Login</h2>
          {error && <div className="error-message">{error}</div>}
          <form onSubmit={handleLogin}>
            <div className="form-group">
              <input
                type="text"
                placeholder="Username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                disabled={isSubmitting}
                required
              />
            </div>
            <div className="form-group">
              <input
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                disabled={isSubmitting}
                required
                minLength={6}
              />
            </div>
            <button 
              type="submit" 
              className="login-button"
              disabled={isSubmitting}
            >
              {isSubmitting ? 'Logging in...' : 'Login'}
            </button>
          </form>
        </div>
      </header>
    </div>
  );
}

export default App;
