const express = require('express');
const cors = require('cors');
const compression = require('compression');
const { initDB } = require('./db');

const authRoutes = require('./routes/authRoutes');
const productRoutes = require('./routes/productRoutes');
const orderRoutes = require('./routes/orderRoutes');

const app = express();

app.use(compression());
app.use(cors());
app.use(express.json());

app.use('/api/auth', authRoutes);
app.use('/api/products', productRoutes);
app.use('/api/orders', orderRoutes);

initDB().then(() => {
  app.listen(3000, () => console.log('Server is running on port 3000'));
}).catch(err => {
  console.error("Failed to initialize database", err);
});