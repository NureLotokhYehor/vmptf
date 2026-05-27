const jwt = require('jsonwebtoken');

const SECRET_KEY = "super_secret_key_for_lab";

const authenticate = (req, res, next) => {
  const token = req.headers.authorization?.split(" ")[1];
  if (!token) return res.status(401).json({ message: "Немає доступу" });
  
  try {
    const decoded = jwt.verify(token, SECRET_KEY);
    req.user = decoded;
    next();
  } catch (err) {
    res.status(401).json({ message: "Недійсний токен" });
  }
};

module.exports = { authenticate, SECRET_KEY };