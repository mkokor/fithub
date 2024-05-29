import React from 'react';
import classNames from 'classnames';

const InputField = ({ type, name, placeholder, value, onChange, hasError }) => {
  return (
    <div className="input-field">
      <input 
        type={type}
        name={name}
        placeholder={placeholder}
        value={value}
        onChange={onChange}
        className={classNames({ error: hasError && !value })}
      />
      {hasError && !value && <p className="error-message">{`${placeholder} is required`}</p>}
    </div>
  );
};

export default InputField;
