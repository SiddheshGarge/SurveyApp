// src/components/SurveyDetails.js
import React, { useState, useEffect } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import axios from 'axios';

const SurveyDetails = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [survey, setSurvey] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchSurvey();
  }, [id]);

  const fetchSurvey = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/api/surveys/${id}`);
      setSurvey(response.data);
      setLoading(false);
    } catch (error) {
      console.error('Error fetching survey:', error);
      setLoading(false);
    }
  };

  if (loading) {
    return <div className="loading">Loading survey details...</div>;
  }

  if (!survey) {
    return <div className="error">Survey not found.</div>;
  }

  return (
    <div className="survey-details">
      <div className="survey-details-header">
        <button onClick={() => navigate('/dashboard')} className="btn btn-secondary">
          Back to Dashboard
        </button>
        <h1>{survey.title}</h1>
        <p className="survey-description">{survey.description}</p>
        <div className="survey-meta">
          <p><strong>Created by:</strong> {survey.creatorUsername}</p>
          <p><strong>Created on:</strong> {new Date(survey.createdAt).toLocaleDateString()}</p>
          <p><strong>Questions:</strong> {survey.questions?.length || 0}</p>
        </div>
      </div>

      <div className="survey-questions-preview">
        <h2>Questions Preview</h2>
        {survey.questions && survey.questions.length > 0 ? (
          survey.questions.map((question, index) => (
            <div key={question.id} className="question-preview">
              <h3>Question {index + 1}: {question.questionText}</h3>
              <ul className="options-preview">
                {question.options?.map((option, optIndex) => (
                  <li key={option.id}>{String.fromCharCode(65 + optIndex)}. {option.optionText}</li>
                ))}
              </ul>
            </div>
          ))
        ) : (
          <p>No questions found for this survey.</p>
        )}
      </div>

      <div className="survey-actions">
        <Link to={`/take-survey/${survey.id}`} className="btn btn-primary">
          Take This Survey
        </Link>
        <Link to={`/survey/${survey.id}/analytics`} className="btn btn-info">
          View Analytics
        </Link>
      </div>
    </div>
  );
};

export default SurveyDetails;
