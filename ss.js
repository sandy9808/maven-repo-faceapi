const formdata = new FormData();
formdata.append("base64_string", );

const requestOptions = {
  method: "POST",
  body: formdata,
  redirect: "follow"
};

fetch("http://localhost:9000/detect", requestOptions)
  .then((response) => response.text())
  .then((result) => console.log(result))
  .catch((error) => console.error(error));