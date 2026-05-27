import { useState, useEffect } from 'react';

export default function PublicReviews() {
  const [reviews, setReviews] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetch('https://jsonplaceholder.typicode.com/comments?_limit=3')
      .then(response => response.json())
      .then(json => {
        setReviews(json);
        setLoading(false);
      })
      .catch(() => {
        setLoading(false);
      })
      .finally(() => console.log('Запит до API завершено'));
  }, []);

  return (
    <div className="card">
      <h2>Рівень 4: Публічні рецензії від критиків (API)</h2>
      {loading ? <p>Завантаження</p> : (
        <ul style={{ textAlign: 'left' }}>
          {reviews.map(review => (
            <li key={review.id} style={{ marginBottom: '15px' }}>
              <strong>{review.email}</strong> пише:
              <br/>
              "{review.body}"
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}