// src/components/TakeSurvey.js
import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';

const TakeSurvey = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [survey, setSurvey] = useState(null);
  const [answers, setAnswers] = useState({});
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);

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

  const handleAnswerChange = (questionId, optionId) => {
    setAnswers(prev => ({
      ...prev,
      [questionId]: optionId
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSubmitting(true);

    try {
      const answersArray = Object.entries(answers).map(([questionId, selectedOptionId]) => ({
        questionId: parseInt(questionId),
        selectedOptionId: parseInt(selectedOptionId)
      }));

      await axios.post(`http://localhost:8080/api/surveys/${id}/respond`, {
        answers: answersArray
      });

      alert('Survey submitted successfully!');
      navigate('/dashboard');
    } catch (error) {
      console.error('Error submitting survey:', error);
      alert('Failed to submit survey. Please try again.');
    } finally {
      setSubmitting(false);
    }
  };

  if (loading) {
    return <div className="loading">Loading survey...</div>;
  }

  if (!survey) {
    return <div className="error">Survey not found.</div>;
  }

  const allQuestionsAnswered = survey.questions.every(q => answers[q.id]);

  return (
    <div className="take-survey">
      <div className="survey-header">
        <button onClick={() => navigate('/dashboard')} className="btn btn-secondary">
          Back to Dashboard
        </button>
        <h1>{survey.title}</h1>
        <p>{survey.description}</p>
        <p className="survey-meta">Created by: {survey.creatorUsername}</p>
      </div>

      <form onSubmit={handleSubmit} className="survey-form">
        {survey.questions.map((question) => (
          <div key={question.id} className="question-section">
            <h3>{question.questionText}</h3>
            <div className="options">
              {question.options.map((option) => (
                <label key={option.id} className="option-label">
                  <input
                    type="radio"
                    name={`question-${question.id}`}
                    value={option.id}
                    onChange={() => handleAnswerChange(question.id, option.id)}
                    required
                  />
                  <span>{option.optionText}</span>
                </label>
              ))}
            </div>
          </div>
        ))}

        <div className="form-actions">
          <button
            type="submit"
            disabled={!allQuestionsAnswered || submitting}
            className="btn btn-primary btn-lg"
          >
            {submitting ? 'Submitting...' : 'Submit Survey'}
          </button>
        </div>
      </form>
    </div>
  );
};

export default TakeSurvey;
