import { useState } from 'react';

export default function MovieTracker() {
  const [movies, setMovies] = useState([
    { id: 1, title: 'Inception', review: 'Дуже заплутаний, але цікавий фільм.' }
  ]);
  const [newTitle, setNewTitle] = useState('');
  const [newReview, setNewReview] = useState('');

  const addMovie = (e) => {
    e.preventDefault();
    if (!newTitle.trim()) return;
    const newMovie = {
      id: Date.now(),
      title: newTitle,
      review: newReview
    };
    setMovies([...movies, newMovie]);
    setNewTitle('');
    setNewReview('');
  };

  return (
    <div className="card">
      <h2>Рівень 3: Мої Фільми та Рецензії</h2>
      <form onSubmit={addMovie} style={{ display: 'flex', flexDirection: 'column', gap: '10px', marginBottom: '20px' }}>
        <input 
          type="text" 
          placeholder="Назва фільму" 
          value={newTitle} 
          onChange={(e) => setNewTitle(e.target.value)} 
          required 
        />
        <textarea 
          placeholder="Ваша рецензія..." 
          value={newReview} 
          onChange={(e) => setNewReview(e.target.value)} 
          required 
        />
        <button type="submit">Додати фільм</button>
      </form>

      <div className="movie-list">
        {movies.map(movie => (
          <div key={movie.id} style={{ border: '1px solid #ccc', padding: '10px', margin: '5px 0' }}>
            <h4>{movie.title}</h4>
            <p><i>{movie.review}</i></p>
          </div>
        ))}
      </div>
    </div>
  );
}