import React, { useEffect, useState } from 'react';

const Detail = (props) => {
  const id = props.match.params.id;
  const [book, setBook] = useState({
    id: '',
    title: '',
    author: '',
  });
  useEffect(() => {
    fetch('http://localhost:8085/book/' + id)
      .then((res) => res.json())
      .then((res) => {
        setBook(res);
      });
  }, []);

  return (
    <div>
      <h1>책 상세보기</h1>
      <hr />
      <h3>{book.author}</h3>
      <h4>{book.title}</h4>
    </div>
  );
};

export default Detail;
