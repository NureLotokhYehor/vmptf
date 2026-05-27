const sqlite3 = require('sqlite3').verbose();
const { open } = require('sqlite');

let dbInstance = null;

async function initDB() {
  if (dbInstance) return dbInstance;
  
  dbInstance = await open({
    filename: './database.sqlite',
    driver: sqlite3.Database
  });

  await dbInstance.exec(`
    CREATE TABLE IF NOT EXISTS users (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      username TEXT UNIQUE,
      password TEXT
    );
    CREATE TABLE IF NOT EXISTS products (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      name TEXT,
      price REAL,
      category TEXT
    );
    CREATE TABLE IF NOT EXISTS orders (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      user_id INTEGER,
      total REAL,
      status TEXT,
      date TEXT
    );
    CREATE TABLE IF NOT EXISTS order_items (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      order_id INTEGER,
      product_id INTEGER,
      price REAL
    );
  `);

  const productCount = await dbInstance.get('SELECT COUNT(*) as count FROM products');
  if (productCount.count === 0) {
    await dbInstance.exec(`
      INSERT INTO products (name, price, category) VALUES
      ('Ноутбук Dell XPS', 1500, 'electronics'),
      ('Смартфон iPhone 14', 999, 'electronics'),
      ('Книга "Clean Code"', 30, 'books'),
      ('Навушники Sony WH-1000XM4', 350, 'electronics'),
      ('Книга "Design Patterns"', 40, 'books');
    `);
  }
  
  return dbInstance;
}

async function getDB() {
  if (!dbInstance) {
    return await initDB();
  }
  return dbInstance;
}

module.exports = { initDB, getDB };