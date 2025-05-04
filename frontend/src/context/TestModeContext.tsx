import React, { createContext, useContext, useState, ReactNode } from 'react';

type TestMode = 'service' | 'json';


interface TestModeContextType {
  testMode: TestMode;
  setTestMode: (mode: TestMode) => void;
}

const TestModeContext = createContext<TestModeContextType | undefined>(undefined);

export const TestModeProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const [testMode, setTestMode] = useState<TestMode>('json');

  return (
    <TestModeContext.Provider value={{ testMode, setTestMode }}>
      {children}
    </TestModeContext.Provider>
  );
};

export const useTestMode = () => {
  const context = useContext(TestModeContext);
  if (context === undefined) {
    throw new Error('useTestMode must be used within a TestModeProvider');
  }
  return context;
};
