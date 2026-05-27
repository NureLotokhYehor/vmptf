const express = require('express');
const { getDB } = require('../db');
const { authenticate } = require('../middleware/auth');

const router = express.Router();

router.post('/', authenticate, async (req, res) => {
  const { items, total } = req.body;
  const db = await getDB();
  
  try {
    const result = await db.run(
      'INSERT INTO orders (user_id, total, status, date) VALUES (?, ?, ?, ?)', 
      [req.user.id, total, 'В обробці', new Date().toISOString()]
    );
    const orderId = result.lastID;
    
    for (let item of items) {
      await db.run(
        'INSERT INTO order_items (order_id, product_id, price) VALUES (?, ?, ?)',
        [orderId, item.id, item.price]
      );
    }
    
    res.status(201).json({ id: orderId, total, status: 'В обробці' });
  } catch (err) {
    res.status(500).json({ message: "Помилка оформлення замовлення" });
  }
});

router.get('/', authenticate, async (req, res) => {
  const db = await getDB();
  const userOrders = await db.all('SELECT * FROM orders WHERE user_id = ? ORDER BY id DESC', [req.user.id]);
  res.json(userOrders);
});

module.exports = router;