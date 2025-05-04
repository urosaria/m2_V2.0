jQuery.j || (function($, window, document, undefined){
	$.j= {
		version: '0.1.0',

		// Options defaults
		def: {
		}
	}

	$.fn.j= function(options){
		var
			opt=$.extend({}, $.j.def, options),
			_inputAtcObj=$('input.focusAct')

		/* binding **************************************************************************************************/
		_inputAtcObj.bind('focus blur',_inputAtc);

		/* binding function **************************************************************************************************/
		/* _inputAtc */
		function _inputAtc (event) {			console.log('input')
			var _this = $(this),
				_p = _this.parent('div'),
				_v = _this.val();
			if (event.type == 'focus') {
				if (!_p.hasClass('focus')) {
					_p.addClass('focus');
				}
			} else if (event.type == 'blur') {
				if (_v === '') {
					_p.removeClass('focus').addClass('showMsg');
				} else {
					_p.removeClass('showMsg').addClass('inputted');
				}
			}
			return
		}
	}

	$(document).ready(function() {

    $("input[type=number]").on("click", function(){
      $(this).select();
    });

		$('body').j({
			// navFnH:30,
			// navW:220
		});
		$('body').keydown(function(e) {
			if (e.keyCode == 116) {
				document.location.reload();
				return false;
			}
		});


	});

	$(document).scroll(function() {
	});

})(jQuery, window, document);
