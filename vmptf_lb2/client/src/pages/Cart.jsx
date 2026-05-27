import { useContext } from 'react';
import axios from 'axios';
import { CartContext } from '../context/CartContext';
import { AuthContext } from '../context/AuthContext';

export default function Cart() {
  const { cart, clearCart } = useContext(CartContext);
  const { user } = useContext(AuthContext);

  const total = cart.reduce((sum, item) => sum + item.price, 0);

  const handleCheckout = async () => {
    if (!user) return alert("Будь ласка, авторизуйтесь");
    try {
      await axios.post('http://localhost:3000/api/orders', {
        items: cart,
        total
      }, {
        headers: { Authorization: `Bearer ${user.token}` }
      });
      alert("Замовлення успішно оформлено!");
      clearCart();
    } catch (err) {
      alert("Помилка оформлення");
    }
  };

  return (
    <div>
      <h2>Кошик</h2>
      {cart.length === 0 ? <p>Кошик порожній</p> : (
        <ul>
          {cart.map((item, idx) => (
            <li key={idx}>{item.name} - ${item.price}</li>
          ))}
        </ul>
      )}
      <h3>Сума: ${total}</h3>
      <button onClick={handleCheckout} disabled={cart.length === 0}>
        Оформити замовлення
      </button>
    </div>
  );
}