import { useState } from 'react';

export default function JsonParser() {
  const [jsonInput, setJsonInput] = useState('{"title": "Matrix", "rating": 5}');
  const [parsedResult, setParsedResult] = useState(null);
  const [error, setError] = useState('');

  const handleParse = () => {
    try {
      const obj = JSON.parse(jsonInput);
      setParsedResult(obj);
      setError('');
    } catch (err) {
      setError('Невалідний JSON-рядок');
      setParsedResult(null);
    }
  };

  return (
    <div className="card">
      <h2>Рівень 2: Парсер JSON</h2>
      <textarea 
        rows="4" 
        value={jsonInput} 
        onChange={(e) => setJsonInput(e.target.value)}
        placeholder="Введіть JSON сюди"
        style={{ width: '100%', marginBottom: '10px' }}
      />
      <button onClick={handleParse}>Розпарсити</button>
      
      {error && <p style={{ color: 'red' }}>{error}</p>}
      
      {parsedResult && (
        <div style={{ textAlign: 'left', marginTop: '10px' }}>
          <h4>Результат (деревоподібна структура):</h4>
          <pre style={{ background: '#f4f4f4', padding: '10px', borderRadius: '5px' }}>
            {JSON.stringify(parsedResult, null, 2)}
          </pre>
        </div>
      )}
    </div>
  );
}