import React from 'react';
import '../../assets/css/m2/common.css';

interface PrivacyPopupProps {
  isOpen: boolean;
  onClose: () => void;
  onAccept?: () => void;
}

const PrivacyPopup: React.FC<PrivacyPopupProps> = ({ isOpen, onClose, onAccept }) => {
  if (!isOpen) return null;

  const handleAccept = () => {
    if (onAccept) onAccept();
    onClose();
  };

  return (
    <div className="popupArea on">
      <div className="dimmed" onClick={onClose}></div>
      <div className="popup hasReaction">
        <div className="popupContent">
          <div className="popupMsg">
            <h3>개인정보 처리방침</h3>
            <div className="privacy-content">
              {/* Add your privacy policy content here */}
              <p>
                1. 개인정보의 처리 목적<br />
                회사는 다음의 목적을 위하여 개인정보를 처리하고 있으며, 다음의 목적 이외의 용도로는 이용하지 않습니다.
              </p>
              {/* Add more content as needed */}
            </div>
          </div>
        </div>
        <div className="popupReaction">
          <button className="negative" onClick={onClose}>취소</button>
          <button className="positive" onClick={handleAccept}>동의</button>
        </div>
      </div>
    </div>
  );
};

export default PrivacyPopup;
