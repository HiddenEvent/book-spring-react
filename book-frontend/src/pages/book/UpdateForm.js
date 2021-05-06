import React, { useEffect, useState } from 'react';
import { Form, Button } from 'react-bootstrap';

//페이지 이동시 props 필요함
const UpdateForm = (props) => {
  const [book, setBook] = useState({
    title: '',
    author: '',
  });

  const changeValue = (e) => {
    setBook({
      ...book,
      [e.target.name]: e.target.value,
    });
  };
  const id = props.match.params.id;

  useEffect(() => {
    fetch('http://localhost:8085/book/' + id)
      .then((res) => res.json())
      .then((res) => {
        setBook(res);
      });
  }, []);

  const submitBook = (e) => {
    e.preventDefault(); //submit이 action을 안타고 자기 할일을 그만함.
    fetch('http://localhost:8085/book/' + id, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json; charset=utf-8',
      },
      body: JSON.stringify(book),
    })
      .then((res) => {
        console.log(1, res);
        if (res.status === 200) {
          return res.json();
        } else {
          return null;
        }
      })
      .then((res) => {
        console.log(res);
        if (res !== null) {
          props.history.push('/book/' + id);
        } else {
          alert('책 수정에 실패하였습니다.');
        }
      })
      //catch 는 then에서 오류가 나야 실행된다.
      .catch((error) => {
        console.log('에러');
        console.log(error);
      });
  };

  return (
    <Form onSubmit={submitBook}>
      <Form.Group controlId="formBasicEmail">
        <Form.Label>Title</Form.Label>
        <Form.Control type="text" placeholder="Enter Title" onChange={changeValue} name="title" value={book.title} />
      </Form.Group>
      <Form.Group controlId="formBasicEmail">
        <Form.Label>Author</Form.Label>
        <Form.Control type="text" placeholder="Enter Author" onChange={changeValue} name="author" value={book.author} />
      </Form.Group>

      <Button variant="primary" type="submit">
        Submit
      </Button>
    </Form>
  );
};

export default UpdateForm;
