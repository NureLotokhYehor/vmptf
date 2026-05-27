import { useEffect, useRef } from 'react';

export default function PersonalData() {
  const containerRef = useRef(null);

  useEffect(() => {
    fetch('/me.json')
      .then(response => response.text())
      .then(text => {
        const data = JSON.parse(text); 
        
        if (containerRef.current && containerRef.current.children.length === 0) {
          const nameEl = document.createElement('h3');
          nameEl.textContent = `Ім'я: ${data.name}`;
          
          const groupEl = document.createElement('p');
          groupEl.textContent = `Група: ${data.group}`;
          
          const ageEl = document.createElement('p');
          ageEl.textContent = `Вік: ${data.age}`;
          
          const hobbiesEl = document.createElement('p');
          hobbiesEl.textContent = `Хобі: ${data.hobbies.join(', ')}`;

          containerRef.current.appendChild(nameEl);
          containerRef.current.appendChild(groupEl);
          containerRef.current.appendChild(ageEl);
          containerRef.current.appendChild(hobbiesEl);
        }
      })
      .catch(() => console.log("ERROR")); 
  }, []);

  return (
    <div className="card">
      <h2>Рівень 1: Дані про себе</h2>
      <div ref={containerRef} className="personal-info" style={{ textAlign: 'left' }}></div>
    </div>
  );
}