import { useState, useEffect, useContext } from 'react';
import axios from 'axios';
import { AuthContext } from '../context/AuthContext';

export default function Orders() {
  const [orders, setOrders] = useState([]);
  const { user } = useContext(AuthContext);

  useEffect(() => {
    if (user) {
      axios.get('http://localhost:3000/api/orders', {
        headers: { Authorization: `Bearer ${user.token}` }
      }).then(res => setOrders(res.data));
    }
  }, [user]);

  return (
    <div>
      <h2>Мої замовлення та статуси</h2>
      {orders.map(o => (
        <div key={o.id} style={{ border: '1px solid black', margin: '10px', padding: '10px' }}>
          <p>Замовлення №{o.id}</p>
          <p>Сума: ${o.total}</p>
          <p>Статус: <b>{o.status}</b></p>
        </div>
      ))}
    </div>
  );
}