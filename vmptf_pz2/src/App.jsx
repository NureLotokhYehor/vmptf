import './App.css'
import PersonalData from './components/PersonalData'
import JsonParser from './components/JsonParser'
import MovieTracker from './components/MovieTracker'
import PublicReviews from './components/PublicReviews'

function App() {
  return (
    <div className="container">
      <h1>Практичне заняття 2</h1>
      
      <div className="grid">
        <PersonalData />
        <JsonParser />
        <MovieTracker />
        <PublicReviews />
      </div>
    </div>
  )
}

export default App