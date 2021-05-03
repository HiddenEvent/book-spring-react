import React, { useEffect, useState } from 'react';
import BookItem from '../../components/BookItem';

const Home = () => {
  const[books, setBooks] = useState([]);

  // 함수 실행시 최초 한번 실행되는 것
  useEffect(() => {
    fetch("http://localhost:8085/book")
    .then(res=> res.json())
    .then(res=>{
      console.log(1, res)
    }); // 비동기 함수
  }, [books])


    return (
        <div>
          <BookItem/>
        </div>
    );
};

export default Home;