const express = require('express');
const { getDB } = require('../db');
const { authenticate } = require('../middleware/auth');

const router = express.Router();

router.get('/', async (req, res) => {
  const db = await getDB();
  const products = await db.all('SELECT * FROM products');
  res.json(products);
});

router.get('/recommendations', authenticate, async (req, res) => {
  const db = await getDB();
  
  const query = `
    SELECT * FROM products 
    WHERE category IN (
      SELECT DISTINCT p.category 
      FROM order_items oi 
      JOIN orders o ON oi.order_id = o.id 
      JOIN products p ON oi.product_id = p.id 
      WHERE o.user_id = ?
    )
    AND id NOT IN (
      SELECT DISTINCT product_id 
      FROM order_items oi 
      JOIN orders o ON oi.order_id = o.id 
      WHERE o.user_id = ?
    )
  `;
  
  const recommended = await db.all(query, [req.user.id, req.user.id]);
  res.json(recommended);
});

module.exports = router;