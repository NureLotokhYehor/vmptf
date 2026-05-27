import { Link } from 'react-router-dom';
import { useContext } from 'react';
import { AuthContext } from '../context/AuthContext';
import { CartContext } from '../context/CartContext';
import '../App.css'; 

export default function Navbar() {
  const { user, logout } = useContext(AuthContext);
  const { cart } = useContext(CartContext);

  return (
    <nav className="navbar">
      <h2 style={{ margin: 0, color: 'var(--primary)' }}>Магазин</h2>
      <div className="nav-links">
        <Link to="/">Каталог</Link>
        <Link to="/cart">Кошик <b>({cart.length})</b></Link>
        {user ? (
          <>
            <Link to="/orders">Мої замовлення</Link>
            <button className="btn btn-outline" onClick={logout}>Вийти ({user.username})</button>
          </>
        ) : (
          <Link to="/login" className="btn">Увійти</Link>
        )}
      </div>
    </nav>
  );
}