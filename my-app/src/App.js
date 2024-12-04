import { BrowserRouter as Router, Routes, Route, Navigate, useNavigate } from 'react-router-dom';
import './App.css';
import { useAuth } from './context/AuthContext';
import Register from './components/Register';
import Login from './components/Login';
import Posts from './components/Posts';
import NavBar from './components/NavBar';

const Home = () => {
  const { user } = useAuth();
  
  return (
    <div className="App-header">
      <div className="user-profile">
        <h1>Welcome, {user?.username}!</h1>
        <p>Email: {user?.email}</p>
      </div>
    </div>
  );
};

function App() {
  const { user, loading } = useAuth();

  if (loading) {
    return (
      <div className="App">
        <header className="App-header">
          <div className="loading-spinner">Loading...</div>
        </header>
      </div>
    );
  }

  return (
    <Router>
      <div className="App">
        <NavBar />
        <div className="content">
          <Routes>
            <Route path="/login" element={!user ? <Login /> : <Navigate to="/" />} />
            <Route path="/register" element={!user ? <Register /> : <Navigate to="/" />} />
            <Route path="/posts" element={user ? <Posts /> : <Navigate to="/login" />} />
            <Route path="/" element={user ? <Home /> : <Navigate to="/login" />} />
          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;
