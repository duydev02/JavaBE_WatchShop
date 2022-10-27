$(document).ready(function() {
	$(".flaticon-heart").click(function(event) {
		if ($(event.target).hasClass('active')) {
			$(event.target).removeClass('active');
		} else {
			$(event.target).addClass('active');
		}
	});
});