import { useState, useContext } from 'react';
import { AuthContext } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';

export default function Login() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const { login, register } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      await login(username, password);
      navigate('/');
    } catch (err) {
      alert("Невірний логін або пароль");
    }
  };

  const handleRegister = async () => {
    if (!username || !password) return alert("Заповніть поля");
    try {
      await register(username, password);
      alert("Успішно! Тепер ви можете увійти.");
    } catch (err) {
      alert("Користувач з таким іменем вже існує");
    }
  };

  return (
    <div className="auth-container">
      <h2 style={{ textAlign: 'center', marginBottom: '2rem' }}>Вхід / Реєстрація</h2>
      
      <form onSubmit={handleLogin}>
        <div className="form-group">
          <input 
            value={username} 
            onChange={e => setUsername(e.target.value)} 
            placeholder="Ваш логін" 
            required 
          />
          <input 
            type="password" 
            value={password} 
            onChange={e => setPassword(e.target.value)} 
            placeholder="Ваш пароль" 
            required 
          />
        </div>
        
        <div style={{ display: 'flex', gap: '1rem', flexDirection: 'column' }}>
          <button type="submit" className="btn">Увійти</button>
          <button type="button" className="btn btn-outline" onClick={handleRegister}>
            Створити новий акаунт
          </button>
        </div>
      </form>
    </div>
  );
}