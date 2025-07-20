// src/components/Dashboard.js
import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import axios from 'axios';

const Dashboard = () => {
  const [surveys, setSurveys] = useState([]);
  const [mySurveys, setMySurveys] = useState([]);
  const [loading, setLoading] = useState(true);
  const { logout, currentUser } = useAuth();

  useEffect(() => {
    fetchSurveys();
    fetchMySurveys();
  }, []);

  const fetchSurveys = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/surveys');
      setSurveys(response.data);
    } catch (error) {
      console.error('Error fetching surveys:', error);
    }
  };

  const fetchMySurveys = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/surveys/my-surveys');
      setMySurveys(response.data);
      setLoading(false);
    } catch (error) {
      console.error('Error fetching my surveys:', error);
      setLoading(false);
    }
  };

  if (loading) {
    return <div className="loading">Loading...</div>;
  }

  return (
    <div className="dashboard">
      <header className="dashboard-header">
        <h1>Survey Dashboard</h1>
        <div className="header-actions">
          <span>Welcome, {currentUser?.username}!</span>
          <Link to="/create-survey" className="btn btn-primary">Create Survey</Link>
          <button onClick={logout} className="btn btn-secondary">Logout</button>
        </div>
      </header>

      <div className="dashboard-content">
        <div className="survey-section">
          <h2>My Surveys</h2>
          {mySurveys.length === 0 ? (
            <p>You haven't created any surveys yet. <Link to="/create-survey">Create one now!</Link></p>
          ) : (
            <div className="survey-grid">
              {mySurveys.map(survey => (
                <div key={survey.id} className="survey-card">
                  <h3>{survey.title}</h3>
                  <p>{survey.description}</p>
                  <div className="survey-actions">
                    <Link to={`/survey/${survey.id}/analytics`} className="btn btn-info">
                      View Analytics
                    </Link>
                    <Link to={`/survey/${survey.id}`} className="btn btn-secondary">
                      View Details
                    </Link>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>

        <div className="survey-section">
          <h2>Available Surveys</h2>
          {surveys.length === 0 ? (
            <p>No surveys available at the moment.</p>
          ) : (
            <div className="survey-grid">
              {surveys.map(survey => (
                <div key={survey.id} className="survey-card">
                  <h3>{survey.title}</h3>
                  <p>{survey.description}</p>
                  <p className="survey-creator">By: {survey.creatorUsername}</p>
                  <Link to={`/take-survey/${survey.id}`} className="btn btn-primary">
                    Take Survey
                  </Link>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
