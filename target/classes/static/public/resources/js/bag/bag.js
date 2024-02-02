function addToBag(id, name, price) {
	// Gửi yêu cầu POST đến một endpoint trên server
	fetch('/api/api2/addToBag',
		{
			method: 'post',
			body: JSON.stringify({
				"id": id,
				"name": name,
				"price": price
			}),
			headers: {
				'Content-Type': 'application/json'
			}
		}).then(function(res) {
			return res.json();  //chuyển đối tượng phản hồi thành đối tượng JSON bằng cách sử dụng phương thức json() của đối tượng phản hồi.
		}).then(data => console.log(data))
		.catch(error => console.error("Error:", error));
}