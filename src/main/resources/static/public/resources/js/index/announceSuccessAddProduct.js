var closeBt = document.querySelector('.closeBtDiv')
let overlay = document.querySelector('.overlay');


closeBt.addEventListener('click' , function() { 
   overlay.classList.toggle('show')
})

var addToBagBt = document.querySelectorAll('.addToBagBt')
var tempPName = document.querySelectorAll('.tempPName')
var tempPImage = document.querySelectorAll('.tempPImage')
var tempPPrice = document.querySelectorAll('.tempPPrice')
var tempPDes = document.querySelectorAll('.tempPDes')

for (let i = 0; i < addToBagBt.length; i++)
	addToBagBt[i].addEventListener('click', function() {
	
	 overlay.classList.toggle('show')
	 
	 // truyen data vao form thong bao add thanh cong
	 document.querySelector('.p-name').innerHTML = tempPName[i].innerHTML
	 document.querySelector('.p-price').innerHTML = tempPPrice[i].innerHTML
	 document.querySelector('.p-des').innerHTML = tempPDes[i].innerHTML
	 document.querySelector('.p-image').src = tempPImage[i].src;
		
});
