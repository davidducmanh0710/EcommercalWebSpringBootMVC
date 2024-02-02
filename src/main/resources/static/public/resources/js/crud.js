$(document).ready(function(){
	// Activate tooltip
	$('[data-toggle="tooltip"]').tooltip();
	
	// Select/Deselect checkboxes
	var checkbox = $('table tbody input[type="checkbox"]');
	$("#selectAll").click(function(){
		if(this.checked){
			checkbox.each(function(){
				this.checked = true;                        
			});
		} else{
			checkbox.each(function(){
				this.checked = false;                        
			});
		} 
	});
	checkbox.click(function(){
		if(!this.checked){
			$("#selectAll").prop("checked", false);
		}
	});
});


 (function($) {
            "use strict";

            jQuery('#vmap').vectorMap({
                map: 'world_en',
                backgroundColor: null,
                color: '#ffffff',
                hoverOpacity: 0.7,
                selectedColor: '#1de9b6',
                enableZoom: true,
                showTooltip: true,
                values: sample_data,
                scaleColors: ['#1de9b6', '#03a9f5'],
                normalizeFunction: 'polynomial'
            });
        })(jQuery);
        
// truyền dữ liệu từ nút edit sang form hiện ra và trả id về đường POST /editCategories
        
var editCategoryModal = document.querySelectorAll(".editCategoryModaIcon");
    
for (let i = 0; i < editCategoryModal.length; i++)
	editCategoryModal[i].addEventListener('click', function() {

		var categoryId = document.getElementsByClassName("tempCategoryId")[i].value; // Thay đổi giá trị theo yêu cầu của bạn
		var categoryIdHidden = document.getElementById('category_id_hidden');
		categoryIdHidden.value = categoryId;
		
		var categoryName = document.getElementsByClassName("tempCategoryName")[i].value; // Thay đổi giá trị theo yêu cầu của bạn
		var editInputCategoryAutoFill = document.querySelector('.editInputCategoryAutoFill');
		editInputCategoryAutoFill.value = categoryName
		
});















