import { useState, useEffect, useContext } from 'react';
import axios from 'axios';
import { CartContext } from '../context/CartContext';
import { AuthContext } from '../context/AuthContext';

export default function Home() {
  const [products, setProducts] = useState([]);
  const [recs, setRecs] = useState([]);
  const { addToCart } = useContext(CartContext);
  const { user } = useContext(AuthContext);

  useEffect(() => {
    axios.get('http://localhost:3000/api/products').then(res => setProducts(res.data));
    
    if (user) {
      axios.get('http://localhost:3000/api/products/recommendations', {
        headers: { Authorization: `Bearer ${user.token}` }
      }).then(res => setRecs(res.data));
    }
  }, [user]);

  return (
    <div>
      <h2>Каталог товарів</h2>
      <div className="products-grid">
        {products.map(p => (
          <div key={p.id} className="card">
            <h3 className="card-title">{p.name}</h3>
            <p className="card-price">${p.price}</p>
            <button className="btn" onClick={() => addToCart(p)}>Додати в кошик</button>
          </div>
        ))}
      </div>

      {recs.length > 0 && (
        <div style={{ marginTop: '3rem' }}>
          <h2>Рекомендовано для вас</h2>
          <div className="products-grid">
            {recs.map(p => (
              <div key={p.id} className="card" style={{ border: '2px solid var(--primary)' }}>
                <h3 className="card-title">{p.name}</h3>
                <p className="card-price">${p.price}</p>
                <button className="btn" onClick={() => addToCart(p)}>Додати в кошик</button>
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
}