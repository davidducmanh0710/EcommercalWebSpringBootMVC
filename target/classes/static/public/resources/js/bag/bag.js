
var items = document.querySelectorAll(".count-product-bag");
var prices = document.querySelectorAll(".total-cost-value")

function addToBag(id, name, price) {
	// Gửi yêu cầu POST đến một endpoint trên server
	fetch('/api/addToBag',
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
		}).then(function(data) // data là lấy từ body ở trên nè
		{
			console.log(data)
			for (let item of items)
				item.innerText = data.totalQuantity
			
			
			for (let price of prices)
				price.innerText = data.totalPrice
		})
		.catch(error => console.error("Error:", error));
}

function updateBag(id, obj) {
	obj.disabled = true;

	fetch(`/api/updateToBag/${id}`, {
		method: "PUT",
		body: JSON.stringify({
			"quantity": obj.value
		}),
		headers: {
			'Content-Type': 'application/json'
		}
	}).then(function(res) {
		return res.json();
	}).then(function(data) {
		obj.disabled = false;
		console.log(data)
		for (let item of items)
			item.innerText = data.totalQuantity
		
		for (let price of prices)
				price.innerText = data.totalPrice

	}).catch(error => console.error("Error:", error));
}



function deleteBag(id, obj) {
	obj.disbaled = true;

	if (confirm("Are you sure to delete this item ? ") == true) {
		fetch(`/api/deleteToBag/${id}`, {
			method: "DELETE",
		}).then(function(res) {
			return res.json();
		}).then(function(data) {
			obj.disbaled = false;

			console.log(data)
			for (let item of items)
				item.innerText = data.totalQuantity
				
			for (let price of prices)
				price.innerText = data.totalPrice
				
			var bagItemX = document.querySelector(`#bagItem${id}`)
			bagItemX.style.display = "none";
			
			if(data.totalQuantity <= 0){
				var paymentBt_div = document.querySelector('.paymentBt-div')
				paymentBt_div.style.display = "none"
			}
			
		}).catch(error => console.error("Error:", error));
	}
}













