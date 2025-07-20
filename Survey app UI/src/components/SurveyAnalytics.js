// src/components/SurveyAnalytics.js
import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';

const SurveyAnalytics = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [analytics, setAnalytics] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchAnalytics();
  }, [id]);

  const fetchAnalytics = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/api/surveys/${id}/analytics`);
      setAnalytics(response.data);
      setLoading(false);
    } catch (error) {
      console.error('Error fetching analytics:', error);
      setLoading(false);
    }
  };

  if (loading) {
    return <div className="loading">Loading analytics...</div>;
  }

  if (!analytics) {
    return <div className="error">Unable to load analytics.</div>;
  }

  return (
    <div className="survey-analytics">
      <div className="analytics-header">
        <button onClick={() => navigate('/dashboard')} className="btn btn-secondary">
          Back to Dashboard
        </button>
        <h1>Survey Analytics: {analytics.surveyTitle}</h1>
        <div className="total-responses">
          <strong>Total Responses: {analytics.totalResponses}</strong>
        </div>
      </div>

      <div className="analytics-content">
        {analytics.questions.map((question) => (
          <div key={question.questionId} className="question-analytics">
            <h3>{question.questionText}</h3>
            <div className="options-analytics">
              {question.options.map((option) => {
                const percentage = analytics.totalResponses > 0 
                  ? ((option.count / analytics.totalResponses) * 100).toFixed(1)
                  : 0;
                
                return (
                  <div key={option.optionId} className="option-analytics">
                    <div className="option-header">
                      <span className="option-text">{option.optionText}</span>
                      <span className="option-stats">
                        {option.count} responses ({percentage}%)
                      </span>
                    </div>
                    <div className="progress-bar">
                      <div 
                        className="progress-fill"
                        style={{ width: `${percentage}%` }}
                      ></div>
                    </div>
                  </div>
                );
              })}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default SurveyAnalytics;
