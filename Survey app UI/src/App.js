// src/App.js
import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import Login from './components/Login';
import Register from './components/Register';
import Dashboard from './components/Dashboard';
import CreateSurvey from './components/CreateSurvey';
import SurveyDetails from './components/SurveyDetails';
import TakeSurvey from './components/TakeSurvey';
import SurveyAnalytics from './components/SurveyAnalytics';
import ProtectedRoute from './components/ProtectedRoute';
import './App.css';

function App() {
  return (
    <AuthProvider>
      <Router>
        <div className="App">
          <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/dashboard" element={
              <ProtectedRoute>
                <Dashboard />
              </ProtectedRoute>
            } />
            <Route path="/create-survey" element={
              <ProtectedRoute>
                <CreateSurvey />
              </ProtectedRoute>
            } />
            <Route path="/survey/:id" element={
              <ProtectedRoute>
                <SurveyDetails />
              </ProtectedRoute>
            } />
            <Route path="/take-survey/:id" element={
              <ProtectedRoute>
                <TakeSurvey />
              </ProtectedRoute>
            } />
            <Route path="/survey/:id/analytics" element={
              <ProtectedRoute>
                <SurveyAnalytics />
              </ProtectedRoute>
            } />
            <Route path="/" element={<Navigate to="/dashboard" />} />
          </Routes>
        </div>
      </Router>
    </AuthProvider>
  );
}

export default App;
