const express = require('express');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const { getDB } = require('../db');
const { SECRET_KEY } = require('../middleware/auth');

const router = express.Router();

router.post('/register', async (req, res) => {
  const { username, password } = req.body;
  const db = await getDB();
  
  try {
    const hashedPassword = await bcrypt.hash(password, 10);
    await db.run('INSERT INTO users (username, password) VALUES (?, ?)', [username, hashedPassword]);
    res.status(201).json({ message: "Реєстрація успішна" });
  } catch (err) {
    if (err.message.includes("UNIQUE constraint failed")) {
      return res.status(400).json({ message: "Користувач існує" });
    }
    res.status(500).json({ message: "Помилка сервера" });
  }
});

router.post('/login', async (req, res) => {
  const { username, password } = req.body;
  const db = await getDB();
  
  const user = await db.get('SELECT * FROM users WHERE username = ?', [username]);
  
  if (!user || !(await bcrypt.compare(password, user.password))) {
    return res.status(400).json({ message: "Невірні дані" });
  }
  
  const token = jwt.sign({ id: user.id, username: user.username }, SECRET_KEY, { expiresIn: '1h' });
  res.json({ token, username: user.username });
});

module.exports = router;