import React from 'react';
import { useParams, Navigate } from 'react-router-dom';
import BoardList from './BoardList';

const BOARD_TEMPLATES = {
  '1': {
    title: '공지사항',
    description: '공지사항 게시판입니다.',
  },
  '2': {
    title: 'Q&A',
    description: '질문과 답변 게시판입니다.',
  },
  '3': {
    title: 'FAQ',
    description: '자주 묻는 질문 게시판입니다.',
  },
};

const BoardTemplate: React.FC = () => {
  const { boardId } = useParams<{ boardId: string }>();

  if (!boardId || !BOARD_TEMPLATES[boardId as keyof typeof BOARD_TEMPLATES]) {
    return <Navigate to="/" replace />;
  }

  const template = BOARD_TEMPLATES[boardId as keyof typeof BOARD_TEMPLATES];

  return (
    <BoardList
      title={template.title}
      description={template.description}
      boardId={boardId}
    />
  );
};

export default BoardTemplate;
